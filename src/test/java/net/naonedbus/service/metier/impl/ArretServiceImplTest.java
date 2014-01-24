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
package net.naonedbus.service.metier.impl;

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
import java.util.List;

import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.model.Arret;
import net.naonedbus.model.criteria.impl.ArretSearchCriteria;
import net.naonedbus.model.criteria.impl.ArretsWithHorairesSearchCriteria;
import net.naonedbus.service.metier.ArretService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Test du service des arrêts.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class ArretServiceImplTest
{
    /**
     * DAO mockée.
     */
    private GenericDao<Arret> daoMock;

    /**
     * Service à tester.
     */
    private ArretService service;

    /**
     * Initialisation.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp()
    {
        this.daoMock = Mockito.mock(GenericDao.class);
        this.service = new ArretServiceImpl(this.daoMock);
    }

    /**
     * Finalisation.
     */
    @After
    public void tearDown()
    {
        this.daoMock = null;
        this.service = null;
    }

    /**
     * Vérification initialisation.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.service);
        Assert.assertNotNull(this.daoMock);
    }

    /**
     * Test du get.
     */
    @Test
    public void get()
    {
        final Arret arret = new Arret();
        arret.setId(1);
        arret.setCode("TEST");
        final List<Arret> arrets = new ArrayList<Arret>();
        arrets.add(arret);

        Mockito.when(this.daoMock.getAll(Matchers.any(ArretSearchCriteria.class)))
                .thenAnswer(new Answer<List<Arret>>() {

                    @Override
                    public List<Arret> answer(final InvocationOnMock invocation)
                        throws Throwable
                    {
                        final ArretSearchCriteria crit =
                            (ArretSearchCriteria) invocation.getArguments()[0];
                        Assert.assertEquals("C",
                                            crit.getCode());
                        return arrets;
                    }
                });

        final Arret ret = this.service.get("A",
                                           "B",
                                           "C");

        Assert.assertEquals(arret,
                            ret);
    }

    /**
     * Test du get sans résultat.
     */
    @Test
    public void getNull()
    {
        final List<Arret> arrets = new ArrayList<Arret>();

        Mockito.when(this.daoMock.getAll(Matchers.any(ArretSearchCriteria.class)))
                .thenAnswer(new Answer<List<Arret>>() {

                    @Override
                    public List<Arret> answer(final InvocationOnMock invocation)
                        throws Throwable
                    {
                        final ArretSearchCriteria crit =
                            (ArretSearchCriteria) invocation.getArguments()[0];
                        Assert.assertEquals("C",
                                            crit.getCode());
                        return arrets;
                    }
                });

        final Arret ret = this.service.get("A",
                                           "B",
                                           "C");

        Assert.assertNull(ret);
    }

    /**
     * Test getArretsWithHoraires.
     */
    @Test
    public void getArretsWithHoraires()
    {
        this.service.getArretsWithHoraires();
        Mockito.verify(this.daoMock).getAll(Matchers.any(ArretsWithHorairesSearchCriteria.class));
    }
}
