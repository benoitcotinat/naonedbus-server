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
package net.naonedbus.model.criteria.impl;

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


import net.naonedbus.model.Favoris;
import net.naonedbus.model.criteria.AbstractSearchCriteria;

/**
 * Critère de recherche sur {@link Favoris}.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class FavorisSearchCriteria
    extends AbstractSearchCriteria
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -3483144770614415237L;

    /**
     * Identifiant des favoris de l'utilisateur.
     */
    private String identifiant;

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear()
    {
        this.setIdentifiant(null);
    }

    /**
     * Getter pour identifiant.
     * @return Le identifiant
     */
    public String getIdentifiant()
    {
        return this.identifiant;
    }

    /**
     * Setter pour identifiant.
     * @param identifiant Le identifiant à écrire.
     */
    public void setIdentifiant(final String identifiant)
    {
        this.identifiant = identifiant;
    }
}
