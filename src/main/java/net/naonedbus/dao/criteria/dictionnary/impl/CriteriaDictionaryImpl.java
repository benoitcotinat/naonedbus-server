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
package net.naonedbus.dao.criteria.dictionnary.impl;

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
import java.util.HashMap;
import java.util.Map;

import net.naonedbus.dao.criteria.SearchCriteria;
import net.naonedbus.dao.criteria.dictionnary.CriteriaDictionary;
import net.naonedbus.dao.criteria.dictionnary.maker.CriteriaMaker;

import org.hibernate.Criteria;
import org.springframework.util.Assert;

/**
 * Dictionnaire de critères de recherche.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CriteriaDictionaryImpl
    implements CriteriaDictionary, Serializable
{
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 7109461324902291745L;

    /**
     * Dictionnaire d'artisans.
     */
    @SuppressWarnings("rawtypes")
    private Map<Class, CriteriaMaker> makers = new HashMap<Class, CriteriaMaker>();

    /**
     * {@inheritDoc}
     */
    public void apply(final Criteria criteria,
                      final SearchCriteria searchCrit)
    {
        Assert.notNull(searchCrit,
                       "SearchCriteria must not be null");
        final CriteriaMaker maker = this.getMakers().get(searchCrit.getClass());
        Assert.notNull(maker,
                       "Maker must not be null");
        if (maker.supports(searchCrit.getClass()))
        {
            maker.transform(criteria,
                            searchCrit);
        }
    }

    /**
     * Getter pour makers.
     * @return Retourne les artisans.
     */
    @SuppressWarnings("rawtypes")
    public Map<Class, CriteriaMaker> getMakers()
    {
        return this.makers;
    }

    /**
     * Setter pour makers.
     * @param makers les artisans à écrire.
     */
    @SuppressWarnings("rawtypes")
    public void setMakers(final Map<Class, CriteriaMaker> makers)
    {
        this.makers = makers;
    }

}
