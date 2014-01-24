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


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * Classe récupérant les terminus des horaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HoraireTerminusProcessor
{
    /**
     * Regex permettant de récupérer la partie html contenant les terminus alternatifs.
     */
    private static final String typesHoraireContent = "</table>([\\S\\s]*?)<a";

    /**
     * Regex permettant de récupérer un couple "code:nom" de terminus alternatif.
     */
    private static final String typeHoraireTocken = "[\\S]:(.[^<]+)";

    /**
     * Séparateur entre le code et le nom du terminus.
     */
    private static final String splitTerminus = ": ";

    /**
     * Caractère html 'apostrophe'.
     */
    private static final String htmlApos = "&apos;";

    /**
     * Caractère 'apostrophe'.
     */
    private static String unescapeApos = "\'";

    /**
     * Récupère les terminus alternatif de la page des horaires.
     * @param content Contenu html de la page des horaires.
     * @return Liste des associations entre le code du temrinus alternatif et son nom.
     */
    public Map<String, String> process(final String content)
    {
        final Map<String, String> terminus = new HashMap<String, String>();

        final Pattern patternTerminusContent =
            Pattern.compile(HoraireTerminusProcessor.typesHoraireContent);
        final Matcher matcherTerminusContent = patternTerminusContent.matcher(content);
        if (matcherTerminusContent.find())
        {
            final String terminusContent = matcherTerminusContent.group(1);

            final Pattern patternTerminusTocken =
                Pattern.compile(HoraireTerminusProcessor.typeHoraireTocken,
                                Pattern.DOTALL
                                        | Pattern.CASE_INSENSITIVE);
            final Matcher matcherTerminusTocken = patternTerminusTocken.matcher(terminusContent);
            while (matcherTerminusTocken.find())
            {
                final String terminusTocken = matcherTerminusTocken.group();
                final String codeTerminus =
                    terminusTocken.split(HoraireTerminusProcessor.splitTerminus)[0];
                final String nomTerminus =
                    StringUtils
                            .replace(StringUtils.chomp(terminusTocken
                                             .split(HoraireTerminusProcessor.splitTerminus)[1]),
                                     HoraireTerminusProcessor.htmlApos,
                                     HoraireTerminusProcessor.unescapeApos);
                terminus.put(codeTerminus,
                             nomTerminus);
            }
        }

        return terminus;
    }
}
