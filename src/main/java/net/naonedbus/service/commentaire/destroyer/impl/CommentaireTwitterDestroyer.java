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
package net.naonedbus.service.commentaire.destroyer.impl;

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

import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.model.Commentaire;
import net.naonedbus.service.commentaire.destroyer.CommentaireDestroyer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireTwitterDestroyer
    implements CommentaireDestroyer
{
    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Instance de l'accès à Twitter.
     */
    @Resource(name = "tanNantesTwitter")
    private Twitter twitter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy(final Commentaire commentaire)
        throws NaonedbusException
    {
        try
        {
            this.twitter.destroyStatus(commentaire.getTweetId());
        }
        catch (final TwitterException te)
        {
            this.log.error("Erreur lors de la suppression du tweet : "
                           + te.getMessage());
        }
    }

    /**
     * Setter pour twitter.
     * @param twitter Le twitter à écrire.
     */
    public void setTwitter(final Twitter twitter)
    {
        this.twitter = twitter;
    }

}
