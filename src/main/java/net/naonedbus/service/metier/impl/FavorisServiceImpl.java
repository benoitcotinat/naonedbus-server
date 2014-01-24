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


import java.io.Serializable;

import javax.annotation.Resource;

import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.factory.impl.FavorisFactory;
import net.naonedbus.model.Favoris;
import net.naonedbus.model.criteria.impl.FavorisSearchCriteria;
import net.naonedbus.service.common.impl.GenericServiceImpl;
import net.naonedbus.service.metier.FavorisService;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Service spécifique aux favoris.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class FavorisServiceImpl
    extends GenericServiceImpl<Favoris>
    implements FavorisService, Serializable, BeanFactoryAware
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -6538490475988808407L;

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Factory Spring.
     */
    private BeanFactory beanFactory;

    /**
     * Factory de Favoris.
     */
    @Resource(name = "favorisFactory")
    private FavorisFactory favorisFactory;

    /**
     * Constructeur.
     * @param genericDao DAO des favoris
     */
    public FavorisServiceImpl(final GenericDao<Favoris> genericDao)
    {
        super(genericDao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Favoris get(final String identifiant)
    {
        final FavorisSearchCriteria favorisCriteria =
            (FavorisSearchCriteria) this.beanFactory.getBean("favorisSearchCriteria");
        favorisCriteria.setIdentifiant(identifiant);
        final Favoris favoris = this.get(favorisCriteria);
        return favoris;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getUniqueIdentifiant()
    {
        String favId;
        Favoris favWithIdExist;
        do
        {
            favId = RandomStringUtils.randomAlphabetic(5).toUpperCase();
            favWithIdExist = this.get(favId);
        } while (favWithIdExist != null);

        return favId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String exporter(final String contenu)
    {
        Favoris favoris = this.favorisFactory.getInitializedObject(contenu);
        favoris = this.save(favoris);
        return favoris.getIdentifiant();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String importer(final String identifiant)
    {
        final Favoris favoris = this.get(identifiant);

        String contenu = null;
        if (null != favoris)
        {
            contenu = favoris.getContenu();
        }
        else
        {
            this.log.error("Import de Favoris avec un identifiant inconnu : "
                           + identifiant);
        }
        return contenu;
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
     * Setter pour favorisFactory.
     * @param favorisFactory Le favorisFactory à écrire.
     */
    public void setFavorisFactory(final FavorisFactory favorisFactory)
    {
        this.favorisFactory = favorisFactory;
    }

}
