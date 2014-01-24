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
package net.naonedbus.factory.impl;

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


import net.naonedbus.model.Favoris;
import net.naonedbus.service.metier.FavorisService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Teste la factory de favoris.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class FavorisFactoryTest
{
    /**
     * Service des factory mocké.
     */
    private FavorisService favorisServiceMock;

    /**
     * Factory à tester.
     */
    private FavorisFactory factory;

    /**
     * Initialisation.
     */
    @Before
    public void setUp()
    {
        this.favorisServiceMock = Mockito.mock(FavorisService.class);
        this.factory = new FavorisFactory();
        this.factory.setFavorisService(this.favorisServiceMock);
    }

    /**
     * Finalisation.
     */
    @After
    public void tearDown()
    {
        this.favorisServiceMock = null;
        this.factory = null;
    }

    /**
     * Vérification de l'initialisation.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.factory);
        Assert.assertNotNull(this.favorisServiceMock);
    }

    /**
     * Teste la constrcution d'un favoris.
     */
    @Test
    public void getInitializedObject()
    {
        Mockito.when(this.favorisServiceMock.getUniqueIdentifiant()).thenReturn("TEST");

        final Favoris ret = this.factory.getInitializedObject("content");

        Assert.assertNotNull(ret);
        Assert.assertEquals("content",
                            ret.getContenu());
        Assert.assertEquals("TEST",
                            ret.getIdentifiant());
    }
}
