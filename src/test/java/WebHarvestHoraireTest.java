/*
 * #%L
 * Naonedbus-server
 * %%
 * Copyright (C) 2010 - 2013 Naonedbus
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
/**
 * <p>
 * Copyright (C) 2011 Romain Guefveneu
 * </p>
 * <p>
 * This file is part of naonedbus.
 * </p>
 * <p>
 * Naonedbus is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * </p>
 * <p>
 * Naonedbus is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * </p>
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 * </p>
 */
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.naonedbus.model.Horaire;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.definition.XmlNode;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.variables.NodeVariable;
import org.webharvest.runtime.variables.Variable;
import org.xml.sax.InputSource;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class WebHarvestHoraireTest
{

    private static final String typeHoraireTocken = "[\\S]:(.[^<]+)";
    private static final String typesHoraireContent = "</table>([\\S\\s]*?)<a";

    /**
     * @param args
     * @throws FileNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static void main(final String[] args)
        throws FileNotFoundException
    {
        final ScraperConfiguration config =
            new ScraperConfiguration("src/main/resources/webharvest/horaires.xml");

        final Scraper scraper = new Scraper(config,
                                            "C:/Temp2/");
        // prévoir le remplacement des paramètres
        scraper
                .addVariableToContext("url",
                                      "http://www.tan.fr/php/horaires/aff_arret.php?MyArr=BAST2&MyDat=06/07/2011&MyLig=88&MySe=1");
        scraper.setDebug(true);
        scraper.execute();
        scraper.stopExecution();

        // Récupération de la liste des terminus
        final Variable varsContent = (Variable) scraper.getContext().get("content");
        final Map<String, String> terminus =
            WebHarvestHoraireTest.terminusProcessor(varsContent.toString());

        // Récupération de la liste des horaires
        final Variable varsHoraires = (Variable) scraper.getContext().get("horaires");
        final Map<String, List<String>> horaires =
            WebHarvestHoraireTest.horairesProcessor(varsHoraires.toList());

        // Création des horaires, en les associant au bon terminus
        WebHarvestHoraireTest.buildHoraires(horaires,
                                            terminus);
    }

    private static void buildHoraires(final Map<String, List<String>> horaires,
                                      final Map<String, String> terminus)
    {
        final List<Horaire> beans = new ArrayList<Horaire>();

        final Set<Map.Entry<String, List<String>>> horairesEntries = horaires.entrySet();
        for (final Map.Entry<String, List<String>> horaireEntry : horairesEntries)
        {
            final String heure = horaireEntry.getKey().replaceAll("[A-Za-z]",
                                                                  "");

            // on supprime les horaires vides
            final List<String> minutes = horaireEntry.getValue();
            CollectionUtils.filter(minutes,
                                   new Predicate() {
                                       @Override
                                       public boolean evaluate(final Object object)
                                       {
                                           return object.toString().matches("[0-9]*[A-Za-z]*");
                                       }
                                   });
            for (final String minute : minutes)
            {
                // gestion des terminus alternatif
                String minuteClean = minute;
                String terminusAlt = StringUtils.EMPTY;
                if (minute.matches("[0-9]*[A-Za-z]+"))
                {
                    terminusAlt = minuteClean.substring(2,
                                                        3);
                    minuteClean = minuteClean.substring(0,
                                                        2);
                }

                final Calendar passage = Calendar.getInstance(Locale.FRANCE);
                passage.set(Calendar.HOUR_OF_DAY,
                            Integer.valueOf(heure));
                passage.set(Calendar.MINUTE,
                            Integer.valueOf(minuteClean));
                final Horaire horaire = new Horaire();
                horaire.setTimestamp(passage.getTimeInMillis());
                horaire.setTerminus(terminusAlt);
                beans.add(horaire);
            }
        }

        System.out.println(beans);
    }

    private static Map<String, String> terminusProcessor(final String content)
    {
        final Map<String, String> terminus = new HashMap<String, String>();

        final Pattern patternTerminusContent =
            Pattern.compile(WebHarvestHoraireTest.typesHoraireContent);
        final Matcher matcherTerminusContent = patternTerminusContent.matcher(content);
        if (matcherTerminusContent.find())
        {
            final String terminusContent = matcherTerminusContent.group(1);

            final Pattern patternTerminusTocken =
                Pattern.compile(WebHarvestHoraireTest.typeHoraireTocken,
                                Pattern.DOTALL
                                        | Pattern.CASE_INSENSITIVE);
            final Matcher matcherTerminusTocken = patternTerminusTocken.matcher(terminusContent);
            while (matcherTerminusTocken.find())
            {
                final String terminusTocken = matcherTerminusTocken.group();
                final String codeTerminus = terminusTocken.split(": ")[0];
                final String nomTerminus = terminusTocken.split(": ")[1];
                terminus.put(codeTerminus,
                             nomTerminus);
            }
        }

        System.out.println(terminus);
        return terminus;
    }
    /**
     * @param xmlLignes
     */
    private static Map<String, List<String>> horairesProcessor(final List<NodeVariable> xmlLignes)
    {
        final Map<String, List<String>> horaires = new HashMap<String, List<String>>();

        // Compter le nombre de TD dans la première ligne, pour savoir combien de fois il faut
        // boucler sur l'ensemble des TR
        final XmlNode ligneCount =
            XmlNode.getInstance(new InputSource(new StringReader(xmlLignes.get(0).toString())));
        final int nbTR = xmlLignes.size();
        final int nbTD = ligneCount.getElementList().size();
        for (int i = 0; i < nbTD; i++)
        {
            final XmlNode firstLigne =
                XmlNode
                        .getInstance(new InputSource(new StringReader(xmlLignes.get(0).toString())));
            final List<String> minutes = new ArrayList<String>();
            final XmlNode xmlHeure = (XmlNode) firstLigne.getElementList().get(i);
            horaires.put(xmlHeure.getText(),
                         minutes);
            for (int j = 1; j < nbTR; j++)
            {
                final XmlNode ligne =
                    XmlNode.getInstance(new InputSource(new StringReader(xmlLignes
                            .get(j)
                            .toString())));
                final XmlNode xmlMinute = (XmlNode) ligne.getElementList().get(i);
                minutes.add(xmlMinute.getText());
            }
        }

        System.out.println(horaires);
        return horaires;
    }

}
