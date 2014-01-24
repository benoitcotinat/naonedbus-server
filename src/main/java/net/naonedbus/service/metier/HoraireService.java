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


import java.util.List;

import net.naonedbus.model.Horaire;
import net.naonedbus.service.common.GenericService;

/**
 * Interface posant les contrats pour les services des horaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public interface HoraireService
    extends GenericService<Horaire>
{
    /**
     * Méthode en charge de retourner les horaires d'un arrêt à partir d'une date donnée.
     * @param codeLigne Code de la ligne.
     * @param codeSens Code du sens.
     * @param codeArret Code de l'arrêt.
     * @param timestamp Date mini pour la recherche des horaires.
     * @return Liste des horaires.
     */
    List<Horaire> get(final String codeLigne,
                      final String codeSens,
                      final String codeArret,
                      final long timestamp);
}
