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
package net.naonedbus.model.common;

import java.io.Serializable;

/**
 * Interface commune exposant le contrat pour les commentaire.
 * @author Romain
 * @version $Revision$ $Date$
 */
public interface ICommentaire
    extends IBeanObject, Serializable
{
    String getCodeLigne();
    void setCodeLigne(String codeLigne);

    String getCodeSens();
    void setCodeSens(String codeSens);

    String getCodeArret();
    void setCodeArret(String codeArret);

    String getMessage();
    void setMessage(String message);

    Long getTimestamp();
    void setTimestamp(Long timestamp);

    String getSource();
    void setSource(String source);
}
