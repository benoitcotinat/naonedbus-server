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
package net.naonedbus.dao.hibernate;

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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TransactionRequiredException;

import net.naonedbus.model.BeanObject;
import net.naonedbus.model.Ligne;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Classe en charge de tester la DAO Hibernate.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class GenericDaoHibernateTest
{
    /**
     * GenericDaoHibernate à tester.
     */
    private GenericDaoHibernate<BeanObject> genericDao;

    /**
     * EntityManager.
     */
    private EntityManager entityManager;

    /**
     * Session.
     */
    private Session session;

    /**
     * Méthode d'initialisation du test.
     */
    @Before
    public void setUp()
    {
        this.entityManager = Mockito.mock(EntityManager.class);
        this.session = Mockito.mock(Session.class);

        Mockito.when(this.entityManager.getDelegate()).thenReturn(this.session);

        this.genericDao = new GenericDaoHibernate<BeanObject>(BeanObject.class);
        this.genericDao.setEntityManager(this.entityManager);
    }

    /**
     * Méthode de finalisation de test.
     */
    @After
    public void tearDown()
    {
        this.genericDao = null;
        this.entityManager = null;
        this.session = null;
    }

    /**
     * Méthode en charge de tester les données d'init du test.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.genericDao.getEntityManager());
    }

    /**
     * Méthode en charge de tester le get avec une exception IllegalArgumentException.
     */
    @Test
    public void getKOIllegalArgumentException()
    {
        final Integer id = 1;
        final IllegalArgumentException e = new IllegalArgumentException();
        Mockito.doThrow(e).when(this.entityManager).find(BeanObject.class,
                                                         id);
        this.genericDao.get(id);
        Mockito.verify(this.entityManager).find(BeanObject.class,
                                                id);
    }

    /**
     * Méthode en charge de tester le get avec une exception IllegalStateException.
     */
    @Test
    public void getKOIllegalStateException()
    {
        final Integer id = 1;
        final IllegalStateException e = new IllegalStateException();
        Mockito.doThrow(e).when(this.entityManager).find(BeanObject.class,
                                                         id);
        this.genericDao.get(id);
        Mockito.verify(this.entityManager).find(BeanObject.class,
                                                id);
    }

    /**
     * Méthode en charge de tester le save avec une exception IllegalStateException.
     */
    @Test
    public void saveKOIllegalStateException()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        final IllegalStateException e = new IllegalStateException();
        Mockito.doThrow(e).when(this.entityManager).merge(bean);
        this.genericDao.save(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).merge(bean);
    }

    /**
     * Méthode en charge de tester le save avec une exception IllegalArgumentException.
     */
    @Test
    public void saveKOIllegalArgumentException()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        final IllegalArgumentException e = new IllegalArgumentException();
        Mockito.doThrow(e).when(this.entityManager).merge(bean);
        this.genericDao.save(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).merge(bean);
    }

    /**
     * Méthode en charge de tester le save avec une exception TransactionRequiredException.
     */
    @Test
    public void saveKOTransactionRequiredException()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        final TransactionRequiredException e = new TransactionRequiredException();
        Mockito.doThrow(e).when(this.entityManager).merge(bean);
        this.genericDao.save(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).merge(bean);
    }

    /**
     * Méthode en charge de tester le save avec une exception EntityExistsException.
     */
    @Test
    public void saveKOEntityExistsException()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        final EntityExistsException e = new EntityExistsException();
        Mockito.doThrow(e).when(this.entityManager).merge(bean);
        this.genericDao.save(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).merge(bean);
    }

    /**
     * Méthode en charge de tester le remove avec une exception IllegalStateException.
     */
    @Test
    public void removeKOIllegalStateException()
    {
        final BeanObject beanObject = Mockito.mock(BeanObject.class);
        final IllegalStateException e = new IllegalStateException();
        Mockito.doThrow(e).when(this.entityManager).remove(Matchers.anyObject());
        this.genericDao.remove(beanObject);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).remove(Matchers.anyObject());
    }

    /**
     * Méthode en charge de tester le remove avec une exception IllegalArgumentException.
     */
    @Test
    public void removeKOIllegalArgumentException()
    {
        final BeanObject beanObject = Mockito.mock(BeanObject.class);
        final IllegalArgumentException e = new IllegalArgumentException();
        Mockito.doThrow(e).when(this.entityManager).remove(Matchers.anyObject());
        this.genericDao.remove(beanObject);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).remove(Matchers.anyObject());
    }

    /**
     * Méthode en charge de tester le remove avec une exception TransactionRequiredException.
     */
    @Test
    public void removeKOTransactionRequiredException()
    {
        final BeanObject beanObject = Mockito.mock(BeanObject.class);
        final TransactionRequiredException e = new TransactionRequiredException();
        Mockito.doThrow(e).when(this.entityManager).remove(Matchers.anyObject());
        this.genericDao.remove(beanObject);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).remove(Matchers.anyObject());
    }

    /**
     * Méthode en charge de tester le reattach avec un objet null.
     */
    @Test
    public void reattachWithNull()
    {
        final BeanObject result = this.genericDao.reattach(null);
        Assert.assertNull(result);
    }

    /**
     * Méthode en charge de tester le reattach avec un objet dont l'identifiant est null.
     */
    @Test
    public void reattachWithIdNull()
    {
        final BeanObject bean = new Ligne();
        final BeanObject result = this.genericDao.reattach(bean);
        Assert.assertNull(bean.getId());
        Assert.assertNull(result.getId());
    }

    /**
     * Méthode en charge de tester le reattach avec un objet comprenant un identifiant (pas
     * d'exception).
     */
    @Test
    public void reattachWithId()
    {
        final BeanObject bean = new Ligne();
        bean.setId(1);
        Mockito.when(this.entityManager.merge(bean)).thenReturn(bean);
        final BeanObject result = this.genericDao.reattach(bean);
        Assert.assertEquals(1L,
                            bean.getId().longValue());
        Assert.assertEquals(1L,
                            result.getId().longValue());
    }

    /**
     * Méthode en charge de tester le reattach avec une exception IllegalStateException.
     */
    @Test
    public void reattachKOIllegalStateException()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        final IllegalStateException e = new IllegalStateException();
        Mockito.doThrow(e).when(this.entityManager).merge(bean);
        this.genericDao.reattach(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).merge(bean);
    }

    /**
     * Méthode en charge de tester le reattach avec une exception IllegalArgumentException.
     */
    @Test
    public void reattachKOIllegalArgumentException()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        final IllegalArgumentException e = new IllegalArgumentException();
        Mockito.doThrow(e).when(this.entityManager).merge(bean);
        this.genericDao.reattach(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).merge(bean);
    }

    /**
     * Méthode en charge de tester le reattach avec une exception TransactionRequiredException.
     */
    @Test
    public void reattachKOTransactionRequiredException()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        final TransactionRequiredException e = new TransactionRequiredException();
        Mockito.doThrow(e).when(this.entityManager).merge(bean);
        this.genericDao.reattach(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).merge(bean);
    }

    /**
     * Méthode en charge de tester le dettach sans exception.
     */
    @Test
    public void dettachOK()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        this.genericDao.dettach(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).refresh(bean);
    }

    /**
     * Méthode en charge de tester le dettach avec une exception IllegalStateException.
     */
    @Test
    public void dettachKOIllegalStateException()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        final IllegalStateException e = new IllegalStateException();
        Mockito.doThrow(e).when(this.entityManager).refresh(bean);
        this.genericDao.dettach(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).refresh(bean);
    }

    /**
     * Méthode en charge de tester le dettach avec une exception IllegalArgumentException.
     */
    @Test
    public void dettachKOIllegalArgumentException()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        final IllegalArgumentException e = new IllegalArgumentException();
        Mockito.doThrow(e).when(this.entityManager).refresh(bean);
        this.genericDao.dettach(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).refresh(bean);
    }

    /**
     * Méthode en charge de tester le dettach avec une exception TransactionRequiredException.
     */
    @Test
    public void dettachKOTransactionRequiredException()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        final TransactionRequiredException e = new TransactionRequiredException();
        Mockito.doThrow(e).when(this.entityManager).refresh(bean);
        this.genericDao.dettach(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).refresh(bean);
    }

    /**
     * Méthode en charge de tester le dettach avec une exception EntityNotFoundException.
     */
    @Test
    public void dettachKOEntityNotFoundException()
    {
        final BeanObject bean = Mockito.mock(BeanObject.class);
        final EntityNotFoundException e = new EntityNotFoundException();
        Mockito.doThrow(e).when(this.entityManager).refresh(bean);
        this.genericDao.dettach(bean);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).refresh(bean);
    }

    /**
     * Méthode en charge de tester la récupération de la session sans exception.
     */
    @Test
    public void getCurrentSessionOK()
    {
        Mockito.when(this.entityManager.getDelegate()).thenReturn(null);
        final Session result = this.genericDao.getCurrentSession();
        Assert.assertNull(result);
    }

    /**
     * Méthode en charge de tester la récupération de la session avec exception.
     */
    @Test
    public void getCurrentSessionKO()
    {
        final IllegalStateException e = new IllegalStateException();
        Mockito.when(this.entityManager.getDelegate()).thenThrow(e);
        final Session result = this.genericDao.getCurrentSession();
        Assert.assertNull(result);
    }

    /**
     * Méthode en charge de tester la gestion des exceptions.
     */
    @Test
    public void handleException()
    {
        final IllegalStateException e = new IllegalStateException();
        this.genericDao.handleException("handleException",
                                        e,
                                        "detailMessage");
    }

    /**
     * Méthode en charge de tester la méthode getAll.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void getAll()
    {
        final Criteria criteria = Mockito.mock(Criteria.class);
        final List<BeanObject> expected = new ArrayList<BeanObject>();
        Mockito.when(this.session.createCriteria((Class<BeanObject>) Matchers.anyObject()))
                .thenReturn(criteria);
        Mockito.when(criteria.list()).thenReturn(expected);
        final List<BeanObject> result = this.genericDao.getAll();
        Mockito.verify(this.session).createCriteria((Class<BeanObject>) Matchers.anyObject());
        Mockito.verify(criteria).list();
        Assert.assertNotNull(result);
        Assert.assertEquals(expected.size(),
                            result.size());
    }
}
