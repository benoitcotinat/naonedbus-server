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

import javax.annotation.Resource;

import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.model.Commentaire;
import net.naonedbus.model.criteria.impl.CommentaireSearchCriteria;
import net.naonedbus.service.commentaire.retriever.CommentaireRetriever;
import net.naonedbus.service.commentaire.transformer.TwitterCommentaireTransformer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Retriever de commentaire recherchant sur un compte twitter.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireTwitterRetriever
    implements CommentaireRetriever
{
    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /** Liste des comptes twitter donc il faut récupérer les tweets. */
    @Resource(name = "twitterAccountsToRetrieve")
    private List<String> accounts;

    /**
     * Instance de l'accès à Twitter.
     */
    @Resource(name = "naonedbusTwitter")
    private Twitter twitter;

    /** Transformer de statut Twitter en Commentaire. */
    @Resource(name = "twitterCommentaireTransformer")
    private TwitterCommentaireTransformer twitterCommentaireTransformer;
    /** Liste des prédicats pour filtrer les statuts twitter. */
    @Resource(name = "twitterPredicates")
    private List<Predicate> twitterPredicates;

    /**
     * {@inheritDoc}
     * @throws NaonedbusException En cas d'erreur lors de la récupération des tweets.
     */
    @SuppressWarnings({"rawtypes", "unchecked" })
    @Override
    public List<Commentaire> retrieve(final CommentaireSearchCriteria commentaireCriteria)
    {
        final Paging paging = new Paging(1,
                                         commentaireCriteria.getMaxResults());

        final List<Status> statuses = new ArrayList<Status>();
        for (final String account : this.accounts)
        {
            try
            {
                statuses.addAll(this.twitter.getUserTimeline(account,
                                                             paging));
            }
            catch (final TwitterException e)
            {
                this.log.error("Erreur lors de la récupération des tweets de "
                                       + account,
                               e);
            }
        }

        CollectionUtils.filter(statuses,
                               PredicateUtils.allPredicate(this.twitterPredicates));

        final List commentaires = new ArrayList(statuses);
        CollectionUtils.transform(commentaires,
                                  this.twitterCommentaireTransformer);

        return commentaires;
    }

    /**
     * Setter pour twitterCommentaireTransformer.
     * @param twitterCommentaireTransformer Le twitterCommentaireTransformer à écrire.
     */
    public void setTwitterCommentaireTransformer(final TwitterCommentaireTransformer twitterCommentaireTransformer)
    {
        this.twitterCommentaireTransformer = twitterCommentaireTransformer;
    }
    /**
     * Setter pour twitter.
     * @param twitter Le twitter à écrire.
     */
    public void setTwitter(final Twitter twitter)
    {
        this.twitter = twitter;
    }

    public void setAccounts(final List<String> accounts)
    {
        this.accounts = accounts;
    }

    public void setTwitterPredicates(final List<Predicate> twitterPredicates)
    {
        this.twitterPredicates = twitterPredicates;
    }
}
