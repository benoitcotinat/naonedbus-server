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
package net.naonedbus.scraper;

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
import java.util.List;

import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.model.BeanObject;
import net.naonedbus.scraper.wraper.NaonedbusScraperWrapper;

/**
 * Interface générique posant les contrats de récupération des données par webharvest.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public interface NaonedbusScraper<BEAN extends BeanObject>
    extends Serializable
{

    /**
     * Méthode en charge de récupérer les données.
     * @param wrapper Wrapper contenant les données nécessaires à la récupération des infos.
     * @return Liste des données voulues.
     * @throws NaonedbusException En cas d'erreur lors de la récupération des données.
     */
    List<BEAN> scrap(NaonedbusScraperWrapper wrapper)
        throws NaonedbusException;

}
