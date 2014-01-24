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
package net.naonedbus.scraper.wraper.impl;

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


import java.util.Calendar;

import net.naonedbus.model.Arret;
import net.naonedbus.scraper.wraper.NaonedbusScraperWrapper;

/**
 * Classe encapsulant les données nécessaires à la récupération des horaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HoraireScraperWrapper
    implements NaonedbusScraperWrapper
{

    /**
     * Serial ID;
     */
    private static final long serialVersionUID = -2162554454228192998L;

    /**
     * Arret demandé.
     */
    private Arret arret;

    /**
     * Date demandée.
     */
    private Calendar calendar;

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
     * Getter pour calendar.
     * @return Le calendar
     */
    public Calendar getCalendar()
    {
        return this.calendar;
    }

    /**
     * Setter pour calendar.
     * @param calendar Le calendar à écrire.
     */
    public void setCalendar(final Calendar calendar)
    {
        this.calendar = calendar;
    }

}
