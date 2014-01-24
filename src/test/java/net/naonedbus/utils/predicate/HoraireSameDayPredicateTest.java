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
package net.naonedbus.utils.predicate;

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


import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import net.naonedbus.model.Horaire;

import org.junit.Test;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HoraireSameDayPredicateTest
{

    /**
     * Test method for
     * {@link net.naonedbus.utils.predicate.HoraireSameDayPredicate#HoraireSameDayPredicate(java.lang.Long)}
     * .
     */
    @Test
    public void testHoraireSameDayPredicate()
    {
        final HoraireSameDayPredicate predicate = new HoraireSameDayPredicate(1L);
        Assert.assertEquals(1L,
                            predicate.getCalReference().getTimeInMillis());
    }

    /**
     * Test method for
     * {@link net.naonedbus.utils.predicate.HoraireSameDayPredicate#HoraireSameDayPredicate(java.lang.Long)}
     * .
     */
    @Test
    public void testHoraireSameDayPredicate2()
    {
        final long timestamp = System.currentTimeMillis();
        final HoraireSameDayPredicate predicate = new HoraireSameDayPredicate(timestamp);
        Assert.assertEquals(timestamp,
                            predicate.getCalReference().getTimeInMillis());
    }

    /**
     * Test method for
     * {@link net.naonedbus.utils.predicate.HoraireSameDayPredicate#evaluate(java.lang.Object)}.
     */
    @Test
    public void testEvaluateStrictEquals()
    {
        final long timestamp = System.currentTimeMillis();
        final Horaire h = new Horaire();
        h.setTimestamp(timestamp);
        final HoraireSameDayPredicate predicate = new HoraireSameDayPredicate(timestamp);

        final boolean ret = predicate.evaluate(h);

        Assert.assertTrue(ret);
    }

    /**
     * Test method for
     * {@link net.naonedbus.utils.predicate.HoraireSameDayPredicate#evaluate(java.lang.Object)}.
     */
    @Test
    public void testEvaluateSameDay()
    {
        final long timestamp = System.currentTimeMillis();
        final Horaire h = new Horaire();
        h.setTimestamp(timestamp + 500);
        final HoraireSameDayPredicate predicate = new HoraireSameDayPredicate(timestamp);

        final boolean ret = predicate.evaluate(h);

        Assert.assertTrue(ret);
    }

    /**
     * Test method for
     * {@link net.naonedbus.utils.predicate.HoraireSameDayPredicate#evaluate(java.lang.Object)}.
     */
    @Test
    public void testEvaluateDayDifferent()
    {
        final long timestamp = System.currentTimeMillis();
        final Horaire h = new Horaire();
        h.setTimestamp(timestamp
                       + TimeUnit.DAYS.toMillis(1));
        final HoraireSameDayPredicate predicate = new HoraireSameDayPredicate(timestamp);

        final boolean ret = predicate.evaluate(h);

        Assert.assertFalse(ret);
    }
}
