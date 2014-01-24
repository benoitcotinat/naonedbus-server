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
package net.naonedbus.scraper.builder;

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


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.naonedbus.model.Arret;
import net.naonedbus.model.Horaire;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

/**
 * Classe en charge de construire la liste de beans horaires à partir des infos récupérés du site
 * de la tan.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HorairesBuidler
{
    /**
     * Caractère espace insécable.
     */
    public static final String hourEmpty = "\u00A0";

    /**
     * Regex permettant de trouver une lettre.
     */
    private static final String hourTocken = "[A-Za-z]";

    /**
     * Regex permettant de vérifier la présence de contenu.
     */
    private static final String hasContent = "[0-9]*[A-Za-z]*";

    /**
     * Regex permettant de vérifier si l'horaire est associé à un terminus alternatif.
     */
    private static final String terminusAltContent = "[0-9]*[A-Za-z]+";

    /**
     * Construit la liste des beans Horaires.
     * @param baseDate Date demandée
     * @param horaires Liste des horaires récupérés
     * @param terminus Liste des terminus alternatifs
     * @return
     */
    public List<Horaire> build(final Arret arret,
                               final Calendar baseDate,
                               final Map<String, List<String>> horaires,
                               final Map<String, String> terminus)
    {
        final List<Horaire> beans = new ArrayList<Horaire>();

        // Suppression des heures vides.
        horaires.remove(HorairesBuidler.hourEmpty);

        // Gestion du premier horaire de la grille
        Boolean first = Boolean.TRUE;
        String firstHour = null;
        Integer firstHourVal = null;

        final Set<Map.Entry<String, List<String>>> horairesEntries = horaires.entrySet();
        for (final Map.Entry<String, List<String>> horaireEntry : horairesEntries)
        {
            // Suppression du caractère "h" des heures.
            final String heure = horaireEntry.getKey().replaceAll(HorairesBuidler.hourTocken,
                                                                  "");

            // Récupération du premier horaire de la grille, servant à délimiter les horaires du
            // lendemain
            if (first)
            {
                firstHour = heure;
                firstHourVal = Integer.valueOf(firstHour);
                first = Boolean.FALSE;
            }

            // on supprime les horaires vides
            final List<String> minutes = horaireEntry.getValue();
            CollectionUtils.filter(minutes,
                                   new Predicate() {
                                       @Override
                                       public boolean evaluate(final Object object)
                                       {
                                           return object
                                                   .toString()
                                                   .matches(HorairesBuidler.hasContent);
                                       }
                                   });
            for (final String minute : minutes)
            {
                // gestion des terminus alternatif
                String minuteClean = minute;
                String terminusAlt = StringUtils.EMPTY;
                if (minute.matches(HorairesBuidler.terminusAltContent))
                {
                    terminusAlt = minuteClean.substring(2,
                                                        3);
                    minuteClean = minuteClean.substring(0,
                                                        2);
                }

                final Integer heureVal = Integer.valueOf(heure);
                final Integer minuteVal = Integer.valueOf(minuteClean);
                final Calendar passage = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                              NaonedbusConstants.LOCALE);
                passage.setTimeInMillis(baseDate.getTimeInMillis());
                passage.set(Calendar.HOUR_OF_DAY,
                            heureVal);
                passage.set(Calendar.MINUTE,
                            minuteVal);

                // Gestion des horaires après 00:00 qui appartiennent au lendemain
                if (heureVal < firstHourVal)
                {
                    passage.add(Calendar.DAY_OF_MONTH,
                                1);
                }

                final Horaire horaire = new Horaire();
                horaire.setArret(arret);
                horaire.setTimestamp(passage.getTimeInMillis());
                horaire.setTerminus(terminus.get(terminusAlt));
                beans.add(horaire);
            }
        }

        return beans;
    }
}
