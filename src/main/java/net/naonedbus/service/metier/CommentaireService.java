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

import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.model.Commentaire;
import net.naonedbus.service.common.GenericService;

/**
 * Interface posant les contrats pour les services des commentaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public interface CommentaireService
    extends GenericService<Commentaire>
{
    /**
     * Méthode en charge de gérer un message chiffré : déchiffrement, vérification, et sauvegarde
     * le cas échéant.
     * @param codeLigne Ligne de l'arrêt
     * @param codeSens Sens de l'arrêt
     * @param codeArret Arrêt dont on veut les horaires
     * @param message à sauvegarder
     * @param cryptedHash Hash des paramètres.
     * @param idClient Clé permettant de vérifier l'intégrité des données transmises
     * @throws NaonedbusException Si le chiffrement n'est pas valide
     */
    void manageEncryptedMessage(final String codeLigne,
                                final String codeSens,
                                final String codeArret,
                                final String message,
                                final String cryptedHash,
                                final String idClient)
        throws NaonedbusException;
    /**
     * Méthode en charge de gérer un message signé : déchiffrement, vérification, et sauvegarde
     * le cas échéant.
     * @param codeLigne Ligne de l'arrêt
     * @param codeSens Sens de l'arrêt
     * @param codeArret Arrêt dont on veut les horaires
     * @param message à sauvegarder
     * @param cryptedHash Hash des paramètres.
     * @param idClient Clé permettant de vérifier l'intégrité des données transmises
     * @throws NaonedbusException Si le chiffrement n'est pas valide
     */
    void manageSignedMessage(final String codeLigne,
                             final String codeSens,
                             final String codeArret,
                             final String message,
                             final String signedMessage,
                             final String idClient)
        throws NaonedbusException;

    /**
     * Retourne la liste des commentaires concernés par les critères.
     * @param codeLigne Code de la ligne.
     * @param codeSens Code du sens.
     * @param codeArret Code de l'arrêt.
     * @param timestamp Date à partir de quand on veut les commentaires.
     * @param limit Nombre maximum de commentaires voulus.
     * @return Liste des commentaires.
     * @throws NaonedbusException S'il y a un problème pour la récupération des commentaires.
     */
    List<Commentaire> get(final String codeLigne,
                          final String codeSens,
                          final String codeArret,
                          final long timestamp,
                          final int limit)
        throws NaonedbusException;

    /**
     * Permet de supprimer un commentaire.
     * @param id ID technique du commentaire à supprimer.
     * @param cryptedHash Hash des paramètres.
     * @param idClient Clé permettant de vérifier l'intégrité des données transmises.
     */
    void delete(final int id,
                final String cryptedHash,
                final String idClient)
        throws NaonedbusException;
}
