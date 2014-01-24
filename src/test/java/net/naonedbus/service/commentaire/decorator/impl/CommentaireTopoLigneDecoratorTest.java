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
import net.naonedbus.model.Commentaire;
import net.naonedbus.model.Ligne;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test l'ajout des infos de la ligne.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireTopoLigneDecoratorTest
{

    /**
     * Décorator à tester.
     */
    private CommentaireTopoLigneDecorator decorator;

    /**
     * Initialisation.
     */
    @Before
    public void setUp()
    {
        this.decorator = new CommentaireTopoLigneDecorator();
    }

    /**
     * Finalisation
     */
    @After
    public void tearDown()
    {
        this.decorator = null;
    }

    /**
     * Vérification de l'initialisation.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.decorator);
    }

    /**
     * Test la décoration avec une info de topo sur le commentaire.
     */
    @Test
    public void testDecorateWithTopo()
    {
        final Ligne ligne = new Ligne();
        ligne.setCode("TEST");
        final Commentaire commentaire = new Commentaire();
        commentaire.setLigne(ligne);
        commentaire.setMessage("Test message");

        this.decorator.decorate(commentaire);

        Assert.assertTrue(commentaire.getMessage().startsWith(NaonedbusConstants.TOPO_LIGNE
                                                              + "TEST"));
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
