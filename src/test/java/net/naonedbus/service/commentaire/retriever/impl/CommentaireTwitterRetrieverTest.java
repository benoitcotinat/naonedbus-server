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
package net.naonedbus.service.commentaire.retriever.impl;

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


import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.model.Commentaire;
import net.naonedbus.service.commentaire.transformer.TwitterCommentaireTransformer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Test de la récupération de statuts twitter.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireTwitterRetrieverTest
{
    /**
     * Classe à tester.
     */
    private CommentaireTwitterRetriever retriever;

    /**
     * Mock de la gestion de twitter.
     */
    private Twitter twitterMock;

    /**
     * Mock de transformer.
     */
    private TwitterCommentaireTransformer twitterCommentaireTransformerMock;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp()
        throws Exception
    {
        this.twitterCommentaireTransformerMock =
            Mockito.mock(TwitterCommentaireTransformer.class);
        this.twitterMock = Mockito.mock(Twitter.class);

        this.retriever = new CommentaireTwitterRetriever();
        this.retriever.setAccounts(Arrays.asList("tan_trafic"));
        this.retriever.setTwitter(this.twitterMock);
        this.retriever.setTwitterCommentaireTransformer(this.twitterCommentaireTransformerMock);
    }

    /**
     */
    @After
    public void tearDown()
    {
        this.twitterCommentaireTransformerMock = null;
        this.twitterMock = null;
        this.retriever = null;
    }

    /**
     * Teste la récupération d'un flux twitter.
     * @throws NaonedbusException
     * @throws TwitterException
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testRetrieve()
        throws NaonedbusException,
            TwitterException
    {
        final Commentaire commentaire = new Commentaire();
        Mockito.when(this.twitterCommentaireTransformerMock.transform(Matchers.any(Status.class)))
                .thenReturn(commentaire);
        final ResponseList<Status> responseList = Mockito.mock(ResponseList.class);
        Mockito.doReturn(new Status[]
        {Mockito.mock(Status.class) }).when(responseList).toArray();
        Mockito.when(this.twitterMock.getUserTimeline(Matchers.anyString()))
                .thenReturn(responseList);

        final List<Commentaire> ret = this.retriever.retrieve(null);

        Assert.assertNotNull(ret);
        Assert.assertFalse(ret.isEmpty());
    }

    /**
     * Teste la récupération d'un flux twitter .
     * @throws NaonedbusException
     * @throws TwitterException
     */
    @Test(expected = NaonedbusException.class)
    public void testRetrieveWithError()
        throws NaonedbusException,
            TwitterException
    {
        Mockito.when(this.twitterMock.getUserTimeline(Matchers.anyString()))
                .thenThrow(new TwitterException(""));

        this.retriever.retrieve(null);
    }

}
