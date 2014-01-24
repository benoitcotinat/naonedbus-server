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
package net.naonedbus.factory.impl;

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
import java.util.Calendar;

import javax.annotation.Resource;

import net.naonedbus.model.Arret;
import net.naonedbus.model.Commentaire;
import net.naonedbus.model.Ligne;
import net.naonedbus.model.Sens;
import net.naonedbus.model.criteria.impl.ArretSearchCriteria;
import net.naonedbus.model.criteria.impl.SensSearchCriteria;
import net.naonedbus.service.common.CodeService;
import net.naonedbus.service.common.GenericService;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.apache.commons.lang.StringUtils;

/**
 * Classe en charge de créer des beans {@link Commentaire}.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireFactory
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -149362761280814285L;

    /**
     * Service des lignes.
     */
    @Resource(name = "ligneService")
    private CodeService<Ligne> ligneService;

    /**
     * Service des Sens.
     */
    @Resource(name = "sensService")
    private GenericService<Sens> sensService;

    /**
     * Service des arrêts.
     */
    @Resource(name = "arretService")
    private GenericService<Arret> arretService;

    /**
     * Créé un {@link Commentaire}.
     * @return Un {@link Commentaire} initialisé.
     */
    public Commentaire getInitializedObject(final String codeLigne,
                                            final String codeSens,
                                            final String codeArret,
                                            final String idClient,
                                            final String message)
    {
        final Commentaire commentaire = new Commentaire();
        if (!StringUtils.isEmpty(codeLigne))
        {
            commentaire.setLigne(this.ligneService.get(codeLigne));
        }
        if (!StringUtils.isEmpty(codeSens))
        {
            final SensSearchCriteria sensCriteria = new SensSearchCriteria();
            sensCriteria.setCode(codeSens);
            sensCriteria.setCodeLigne(codeLigne);
            commentaire.setSens(this.sensService.get(sensCriteria));
        }
        if (!StringUtils.isEmpty(codeArret))
        {
            final ArretSearchCriteria arretCriteria = new ArretSearchCriteria();
            arretCriteria.setCode(codeArret);
            arretCriteria.setCodeSens(codeSens);
            arretCriteria.setCodeLigne(codeLigne);
            commentaire.setArret(this.arretService.get(arretCriteria));
        }
        commentaire.setSource(idClient);
        commentaire.setMessage(message);
        commentaire.setDatePublication(Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                            NaonedbusConstants.LOCALE));
        return commentaire;
    }

    /**
     * Setter pour ligneService.
     * @param ligneService Le ligneService à écrire.
     */
    public void setLigneService(final CodeService<Ligne> ligneService)
    {
        this.ligneService = ligneService;
    }

    /**
     * Setter pour sensService.
     * @param sensService Le sensService à écrire.
     */
    public void setSensService(final CodeService<Sens> sensService)
    {
        this.sensService = sensService;
    }

    /**
     * Setter pour arretService.
     * @param arretService Le arretService à écrire.
     */
    public void setArretService(final CodeService<Arret> arretService)
    {
        this.arretService = arretService;
    }
}
