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
package net.naonedbus.dao.hibernate.mock;

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


import javax.persistence.EntityManager;

import net.naonedbus.dao.hibernate.GenericDaoHibernate;
import net.naonedbus.model.Ligne;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Classe en charge de tester la DAO des lignes.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class LigneDaoHibernateTest
{
    /**
     * Dao.
     */
    private GenericDaoHibernate<Ligne> ligneDao;

    /**
     * EntityManager.
     */
    private EntityManager entityManager;

    /**
     * Méthode d'initialisation du test.
     */
    @Before
    public void init()
    {
        this.entityManager = Mockito.mock(EntityManager.class);
        this.ligneDao = new GenericDaoHibernate<Ligne>(Ligne.class);
        this.ligneDao.setEntityManager(this.entityManager);
    }

    /**
     * Méthode de finalisation de test.
     */
    @After
    public void end()
    {
        this.ligneDao = null;
        this.entityManager = null;
    }

    /**
     * Méthode de test Get.
     */
    @Test
    public void get()
    {
        final Ligne ligne = new Ligne();
        ligne.setId(1);

        Mockito.when(this.entityManager.find(Ligne.class,
                                             ligne.getId())).thenReturn(ligne);

        final Ligne ligneReturned = this.ligneDao.get(ligne.getId());
        Assert.assertNotNull(ligneReturned);
        Assert.assertNotNull(ligneReturned.getId());
        Assert.assertEquals(1L,
                            ligneReturned.getId().longValue());
    }

    /**
     * Méthode de test Save.
     */
    @Test
    public void save()
    {
        final Ligne ligne = new Ligne();
        ligne.setId(1);
        Mockito.when(this.entityManager.merge(ligne)).thenReturn(ligne);
        final Ligne ligneSaved = this.ligneDao.save(ligne);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).merge(Matchers.any());
        Assert.assertNotNull(ligneSaved);
        Assert.assertEquals(ligne,
                            ligneSaved);
    }

    /**
     * Méthode de test Delete.
     */
    @Test
    public void delete()
    {
        final Ligne ligne = new Ligne();
        Mockito.when(this.ligneDao.get(ligne.getId())).thenReturn(ligne);
        this.ligneDao.remove(ligne);
        Mockito.verify(this.entityManager,
                       Mockito.times(1)).remove(Matchers.any());
    }
}
