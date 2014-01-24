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
package net.naonedbus.dao.hibernate.session;

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
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.naonedbus.dao.criteria.dictionnary.impl.CriteriaDictionaryImpl;
import net.naonedbus.dao.hibernate.GenericDaoHibernate;
import net.naonedbus.model.Arret;
import net.naonedbus.model.Ligne;
import net.naonedbus.model.Sens;
import net.naonedbus.model.criteria.impl.ArretSearchCriteria;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe en charge de tester la DAO Arret avec une session réelle.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{"classpath*:config/applicationContext.xml" })
@Transactional
public class LigneDaoHibernateSessionTest
{
    /**
     * Entité manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Dao de gestion de Ligne.
     */
    private GenericDaoHibernate<Ligne> ligneDao;

    /**
     * Dictionnaire de critère de recherche.
     */
    @Resource(name = "criteriaDictionary")
    private CriteriaDictionaryImpl dictionary;

    /**
     * Initialisation du test.
     */
    @Before
    public void init()
    {
        this.ligneDao = new GenericDaoHibernate<Ligne>(Ligne.class);
        this.ligneDao.setEntityManager(this.entityManager);
        this.ligneDao.setCriteriaDictionary(this.dictionary);
    }

    /**
     * Méthode de finalisation de test.
     */
    @After
    public void end()
    {
        this.ligneDao = null;
        this.entityManager = null;
        this.dictionary = null;
    }

    /**
     * Test des méthodes de DAO Ligne.
     */
    @Test
    public void saveAndGetAndRemove()
    {
        final Arret arret = new Arret();
        arret.setCode("codeArret");
        arret.setNom("nomArret");
        final List<Arret> arrets = new ArrayList<Arret>();
        arrets.add(arret);

        final Sens sens = new Sens();
        sens.setCode("codeSens");
        sens.setNom("nomSens");
        sens.setArrets(arrets);
        final List<Sens> senss = new ArrayList<Sens>();
        senss.add(sens);

        // Création et sauvegarde de la ligne
        Ligne ligne = new Ligne();
        ligne.setCode("codeLigne");
        ligne.setNom("nomLigne");
        ligne.setSens(senss);

        sens.setLigne(ligne);
        arret.setSens(sens);

        ligne = this.ligneDao.save(ligne);

        // Récupération de Ligne
        final Ligne ligneReturn = this.ligneDao.get(ligne.getId());

        Assert.assertEquals(ligne.getNom(),
                            ligneReturn.getNom());

        // Suppression de Ligne
        this.ligneDao.remove(ligne);
    }

    /**
     * Test de la récupération de tous les Lignes.
     */
    @Test
    public void getAllWithCriteria()
    {
        // Récupération de tous les Ligne
        final ArretSearchCriteria criteria = new ArretSearchCriteria();
        criteria.setActiveOrder("code");
        criteria.setCode("code");
        criteria.setAscending(true);
        criteria.setCaseSensitiveOrder(true);
        final Collection<Ligne> lignes = this.ligneDao.getAll(criteria);

        Assert.assertNotNull(lignes);

        for (final Ligne ligne : lignes)
        {
            Assert.assertNotNull(ligne.getId());
        }
    }

    /**
     * Test de la récupération de tous les Lignes.
     */
    @Test
    public void getAll()
    {
        // Récupération de tous les Ligne
        final ArretSearchCriteria criteria = new ArretSearchCriteria();
        final Collection<Ligne> lignes = this.ligneDao.getAll(criteria);
        Assert.assertNotNull(lignes);
        for (final Ligne ligne : lignes)
        {
            Assert.assertNotNull(ligne.getId());
        }
    }

    /**
     * Test de la méthode de récupération du nombre de résultats.
     */
    @Test
    public void nbResults()
    {
        final ArretSearchCriteria criteria = new ArretSearchCriteria();
        criteria.setCode("code");
        final Integer nbResults = this.ligneDao.nbResults(criteria);
        Assert.assertNotNull(nbResults);
    }

}
