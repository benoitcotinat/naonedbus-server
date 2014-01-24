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
import java.io.FileNotFoundException;
import java.util.List;

import net.naonedbus.model.TypeJour;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.variables.NodeVariable;
import org.webharvest.runtime.variables.Variable;

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

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class WebHarvestCalendrierTest
{

    /**
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(final String[] args)
        throws FileNotFoundException
    {
        final ScraperConfiguration config =
            new ScraperConfiguration("src/main/resources/webharvest/calendrier.xml");

        final Scraper scraper = new Scraper(config,
                                            "C:/Temp/");
        // scraper
        // .addVariableToContext("url",
        // "http://www.tan.fr/php/horaires/LI_recherche_ligne.php?txtdate=27%2F07%2F2011&amp;MyLig=&amp;service=HORQUO&amp;tokenewp=");
        scraper.setDebug(true);
        scraper.execute();

        final Variable vars = (Variable) scraper.getContext().get("content");

        WebHarvestCalendrierTest.joursProcessor(vars.toList());
    }

    public static void joursProcessor(final List<NodeVariable> xmlJours)
    {
        for (final NodeVariable xmlJour : xmlJours)
        {
            String dataJour = xmlJour.toString();
            dataJour = dataJour.replaceAll("\"",
                                           "");
            dataJour = dataJour.substring(dataJour.indexOf("(") + 1);

            final String[] datasJour = dataJour.split(",");

            final String typeJour = TypeJour.getBtCssClass(datasJour[0]).getLibelle();
            final String jour = datasJour[1];
            final String mois = datasJour[2];
            final String annee = datasJour[3];

            System.out.println(typeJour
                               + "-"
                               + jour
                               + "-"
                               + mois
                               + "-"
                               + annee);
        }
    }
}
