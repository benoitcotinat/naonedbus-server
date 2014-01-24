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


import java.util.List;

import javax.annotation.Resource;

import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.model.Commentaire;
import net.naonedbus.model.criteria.impl.CommentaireSearchCriteria;
import net.naonedbus.service.commentaire.retriever.CommentaireRetriever;

/**
 * Retriever de commentaire recherchant en base de données.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireBDDRetriever
    implements CommentaireRetriever
{
    /**
     * DAO des commentaires.
     */
    @Resource(name = "commentaireDao")
    private GenericDao<Commentaire> commentaireDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Commentaire> retrieve(final CommentaireSearchCriteria commentaireCriteria)
    {
        final List<Commentaire> commentaires = this.commentaireDao.getAll(commentaireCriteria);

        return commentaires;
    }

    /**
     * Setter pour commentaireDao.
     * @param commentaireDao Le commentaireDao à écrire.
     */
    public void setCommentaireDao(final GenericDao<Commentaire> commentaireDao)
    {
        this.commentaireDao = commentaireDao;
    }

    /**
     * Getter pour commentaireDao.
     * @return Le commentaireDao
     */
    public GenericDao<Commentaire> getCommentaireDao()
    {
        return this.commentaireDao;
    }

}
