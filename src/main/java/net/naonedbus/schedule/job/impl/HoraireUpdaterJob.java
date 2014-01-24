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
package net.naonedbus.schedule.job.impl;

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
import java.util.List;

import javax.annotation.Resource;

import net.naonedbus.model.Arret;
import net.naonedbus.schedule.job.CommonJob;
import net.naonedbus.service.metier.ArretService;
import net.naonedbus.service.metier.HoraireService;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe contenant les jobs concernant les horaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HoraireUpdaterJob
    implements CommonJob
{

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Nombre de jours d'horaires à updater.
     */
    private Integer nbJoursToUpdate;

    /**
     * Service des arrets.
     */
    @Resource(name = "arretService")
    private ArretService arretService;

    /**
     * Service des horaires.
     */
    @Resource(name = "horaireService")
    private HoraireService horaireService;

    /**
     * Méthode en charge d'appeler le chargement des horaires des arrêts présents en base pour
     * <code>nbJoursToUpdate</code> prochains jours.
     */
    @Transactional
    public void execute()
    {
        final List<Arret> arrets = this.arretService.getArretsWithHoraires();

        if (this.log.isDebugEnabled())
        {
            this.log.debug("Récupération de "
                           + arrets.size()
                           + " arrêts avec des horaires.");
            this.log.debug("Il y a "
                           + this.arretService.getAll().size()
                           + " arrêts en tout dans la base.");
        }

        final Calendar today = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                    NaonedbusConstants.LOCALE);
        today.add(this.nbJoursToUpdate,
                  Calendar.DAY_OF_MONTH);
        final Long timestamp = today.getTimeInMillis();

        for (final Arret arret : arrets)
        {
            this.horaireService.get(arret.getSens().getLigne().getCode(),
                                    arret.getSens().getCode(),
                                    arret.getCode(),
                                    timestamp);
        }
    }

    /**
     * Setter pour horaireService.
     * @param horaireService Le horaireService à écrire.
     */
    public void setArretService(final ArretService arretService)
    {
        this.arretService = arretService;
    }

    /**
     * Setter pour horaireService.
     * @param horaireService Le horaireService à écrire.
     */
    public void setHoraireService(final HoraireService horaireService)
    {
        this.horaireService = horaireService;
    }

    /**
     * Setter pour nbJoursToUpdate.
     * @param nbJoursToUpdate Le nbJoursToUpdate à écrire.
     */
    public void setNbJoursToUpdate(final Integer nbJoursToUpdate)
    {
        this.nbJoursToUpdate = nbJoursToUpdate;
    }
}
