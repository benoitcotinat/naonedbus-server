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
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;
import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.model.Arret;
import net.naonedbus.model.Horaire;
import net.naonedbus.model.criteria.impl.HoraireSearchCriteria;
import net.naonedbus.service.horaire.retriever.HoraireRetriever;
import net.naonedbus.service.metier.ArretService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@RunWith(MockitoJUnitRunner.class)
public class HoraireServiceImplTest
{
    @Mock
    private ArretService arretService;
    @Mock
    private BeanFactory beanFactory;
    @Mock
    private GenericDao<Horaire> genericDao;
    @Mock
    private HoraireRetriever horaireRetriever;
    @Mock
    private Logger log;

    /** Timestamp du moment courant. */
    private Long timestamp;
    private Arret arret;
    /** Horaire de test n°1 avec un timestamp à 1. */
    private Horaire horaire1;
    /** Horaire de test n°2 avec un timestamp à 2. */
    private Horaire horaire2;
    /** Liste d'horaires préchargés avec les horaires n°1 et n°2. */
    private List<Horaire> horaires;
    private HoraireSearchCriteria horaireSearchCriteria;
    private HoraireServiceImpl horaireServiceImpl;

    @Before
    public void createHoraireServiceImpl()
        throws Exception
    {
        this.horaireServiceImpl = new HoraireServiceImpl(this.genericDao);
        this.horaireServiceImpl.setArretService(this.arretService);
        this.horaireServiceImpl.setBeanFactory(this.beanFactory);
        this.horaireServiceImpl.setHoraireRetriever(this.horaireRetriever);
    }

    /**
     * Initialisation.
     */
    @Before
    public void setUp()
    {
        this.arret = new Arret();
        this.arret.setId(1);
        this.arret.setCode("TEST");
        this.arret.setNom("Nom Arret");

        this.horaire1 = new Horaire();
        this.horaire1.setId(1);
        this.horaire1.setTimestamp(1L);
        this.horaire2 = new Horaire();
        this.horaire2.setId(2);
        this.horaire2.setTimestamp(2L);
        this.horaires = new ArrayList<Horaire>();
        this.horaires.add(this.horaire1);
        this.horaires.add(this.horaire2);

        this.timestamp = System.currentTimeMillis();

        this.horaireSearchCriteria = new HoraireSearchCriteria();
        Mockito.when(this.beanFactory.getBean("horaireSearchCriteria"))
                .thenReturn(this.horaireSearchCriteria);
    }

    /**
     */
    @After
    public void tearDown()
        throws Exception
    {
    }

    /**
     * Test get arret null.
     */
    @Test
    public void getArretNull()
    {
        Mockito.when(this.arretService.get("L",
                                           "S",
                                           "A")).thenReturn(null);

        final List<Horaire> ret = this.horaireServiceImpl.get("L",
                                                              "S",
                                                              "A",
                                                              this.timestamp);

        Assert.assertTrue(ret.isEmpty());
    }

    /**
     * Test get horaires de base vide.
     * @throws NaonedbusException
     */
    @Test
    public void getHorairesEmpty()
        throws NaonedbusException
    {
        this.horaire2.setTimestamp(System.currentTimeMillis() + 10000);

        Mockito.when(this.arretService.get("L",
                                           "S",
                                           "A")).thenReturn(this.arret);
        Mockito.when(this.genericDao.getAll(Matchers.any(HoraireSearchCriteria.class)))
                .thenReturn(new ArrayList<Horaire>());
        Mockito.when(this.horaireRetriever.retrieve(Matchers.anyLong(),
                                                    Matchers.any(Arret.class)))
                .thenReturn(this.horaires);

        final List<Horaire> ret = this.horaireServiceImpl.get("L",
                                                              "S",
                                                              "A",
                                                              this.timestamp);

        Mockito.verify(this.genericDao).getAll(Matchers.any(HoraireSearchCriteria.class));
        Mockito.verify(this.genericDao,
                       Mockito.times(2)).save(this.horaire1);
        Mockito.verify(this.genericDao,
                       Mockito.times(2)).save(this.horaire2);
        Assert.assertFalse(ret.isEmpty());
        Assert.assertFalse(ret.contains(this.horaire1));
        Assert.assertTrue(ret.contains(this.horaire2));
    }

    /**
     * Test get horaires de base existantset suffisants.
     * @throws NaonedbusException
     */
    @Test
    public void getHorairesNotEmpty()
        throws NaonedbusException
    {
        this.horaire2.setTimestamp(System.currentTimeMillis() + 10000);

        Mockito.when(this.arretService.get("L",
                                           "S",
                                           "A")).thenReturn(this.arret);
        Mockito.when(this.genericDao.getAll(Matchers.any(HoraireSearchCriteria.class)))
                .thenReturn(this.horaires);

        final List<Horaire> ret = this.horaireServiceImpl.get("L",
                                                              "S",
                                                              "A",
                                                              this.timestamp);

        Mockito.verify(this.genericDao).getAll(Matchers.any(HoraireSearchCriteria.class));
        Mockito.verify(this.horaireRetriever,
                       Mockito.never()).retrieve(Matchers.anyLong(),
                                                 Matchers.any(Arret.class));
        Assert.assertFalse(ret.isEmpty());
        Assert.assertTrue(ret.contains(this.horaire2));
    }

    /**
     * Test load.
     */
    @Test
    public void load()
    {
        Mockito.when(this.genericDao.getAll(Matchers.any(HoraireSearchCriteria.class)))
                .thenAnswer(new Answer<List<Horaire>>() {

                    @Override
                    public List<Horaire> answer(final InvocationOnMock invocation)
                        throws Throwable
                    {
                        final HoraireSearchCriteria crit =
                            (HoraireSearchCriteria) invocation.getArguments()[0];
                        Assert.assertEquals(HoraireServiceImplTest.this.arret,
                                            crit.getArret());
                        Assert.assertEquals(HoraireServiceImplTest.this.timestamp
                                                    - TimeUnit.DAYS.toMillis(1),
                                            crit.getTimestampMin().longValue());
                        Assert.assertTrue(crit.getTimestampMax() > HoraireServiceImplTest.this.timestamp);
                        Assert
                                .assertTrue(crit.getTimestampMax() < HoraireServiceImplTest.this.timestamp
                                                                     + TimeUnit.DAYS.toMillis(1));
                        return HoraireServiceImplTest.this.horaires;
                    }
                });

        final List<Horaire> ret = this.horaireServiceImpl.load(this.arret,
                                                               this.timestamp);

        Mockito.verify(this.genericDao).getAll(Matchers.any(HoraireSearchCriteria.class));
        Assert.assertEquals(this.horaires,
                            ret);
    }
}
