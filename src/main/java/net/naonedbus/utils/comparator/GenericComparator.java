package net.naonedbus.utils.comparator;

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
import java.util.Comparator;

import net.naonedbus.utils.introspection.BeanTool;

/**
 * Comparateur générique.
 * @param <BEAN> bean.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class GenericComparator<BEAN extends Object>
    implements Comparator<BEAN>, Serializable
{

    /**
     * Serial id.
     */
    private static final long serialVersionUID = 4574987235322605074L;

    /**
     * Nom de la propriété à comparer.
     */
    private final String propertyName;

    /**
     * Constructeur.
     * @param propertyName Propriété à comparer.
     */
    public GenericComparator(final String propertyName)
    {
        this.propertyName = propertyName;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public int compare(final BEAN o1,
                       final BEAN o2)
    {
        return ((Comparable) BeanTool.getPropriete(o1,
                                                   this.getPropertyName())).compareTo(BeanTool
                .getPropriete(o2,
                              this.getPropertyName()));
    }

    /**
     * Retourne le propertyName.
     * @return Retourne le propertyName.
     */
    public String getPropertyName()
    {
        return this.propertyName;
    }
}
