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
package net.naonedbus.model.common;

import java.io.Serializable;

/**
 * Interface commune aux beans.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public interface IBeanObject
    extends Serializable
{
    /**
     * Getter pour id.
     * @return Le id
     */
    Integer getId();

    /**
     * Setter pour id.
     * @param id Le id à écrire.
     */
    void setId(final Integer id);
}
