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
package net.naonedbus.service.commentaire.transformer;

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


import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.Assert;
import net.naonedbus.factory.impl.CommentaireFactory;
import net.naonedbus.model.Commentaire;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import twitter4j.Status;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class TwitterCommentaireTransformerTest
{
    /**
     * Mock de la factory des commentaires.
     */
    private CommentaireFactory commentaireFactoryMock;

    /**
     * Transformer à tester.
     */
    private TwitterCommentaireTransformer transformer;

    /**
     * Initialisation.
     */
    @Before
    public void setUp()
    {
        this.commentaireFactoryMock = Mockito.mock(CommentaireFactory.class);

        this.transformer = new TwitterCommentaireTransformer();
        this.transformer.setCommentaireFactory(this.commentaireFactoryMock);
    }

    /**
     * Finalisation.
     */
    @After
    public void tearDown()
    {
        this.commentaireFactoryMock = null;
        this.transformer = null;
    }

    /**
     * Vérification de l'initialisation.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.transformer);
        Assert.assertNotNull(this.transformer.getCommentaireFactory());
    }

    /**
     * Test de la transformation avec un objet incorrect.
     */
    @Test
    public void testTransformKOInstance()
    {
        Assert.assertNull(this.transformer.transform(""));
    }

    /**
     * Test de la transformation OK.
     */
    @Test
    public void testTransformOK()
    {
        final Commentaire comm = new Commentaire();

        Mockito.when(this.commentaireFactoryMock.getInitializedObject(Matchers.anyString(),
                                                                      Matchers.anyString(),
                                                                      Matchers.anyString(),
                                                                      Matchers.anyString(),
                                                                      Matchers.anyString()))
                .thenReturn(comm);

        final Calendar cal = new GregorianCalendar(1987,
                                                   11,
                                                   6);
        final Status status = Mockito.mock(Status.class);
        Mockito.when(status.getId()).thenReturn(1L);
        Mockito.when(status.getCreatedAt()).thenReturn(cal.getTime());

        final Object o = this.transformer.transform(status);

        Assert.assertNotNull(o);
        Assert.assertTrue(o instanceof Commentaire);
        final Commentaire commentaire = (Commentaire) o;
        Assert.assertEquals(new Integer(1),
                            commentaire.getId());
        Assert.assertEquals(cal.getTimeInMillis(),
                            commentaire.getTimestamp().longValue());
    }

}
