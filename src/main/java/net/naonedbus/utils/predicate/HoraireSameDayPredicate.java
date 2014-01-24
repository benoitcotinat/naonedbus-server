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
package net.naonedbus.utils.predicate;

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

import net.naonedbus.model.Horaire;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.time.DateUtils;

/**
 * Predicate testant si un horaire est le même jour qu'un timestamp de référence.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HoraireSameDayPredicate
    implements Predicate
{
    /**
     * Timestamp de référence.
     */
    private final Calendar calReference;

    /**
     * Constructeur prenant le timestamp de référence.
     */
    public HoraireSameDayPredicate(final Long timestamp)
    {
        this.calReference = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                 NaonedbusConstants.LOCALE);
        this.calReference.setTimeInMillis(timestamp);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evaluate(final Object object)
    {
        final Horaire h = (Horaire) object;
        final Calendar cal = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                  NaonedbusConstants.LOCALE);
        cal.setTimeInMillis(h.getTimestamp());
        return DateUtils.isSameDay(this.calReference,
                                   cal);
    }
    /**
     * Getter pour calReference.
     * @return Le calReference
     */
    Calendar getCalReference()
    {
        return this.calReference;
    }

}
