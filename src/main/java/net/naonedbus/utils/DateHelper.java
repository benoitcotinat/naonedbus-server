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


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.naonedbus.utils.constants.NaonedbusConstants;

/**
 * Helper pour les opérations sur les dates.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class DateHelper
{
    /**
     * Heure de midi.
     */
    private static int MID_HOUR = 12;

    /**
     * Nombre d'heures dans une journée.
     */
    private static int MAX_HOUR = 23;

    /**
     * Nombre de minutes dans une heure.
     */
    private static int MAX_MINUTE = 59;

    /**
     * Nombre de secondes dans une minute.
     */
    private static int MAX_SECOND = 59;

    /**
     * Nombre de secondes dans une minute.
     */
    private static int MAX_MILLISECOND = 999;

    /**
     * Méthode calculant le timestamp correspondant au milieu d'une journée (le jour à
     * 10:00:00.000).
     * @param timestamp Timestamp d'un moment d'une journée.
     * @return Le timestamp du midi de la journée du paramètre.
     */
    public static Long getTimestampStartOfDay(final Long timestamp)
    {
        final Calendar calTimestamp = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                           NaonedbusConstants.LOCALE);
        calTimestamp.setTimeInMillis(timestamp);
        calTimestamp.set(Calendar.HOUR_OF_DAY,
                         0);
        calTimestamp.set(Calendar.MINUTE,
                         0);
        calTimestamp.set(Calendar.SECOND,
                         0);
        calTimestamp.set(Calendar.MILLISECOND,
                         0);

        return calTimestamp.getTimeInMillis();
    }

    /**
     * Méthode calculant le timestamp correspondant au milieu d'une journée (le jour à
     * 12:00:00.000).
     * @param timestamp Timestamp d'un moment d'une journée.
     * @return Le timestamp du midi de la journée du paramètre.
     */
    public static Long getTimestampMidOfDay(final Long timestamp)
    {
        final Calendar calTimestamp = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                           NaonedbusConstants.LOCALE);
        calTimestamp.setTimeInMillis(timestamp);
        calTimestamp.set(Calendar.HOUR_OF_DAY,
                         DateHelper.MID_HOUR);
        calTimestamp.set(Calendar.MINUTE,
                         0);
        calTimestamp.set(Calendar.SECOND,
                         0);
        calTimestamp.set(Calendar.MILLISECOND,
                         0);

        return calTimestamp.getTimeInMillis();
    }

    /**
     * Méthode calculant le timestamp correspondant au dernier instant d'une journée (le jour à
     * 23:59:59.999).
     * @param timestamp Timestamp d'un moment d'une journée.
     * @return Le timestamp de la fin de la journée du paramètre.
     */
    public static Long getTimestampEndOfDay(final Long timestamp)
    {
        final Calendar calTimestamp = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                           NaonedbusConstants.LOCALE);
        calTimestamp.setTimeInMillis(timestamp);
        calTimestamp.set(Calendar.HOUR_OF_DAY,
                         DateHelper.MAX_HOUR);
        calTimestamp.set(Calendar.MINUTE,
                         DateHelper.MAX_MINUTE);
        calTimestamp.set(Calendar.SECOND,
                         DateHelper.MAX_SECOND);
        calTimestamp.set(Calendar.MILLISECOND,
                         DateHelper.MAX_MILLISECOND);

        return calTimestamp.getTimeInMillis();
    }

    /**
     * Retourne une liste de timestamps pour <code>n</code> jours.
     * @param n Le nombre de jours à partir d'aujourd'hui pour lesquels on veut un timestamp.
     * @return Liste des timestamps des <code>n</code> prochains jours.
     */
    public static List<Long> getTimestampsForNDays(final int n)
    {
        final List<Long> timestamps = new ArrayList<Long>();

        final Calendar cal = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                  NaonedbusConstants.LOCALE);
        for (int i = 0; i < n; i++)
        {
            timestamps.add(cal.getTimeInMillis());
            cal.roll(Calendar.DATE,
                     true);
        }
        return timestamps;
    }
}
