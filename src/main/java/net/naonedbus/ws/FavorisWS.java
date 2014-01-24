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
package net.naonedbus.ws;

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


import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.naonedbus.service.metier.FavorisService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web Service publiant les contrats d'import et d'export des favoris.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@Path("favoris")
public class FavorisWS
{

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Service des commentaires.
     */
    @Resource(name = "favorisService")
    private FavorisService favorisService;

    /**
     * Méthode gérant l'export des favoris.
     * @param contenu Contenu des favoris à sauvegarder.
     * @return Identifiant associé aux favoris.
     */
    @POST
    @Produces(
              {MediaType.APPLICATION_JSON })
    public String exporter(final @QueryParam("contenu") String contenu)
    {
        if (this.log.isDebugEnabled())
        {
            final StringBuilder sb = new StringBuilder();
            sb.append(" => Export des favoris :\n");
            sb.append(contenu);
            this.log.debug(sb.toString());
        }

        final String identifiant = this.favorisService.exporter(contenu);

        if (this.log.isDebugEnabled())
        {
            final StringBuilder sb = new StringBuilder();
            sb.append("Identifiant : ");
            sb.append(identifiant);
            this.log.debug(sb.toString());
        }

        return identifiant;
    }

    /**
     * Méthode gérant l'import des favoris.
     * @param identifiant Identifiant des favoris de l'utilisateur.
     * @return Favoris associé à l'identifiant.
     */
    @GET
    @Produces(
    {MediaType.APPLICATION_JSON })
    public String importer(final @QueryParam("identifiant") String identifiant)
    {
        if (this.log.isDebugEnabled())
        {
            final StringBuilder sb = new StringBuilder();
            sb.append(" => Import des favoris avec l'identifiant : ");
            sb.append(identifiant);
            this.log.debug(sb.toString());
        }

        final String contenu = this.favorisService.importer(identifiant);
        return contenu;
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
