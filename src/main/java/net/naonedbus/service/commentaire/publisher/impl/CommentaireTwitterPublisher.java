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


import java.util.List;

import javax.annotation.Resource;

import net.naonedbus.factory.impl.CommentaireFactory;
import net.naonedbus.model.Commentaire;
import net.naonedbus.service.commentaire.decorator.CommentaireDecorator;
import net.naonedbus.service.commentaire.publisher.CommentairePublisher;
import net.naonedbus.service.common.GenericService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireTwitterPublisher
    implements CommentairePublisher
{

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Factory des commentaires.
     */
    @Resource(name = "commentaireFactory")
    private CommentaireFactory commentaireFactory;

    /**
     * Gestionnaire de connexion à twitter.
     */
    @Resource(name = "tanNantesTwitter")
    private Twitter twitter;

    /**
     * Liste des décorators du message du commentaire.
     */
    @Resource(name = "commentaireDecorators")
    private List<CommentaireDecorator> commentaireDecorators;

    /**
     * Service des commentaires.
     */
    @Resource(name = "commentaireService")
    private GenericService<Commentaire> commentaireService;

    /** Indique s'il faut activer la publication de tweets. */
    private boolean twitterActivated;

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(final Commentaire commentaire)
    {
        final Commentaire twitterCommentaire =
            this.commentaireFactory.getInitializedObject(commentaire.getCodeLigne(),
                                                         commentaire.getCodeSens(),
                                                         commentaire.getCodeArret(),
                                                         commentaire.getSource(),
                                                         commentaire.getMessage());
        try
        {
            for (final CommentaireDecorator commDecorator : this.commentaireDecorators)
            {
                commDecorator.decorate(twitterCommentaire);
            }

            if (this.isTwitterActivated())
            {
                final Status status = this.twitter.updateStatus(twitterCommentaire.getMessage());

                commentaire.setTweetId(status.getId());
                this.commentaireService.save(commentaire);
            }
        }
        catch (final TwitterException e)
        {
            this.log.error("Erreur lors du tweet du commentaire : "
                           + e.getMessage());
        }
    }

    /**
     * Setter pour twitter.
     * @param twitter Le twitter à écrire.
     */
    public void setTwitter(final Twitter twitter)
    {
        this.twitter = twitter;
    }

    /**
     * Setter pour commentaireDecorators.
     * @param commentaireDecorators Le commentaireDecorators à écrire.
     */
    public void setCommentaireDecorators(final List<CommentaireDecorator> commentaireDecorators)
    {
        this.commentaireDecorators = commentaireDecorators;
    }

    /**
     * Setter pour commentaireFactory.
     * @param commentaireFactory Le commentaireFactory à écrire.
     */
    public void setCommentaireFactory(final CommentaireFactory commentaireFactory)
    {
        this.commentaireFactory = commentaireFactory;
    }

    /**
     * Setter pour commentaireService.
     * @param commentaireService Le commentaireService à écrire.
     */
    public void setCommentaireService(final GenericService<Commentaire> commentaireService)
    {
        this.commentaireService = commentaireService;
    }

    public boolean isTwitterActivated()
    {
        return this.twitterActivated;
    }

    public void setTwitterActivated(final boolean twitterActivated)
    {
        this.twitterActivated = twitterActivated;
    }

}
