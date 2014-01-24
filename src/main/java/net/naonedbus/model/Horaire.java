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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.naonedbus.model.common.IHoraire;

import org.hibernate.annotations.Index;

/**
 * Objet représentant un horaire.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@XmlRootElement
@Entity(name = "horaire")
public class Horaire
    extends BeanObject
    implements Serializable, IHoraire
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -3549553526091853953L;

    /**
     * Horaire de passage.
     */
    @Column(name = "timestamp")
    @NotNull(message = "La date de l'horaire est nul !")
    private Long timestamp;

    /**
     * Arret de cet horaire.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_arret", nullable = false)
    @Index(name = "idx_horaire_arret")
    @NotNull(message = "L'arrêt de l'horaire est nul !")
    private Arret arret;

    /**
     * Nom du terminus.
     */
    @Column(name = "terminus")
    private String terminus;

    // /**
    // * {@inheritDoc}
    // */
    // @Override
    // public boolean equals(final Object obj)
    // {
    // if (!(obj instanceof Horaire))
    // {
    // return false;
    // }
    //
    // final Horaire other = (Horaire) obj;
    //
    // final boolean equals = super.equals(obj)
    // && this.getTimestamp().equals(other.getTimestamp());
    //
    // return equals;
    // }

    /**
     * Getter pour time.
     * @return Le time
     */
    public Long getTimestamp()
    {
        return this.timestamp;
    }

    /**
     * Setter pour time.
     * @param time Le time à écrire.
     */
    public void setTimestamp(final Long timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * Getter pour arret.
     * @return Le arret
     */
    @XmlTransient
    public Arret getArret()
    {
        return this.arret;
    }

    /**
     * Setter pour arret.
     * @param arret Le arret à écrire.
     */
    public void setArret(final Arret arret)
    {
        this.arret = arret;
    }

    /**
     * Getter pour terminus.
     * @return Le terminus
     */
    public String getTerminus()
    {
        return this.terminus;
    }

    /**
     * Setter pour terminus.
     * @param terminus Le terminus à écrire.
     */
    public void setTerminus(final String terminus)
    {
        this.terminus = terminus;
    }
}
