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
package net.naonedbus.factory.impl;

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

import javax.annotation.Resource;

import net.naonedbus.model.Favoris;
import net.naonedbus.service.metier.FavorisService;

/**
 * Classe en charge de créer des beans {@link Favoris}.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class FavorisFactory
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -3808357014978693128L;

    /**
     * Service des Favoris.
     */
    @Resource(name = "favorisService")
    private FavorisService favorisService;

    /**
     * Créé un {@link Favoris}.
     * @param contenu Contenu des favoris.
     * @return Un {@link Favoris} initialisé.
     */
    public synchronized Favoris getInitializedObject(final String contenu)
    {
        final String favId = this.favorisService.getUniqueIdentifiant();
        final Favoris favoris = new Favoris();
        favoris.setIdentifiant(favId);
        favoris.setContenu(contenu);

        return favoris;
    }
    /**
     * Setter pour favorisService.
     * @param favorisService Le favorisService à écrire.
     */
    public void setFavorisService(final FavorisService favorisService)
    {
        this.favorisService = favorisService;
    }
}
