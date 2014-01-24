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
package net.naonedbus.model.criteria.impl;

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


import net.naonedbus.model.Arret;

/**
 * Critère de recherche sur {@link Arret}.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class ArretSearchCriteria
    extends CommonCodeSearchCriteria
{
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -7098463655322605750L;

    /**
     * Code du sens de l'arrêt.
     */
    private String codeSens;

    /**
     * Code de la ligne de l'arrêt.
     */
    private String codeLigne;

    /**
     * Getter pour codeSens.
     * @return Le codeSens
     */
    public String getCodeSens()
    {
        return this.codeSens;
    }

    /**
     * Setter pour codeSens.
     * @param codeSens Le codeSens à écrire.
     */
    public void setCodeSens(final String codeSens)
    {
        this.codeSens = codeSens;
    }

    /**
     * Getter pour codeLigne.
     * @return Le codeLigne
     */
    public String getCodeLigne()
    {
        return this.codeLigne;
    }

    /**
     * Setter pour codeLigne.
     * @param codeLigne Le codeLigne à écrire.
     */
    public void setCodeLigne(final String codeLigne)
    {
        this.codeLigne = codeLigne;
    }
}
