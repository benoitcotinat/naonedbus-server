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

import javax.persistence.Column;

/**
 * Sens entity.
 * @author Benoît
 * @version $Revision$ $Date$
 */
// @Entity(name = "sens")
public class Sens
    extends BeanObjectCode
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 1725786742188278451L;

    /**
     * Nom de l'élement.
     */
    @Column(name = "nom")
    protected String nom;

    /**
     * Ligne.
     */
    // @ManyToOne(optional = false)
    // @JoinColumn(name = "id_ligne", nullable = false)
    // @Index(name = "idx_sens_ligne")
    // @NotNull(message = "La ligne du sens est nulle !")
    private Ligne ligne;

    /**
     * Arrets.
     */
    // @OneToMany(mappedBy = "sens", cascade = CascadeType.ALL)
    // @LazyCollection(LazyCollectionOption.TRUE)
    // @NotNull(message = "La liste des arrets du sens est nule !")
    // @NotEmpty(message = "La liste des arrets du sens est vide !")
    private List<Arret> arrets;

    /**
     * Getter pour arrets.
     * @return Le arrets
     */
    public List<Arret> getArrets()
    {
        return this.arrets;
    }

    /**
     * Setter pour arrets.
     * @param arrets Le arrets à écrire.
     */
    public void setArrets(final List<Arret> arrets)
    {
        this.arrets = arrets;
    }

    /**
     * Getter pour ligne.
     * @return Le ligne
     */
    public Ligne getLigne()
    {
        return this.ligne;
    }

    /**
     * Setter pour ligne.
     * @param ligne Le ligne à écrire.
     */
    public void setLigne(final Ligne ligne)
    {
        this.ligne = ligne;
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
