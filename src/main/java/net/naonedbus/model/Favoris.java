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


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Objet représentant la sauvegarde des favoris d'un utilisateur.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@XmlRootElement
@Entity(name = "favoris")
public class Favoris
    extends BeanObject
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -4353238516393057226L;

    /**
     * Identifiant généré pour les favoris d'un utilisateur.
     */
    @Column(name = "identifiant", length = 5, nullable = false, updatable = false, unique = true)
    private String identifiant;

    /**
     * Contenu des favoris, envoyé par le mobile.
     */
    @Column(name = "contenu", columnDefinition = "text")
    private String contenu;

    /**
     * Getter pour identifiant.
     * @return Le identifiant
     */
    public String getIdentifiant()
    {
        return this.identifiant;
    }

    /**
     * Setter pour identifiant.
     * @param identifiant Le identifiant à écrire.
     */
    public void setIdentifiant(final String identifiant)
    {
        this.identifiant = identifiant;
    }

    /**
     * Getter pour contenu.
     * @return Le contenu
     */
    public String getContenu()
    {
        return this.contenu;
    }

    /**
     * Setter pour contenu.
     * @param contenu Le contenu à écrire.
     */
    public void setContenu(final String contenu)
    {
        this.contenu = contenu;
    }

}
