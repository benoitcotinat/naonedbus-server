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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.naonedbus.model.Arret;
import net.naonedbus.model.Horaire;
import net.naonedbus.model.criteria.impl.HoraireSearchCriteria;
import net.naonedbus.schedule.job.CommonJob;
import net.naonedbus.service.common.GenericService;
import net.naonedbus.utils.DateHelper;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe contenant les jobs concernant les horaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HoraireCleanerJob
    implements CommonJob, BeanFactoryAware
{

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Service des horaires.
     */
    @Resource(name = "horaireService")
    private GenericService<Horaire> horaireService;

    /**
     * Factory Spring.
     */
    private BeanFactory beanFactory;

    /**
     * Méthode en charge de récupérer les anciens horaires et de les supprimer de la base.<br/>
     * Un horaire est considéré comme ancien s'il est avant la veille à minuit :<br/>
     * - si la méthode est appelée le jour J à 3h30, les horaires antérieurs à J-1 à 00:00 seront
     * supprimés.
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public void execute()
    {
        // Construction du timestamp
        final Calendar calMaxForOldHours = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                                NaonedbusConstants.LOCALE);
        calMaxForOldHours.add(Calendar.DAY_OF_MONTH,
                              -1);
        final Long timestampMaxForOldHours =
            DateHelper.getTimestampStartOfDay(calMaxForOldHours.getTimeInMillis());

        // Recherche
        final HoraireSearchCriteria crit =
            (HoraireSearchCriteria) this.beanFactory.getBean("horaireSearchCriteria");
        crit.setTimestampMax(timestampMaxForOldHours);
        final List<Horaire> horaires = this.horaireService.getAll(crit);

        // LOGS
        if (this.log.isDebugEnabled())
        {
            final Set<Arret> arrets = new HashSet<Arret>();
            final Transformer horaireToArretTransformer = new Transformer() {
                /** {@inheritDoc} */
                @Override
                public Object transform(final Object input)
                {
                    final Horaire horaire = (Horaire) input;
                    return horaire.getArret();
                }
            };
            arrets.addAll(CollectionUtils.collect(horaires,
                                                  horaireToArretTransformer,
                                                  arrets));

            this.log.debug("Suppression des "
                           + horaires.size()
                           + " horaires, de "
                           + arrets.size()
                           + " arrêts.");
        }

        this.horaireService.remove(horaires);

        // LOGS
        if (this.log.isDebugEnabled())
        {
            final List<Horaire> horairesRestant = this.horaireService.getAll();
            final Set<Arret> arrets = new HashSet<Arret>();
            final Transformer horaireToArretTransformer = new Transformer() {
                /** {@inheritDoc} */
                @Override
                public Object transform(final Object input)
                {
                    final Horaire horaire = (Horaire) input;
                    return horaire.getArret();
                }
            };
            arrets.addAll(CollectionUtils.collect(horairesRestant,
                                                  horaireToArretTransformer,
                                                  arrets));

            this.log.debug("Il reste "
                           + horairesRestant.size()
                           + " horaires, de "
                           + arrets.size()
                           + " arrêts.");
        }
    }
    /**
     * Setter pour horaireService.
     * @param horaireService Le horaireService à écrire.
     */
    public void setHoraireService(final GenericService<Horaire> horaireService)
    {
        this.horaireService = horaireService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanFactory(final BeanFactory beanFactory)
        throws BeansException
    {
        this.beanFactory = beanFactory;
    }
}
