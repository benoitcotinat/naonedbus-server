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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import net.naonedbus.model.common.IArret;

import org.hibernate.annotations.Index;

/**
 * Défini un arret
 * @author Romain
 * @version 0.1
 */
// @XmlRootElement
// @Entity(name = "arret")
public class Arret
    extends BeanObjectCode
    implements Serializable, IArret
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 175970606864499540L;

    /**
     * Nom de l'élement.
     */
    @Column(name = "nom")
    private String nom;

    /**
     * Nom normalisé de l'arrêt.
     */
    @Column(name = "normalizedNom")
    private String normalizedNom;

    /**
     * Latitude GPS.
     */
    @Column(name = "latitude", nullable = true)
    private Double latitude = null;

    /**
     * Longitude GPS.
     */
    @Column(name = "longitude", nullable = true)
    private Double longitude = null;

    /**
     * Sens de l'arrêt
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sens", nullable = false)
    @Index(name = "idx_arret_sens")
    @NotNull(message = "Le sens de l'arret est nul !")
    private Sens sens;

    /**
     * Getter pour nom.
     * @return Le nom
     */
    @Override
    public String getNom()
    {
        return this.nom;
    }

    /**
     * Setter pour nom.
     * @param nom Le nom à écrire.
     */
    @Override
    public void setNom(final String nom)
    {
        this.nom = nom;
    }

    /**
     * Getter pour normalizedNom.
     * @return Le normalizedNom
     */
    @Override
    public String getNormalizedNom()
    {
        return this.normalizedNom;
    }

    /**
     * Setter pour normalizedNom.
     * @param normalizedNom Le normalizedNom à écrire.
     */
    @Override
    public void setNormalizedNom(final String normalizedNom)
    {
        this.normalizedNom = normalizedNom;
    }

    /**
     * Getter pour latitude.
     * @return Le latitude
     */
    @Override
    public Double getLatitude()
    {
        return this.latitude;
    }

    /**
     * Setter pour latitude.
     * @param latitude Le latitude à écrire.
     */
    @Override
    public void setLatitude(final Double latitude)
    {
        this.latitude = latitude;
    }

    /**
     * Getter pour longitude.
     * @return Le longitude
     */
    @Override
    public Double getLongitude()
    {
        return this.longitude;
    }

    /**
     * Setter pour longitude.
     * @param longitude Le longitude à écrire.
     */
    @Override
    public void setLongitude(final Double longitude)
    {
        this.longitude = longitude;
    }

    /**
     * Getter pour sens.
     * @return Le sens
     */
    public Sens getSens()
    {
        return this.sens;
    }

    /**
     * Setter pour sens.
     * @param sens Le sens à écrire.
     */
    public void setSens(final Sens sens)
    {
        this.sens = sens;
    }

}
