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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.factory.impl.CommentaireFactory;
import net.naonedbus.model.Commentaire;
import net.naonedbus.model.criteria.impl.CommentaireSearchCriteria;
import net.naonedbus.service.commentaire.destroyer.CommentaireDestroyer;
import net.naonedbus.service.commentaire.publisher.CommentairePublisher;
import net.naonedbus.service.commentaire.retriever.CommentaireRetriever;
import net.naonedbus.service.common.impl.GenericServiceImpl;
import net.naonedbus.service.metier.CommentaireService;
import net.naonedbus.utils.SecurityHelper;
import net.naonedbus.utils.comparator.GenericComparator;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.apache.commons.collections.ComparatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Service spécifiques aux commentaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireServiceImpl
    extends GenericServiceImpl<Commentaire>
    implements CommentaireService, BeanFactoryAware
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -8618750969355772324L;

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Factory Spring.
     */
    private BeanFactory beanFactory;

    /**
     * Factory des commentaires.
     */
    @Resource(name = "commentaireFactory")
    private CommentaireFactory commentaireFactory;

    /**
     * Retrievers des commentaires.
     */
    @Resource(name = "commentaireRetrievers")
    private List<CommentaireRetriever> commentaireRetrievers;

    /**
     * Liste des publishers de commentaire.
     */
    @Resource(name = "commentairePublishers")
    private Map<String, List<CommentairePublisher>> commentairePublishers;

    /**
     * Liste des destroyers de commentaire.
     */
    @Resource(name = "commentaireDestroyers")
    private Map<String, List<CommentaireDestroyer>> commentaireDestroyers;

    /**
     * Helper de sécurité.
     */
    @Resource(name = "securityHelper")
    private SecurityHelper securityHelper;

    /**
     * Constructeur.
     * @param commentaireDao DAO des commentaires
     */
    public CommentaireServiceImpl(final GenericDao<Commentaire> commentaireDao)
    {
        super(commentaireDao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void manageEncryptedMessage(final String codeLigne,
                                       final String codeSens,
                                       final String codeArret,
                                       final String message,
                                       final String cryptedHash,
                                       final String idClient)
        throws NaonedbusException
    {
        if (this.securityHelper.validate(idClient,
                                         cryptedHash,
                                         codeLigne,
                                         codeSens,
                                         codeArret,
                                         message))
        {
            final Commentaire commentaire =
                this.commentaireFactory.getInitializedObject(codeLigne,
                                                             codeSens,
                                                             codeArret,
                                                             idClient,
                                                             message);
            this.postCommentaire(commentaire);
        }
        else
        {
            this.log.error("Soumission d'un commentaire avec une signature non valide pour le client "
                           + idClient);
            throw new NaonedbusException();
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void manageSignedMessage(final String codeLigne,
                                    final String codeSens,
                                    final String codeArret,
                                    final String message,
                                    final String signedMessage,
                                    final String idClient)
        throws NaonedbusException
    {
        if (this.securityHelper.validateSignedMessage(idClient,
                                                      signedMessage,
                                                      codeLigne,
                                                      codeSens,
                                                      codeArret,
                                                      message))
        {
            final Commentaire commentaire = this.commentaireFactory.getInitializedObject(codeLigne,
                                                                                         codeSens,
                                                                                         codeArret,
                                                                                         idClient,
                                                                                         message);
            this.postCommentaire(commentaire);
        }
        else
        {
            this.log.error("Soumission d'un commentaire avec une signature non valide pour le client "
                           + idClient);
            throw new NaonedbusException();
        }
    }

    /**
     * {@inheritDoc}
     * @throws NaonedbusException
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Commentaire> get(final String codeLigne,
                                 final String codeSens,
                                 final String codeArret,
                                 final long timestamp,
                                 final int limit)
        throws NaonedbusException
    {
        final Calendar datePublication = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                              NaonedbusConstants.LOCALE);
        datePublication.setTimeInMillis(timestamp);

        final CommentaireSearchCriteria commentaireCriteria =
            (CommentaireSearchCriteria) this.beanFactory.getBean("commentaireSearchCriteria");
        commentaireCriteria.setMaxResults(limit);
        commentaireCriteria.setCodeLigne(codeLigne);
        commentaireCriteria.setCodeSens(codeSens);
        commentaireCriteria.setCodeArret(codeArret);
        commentaireCriteria.setDatePublication(datePublication);

        List<Commentaire> commentaires = new ArrayList<Commentaire>();
        for (final CommentaireRetriever retriever : this.commentaireRetrievers)
        {
            commentaires.addAll(retriever.retrieve(commentaireCriteria));
        }
        Collections.sort(commentaires,
                         ComparatorUtils
                                 .reversedComparator(new GenericComparator<Object>("timestamp")));

        if (commentaires.size() > limit)
        {
            commentaires = commentaires.subList(0,
                                                limit);
        }

        return commentaires;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final int id,
                       final String signedMessage,
                       final String idClient)
        throws NaonedbusException
    {
        if (this.securityHelper.validateSignedMessage(idClient,
                                                      signedMessage,
                                                      String.valueOf(id)))
        {
            final Commentaire commentaire = this.get(id);
            final List<CommentaireDestroyer> destroyers =
                this.commentaireDestroyers.get(commentaire.getSource());
            for (final CommentaireDestroyer destroyer : destroyers)
            {
                destroyer.destroy(commentaire);
            }
        }
        else
        {
            this.log.error("Tentative de suppression du commentaire "
                           + id
                           + " avec un hash non valide de la part du client "
                           + idClient);
            throw new NaonedbusException();
        }
    }

    /**
     * Méthode en charge de poster le commentaire.
     * @param commentaire Commentaire envoyé par l'utilisateur
     */
    void postCommentaire(final Commentaire commentaire)
    {
        final List<CommentairePublisher> publishers =
            this.commentairePublishers.get(commentaire.getSource());
        for (final CommentairePublisher publisher : publishers)
        {
            publisher.publish(commentaire);
        }
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
     * Setter pour securityHelper.
     * @param securityHelper Le securityHelper à écrire.
     */
    public void setSecurityHelper(final SecurityHelper securityHelper)
    {
        this.securityHelper = securityHelper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanFactory(final BeanFactory beanFactory)
        throws BeansException
    {
        this.beanFactory = beanFactory;
    }

    /**
     * Getter pour commentaireRetrievers.
     * @return Le commentaireRetrievers
     */
    public List<CommentaireRetriever> getCommentaireRetrievers()
    {
        return this.commentaireRetrievers;
    }

    /**
     * Setter pour commentaireRetrievers.
     * @param commentaireRetrievers Le commentaireRetrievers à écrire.
     */
    public void setCommentaireRetrievers(final List<CommentaireRetriever> commentaireRetrievers)
    {
        this.commentaireRetrievers = commentaireRetrievers;
    }

    /**
     * Setter pour commentairePublishers.
     * @param commentairePublishers Le commentairePublishers à écrire.
     */
    public void setCommentairePublishers(final Map<String, List<CommentairePublisher>> commentairePublishers)
    {
        this.commentairePublishers = commentairePublishers;
    }

    /**
     * Setter pour commentaireDestroyers.
     * @param commentaireDestroyers Le commentaireDestroyers à écrire.
     */
    public void setCommentaireDestroyers(final Map<String, List<CommentaireDestroyer>> commentaireDestroyers)
    {
        this.commentaireDestroyers = commentaireDestroyers;
    }

}
