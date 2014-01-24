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


import javax.annotation.Resource;

import net.naonedbus.model.Commentaire;
import net.naonedbus.service.commentaire.publisher.CommentairePublisher;
import net.naonedbus.service.common.GenericService;

/**
 * Classe en charge de sauvegarder en base de données un commentaire.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireBDDPublisher
    implements CommentairePublisher
{
    /**
     * Service des commentaires.
     */
    @Resource(name = "commentaireService")
    private GenericService<Commentaire> commentaireService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(final Commentaire commentaire)
    {
        this.commentaireService.save(commentaire);
    }

    /**
     * Setter pour commentaireService.
     * @param commentaireService Le commentaireService à écrire.
     */
    public void setCommentaireService(final GenericService<Commentaire> commentaireService)
    {
        this.commentaireService = commentaireService;
    }

}
