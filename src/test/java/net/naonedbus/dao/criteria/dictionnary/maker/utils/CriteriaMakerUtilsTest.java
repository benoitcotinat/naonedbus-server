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
package net.naonedbus.dao.criteria.dictionnary.maker.utils;

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
import net.naonedbus.model.Ligne;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Classe en charge de tester l'utilitaire de maker de critère de recherche.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CriteriaMakerUtilsTest
{
    /**
     * CriteriaMakerUtils à tester.
     */
    private CriteriaMakerUtils criteriaMakerUtils;

    /**
     * Méthode en charge d'initialiser les données de test.
     */
    @Before
    public void init()
    {
        this.criteriaMakerUtils = new CriteriaMakerUtils();
    }

    /**
     * Méthode en charge de purger les données de test.
     */
    @After
    public void end()
    {
        this.criteriaMakerUtils = null;
    }

    /**
     * Méthode en charge de tester l'initialisation des données de test.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.criteriaMakerUtils);
    }

    /**
     * Méthode en charge de tester l'ajout de critère sql.
     */
    @Test
    public void testAddCritereCriteria()
    {
        final Criteria criteria = Mockito.mock(Criteria.class);
        CriteriaMakerUtils.addCritere(criteria,
                                      "field",
                                      "value");

        Mockito.verify(criteria).add((Criterion) Matchers.any());
    }

    /**
     * Méthode en charge de tester l'ajout de critère sql.
     */
    @Test
    public void testAddCritereDisjunction()
    {
        final Disjunction dij = Mockito.mock(Disjunction.class);
        CriteriaMakerUtils.addCritere(dij,
                                      "field",
                                      "value");

        Mockito.verify(dij).add((Criterion) Matchers.any());
    }

    /**
     * Méthode en charge de tester l'ajout de critère sql.
     */
    @Test
    public void testAddInCritereCriteria()
    {
        final Criteria criteria = Mockito.mock(Criteria.class);
        CriteriaMakerUtils.addInCritere(criteria,
                                        "field",
                                        new Object[]
                                        {"value" });

        Mockito.verify(criteria).add((Criterion) Matchers.any());
    }

    /**
     * Méthode en charge de tester l'ajout de critère sql.
     */
    @Test
    public void testAddInCritereDisjunction()
    {
        final Disjunction dij = Mockito.mock(Disjunction.class);
        CriteriaMakerUtils.addInCritere(dij,
                                        "field",
                                        new Long[]
                                        {1L });

        Mockito.verify(dij).add((Criterion) Matchers.any());
    }

    /**
     * Méthode en charge de tester l'ajout de critère sql.
     */
    @Test
    public void testAddSqlCritereCriteria()
    {
        final Criteria criteria = Mockito.mock(Criteria.class);
        CriteriaMakerUtils.addSqlCritere(criteria,
                                         "field",
                                         "value");

        Mockito.verify(criteria).add((Criterion) Matchers.any());
    }

    /**
     * Méthode en charge de tester l'ajout de critère sql.
     */
    @Test
    public void testAddSqlCritereDisjunction()
    {
        final Disjunction dij = Mockito.mock(Disjunction.class);
        CriteriaMakerUtils.addSqlCritere(dij,
                                         "field",
                                         "value");

        Mockito.verify(dij).add((Criterion) Matchers.any());
    }

    /**
     * Méthode en charge de tester la construction de la liste des identifiants techniques de la
     * liste d'objets.
     */
    @Test
    public void testPrepareObjectIds()
    {
        final Ligne theme1 = new Ligne();
        theme1.setId(1);
        final Ligne theme2 = new Ligne();
        theme2.setId(2);
        final List<Ligne> themes = new ArrayList<Ligne>();
        themes.add(theme1);
        themes.add(theme2);
        final List<Long> resultsInteger = CriteriaMakerUtils.prepareObjectIds(themes);
        Assert.assertEquals(new Integer(1),
                            resultsInteger.get(0));
        Assert.assertEquals(new Integer(2),
                            resultsInteger.get(1));
    }

    /**
     * Méthode en charge de tester la transformation de chaîne.
     */
    @Test
    public void testTransformForSql()
    {
        final String retour = CriteriaMakerUtils.transformForSql("aeiouy");
        Assert.assertEquals("%[AÀÄÂaàäâ][EÉÈÊËeéèêë][IÎÏiîï][OÔÖoôö][UÛÜuûü]Y%",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du A.
     */
    @Test
    public void testHandleA1()
    {
        final String retour = CriteriaMakerUtils.handleA('A');
        Assert.assertEquals("[AÀÄÂaàäâ]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du À.
     */
    @Test
    public void testHandleA2()
    {
        final String retour = CriteriaMakerUtils.handleA('À');
        Assert.assertEquals("[AÀÄÂaàäâ]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du Ä.
     */
    @Test
    public void testHandleA3()
    {
        final String retour = CriteriaMakerUtils.handleA('Ä');
        Assert.assertEquals("[AÀÄÂaàäâ]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du Â.
     */
    @Test
    public void testHandleA4()
    {
        final String retour = CriteriaMakerUtils.handleA('Â');
        Assert.assertEquals("[AÀÄÂaàäâ]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du A KO.
     */
    @Test
    public void testHandleAKO()
    {
        final String retour = CriteriaMakerUtils.handleA('x');
        Assert.assertEquals(StringUtils.EMPTY,
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du E.
     */
    @Test
    public void testHandleE1()
    {
        final String retour = CriteriaMakerUtils.handleE('E');
        Assert.assertEquals("[EÉÈÊËeéèêë]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du É.
     */
    @Test
    public void testHandleE2()
    {
        final String retour = CriteriaMakerUtils.handleE('É');
        Assert.assertEquals("[EÉÈÊËeéèêë]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du È.
     */
    @Test
    public void testHandleE3()
    {
        final String retour = CriteriaMakerUtils.handleE('È');
        Assert.assertEquals("[EÉÈÊËeéèêë]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du Ê.
     */
    @Test
    public void testHandleE4()
    {
        final String retour = CriteriaMakerUtils.handleE('Ê');
        Assert.assertEquals("[EÉÈÊËeéèêë]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du Ë.
     */
    @Test
    public void testHandleE5()
    {
        final String retour = CriteriaMakerUtils.handleE('Ë');
        Assert.assertEquals("[EÉÈÊËeéèêë]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du E KO.
     */
    @Test
    public void testHandleEKO()
    {
        final String retour = CriteriaMakerUtils.handleE('x');
        Assert.assertEquals(StringUtils.EMPTY,
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du I.
     */
    @Test
    public void testHandleI1()
    {
        final String retour = CriteriaMakerUtils.handleI('I');
        Assert.assertEquals("[IÎÏiîï]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du Î.
     */
    @Test
    public void testHandleI2()
    {
        final String retour = CriteriaMakerUtils.handleI('Î');
        Assert.assertEquals("[IÎÏiîï]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du Ï.
     */
    @Test
    public void testHandleI3()
    {
        final String retour = CriteriaMakerUtils.handleI('Ï');
        Assert.assertEquals("[IÎÏiîï]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du I KO.
     */
    @Test
    public void testHandleIKO()
    {
        final String retour = CriteriaMakerUtils.handleI('x');
        Assert.assertEquals(StringUtils.EMPTY,
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du O.
     */
    @Test
    public void testHandleO1()
    {
        final String retour = CriteriaMakerUtils.handleO('O');
        Assert.assertEquals("[OÔÖoôö]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du Ô.
     */
    @Test
    public void testHandleO2()
    {
        final String retour = CriteriaMakerUtils.handleO('Ô');
        Assert.assertEquals("[OÔÖoôö]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du Ö.
     */
    @Test
    public void testHandleO3()
    {
        final String retour = CriteriaMakerUtils.handleO('Ö');
        Assert.assertEquals("[OÔÖoôö]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du O KO.
     */
    @Test
    public void testHandleOKO()
    {
        final String retour = CriteriaMakerUtils.handleO('x');
        Assert.assertEquals(StringUtils.EMPTY,
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du U.
     */
    @Test
    public void testHandleU1()
    {
        final String retour = CriteriaMakerUtils.handleU('U');
        Assert.assertEquals("[UÛÜuûü]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du Û.
     */
    @Test
    public void testHandleU2()
    {
        final String retour = CriteriaMakerUtils.handleU('Û');
        Assert.assertEquals("[UÛÜuûü]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du Ü.
     */
    @Test
    public void testHandleU3()
    {
        final String retour = CriteriaMakerUtils.handleU('Ü');
        Assert.assertEquals("[UÛÜuûü]",
                            retour);
    }

    /**
     * Méthode en charge de traiter la méthode de gestion du U KO.
     */
    @Test
    public void testHandleUKO()
    {
        final String retour = CriteriaMakerUtils.handleU('x');
        Assert.assertEquals(StringUtils.EMPTY,
                            retour);
    }

}
