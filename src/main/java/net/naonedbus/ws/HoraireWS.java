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


import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.naonedbus.model.Horaire;
import net.naonedbus.service.metier.HoraireService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web Service publiant les contrats de récupération des horaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@Path("horaire")
public class HoraireWS
{

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Service des arrêts.
     */
    @Resource(name = "horaireService")
    private HoraireService horaireService;

    /**
     * Méthode en charge de récupérer les horaires d'un arrêt à une date donnée.
     * @param codeLigne Ligne de l'arrêt
     * @param codeSens Sens de l'arrêt
     * @param codeArret Arrêt dont on veut les horaires
     * @param timestamp Date à laquelle on veut les horaires
     * @return Liste des horaires de l'arrêt à la date voulue
     */
    @GET
    @Path("horairesForArret")
    @Produces(
    {MediaType.APPLICATION_JSON })
    public List<Horaire> getHorairesForArret(final @QueryParam("codeLigne") String codeLigne,
                                             final @QueryParam("codeSens") String codeSens,
                                             final @QueryParam("codeArret") String codeArret,
                                             final @QueryParam("timestamp") long timestamp)
    {
        if (this.log.isDebugEnabled())
        {
            final StringBuilder sb = new StringBuilder();
            sb.append(" => Recuperation des horaires pour l'arret ");
            sb.append(codeArret);
            sb.append(", sens ");
            sb.append(codeSens);
            sb.append(" de la ligne ");
            sb.append(codeLigne);
            sb.append(" - Timestamp = ");
            sb.append(timestamp);
            this.log.debug(sb.toString());
        }

        final List<Horaire> horaires = this.horaireService.get(codeLigne,
                                                               codeSens,
                                                               codeArret,
                                                               timestamp);

        if (this.log.isDebugEnabled())
        {
            this.log.debug("Fin de la récupération des horaires.");
        }

        return horaires;
    }

    /**
     * Setter pour horaireService.
     * @param horaireService Le horaireService à écrire.
     */
    public void setHoraireService(final HoraireService horaireService)
    {
        this.horaireService = horaireService;
    }
}
