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
package net.naonedbus.service.commentaire.decorator.impl;

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
import net.naonedbus.dao.criteria.SearchCriteria;
import net.naonedbus.model.Commentaire;
import net.naonedbus.model.Sens;
import net.naonedbus.model.criteria.impl.SensSearchCriteria;
import net.naonedbus.service.common.GenericService;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Test l'ajout des infos de la ligne.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireTopoSensDecoratorTest
{
    /**
     * Service des sens mocké.
     */
    private GenericService<Sens> sensServiceMock;

    /**
     * Décorator à tester.
     */
    private CommentaireTopoSensDecorator decorator;

    /**
     * Initialisation.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp()
    {
        this.sensServiceMock = Mockito.mock(GenericService.class);

        this.decorator = new CommentaireTopoSensDecorator();
        this.decorator.setSensService(this.sensServiceMock);
    }

    /**
     * Finalisation
     */
    @After
    public void tearDown()
    {
        this.sensServiceMock = null;
        this.decorator = null;
    }

    /**
     * Vérification de l'initialisation.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.decorator);
        Assert.assertNotNull(this.sensServiceMock);
    }

    /**
     * Test la décoration avec une info de topo sur le commentaire en raccourcissant son nom.
     */
    @Test
    public void testDecorateWithTopo()
    {
        final Sens sens = new Sens();
        sens.setCode("TEST");
        sens.setNom("sens0123456789");
        Mockito.when(this.sensServiceMock.get(Matchers.any(SearchCriteria.class)))
                .thenAnswer(new Answer<Sens>() {

                    @Override
                    public Sens answer(final InvocationOnMock invocation)
                        throws Throwable
                    {
                        final SensSearchCriteria crit =
                            (SensSearchCriteria) invocation.getArguments()[0];
                        Assert.assertEquals("TEST",
                                            crit.getCode());
                        return sens;
                    }
                });

        final Commentaire commentaire = new Commentaire();
        commentaire.setSens(sens);
        commentaire.setMessage("Test message");

        this.decorator.decorate(commentaire);

        Assert.assertTrue(commentaire.getMessage().startsWith(NaonedbusConstants.SENS
                                                              + "sens0"));
    }

    /**
     * Test la décoration avec une info de topo sur le commentaire.
     */
    @Test
    public void testDecorateWithoutTopo()
    {
        final Commentaire commentaire = new Commentaire();
        commentaire.setMessage("Test message");

        this.decorator.decorate(commentaire);

        Assert.assertEquals("Test message",
                            commentaire.getMessage());
    }
}
