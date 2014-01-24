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
package net.naonedbus.context;

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


import junit.framework.Assert;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class JdbcPwdPostProcessorTest
{
    /**
     * Processor à tester.
     */
    private JdbcPwdPostProcessor processor;

    /**
     */
    @Before
    public void setUp()
    {
        this.processor = new JdbcPwdPostProcessor();
    }

    /**
     */
    @After
    public void tearDown()
    {
        this.processor = null;
    }

    /**
     * Vérification de l'initialisation.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.processor);
    }

    /**
     * Test Before.
     */
    @Test
    public void postProcessBeforeInitialization()
    {
        final Object o = new Object();

        final Object ret = this.processor.postProcessBeforeInitialization(o,
                                                                          "");
        Assert.assertEquals(o,
                            ret);
    }

    /**
     * Test After avec la data source et le chgt de mot de passe.
     */
    @Test
    public void postProcessAfterInitializationOK()
    {
        final ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setPassword("");
        System.setProperty(NaonedbusConstants.PROP_HIBERNATE_PWD,
                           "test");

        final ComboPooledDataSource ret =
            (ComboPooledDataSource) this.processor.postProcessAfterInitialization(cpds,
                                                                                  "dataSource");

        Assert.assertEquals("test",
                            ret.getPassword());
    }

    /**
     * Test After avec un autre bean.
     */
    @Test
    public void postProcessAfterInitializationOtherBean()
    {
        final Object o = new Object();

        final Object ret = this.processor.postProcessAfterInitialization(o,
                                                                         "other");

        Assert.assertEquals(o,
                            ret);
    }

    /**
     * Test After avec un autre bean.
     */
    @Test
    public void postProcessAfterInitializationAlreadyPwd()
    {
        final ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setPassword("test1");
        System.setProperty(NaonedbusConstants.PROP_HIBERNATE_PWD,
                           "test2");

        final ComboPooledDataSource ret =
            (ComboPooledDataSource) this.processor.postProcessAfterInitialization(cpds,
                                                                                  "dataSource");

        Assert.assertEquals("test1",
                            ret.getPassword());
    }
}
