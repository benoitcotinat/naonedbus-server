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
package net.naonedbus.dao.criteria.dictionnary.maker;

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

import org.hibernate.Criteria;

/**
 * Interface des artisans de recherche.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public interface CriteriaMaker
{
    /**
     * Méthode de transformation d'un critère de recherche.
     * @param criteria Le critéria de recherche.
     * @param searchCrit Le critère de recherche.
     */
    void transform(final Criteria criteria,
                   final SearchCriteria searchCrit);

    /**
     * Vérifie que le maker peut effectuer la transformation.
     * @param clazz La classe à vérifier.
     * @return <code>true</code> si le maker est adapté, <code>false</code> sinon.
     */
    @SuppressWarnings("rawtypes")
    boolean supports(final Class clazz);

}
