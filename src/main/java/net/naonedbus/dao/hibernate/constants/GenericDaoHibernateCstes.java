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
package net.naonedbus.dao.hibernate.constants;

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


/**
 * Classe de constantes utilisées dans GenericDaoHibernate.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class GenericDaoHibernateCstes
{
    /**
     * EntityManager fermé.
     */
    public static final String ENTITY_MANAGER_CLOSED = "L'entity manager a été fermé";

    /**
     * Bean non managé par l'entity manager.
     */
    public static final String BEAN_UNMANAGED = "Le bean n'est pas managé par l'entity manager";

    /**
     * Aucune transaction n'est disponible pour l'entity manager.
     */
    public static final String NO_TRANSACTION_AVAILABLE = "Aucune transaction disponible";

    /**
     * L'entité n'est plus présente en base.
     */
    public static final String ENTITY_NOT_FOUND_DB = "L'entité n'est plus présente en base";

    /**
     * L'entité ne peut pas être persistée.
     */
    public static final String ENTITY_ERROR_PERSIST = "L'entité ne peut pas être persistée";

    /**
     * Constructeur.
     */
    protected GenericDaoHibernateCstes()
    {
    }
}
