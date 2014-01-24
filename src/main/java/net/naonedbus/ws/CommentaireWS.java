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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.model.Commentaire;
import net.naonedbus.service.metier.CommentaireService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web Service publiant les contrats d'accès aux commentaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@Path("commentaire")
public class CommentaireWS
{

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Service des commentaires.
     */
    @Resource(name = "commentaireService")
    private CommentaireService commentaireService;

    /**
     * Méthode en charge de sauvegarder un commentaire.
     * @param codeLigne Code de la ligne concernée.
     * @param codeSens Code du sens concerné.
     * @param codeArret Code de l'arrêt concerné.
     * @param message Message à sauvegarder.
     * @param cryptedHash Hash chiffré des paramètres.
     * @param idClient Clé permettant de vérifier l'intégrité des données transmises.
     * @throws NaonedbusException En cas de chiffrement non valide.
     */
    @POST
    public void save(final @QueryParam("codeLigne") String codeLigne,
                     final @QueryParam("codeSens") String codeSens,
                     final @QueryParam("codeArret") String codeArret,
                     final @QueryParam("message") String message,
                     final @QueryParam("hash") String cryptedHash,
                     final @QueryParam("idClient") String idClient)
    {
        if (this.log.isDebugEnabled())
        {
            final StringBuilder sb = new StringBuilder();
            sb.append(" => Réception d'un commentaire de l'application ");
            sb.append(idClient);
            sb.append(" pour l'arret ");
            sb.append(codeArret);
            sb.append(", sens ");
            sb.append(codeSens);
            sb.append(" de la ligne ");
            sb.append(codeLigne);
            sb.append(" - Message = >");
            sb.append(message);
            sb.append("< - Hash chiffré = >");
            sb.append(cryptedHash);
            sb.append("<");
            this.log.debug(sb.toString());
        }

        try
        {
            this.commentaireService.manageEncryptedMessage(codeLigne,
                                                           codeSens,
                                                           codeArret,
                                                           message,
                                                           cryptedHash,
                                                           idClient);
        }
        catch (final NaonedbusException e)
        {
            throw new WebApplicationException(Status.FORBIDDEN);
        }
    }

    /**
     * Retourne la liste des commentaires concernés par les critères.
     * @param codeLigne Code de la ligne.
     * @param codeSens Code du sens.
     * @param codeArret Code de l'arrêt.
     * @param timestamp Date à partir de quand on veut les commentaires.
     * @param limit Nombre maximum de commentaires voulus.
     * @return Liste des commentaires.
     * @throws NaonedbusException Si erreur lors de la récupération des commentaires.
     */
    @GET
    @Produces(
    {MediaType.APPLICATION_JSON })
    public List<Commentaire> get(final @QueryParam("codeLigne") String codeLigne,
                                 final @QueryParam("codeSens") String codeSens,
                                 final @QueryParam("codeArret") String codeArret,
                                 final @QueryParam("timestamp") long timestamp,
                                 final @QueryParam("limit") int limit)
        throws NaonedbusException
    {
        if (this.log.isDebugEnabled())
        {
            final StringBuilder sb = new StringBuilder();
            sb.append(" => Récupération des commentaires pour la ligne : ");
            sb.append(codeLigne);
            sb.append(", le sens ");
            sb.append(codeSens);
            sb.append(" et l'arret ");
            sb.append(codeArret);
            sb.append(", à partir de ");
            sb.append(timestamp);
            sb.append(" dans la limite de ");
            sb.append(limit);
            sb.append(" commentaires.");
            this.log.debug(sb.toString());
        }
        return this.commentaireService.get(codeLigne,
                                           codeSens,
                                           codeArret,
                                           timestamp,
                                           limit);
    }

    /**
     * Supprime un commentaire.
     * @param id ID du commentaire à supprimer.
     * @param cryptedHash Hash chiffré des paramètres.
     * @param idClient Clé permettant de vérifier l'intégrité des données transmises.
     * @throws NaonedbusException
     */
    @POST
    @Path("delete")
    public void delete(final @QueryParam("id") int id,
                       final @QueryParam("hash") String cryptedHash,
                       final @QueryParam("idClient") String idClient)
        throws NaonedbusException
    {
        if (this.log.isDebugEnabled())
        {
            final StringBuilder sb = new StringBuilder();
            sb.append(" => Suppression du commentaire ");
            sb.append(id);
            sb.append(" de la part du client ");
            sb.append(idClient);
            sb.append(" - Hash chiffré = >");
            sb.append(cryptedHash);
            sb.append("<");
            this.log.debug(sb.toString());
        }

        try
        {
            this.commentaireService.delete(id,
                                           cryptedHash,
                                           idClient);
        }
        catch (final NaonedbusException e)
        {
            throw new WebApplicationException(Status.FORBIDDEN);
        }
    }

    /**
     * Setter pour commentaireService.
     * @param commentaireService Le commentaireService à écrire.
     */
    public void setCommentaireService(final CommentaireService commentaireService)
    {
        this.commentaireService = commentaireService;
    }
}
