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
package net.naonedbus.model.criteria;

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


import net.naonedbus.dao.criteria.SearchCriteria;

/**
 * Classe de gestion des critères de recherche.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public abstract class AbstractSearchCriteria
    implements SearchCriteria
{
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 2969514244109034967L;

    /**
     * Critère d'ordonancement.
     */
    private String activeOrder;

    /**
     * Critère de direction d'ordonnancement.
     */
    private boolean ascending = true;

    /**
     * Critère case sensitive sur l'ordonnancement.
     */
    private boolean caseSensitiveOrder = false;

    /**
     * Nombre de résultats maximum par requête par défaut.
     */
    private int maxResults = 20;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getActiveOrder()
    {
        return this.activeOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveOrder(final String activeOrder)
    {
        this.activeOrder = activeOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAscending()
    {
        return this.ascending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAscending(final boolean ascending)
    {
        this.ascending = ascending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCaseSensitiveOrder()
    {
        return this.caseSensitiveOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCaseSensitiveOrder(final boolean caseSensitiveOrder)
    {
        this.caseSensitiveOrder = caseSensitiveOrder;
    }

    /**
     * Méthode en charge de nettoyer les attributs d'un critère de recherche.
     */
    public abstract void clear();

    public int getMaxResults()
    {
        return this.maxResults;
    }

    public void setMaxResults(final int maxResults)
    {
        this.maxResults = maxResults;
    }

}
