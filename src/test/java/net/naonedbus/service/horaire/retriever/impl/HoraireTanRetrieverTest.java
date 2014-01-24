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
package net.naonedbus.service.horaire.retriever.impl;

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

import junit.framework.Assert;
import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.model.Arret;
import net.naonedbus.model.Horaire;
import net.naonedbus.scraper.NaonedbusScraper;
import net.naonedbus.scraper.wraper.impl.HoraireScraperWrapper;

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

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@RunWith(MockitoJUnitRunner.class)
public class HoraireTanRetrieverTest
{
    @Mock
    private NaonedbusScraper<Horaire> horaireScraper;

    /** Timestamp du moment courant. */
    private Long timestamp;
    private Arret arret;
    /** Horaire de test n°1 avec un timestamp à 1. */
    private Horaire horaire1;
    /** Horaire de test n°2 avec un timestamp à 2. */
    private Horaire horaire2;
    /** Liste d'horaires préchargés avec les horaires n°1 et n°2. */
    private List<Horaire> horaires;

    /**
     * Retriever à tester.
     */
    private HoraireTanRetriever retriever;

    @Before
    public void createHoraireTanRetriever()
    {
        this.retriever = new HoraireTanRetriever();
        this.retriever.setHoraireScraper(this.horaireScraper);
    }

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
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test retrieve.
     * @throws NaonedbusException
     */
    @Test
    public void retrieve()
        throws NaonedbusException
    {
        Mockito.when(this.horaireScraper.scrap(Matchers.any(HoraireScraperWrapper.class)))
                .thenAnswer(new Answer<List<Horaire>>() {

                    @Override
                    public List<Horaire> answer(final InvocationOnMock invocation)
                        throws Throwable
                    {
                        final HoraireScraperWrapper wraper =
                            (HoraireScraperWrapper) invocation.getArguments()[0];
                        Assert.assertEquals(HoraireTanRetrieverTest.this.arret,
                                            wraper.getArret());
                        Assert.assertEquals(HoraireTanRetrieverTest.this.timestamp.longValue(),
                                            wraper.getCalendar().getTimeInMillis());
                        return HoraireTanRetrieverTest.this.horaires;
                    }
                });

        final List<Horaire> ret = this.retriever.retrieve(this.timestamp,
                                                          this.arret);

        Assert.assertEquals(this.horaires,
                            ret);
    }
}
