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
package net.naonedbus.model;

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

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Défini une base pour les types Lignes, Arret, Sens, etc.
 * @author Romain
 * @version 0.1
 */
@MappedSuperclass
public abstract class BeanObject
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -2801281224678732625L;

    /**
     * Nombre premier utilisé pour le calcul de la valeur de hashcode.
     */
    public static final int NB_PREMIER = 37;

    /**
     * Identifiant technique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id", updatable = false, nullable = false)
    protected Integer id;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (!(obj instanceof BeanObject))
        {
            return false;
        }

        final BeanObject other = (BeanObject) obj;

        if (other == null
            || this.getId() == null
            || other.getId() == null)
        {
            return false;
        }

        if (other == this)
        {
            return true;
        }

        final String otherCle = other.getId().toString();
        final String thisCle = this.getId().toString();

        if (otherCle.equalsIgnoreCase(thisCle))
        {
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        if (this.getId() != null)
        {
            return (BeanObject.NB_PREMIER + this.getId().hashCode())
                   * BeanObject.NB_PREMIER;
        }
        else
        {
            return super.hashCode();
        }
    }

    /**
     * Getter pour id.
     * @return Le id
     */
    public Integer getId()
    {
        return this.id;
    }

    /**
     * Setter pour id.
     * @param id Le id à écrire.
     */
    public void setId(final Integer id)
    {
        this.id = id;
    }

}
