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
import net.naonedbus.model.Arret;
import net.naonedbus.model.Commentaire;
import net.naonedbus.model.criteria.impl.ArretSearchCriteria;
import net.naonedbus.service.common.GenericService;

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
public class CommentaireTopoArretDecoratorTest
{
    /**
     * Service des sens mocké.
     */
    private GenericService<Arret> arretServiceMock;

    /**
     * Décorator à tester.
     */
    private CommentaireTopoArretDecorator decorator;

    /**
     * Initialisation.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp()
    {
        this.arretServiceMock = Mockito.mock(GenericService.class);

        this.decorator = new CommentaireTopoArretDecorator();
        this.decorator.setArretService(this.arretServiceMock);
    }

    /**
     * Finalisation
     */
    @After
    public void tearDown()
    {
        this.arretServiceMock = null;
        this.decorator = null;
    }

    /**
     * Vérification de l'initialisation.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.decorator);
        Assert.assertNotNull(this.arretServiceMock);
    }

    /**
     * Test la décoration avec une info de topo sur le commentaire en raccourcissant son nom.
     */
    @Test
    public void testDecorateWithTopo()
    {
        final Arret arret = new Arret();
        arret.setCode("TEST");
        arret.setNom("Arret0123456789");
        Mockito.when(this.arretServiceMock.get(Matchers.any(SearchCriteria.class)))
                .thenAnswer(new Answer<Arret>() {

                    @Override
                    public Arret answer(final InvocationOnMock invocation)
                        throws Throwable
                    {
                        final ArretSearchCriteria crit =
                            (ArretSearchCriteria) invocation.getArguments()[0];
                        Assert.assertEquals("TEST",
                                            crit.getCode());
                        return arret;
                    }
                });

        final Commentaire commentaire = new Commentaire();
        commentaire.setArret(arret);
        commentaire.setMessage("Test message");

        this.decorator.decorate(commentaire);

        Assert.assertTrue(commentaire.getMessage().startsWith(", Arret"));
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
