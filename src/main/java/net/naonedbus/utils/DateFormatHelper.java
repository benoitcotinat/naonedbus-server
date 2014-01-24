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
package net.naonedbus.utils;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.naonedbus.utils.constants.NaonedbusConstants;

/**
 * Helper commun pour le formattage des dates.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class DateFormatHelper
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 3046097120352858682L;

    /**
     * Méthode en charge de formatter une date de façon complète (jj/MM/yyyy HH:mm:ss)
     * @param calendar Date à formatter
     * @return Date formattée
     */
    public String formatCompleteDateHour(final Long timestamp)
    {
        final Calendar cal = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                  NaonedbusConstants.LOCALE);
        cal.setTimeInMillis(timestamp);
        return this.formatCompleteDateHour(cal);
    }

    /**
     * Méthode en charge de formatter une date de façon complète (jj/MM/yyyy HH:mm:ss)
     * @param calendar Date à formatter
     * @return Date formattée
     */
    public String formatCompleteDateHour(final Calendar calendar)
    {
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS",
                                                           NaonedbusConstants.LOCALE);
        dateFormat.setTimeZone(NaonedbusConstants.TIMEZONE);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Méthode en charge de formatter une date pour la passer à une URL du site de la TAN.
     * @param calendar Date à formatter
     * @return Date formattée
     */
    public String formatForURL(final Long timestamp)
    {
        final Calendar cal = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                  NaonedbusConstants.LOCALE);
        cal.setTimeInMillis(timestamp);
        return this.formatForURL(cal);
    }

    /**
     * Méthode en charge de formatter une date pour la passer à une URL du site de la TAN.
     * @param calendar Date à formatter
     * @return Date formattée
     */
    public String formatForURL(final Calendar calendar)
    {
        final DateFormat dateFormat = new SimpleDateFormat(NaonedbusConstants.PATTERN_DATE_URL,
                                                           NaonedbusConstants.LOCALE);
        dateFormat.setTimeZone(NaonedbusConstants.TIMEZONE);
        return dateFormat.format(calendar.getTime());
    }
}
