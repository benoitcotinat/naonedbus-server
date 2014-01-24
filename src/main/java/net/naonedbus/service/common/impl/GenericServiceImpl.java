package net.naonedbus.service.common.impl;

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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.dao.criteria.SearchCriteria;
import net.naonedbus.model.BeanObject;
import net.naonedbus.service.common.GenericService;

/**
 * Implémentation du manager générique pour l'utilisation standard des POJOs (CRUD).
 * @author Benoît
 * @version $Revision$ $Date$
 * @param <BEAN> Bean Objet Métier.
 */
public class GenericServiceImpl<BEAN extends BeanObject>
    implements GenericService<BEAN>, Serializable
{
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 7232463242497005969L;

    /**
     * Instance de la DAO générique, définie dans le constructeur.
     */
    private GenericDao<BEAN> genericDao;

    /**
     * Constructeur principal pour la création de nouvelle implémentation de manager, plus
     * spécifique.
     * @param genericDao La DAO à utiliser.
     */
    public GenericServiceImpl(final GenericDao<BEAN> genericDao)
    {
        this.setGenericDao(genericDao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dettach(final BEAN bean)
    {
        this.getGenericDao().dettach(bean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BEAN get(final Integer id)
    {
        return this.getGenericDao().get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BEAN get(final SearchCriteria criteria)
    {
        final List<BEAN> beans = this.genericDao.getAll(criteria);
        BEAN bean = null;
        if (beans.size() > 0)
        {
            bean = beans.get(0);
        }
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BEAN> getAll(final SearchCriteria criteria)
    {
        return this.genericDao.getAll(criteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BEAN> getAll()
    {
        return this.genericDao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int nbResults(final SearchCriteria criteria)
    {
        return this.genericDao.nbResults(criteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BEAN reattach(final BEAN bean)
    {
        return this.genericDao.reattach(bean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BEAN> reattachAll(final List<BEAN> beans)
    {
        if (beans == null)
        {
            return beans;
        }

        final List<BEAN> res = new ArrayList<BEAN>();
        for (final BEAN bean : beans)
        {
            res.add(this.reattach(bean));
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(final BEAN object)
    {
        final BEAN bean = this.reattach(object);
        this.genericDao.remove(bean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(final List<BEAN> objects)
    {
        for (final BEAN bean : objects)
        {
            this.remove(bean);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BEAN save(final BEAN object)
    {
        return this.genericDao.save(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BEAN> saveAll(final List<BEAN> beans)
    {
        final List<BEAN> savedBeans = new ArrayList<BEAN>();
        if (beans != null)
        {
            for (final BEAN bean : beans)
            {
                final BEAN savedBean = this.save(bean);
                savedBeans.add(savedBean);
            }
        }
        return savedBeans;
    }

    /**
     * Getter pour genericDao.
     * @return Retourne le genericDao.
     */
    public GenericDao<BEAN> getGenericDao()
    {
        return this.genericDao;
    }

    /**
     * Setter pour genericDao.
     * @param genericDao le genericDao à écrire.
     */
    public void setGenericDao(final GenericDao<BEAN> genericDao)
    {
        this.genericDao = genericDao;
    }

}
