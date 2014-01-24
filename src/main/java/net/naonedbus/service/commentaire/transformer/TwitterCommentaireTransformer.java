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
package net.naonedbus.service.commentaire.transformer;

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


import java.util.Calendar;

import javax.annotation.Resource;

import net.naonedbus.factory.impl.CommentaireFactory;
import net.naonedbus.model.Commentaire;
import net.naonedbus.security.NaonedbusClient;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.apache.commons.collections.Transformer;

import twitter4j.Status;

/**
 * Classe en charge transformer un statut twitter en commentaire.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class TwitterCommentaireTransformer
    implements Transformer
{
    /**
     * Caractère indiquant que le tweet de @TANInfos concerne l'info-trafic.
     */
    @Resource(name = "tanInfosDelimiter")
    private String delimiter;
    /**
     * Factory des commentaires.
     */
    @Resource(name = "commentaireFactory")
    private CommentaireFactory commentaireFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public Object transform(final Object input)
    {
        Commentaire commentaire = null;
        if (input instanceof Status)
        {

            final Status statut = (Status) input;

            final String source = NaonedbusClient.getFromAccount(statut.getUser().getScreenName()).name();

            String message = statut.getText();
            if (message.startsWith(this.delimiter))
            {
                message = message.substring(1).trim();
            }

            commentaire =
                this.commentaireFactory.getInitializedObject(null,
                                                             null,
                                                             null,
                                                                       source,
                                                                       message);

            commentaire.setId(Long.valueOf(statut.getId()).intValue());
            final Calendar calendar = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                           NaonedbusConstants.LOCALE);
            calendar.setTime(statut.getCreatedAt());
            commentaire.setDatePublication(calendar);
        }
        return commentaire;
    }

    /**
     * Getter pour commentaireFactory.
     * @return Le commentaireFactory
     */
    public CommentaireFactory getCommentaireFactory()
    {
        return this.commentaireFactory;
    }

    /**
     * Setter pour commentaireFactory.
     * @param commentaireFactory Le commentaireFactory à écrire.
     */
    public void setCommentaireFactory(final CommentaireFactory commentaireFactory)
    {
        this.commentaireFactory = commentaireFactory;
    }

    public void setDelimiter(final String delimiter)
    {
        this.delimiter = delimiter;
    }
}
