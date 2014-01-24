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
package net.naonedbus.model;

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


import java.util.Arrays;

import net.naonedbus.utils.predicate.GenericPredicate;

import org.apache.commons.collections.CollectionUtils;

/**
 * Types des jours.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public enum TypeJour
{
    JOUR_BLEU("Jour bleu", "titrenum3"),

    JOUR_JAUNE("Jour jaune", "titrenum1"),

    JOUR_ROSE("Jour rose", "titrenum4"),

    JOUR_VERT("Jour vert", "titrenum2"),

    JOUR_NON_DESSERVI("1er Mai", "titrenum");

    /**
     * Libellé du type de jour.
     */
    private String libelle;

    /**
     * Classe CSS associée dans le calendrier.
     */
    private String cssClass;

    /**
     * Constructeur.
     */
    private TypeJour(final String libelle, final String cssClass)
    {
        this.libelle = libelle;
        this.cssClass = cssClass;
    }

    /**
     * Retourne le type de jour correspondant à la classe css.
     * @param cssClass Classe css recherchée.
     * @return Type de jour.
     */
    public static TypeJour getBtCssClass(final String cssClass)
    {
        return (TypeJour) CollectionUtils.find(Arrays.asList(TypeJour.values()),
                                               new GenericPredicate("cssClass",
                                                                    cssClass));
    }
    /**
     * Getter pour libelle.
     * @return Le libelle
     */
    public String getLibelle()
    {
        return this.libelle;
    }

    /**
     * Getter pour cssClass.
     * @return Le cssClass
     */
    public String getCssClass()
    {
        return this.cssClass;
    }
}
