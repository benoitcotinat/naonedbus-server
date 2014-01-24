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
package net.naonedbus.schedule;

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


import net.naonedbus.schedule.job.CommonJob;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommonSchedulerTest
{
    /**
     * Scheduler à tester.
     */
    private CommonScheduler scheduler;

    /**
     * Job mocké.
     */
    private CommonJob jobMock;

    /**
     * Initialisation.
     */
    @Before
    public void setUp()
    {
        this.jobMock = Mockito.mock(CommonJob.class);
        this.scheduler = new CommonScheduler();
        this.scheduler.setJob(this.jobMock);
    }

    /**
     * Finalisation.
     */
    @After
    public void tearDown()
    {
        this.jobMock = null;
        this.scheduler = null;
    }

    /**
     * Vérification de l'initialisation.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.scheduler);
        Assert.assertNotNull(this.jobMock);
    }

    /**
     * Test du lancement du job.
     */
    @Test
    public void schedule()
    {
        this.scheduler.schedule();
        Mockito.verify(this.jobMock).execute();
    }
}
