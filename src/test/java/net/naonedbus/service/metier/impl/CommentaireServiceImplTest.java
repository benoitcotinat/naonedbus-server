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
package net.naonedbus.service.metier.impl;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.factory.impl.CommentaireFactory;
import net.naonedbus.model.Commentaire;
import net.naonedbus.service.commentaire.publisher.CommentairePublisher;
import net.naonedbus.service.commentaire.retriever.CommentaireRetriever;
import net.naonedbus.utils.SecurityHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentaireServiceImplTest
{
    @Mock
    private BeanFactory beanFactory;
    @Mock
    private GenericDao<Commentaire> commentaireDao;
    @Mock
    private CommentaireFactory commentaireFactory;
    @Mock
    private List<CommentaireRetriever> commentaireRetrievers;
    @Mock
    private GenericDao<Commentaire> genericDao;
    @Mock
    private SecurityHelper securityHelper;
    @Mock
    private CommentairePublisher commentairePublisher;

    private CommentaireServiceImpl commentaireServiceImpl;
    private List<CommentairePublisher> publishers;
    private Map<String, List<CommentairePublisher>> commentairePublishers;
    private Commentaire commentaire;

    @Before
    public void createCommentaireServiceImpl()
    {
        this.commentaireServiceImpl = new CommentaireServiceImpl(this.commentaireDao);
        this.commentaireServiceImpl.setBeanFactory(this.beanFactory);
        this.commentaireServiceImpl.setCommentaireFactory(this.commentaireFactory);
        this.commentaireServiceImpl.setCommentairePublishers(this.commentairePublishers);
        this.commentaireServiceImpl.setCommentaireRetrievers(this.commentaireRetrievers);
        this.commentaireServiceImpl.setGenericDao(this.genericDao);
        this.commentaireServiceImpl.setSecurityHelper(this.securityHelper);
    }

    /**
     */
    @Before
    public void setUp()
    {
        this.commentaire = new Commentaire();
        this.commentaire.setId(1);

        this.publishers = new ArrayList<CommentairePublisher>();
        this.publishers.add(this.commentairePublisher);

        this.commentairePublishers = new HashMap<String, List<CommentairePublisher>>();
        this.commentairePublishers.put("TEST",
                                       this.publishers);

        Mockito.when(this.genericDao.reattach(this.commentaire)).thenReturn(this.commentaire);
    }

    /**
     */
    @After
    public void tearDown()
    {
    }

    /**
     * Test post commentaire avec source.
     */
    @Test
    public void postCommentaireAvecSource()
    {
        this.commentaire.setSource("TEST");

        this.commentaireServiceImpl.postCommentaire(this.commentaire);

        Mockito.verify(this.commentairePublisher).publish(this.commentaire);
    }

    /**
     * Test post commentaire sans source.
     */
    @Test(expected = NullPointerException.class)
    public void postCommentaireSansSource()
    {
        this.commentaire.setSource("COM");

        this.commentaireServiceImpl.postCommentaire(this.commentaire);
    }

    /**
     * Test de delete non validé au niveau de la sécurité.
     * @throws NaonedbusException
     */
    @Test(expected = NaonedbusException.class)
    public void deleteInvalid()
        throws NaonedbusException
    {
        Mockito.when(this.securityHelper.validate(Matchers.anyString(),
                                                  Matchers.anyString(),
                                                  Matchers.anyString())).thenReturn(false);

        this.commentaireServiceImpl.delete(1,
                                           "HASH",
                                           "CLI");
    }

    /**
     * Test de delete.
     * @throws NaonedbusException
     */
    @Test
    public void deleteValid()
        throws NaonedbusException
    {
        Mockito.when(this.securityHelper.validate(Matchers.anyString(),
                                                  Matchers.anyString(),
                                                  Matchers.anyString())).thenReturn(true);
        Mockito.when(this.genericDao.get(1)).thenReturn(this.commentaire);

        this.commentaireServiceImpl.delete(1,
                                           "HASH",
                                           "CLI");

        Mockito.verify(this.genericDao).get(1);
        Mockito.verify(this.genericDao).remove(this.commentaire);
    }
}
