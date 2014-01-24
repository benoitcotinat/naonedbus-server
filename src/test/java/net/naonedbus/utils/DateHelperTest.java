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


import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.junit.Test;

/**
 * Classe testant l'utilitaire de calcul de dates.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class DateHelperTest
{
    /**
     * Test de la méthode getTimestampsForNDays.
     */
    @Test
    public void getTimestampsForNDays()
    {
        final Calendar today = Calendar.getInstance(NaonedbusConstants.LOCALE);

        final List<Long> timestamps = DateHelper.getTimestampsForNDays(2);

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamps.get(0));
        Assert.assertEquals(today.get(Calendar.YEAR),
                            cal.get(Calendar.YEAR));
        Assert.assertEquals(today.get(Calendar.MONTH),
                            cal.get(Calendar.MONTH));
        Assert.assertEquals(today.get(Calendar.DAY_OF_MONTH),
                            cal.get(Calendar.DAY_OF_MONTH));

        today.setTimeInMillis(today.getTimeInMillis()
                              + TimeUnit.MILLISECONDS.convert(1L,
                                                              TimeUnit.DAYS));
        cal.setTimeInMillis(timestamps.get(1));
        Assert.assertEquals(today.get(Calendar.YEAR),
                            cal.get(Calendar.YEAR));
        Assert.assertEquals(today.get(Calendar.MONTH),
                            cal.get(Calendar.MONTH));
        Assert.assertEquals(today.get(Calendar.DAY_OF_MONTH),
                            cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Test de la méthode getTimestampEndOfDay.
     */
    @Test
    public void getTimestampEndOfDay()
    {
        final Calendar cal = Calendar.getInstance();

        final Long ret = DateHelper.getTimestampEndOfDay(cal.getTimeInMillis());

        final Calendar calRet = Calendar.getInstance();
        calRet.setTimeInMillis(ret);

        Assert.assertEquals(cal.get(Calendar.YEAR),
                            calRet.get(Calendar.YEAR));
        Assert.assertEquals(cal.get(Calendar.MONTH),
                            calRet.get(Calendar.MONTH));
        Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                            calRet.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(23,
                            calRet.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(59,
                            calRet.get(Calendar.MINUTE));
        Assert.assertEquals(59,
                            calRet.get(Calendar.SECOND));
        Assert.assertEquals(999,
                            calRet.get(Calendar.MILLISECOND));
    }

    /**
     * Test de la méthode getTimestampMidOfDay.
     */
    @Test
    public void getTimestampMidOfDay()
    {
        final Calendar cal = Calendar.getInstance();

        final Long ret = DateHelper.getTimestampMidOfDay(cal.getTimeInMillis());

        final Calendar calRet = Calendar.getInstance();
        calRet.setTimeInMillis(ret);

        Assert.assertEquals(cal.get(Calendar.YEAR),
                            calRet.get(Calendar.YEAR));
        Assert.assertEquals(cal.get(Calendar.MONTH),
                            calRet.get(Calendar.MONTH));
        Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                            calRet.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(12,
                            calRet.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0,
                            calRet.get(Calendar.MINUTE));
        Assert.assertEquals(0,
                            calRet.get(Calendar.SECOND));
        Assert.assertEquals(0,
                            calRet.get(Calendar.MILLISECOND));
    }

    @Test
    public void getTimestampStartOfDay()
    {
        final Calendar cal = Calendar.getInstance();

        final Long ret = DateHelper.getTimestampStartOfDay(cal.getTimeInMillis());

        final Calendar calRet = Calendar.getInstance();
        calRet.setTimeInMillis(ret);

        Assert.assertEquals(cal.get(Calendar.YEAR),
                            calRet.get(Calendar.YEAR));
        Assert.assertEquals(cal.get(Calendar.MONTH),
                            calRet.get(Calendar.MONTH));
        Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                            calRet.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(0,
                            calRet.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0,
                            calRet.get(Calendar.MINUTE));
        Assert.assertEquals(0,
                            calRet.get(Calendar.SECOND));
        Assert.assertEquals(0,
                            calRet.get(Calendar.MILLISECOND));
    }
}
