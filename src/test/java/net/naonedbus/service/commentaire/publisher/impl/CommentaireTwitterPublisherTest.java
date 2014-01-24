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


import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.naonedbus.factory.impl.CommentaireFactory;
import net.naonedbus.model.Commentaire;
import net.naonedbus.service.commentaire.decorator.CommentaireDecorator;
import net.naonedbus.service.common.GenericService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Teste la publication des commentaires en base de données.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireTwitterPublisherTest
{
    /**
     * Service des commentaires mocké.
     */
    private GenericService<Commentaire> commentaireServiceMock;

    /**
     * Factory des commentaires mockée.
     */
    private CommentaireFactory commentaireFactoryMock;

    /**
     * Decorator de commentaires mocké.
     */
    private CommentaireDecorator commentaireDecoratorMock;

    /**
     * Mock de l'instance de twitter.
     */
    private Twitter twitterMock;

    /**
     * Publisher à tester.
     */
    private CommentaireTwitterPublisher publisher;

    /**
     * Initialisation.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp()
    {
        this.commentaireServiceMock = Mockito.mock(GenericService.class);
        this.commentaireFactoryMock = Mockito.mock(CommentaireFactory.class);
        this.commentaireDecoratorMock = Mockito.mock(CommentaireDecorator.class);
        this.twitterMock = Mockito.mock(Twitter.class);
        this.publisher = new CommentaireTwitterPublisher();
        this.publisher.setCommentaireService(this.commentaireServiceMock);
        this.publisher.setCommentaireFactory(this.commentaireFactoryMock);
        this.publisher.setTwitter(this.twitterMock);

        final List<CommentaireDecorator> decorators = new ArrayList<CommentaireDecorator>();
        decorators.add(this.commentaireDecoratorMock);
        this.publisher.setCommentaireDecorators(decorators);
    }

    /**
     * Finalisation.
     */
    @After
    public void tearDown()
    {
        this.commentaireServiceMock = null;
        this.commentaireFactoryMock = null;
        this.commentaireDecoratorMock = null;
        this.twitterMock = null;
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
        Assert.assertNotNull(this.commentaireFactoryMock);
        Assert.assertNotNull(this.commentaireDecoratorMock);
        Assert.assertNotNull(this.twitterMock);
    }

    /**
     * Test de la publication sur twitter.
     * @throws TwitterException
     */
    @Test
    public void publish()
        throws TwitterException
    {
        final Commentaire commentaire = new Commentaire();
        commentaire.setId(1);
        final Commentaire commentaireTweet = new Commentaire();
        commentaireTweet.setId(1);
        commentaireTweet.setMessage("test");
        final Status statusMock = Mockito.mock(Status.class);

        Mockito.when(this.commentaireFactoryMock.getInitializedObject(Matchers.anyString(),
                                                                      Matchers.anyString(),
                                                                      Matchers.anyString(),
                                                                      Matchers.anyString(),
                                                                      Matchers.anyString()))
                .thenReturn(commentaireTweet);
        Mockito.when(this.twitterMock.updateStatus("test")).thenReturn(statusMock);
        Mockito.when(statusMock.getId()).thenReturn(2L);

        this.publisher.publish(commentaire);

        Mockito.verify(this.twitterMock).updateStatus(Matchers.anyString());
        Mockito.verify(this.commentaireServiceMock).save(commentaireTweet);
        Assert.assertEquals(Long.valueOf(2L),
                            commentaire.getTweetId());
    }

    /**
     * Test de la publication sur twitter avec une excpetion.
     * @throws TwitterException
     */
    @Test
    public void publishException()
        throws TwitterException
    {
        final Commentaire commentaire = new Commentaire();
        commentaire.setId(1);
        final Commentaire commentaireTweet = new Commentaire();
        commentaireTweet.setId(1);
        commentaireTweet.setMessage("test");
        final Status statusMock = Mockito.mock(Status.class);

        Mockito.when(this.commentaireFactoryMock.getInitializedObject(Matchers.anyString(),
                                                                      Matchers.anyString(),
                                                                      Matchers.anyString(),
                                                                      Matchers.anyString(),
                                                                      Matchers.anyString()))
                .thenReturn(commentaireTweet);
        Mockito.when(this.twitterMock.updateStatus("test"))
                .thenThrow(new TwitterException("Exception twitter"));

        this.publisher.publish(commentaire);
        Mockito.verify(this.twitterMock).updateStatus(Matchers.anyString());
        Mockito.verify(this.commentaireServiceMock,
                       Mockito.never()).save(commentaireTweet);
    }
}
