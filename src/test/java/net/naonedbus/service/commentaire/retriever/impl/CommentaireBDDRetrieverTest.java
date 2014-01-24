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


import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.model.Commentaire;
import net.naonedbus.model.criteria.impl.CommentaireSearchCriteria;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test le retriever de commentaire allant voir en base de données.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireBDDRetrieverTest
{
    /**
     * Mock de GenericDao.
     */
    private GenericDao<Commentaire> commentaireDaoMock;

    /**
     * Classe à tester.
     */
    private CommentaireBDDRetriever retriever;

    /**
     * Initialisation.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp()
    {
        this.commentaireDaoMock = Mockito.mock(GenericDao.class);

        this.retriever = new CommentaireBDDRetriever();
        this.retriever.setCommentaireDao(this.commentaireDaoMock);
    }

    /**
     * Finalisation.
     */
    @After
    public void tearDown()
    {
        this.commentaireDaoMock = null;
        this.retriever = null;
    }

    /**
     * Vérification initialisation correcte.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.retriever);
        Assert.assertNotNull(this.retriever.getCommentaireDao());
    }

    /**
     * Test de la méthode de récupération des commentaires.
     */
    @Test
    public void testRetrieve()
    {
        final CommentaireSearchCriteria commentaireSearchCriteria =
            new CommentaireSearchCriteria();
        final List<Commentaire> commentaires = new ArrayList<Commentaire>();
        Mockito.when(this.commentaireDaoMock.getAll(commentaireSearchCriteria))
                .thenReturn(commentaires);

        final List<Commentaire> ret = this.retriever.retrieve(commentaireSearchCriteria);

        Assert.assertEquals(commentaires,
                            ret);
    }
}
