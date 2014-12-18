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
package net.naonedbus.utils.constants;

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


import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Some utils constants.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class NaonedbusConstants
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 7842807826934259563L;

    /**
     * Propriété contenant le mot de passe hibernate.
     */
    public static final String PROP_HIBERNATE_PWD = "hibernate.jdbc.password";

    /**
     * Taille maximale du contenu des commentaires.
     */
    public static final int COMMENTAIRE_TAILLE = 140;

    /**
     * Taille des arrêts avant troncature.
     */
    public static final int ARRET_TAILLE = 15;

    /**
     * Taille des sens avant troncature.
     */
    public static final int SENS_TAILLE = 15;

    /**
     * Point.
     */
    public static final String DOT = ".";

    /**
     * New line character.
     */
    public static final String NEW_LINE = "\n";

    /**
     * Locale FRANCE.
     */
    public static final Locale LOCALE = Locale.FRANCE;

    /**
     * TimeZone Paris.
     */
    public static final TimeZone TIMEZONE = TimeZone.getTimeZone("Europe/Paris");

    /**
     * Pattern de date à formatter pour les url du site de la tan.
     */
    public static final String PATTERN_DATE_URL = "dd/MM/yyyy";

    /**
     * Hashtag twitter naonedbus.
     */
    public static final String HASHTAG_NAONEDBUS = "#naonedbus";

    /**
     * Caractère "flèche" à positionner pour le sens.
     */
    public static final Character SENS = '\u2192';

    /**
     * Caractère "points de suspension" à pour illustrer la troncature.
     */
    public static final Character TRONCATURE = '\u2026';

    /**
     * Séparateur entre la topo et le message d'un tweet.
     */
     public static final String TOPO_SEPARATOR = "\u2022";

    /**
     * Préfixe des informations portant sur la ligne.
     */
    public static final String TOPO_LIGNE = "L.";

    /**
     * Caractère "virgule" à positionner après le sens.
     */
    public static final Character COMMA = ',';

    /**
     * Caractère "espace".
     */
    public static final Character SPACE = ' ';
}
