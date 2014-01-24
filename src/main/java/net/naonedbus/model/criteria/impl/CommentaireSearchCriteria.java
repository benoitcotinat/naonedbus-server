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


import java.util.Calendar;

import net.naonedbus.model.Commentaire;
import net.naonedbus.model.criteria.AbstractSearchCriteria;

/**
 * Critère de recherche sur {@link Commentaire}.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireSearchCriteria
    extends AbstractSearchCriteria
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 6690325809875629773L;

    /**
     * Code de la ligne.
     */
    private String codeLigne;

    /**
     * Code du sens.
     */
    private String codeSens;

    /**
     * Code de l'arrêt.
     */
    private String codeArret;

    /**
     * Date de publication du commentaire.
     */
    private Calendar datePublication;

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear()
    {
        this.setCodeLigne(null);
        this.setCodeSens(null);
        this.setCodeArret(null);
        this.setDatePublication(null);
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
     * Getter pour codeArret.
     * @return Le codeArret
     */
    public String getCodeArret()
    {
        return this.codeArret;
    }

    /**
     * Setter pour codeArret.
     * @param codeArret Le codeArret à écrire.
     */
    public void setCodeArret(final String codeArret)
    {
        this.codeArret = codeArret;
    }

    /**
     * Getter pour datePublication.
     * @return Le datePublication
     */
    public Calendar getDatePublication()
    {
        return this.datePublication;
    }

    /**
     * Setter pour datePublication.
     * @param datePublication Le datePublication à écrire.
     */
    public void setDatePublication(final Calendar datePublication)
    {
        this.datePublication = datePublication;
    }

}
