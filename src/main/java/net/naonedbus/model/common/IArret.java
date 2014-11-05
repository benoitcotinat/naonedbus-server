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
 * Interface commune exposant le contrat pour les arrêts.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public interface IArret
    extends IBeanObject, Serializable
{
    /**
     * Getter pour latitude.
     * @return Le latitude
     */
    Double getLatitude();

    /**
     * Setter pour latitude.
     * @param latitude Le latitude à écrire.
     */
    void setLatitude(final Double latitude);

    /**
     * Getter pour longitude.
     * @return Le longitude
     */
    Double getLongitude();

    /**
     * Setter pour longitude.
     * @param longitude Le longitude à écrire.
     */
    void setLongitude(final Double longitude);

    /**
     * Getter pour code.
     * @return Le code
     */
    String getCode();

    /**
     * Setter pour code.
     * @param code Le code à écrire.
     */
    void setCode(final String code);

    /**
     * Getter pour nom.
     * @return Le nom
     */
    String getNom();

    /**
     * Setter pour nom.
     * @param nom Le nom à écrire.
     */
    void setNom(final String nom);

    /**
     * Getter pour normalizedNom.
     * @return Le normalizedNom
     */
    String getNormalizedNom();

    /**
     * Setter pour normalizedNom.
     * @param normalizedNom Le normalizedNom à écrire.
     */
    void setNormalizedNom(final String normalizedNom);
}
