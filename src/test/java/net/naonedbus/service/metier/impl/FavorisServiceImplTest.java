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

import junit.framework.Assert;
import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.factory.impl.FavorisFactory;
import net.naonedbus.model.Favoris;
import net.naonedbus.model.criteria.impl.FavorisSearchCriteria;

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
import org.springframework.beans.factory.BeanFactory;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@RunWith(MockitoJUnitRunner.class)
public class FavorisServiceImplTest
{
    @Mock
    private BeanFactory beanFactory;
    @Mock
    private GenericDao<Favoris> genericDao;
    @Mock
    private FavorisFactory favorisFactory;

    private List<Favoris> favoriss;
    private Favoris favoris;
    private FavorisSearchCriteria favorisCriteria;
    private FavorisServiceImpl favorisServiceImpl;

    @Before
    public void createFavorisServiceImpl()
        throws Exception
    {
        this.favorisServiceImpl = new FavorisServiceImpl(this.genericDao);
        this.favorisServiceImpl.setBeanFactory(this.beanFactory);
        this.favorisServiceImpl.setFavorisFactory(this.favorisFactory);
    }
    /**
     */
    @Before
    public void setUp()
    {
        this.favoris = new Favoris();
        this.favoris.setId(1);
        this.favoris.setIdentifiant("TEST");
        this.favoris.setContenu("azerty");

        this.favoriss = new ArrayList<Favoris>();
        this.favoriss.add(this.favoris);

        this.favorisCriteria = new FavorisSearchCriteria();
        Mockito.when(this.beanFactory.getBean("favorisSearchCriteria"))
                .thenReturn(this.favorisCriteria);

        Mockito.when(this.favorisFactory.getInitializedObject(Matchers.anyString()))
                .thenReturn(this.favoris);
    }

    /**
     */
    @After
    public void tearDown()
    {
    }

    /**
     * Test get par identifiant.
     */
    @Test
    public void get()
    {
        Mockito.when(this.genericDao.getAll(Matchers.any(FavorisSearchCriteria.class)))
                .thenAnswer(new Answer<List<Favoris>>() {

                    @Override
                    public List<Favoris> answer(final InvocationOnMock invocation)
                        throws Throwable
                    {
                        final FavorisSearchCriteria crit =
                            (FavorisSearchCriteria) invocation.getArguments()[0];
                        Assert.assertEquals("FAV",
                                            crit.getIdentifiant());
                        return FavorisServiceImplTest.this.favoriss;
                    }
                });

        final Favoris ret = this.favorisServiceImpl.get("FAV");

        Assert.assertEquals(ret,
                            this.favoris);
    }

    /**
     * Test exporter.
     */
    @Test
    public void exporter()
    {
        Mockito.when(this.genericDao.save(this.favoris)).thenReturn(this.favoris);

        final String ret = this.favorisServiceImpl.exporter("EXPORT");

        Mockito.verify(this.genericDao).save(this.favoris);
        Assert.assertEquals(this.favoris.getIdentifiant(),
                            ret);
    }

    /**
     * Test importer sans résultat.
     */
    @Test
    public void importerKO()
    {
        Mockito.when(this.genericDao.getAll(Matchers.any(FavorisSearchCriteria.class)))
                .thenReturn(new ArrayList<Favoris>());

        final String ret = this.favorisServiceImpl.importer("FAV");

        Assert.assertNull(ret);
    }

    /**
     * Test importer avec résultat.
     */
    @Test
    public void importerOK()
    {
        Mockito.when(this.genericDao.getAll(Matchers.any(FavorisSearchCriteria.class)))
                .thenReturn(this.favoriss);

        final String ret = this.favorisServiceImpl.importer("FAV");

        Assert.assertEquals(this.favoris.getContenu(),
                            ret);
    }
}
