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
package net.naonedbus.exception;

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
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class NaonedbusException
    extends Exception
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 6589721845555406462L;

    /**
     * Constructeur par défaut.
     */
    public NaonedbusException()
    {
        super();
    }

    /**
     * Constructeur par défaut.
     * @param error Message d'erreur.
     */
    public NaonedbusException(final String error)
    {
        super(error);
    }

    /**
     * Constructeur par défaut.
     * @param cause Erreur throwable.
     */
    public NaonedbusException(final Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructeur par défaut.
     * @param error Message d'erreur.
     * @param cause Erreur throwable.
     */
    public NaonedbusException(final String error, final Throwable cause)
    {
        super(error,
              cause);
    }

}
