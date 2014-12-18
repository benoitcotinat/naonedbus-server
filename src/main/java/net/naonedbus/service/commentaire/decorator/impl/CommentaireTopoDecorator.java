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
package net.naonedbus.service.commentaire.decorator.impl;

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


import net.naonedbus.model.Commentaire;
import net.naonedbus.service.commentaire.decorator.CommentaireDecorator;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.apache.commons.lang.StringUtils;

/**
 * Enrichit le message d'un commentaire avec des informations sur son sens.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireTopoDecorator
    implements CommentaireDecorator
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void decorate(final Commentaire commentaire)
    {
        final StringBuffer sbLigne = new StringBuffer();
        final StringBuffer sbSeparator = new StringBuffer();
        if (null != commentaire.getLigne())
        {
            sbLigne.append(NaonedbusConstants.TOPO_LIGNE);
            sbLigne.append(commentaire.getLigne().getNom());

            sbSeparator.append(NaonedbusConstants.DOT);
            sbSeparator.append(NaonedbusConstants.NEW_LINE);
        }

        final StringBuffer sbArret = new StringBuffer();
        if (null != commentaire.getArret())
        {
            sbArret.append(NaonedbusConstants.COMMA);
            sbArret.append(NaonedbusConstants.SPACE);

            // Calculer la taille restante (5 = taille minimale à laisser pour le sens).
            final int placeRestante = 140 - 5 - (sbLigne.length()
                                                  + sbArret.length() + commentaire.getMessage().length());

            sbArret.append(this.appendIfSubstring(commentaire.getArret().getNom(),
                                                  Math.max(placeRestante,
                                                           5)));
        }

        final StringBuffer sbSens = new StringBuffer();
        if (null != commentaire.getSens())
        {
            sbSens.append(NaonedbusConstants.SPACE);
            sbSens.append(NaonedbusConstants.SENS);
            sbSens.append(NaonedbusConstants.SPACE);

            // Calculer la taille restante
            final int placeRestante = 140 - (sbLigne.length()
                                             + sbArret.length() + commentaire.getMessage().length());

            sbSens.append(this.appendIfSubstring(commentaire.getSens().getNom(),
                                                 Math.max(placeRestante,
                                                          5)));
        }

        commentaire.setMessage(sbLigne.append(sbSens).append(sbArret).append(sbSeparator).append(commentaire.getMessage()).toString());
    }

    String appendIfSubstring(final String str,
                                   final int length)
    {
        String result = str;
        if (str.length() > length)
        {
            result = StringUtils.substring(str,
                                  0,
                                  length - 1);
            result += NaonedbusConstants.TRONCATURE;
        }

        return result;
    }
}
