package net.naonedbus.dao.criteria.dictionnary.maker.common;

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

import net.naonedbus.dao.criteria.dictionnary.maker.CriteriaMaker;

/**
 * Classe abstraite de base pour les artisans des critères de recherche.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public abstract class AbstractCriteriaMaker
    implements CriteriaMaker, Serializable
{
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -4110449095770174380L;

    /**
     * La classe du critère de recherche supporté.
     */
    @SuppressWarnings("rawtypes")
    private final Class supportedClass;

    /**
     * Constructeur unique.
     * @param supportedCriteriaClass La classe du critère de recherche supporté.
     */
    @SuppressWarnings("rawtypes")
    public AbstractCriteriaMaker(final Class supportedCriteriaClass)
    {
        this.supportedClass = supportedCriteriaClass;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(
    {"rawtypes", "unchecked" })
    public boolean supports(final Class clazz)
    {
        return this.supportedClass.isAssignableFrom(clazz);
    }

}
