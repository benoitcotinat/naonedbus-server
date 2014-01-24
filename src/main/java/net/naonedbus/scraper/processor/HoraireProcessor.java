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
package net.naonedbus.scraper.processor;

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


import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.webharvest.definition.XmlNode;
import org.webharvest.runtime.variables.NodeVariable;
import org.xml.sax.InputSource;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HoraireProcessor
{
    /**
     * Récupère la liste des horaires.
     * @param xmlLignes
     * @return
     */
    public Map<String, List<String>> process(final List<NodeVariable> xmlLignes)
    {
        final Map<String, List<String>> horaires = new LinkedHashMap<String, List<String>>();

        if (xmlLignes.size() > 0)
        {
            // Compter le nombre de TD dans la première ligne, pour savoir combien de fois il faut
            // boucler sur l'ensemble des TR
            final XmlNode ligneCount =
                XmlNode
                        .getInstance(new InputSource(new StringReader(xmlLignes.get(0).toString())));
            final int nbTR = xmlLignes.size();
            final int nbTD = ligneCount.getElementList().size();
            for (int i = 0; i < nbTD; i++)
            {
                final XmlNode firstLigne =
                    XmlNode.getInstance(new InputSource(new StringReader(xmlLignes
                            .get(0)
                            .toString())));
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
        }

        return horaires;
    }
}
