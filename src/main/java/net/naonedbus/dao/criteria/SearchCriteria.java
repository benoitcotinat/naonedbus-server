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
package net.naonedbus.dao.criteria;

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

/**
 * Classe de gestion des critères de recherche.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public interface SearchCriteria
    extends Serializable
{
    /**
     * Getter pour activeOrder.
     * @return le critère d'ordonancement.
     */
    String getActiveOrder();

    /**
     * Setter pour activeOrder.
     * @param activeOrder le critère d'ordonancement à définir.
     */
    void setActiveOrder(String activeOrder);

    /**
     * Getter pour ascending.
     * @return Retourne le ascending.
     */
    boolean isAscending();

    /**
     * Setter pour ascending.
     * @param ascending le ascending à écrire.
     */
    void setAscending(boolean ascending);

    /**
     * Récupération de caseSensitiveOrder.
     * @return Retourne le caseSensitiveOrder.
     */
    boolean isCaseSensitiveOrder();

    /**
     * Affectation du champ caseSensitiveOrder.
     * @param caseSensitiveOrder le caseSensitiveOrder à écrire.
     */
    void setCaseSensitiveOrder(final boolean caseSensitiveOrder);

    public int getMaxResults();

    public void setMaxResults(final int maxResults);
}
