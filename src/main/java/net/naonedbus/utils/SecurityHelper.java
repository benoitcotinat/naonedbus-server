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
package net.naonedbus.utils;

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
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import net.naonedbus.security.CommonKey;
import net.naonedbus.security.RSAUtils;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe contenant les méthodes relatives à la sécurité de l'application.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class SecurityHelper
    implements Serializable
{

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 5654350175562762777L;

    /**
     * Liste des clés de l'application.
     */
    @Resource(name = "keys")
    private Map<String, CommonKey> keys;

    /**
     * Méthode en charge de chiffrer les arguments avec une clé, et de vérifier que ce résultat
     * correspond au hash fournit.
     * @param idClient Identifiant du client, permettant de récupérer la clé.
     * @param signedMessage Hash des paramètres.
     * @param args Arguments à checker.
     * @return {@code true} si les arguments matchent le hash
     */
    public Boolean validate(final String idClient,
                            final String signedMessage,
                            final String... args)
    {
        Boolean isValid = Boolean.FALSE;

        final String concat = StringUtils.join(args);
        // Déchiffrement et vérification
        final CommonKey naoKey = this.keys.get(idClient.toLowerCase());
        try
        {
            final BouncyCastleProvider bcp = new BouncyCastleProvider();
            Security.addProvider(bcp);

            final PublicKey key = RSAUtils.genNaonedbusKey(naoKey.getModulo(),
                                                           naoKey.getExposant());
            final Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(key);
            signature.update(concat.getBytes());

            isValid = signature.verify(signedMessage.getBytes());

            if (!isValid)
            {
                this.log.debug("Vérification de la signature incorrecte :\n"
                               + "\tMessage : "
                               + concat
                               + "\n\tSignature  : "
                               + signature.toString()
                               + "\tpour les arguments : "
                               + Arrays.asList(args));
            }
        }
        catch (final GeneralSecurityException e)
        {
            this.log
                    .error("Erreur lors de la génération de la clé ou du chiffrement du message de "
                                   + idClient
                                   + " avec les paramètres "
                                   + Arrays.asList(args),
                           e);
        }

        return isValid;
    }

    /**
     * Setter pour keys.
     * @param keys Le keys à écrire.
     */
    public void setKeys(final Map<String, CommonKey> keys)
    {
        this.keys = keys;
    }
}
