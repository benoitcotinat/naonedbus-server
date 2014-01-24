package net.naonedbus.service.commentaire.predicate;

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


import org.apache.commons.collections.Predicate;

import twitter4j.Status;

/**
 * Predicate en charge de supprimer les tweets qui sont des réponses.
 *
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class TwitterStatusReplyPredicate
    implements Predicate
{
    /** {@inheritDoc} */
    @Override
    public boolean evaluate(final Object object)
    {
        final Status statut = (Status) object;
        return null == statut.getInReplyToScreenName();
    }

}
