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
package net.naonedbus.service.commentaire.publisher.impl;

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
import net.naonedbus.service.common.GenericService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Teste la publication des commentaires en base de données.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireBDDPublisherTest
{
    /**
     * Service des commentaires mocké.
     */
    private GenericService<Commentaire> commentaireServiceMock;

    /**
     * Publisher à tester.
     */
    private CommentaireBDDPublisher publisher;

    /**
     * Initialisation.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp()
    {
        this.commentaireServiceMock = Mockito.mock(GenericService.class);
        this.publisher = new CommentaireBDDPublisher();
        this.publisher.setCommentaireService(this.commentaireServiceMock);
    }

    /**
     * Finalisation.
     */
    @After
    public void tearDown()
    {
        this.commentaireServiceMock = null;
        this.publisher = null;
    }

    /**
     * Vérification de l'initialisation.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.publisher);
        Assert.assertNotNull(this.commentaireServiceMock);
    }

    /**
     * Test de la publication.
     */
    @Test
    public void testPublish()
    {
        final Commentaire commentaire = new Commentaire();
        commentaire.setId(1);

        this.publisher.publish(commentaire);

        Mockito.verify(this.commentaireServiceMock).save(commentaire);
    }
}
