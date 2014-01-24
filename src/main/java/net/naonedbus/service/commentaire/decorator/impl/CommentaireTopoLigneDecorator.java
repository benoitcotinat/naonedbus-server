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
 * Enrichit le message d'un commentaire avec des informations sur sa ligne.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireTopoLigneDecorator
    implements CommentaireDecorator
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void decorate(final Commentaire commentaire)
    {
        if (!StringUtils.isEmpty(commentaire.getCodeLigne()))
        {
            final StringBuffer sb = new StringBuffer();
            sb.append(NaonedbusConstants.TOPO_LIGNE);
            sb.append(commentaire.getNomLigne());
            sb.append(commentaire.getMessage());

            commentaire.setMessage(sb.toString());
        }
    }
}
