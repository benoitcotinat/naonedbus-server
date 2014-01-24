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
package net.naonedbus.service.metier;

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


import net.naonedbus.model.Favoris;
import net.naonedbus.service.common.GenericService;

/**
 * Interface posant les contrats pour les services des favoris.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public interface FavorisService
    extends GenericService<Favoris>
{
    /**
     * Getter par l'identifiant des favoris de l'utilisateur.
     * @param identifiant Identifiant généré.
     * @return Favoris associé à l'identifiant, null si n'existe pas.
     */
    Favoris get(String identifiant);

    /**
     * Méthode en charge de déterminer un identifiant généré unique.
     * @return Identifiant.
     */
    String getUniqueIdentifiant();

    /**
     * Méthode en charge de gérer l'export des favoris d'un utilisateur.
     * @param contenu Contenu des Favoris
     * @return Code d'identification des favoris
     */
    String exporter(final String contenu);

    /**
     * Méthode en charge de gérer l'import des favoris d'un utilisateur.
     * @param identifiant Identifiant des Favoris
     * @return Contenu des Favoris,
     */
    String importer(final String identifiant);
}
