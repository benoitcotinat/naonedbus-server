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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Ligne entity.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@Entity(name = "ligne")
public class Ligne
    extends BeanObjectCode
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 8317473019984954293L;

    /**
     * Nom de l'élement.
     */
    @Column(name = "nom")
    protected String nom;

    /**
     * Nom terminus de départ.
     */
    @Column(name = "depuis")
    private String depuis;

    /**
     * Nom terminus d'arrivé.
     */
    @Column(name = "vers")
    private String vers;

    /**
     * Sens.
     */
    @OneToMany(mappedBy = "ligne", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.TRUE)
    @NotNull
    @NotEmpty
    private List<Sens> sens;

    /**
     * Getter pour sens.
     * @return Le sens
     */
    public List<Sens> getSens()
    {
        return this.sens;
    }

    /**
     * Setter pour sens.
     * @param sens Le sens à écrire.
     */
    public void setSens(final List<Sens> sens)
    {
        this.sens = sens;
    }

    /**
     * Getter pour depuis.
     * @return Le depuis
     */
    public String getDepuis()
    {
        return this.depuis;
    }

    /**
     * Setter pour depuis.
     * @param depuis Le depuis à écrire.
     */
    public void setDepuis(final String depuis)
    {
        this.depuis = depuis;
    }

    /**
     * Getter pour vers.
     * @return Le vers
     */
    public String getVers()
    {
        return this.vers;
    }

    /**
     * Setter pour vers.
     * @param vers Le vers à écrire.
     */
    public void setVers(final String vers)
    {
        this.vers = vers;
    }

    /**
     * Getter pour nom.
     * @return Le nom
     */
    public String getNom()
    {
        return this.nom;
    }

    /**
     * Setter pour nom.
     * @param nom Le nom à écrire.
     */
    public void setNom(final String nom)
    {
        this.nom = nom;
    }

}
