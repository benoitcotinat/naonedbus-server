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


import net.naonedbus.model.Arret;
import net.naonedbus.model.Horaire;
import net.naonedbus.model.criteria.AbstractSearchCriteria;

/**
 * Critère de recherche sur {@link Horaire}.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HoraireSearchCriteria
    extends AbstractSearchCriteria
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 4246643294352741897L;

    /**
     * Arrêt de l'horaire.
     */
    private Arret arret;

    /**
     * Timestamp minimum à partir duquel on va chercher les horaires.
     */
    private Long timestampMin;

    /**
     * Timestamp maximum jusqu'au quel on veut les horaires.
     */
    private Long timestampMax;

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear()
    {
        this.setArret(null);
        this.setTimestampMin(null);
        this.setTimestampMax(null);
    }

    /**
     * Getter pour arret.
     * @return Le arret
     */
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
     * Getter pour timestampMin.
     * @return Le timestampMin
     */
    public Long getTimestampMin()
    {
        return this.timestampMin;
    }

    /**
     * Setter pour timestampMin.
     * @param timestampMin Le timestampMin à écrire.
     */
    public void setTimestampMin(final Long timestampMin)
    {
        this.timestampMin = timestampMin;
    }

    /**
     * Getter pour timestampMax.
     * @return Le timestampMax
     */
    public Long getTimestampMax()
    {
        return this.timestampMax;
    }

    /**
     * Setter pour timestampMax.
     * @param timestampMax Le timestampMax à écrire.
     */
    public void setTimestampMax(final Long timestampMax)
    {
        this.timestampMax = timestampMax;
    }
}
