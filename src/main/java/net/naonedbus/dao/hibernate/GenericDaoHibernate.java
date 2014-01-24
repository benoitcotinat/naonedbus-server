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
package net.naonedbus.dao.hibernate;

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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;

import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.dao.criteria.SearchCriteria;
import net.naonedbus.dao.criteria.dictionnary.CriteriaDictionary;
import net.naonedbus.dao.hibernate.constants.GenericDaoHibernateCstes;
import net.naonedbus.model.BeanObject;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO (Data Access Object) générique sur base Hibernate pour l'utilisation standard des POJOs
 * (CRUD).
 * @param <BEAN> Bean Objet Métier.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class GenericDaoHibernate<BEAN extends BeanObject>
    implements GenericDao<BEAN>
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 6739494877670355935L;

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger(GenericDaoHibernate.class);

    /**
     * La classe héritée pour la persistance de la DAO.
     */
    private final Class<BEAN> persistentClass;

    /**
     * Entité manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Le dictionnaire de gestion des critères de recherche.
     */
    private CriteriaDictionary criteriaDictionary;

    /**
     * Constructeur de la DAO générique.
     * @param persistentClass La classe dont le type est à gérer par la DAO.
     */
    public GenericDaoHibernate(final Class<BEAN> persistentClass)
    {
        this.persistentClass = persistentClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<BEAN> getAll(final SearchCriteria criteria)
    {
        final Criteria crit = this.createCriteria();
        crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        crit.setMaxResults(criteria.getMaxResults());
        this.applySearchCriteria(crit,
                                 criteria);
        this.applySortCriteria(crit,
                               criteria);
        return crit.list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int nbResults(final SearchCriteria criteria)
    {
        final Criteria crit = this.createCriteria();
        crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        this.applySearchCriteria(crit,
                                 criteria);
        crit.setProjection(Projections.rowCount());
        final Object[] result = crit.list().toArray();
        return ((Long) result[0]).intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<BEAN> getAll()
    {
        return this.createCriteria().list();
    }

    /**
     * Méthode de traitement du critère de recherche.
     * @param crit Le critère de recherche.
     * @param criteria Le gestionnaire de critères de recherche.
     */
    protected void applySearchCriteria(final Criteria crit,
                                       final SearchCriteria criteria)
    {
        this.getCriteriaDictionary().apply(crit,
                                           criteria);
    }

    /**
     * Méthode en charge d'ajouter les tris sur une requete.
     * @param crit Criteria Hibernate.
     * @param criteria Critère de recherche.
     */
    protected void applySortCriteria(final Criteria crit,
                                     final SearchCriteria criteria)
    {
        if (criteria.getActiveOrder() != null)
        {
            if (criteria.isAscending())
            {
                final Order order = Order.asc(criteria.getActiveOrder());
                if (!criteria.isCaseSensitiveOrder())
                {
                    order.ignoreCase();
                }
                crit.addOrder(order);
            }
            else
            {
                final Order order = Order.desc(criteria.getActiveOrder());
                if (!criteria.isCaseSensitiveOrder())
                {
                    order.ignoreCase();
                }
                crit.addOrder(order);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BEAN get(final Integer id)
    {
        try
        {
            final BEAN entity = this.entityManager.find(this.persistentClass,
                                                        id);
            return entity;
        }
        catch (final IllegalStateException e)
        {
            this.handleException("get",
                                 e,
                                 GenericDaoHibernateCstes.ENTITY_MANAGER_CLOSED);
        }
        catch (final IllegalArgumentException e)
        {
            this.handleException("get",
                                 e,
                                 GenericDaoHibernateCstes.BEAN_UNMANAGED);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BEAN save(final BEAN object)
    {
        try
        {
            if (object.getId() == null)
            {
                this.entityManager.persist(object);
            }
            else
            {
                return this.entityManager.merge(object);
            }
        }
        catch (final IllegalStateException e)
        {
            this.handleException("save",
                                 e,
                                 GenericDaoHibernateCstes.ENTITY_MANAGER_CLOSED);
        }
        catch (final IllegalArgumentException e)
        {
            this.handleException("save",
                                 e,
                                 GenericDaoHibernateCstes.BEAN_UNMANAGED);
        }
        catch (final TransactionRequiredException e)
        {
            this.handleException("save",
                                 e,
                                 GenericDaoHibernateCstes.NO_TRANSACTION_AVAILABLE);
        }
        catch (final EntityExistsException e)
        {
            this.handleException("save",
                                 e,
                                 GenericDaoHibernateCstes.ENTITY_ERROR_PERSIST);
        }
        return object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void remove(final BEAN object)
    {
        try
        {
            this.reattach(object);
            this.entityManager.remove(object);
        }
        catch (final IllegalStateException e)
        {
            this.handleException("remove",
                                 e,
                                 GenericDaoHibernateCstes.ENTITY_MANAGER_CLOSED);

        }
        catch (final IllegalArgumentException e)
        {
            this.handleException("remove",
                                 e,
                                 GenericDaoHibernateCstes.BEAN_UNMANAGED);
        }
        catch (final TransactionRequiredException e)
        {
            this.handleException("remove",
                                 e,
                                 GenericDaoHibernateCstes.NO_TRANSACTION_AVAILABLE);
        }
    }

    /**
     * Définit un critère de recherche sur la session actuelle.
     * @return Un critère de recherche sur la session actuelle.
     */
    protected Criteria createCriteria()
    {
        final Session session = this.getCurrentSession();
        final Criteria crit = session.createCriteria(this.persistentClass);
        return crit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BEAN reattach(final BEAN bean)
    {
        BEAN beanResult = bean;
        if (bean == null)
        {
            return null;
        }
        if (bean.getId() == null)
        {
            return bean;
        }
        try
        {
            beanResult = this.entityManager.merge(bean);
        }
        catch (final IllegalStateException e)
        {
            this.handleException("reattach",
                                 e,
                                 GenericDaoHibernateCstes.ENTITY_MANAGER_CLOSED);
        }
        catch (final IllegalArgumentException e)
        {
            this.handleException("reattach",
                                 e,
                                 GenericDaoHibernateCstes.BEAN_UNMANAGED);
        }
        catch (final TransactionRequiredException e)
        {
            this.handleException("reattach",
                                 e,
                                 GenericDaoHibernateCstes.NO_TRANSACTION_AVAILABLE);
        }
        return beanResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dettach(final BEAN bean)
    {
        try
        {
            this.entityManager.refresh(bean);
        }
        catch (final IllegalStateException e)
        {
            this.handleException("dettach",
                                 e,
                                 GenericDaoHibernateCstes.ENTITY_MANAGER_CLOSED);
        }
        catch (final IllegalArgumentException e)
        {
            this.handleException("dettach",
                                 e,
                                 GenericDaoHibernateCstes.BEAN_UNMANAGED);
        }
        catch (final TransactionRequiredException e)
        {
            this.handleException("dettach",
                                 e,
                                 GenericDaoHibernateCstes.NO_TRANSACTION_AVAILABLE);
        }
        catch (final EntityNotFoundException e)
        {
            this.handleException("dettach",
                                 e,
                                 GenericDaoHibernateCstes.ENTITY_NOT_FOUND_DB);
        }
    }

    /**
     * Obtention de la session courante.
     * @return Session.
     */
    protected Session getCurrentSession()
    {
        try
        {
            return (Session) this.getEntityManager().getDelegate();
        }
        catch (final IllegalStateException e)
        {
            this.handleException("getCurrentSession",
                                 e,
                                 GenericDaoHibernateCstes.ENTITY_MANAGER_CLOSED);
        }
        return null;
    }

    /**
     * Méthode en charge de gérer une exception en affichant un message.
     * @param nameMethod Nom de la méthode.
     * @param throwable Exception.
     * @param detailMessage Détail du message.
     */
    public void handleException(final String nameMethod,
                                final Throwable throwable,
                                final String detailMessage)
    {
        this.log.error("Exception dans la méthode : "
                       + nameMethod
                       + NaonedbusConstants.NEW_LINE
                       + throwable.getMessage()
                       + NaonedbusConstants.NEW_LINE
                       + detailMessage);
    }

    /**
     * Getter pour criteriaDictionary.
     * @return Retourne le criteriaDictionary.
     */
    public CriteriaDictionary getCriteriaDictionary()
    {
        return this.criteriaDictionary;
    }

    /**
     * Setter pour criteriaDictionary.
     * @param criteriaDictionary le criteriaDictionary à écrire.
     */
    public void setCriteriaDictionary(final CriteriaDictionary criteriaDictionary)
    {
        this.criteriaDictionary = criteriaDictionary;
    }

    /**
     * Récupération de entityManager.
     * @return Retourne le entityManager.
     */
    public EntityManager getEntityManager()
    {
        return this.entityManager;
    }

    /**
     * Affectation du champ entityManager.
     * @param entityManager le entityManager à écrire.
     */
    public void setEntityManager(final EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    /**
     * Getter pour persistentClass.
     * @return Retourne le persistentClass.
     */
    protected Class<BEAN> getPersistentClass()
    {
        return this.persistentClass;
    }
}
