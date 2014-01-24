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
package net.naonedbus.service.metier.impl;

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


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.model.Arret;
import net.naonedbus.model.Horaire;
import net.naonedbus.model.criteria.impl.HoraireSearchCriteria;
import net.naonedbus.service.common.impl.GenericServiceImpl;
import net.naonedbus.service.horaire.retriever.HoraireRetriever;
import net.naonedbus.service.metier.ArretService;
import net.naonedbus.service.metier.HoraireService;
import net.naonedbus.utils.DateHelper;
import net.naonedbus.utils.predicate.HoraireSameDayPredicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Service spécifique aux horaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HoraireServiceImpl
    extends GenericServiceImpl<Horaire>
    implements HoraireService, BeanFactoryAware
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -4731437637675667307L;

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Factory Spring.
     */
    private BeanFactory beanFactory;

    /**
     * Service des arrêts.
     */
    @Resource(name = "arretService")
    private ArretService arretService;

    /**
     * Récupération des horaires.
     */
    private HoraireRetriever horaireRetriever;

    /**
     * Constructeur.
     * @param genericDao
     */
    public HoraireServiceImpl(final GenericDao<Horaire> genericDao)
    {
        super(genericDao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Horaire> get(final String codeLigne,
                             final String codeSens,
                             final String codeArret,
                             final long timestamp)
    {
        final List<Horaire> horaires = new ArrayList<Horaire>();

        final Arret arret = this.arretService.get(codeLigne,
                                                  codeSens,
                                                  codeArret);

        if (null == arret)
        {
            this.log.error("Arrêt non trouvé pour le code "
                           + codeArret
                           + " !");
        }
        else
        {
            horaires.addAll(this.load(arret,
                                      timestamp));
            final List<Long> daysToCheckTan = this.daysToCheckTan(timestamp,
                                                                  horaires);
            for (final Long day : daysToCheckTan)
            {
                final List<Horaire> horairesForDay = this.horaireRetriever.retrieve(day,
                                                                                    arret);
                this.saveAll(horairesForDay);
                horaires.addAll(horairesForDay);
            }

            // on ne garde que les horaires compris dans la journée du timestamp demandé
            CollectionUtils.filter(horaires,
                                   new HoraireSameDayPredicate(timestamp));
        }
        return horaires;
    }

    /**
     * Méthode en charge d'indiquer les jours qu'il faut aller chercher sur la tan.<br />
     * <ul>
     * <li>si les horaires retournés ne contiennent pas d'horaire avant 12:00 de J-1, faire une
     * requête sur le site de la tan en demandant les horaires de J-1</li>
     * <li>si les horaires retournés ne contiennent pas d'horaire > à 12:00 de J, faire une
     * requête sur le site de la tan pour J</li>
     * </ul>
     * <b>PRE</b> : Les horaires sont triés par timestamp.
     * @param timestamp Timestamp de la journée demandée.
     * @param horaires Liste des horaires déjà chargés.
     * @return Visite du site de la tan nécessaire ou non.
     */
    List<Long> daysToCheckTan(final Long timestamp,
                              final List<Horaire> horaires)
    {
        final List<Long> timestampsNeed = new ArrayList<Long>();

        final Long midday = DateHelper.getTimestampMidOfDay(timestamp);
        final Long yesterdayMidday = midday
                                     - DateUtils.MILLIS_PER_DAY;
        if (!horaires.isEmpty())
        {
            final Horaire firstHoraire = horaires.get(0);
            // on regarde si on a besoin du jour J-1
            if (firstHoraire.getTimestamp() > yesterdayMidday)
            {
                timestampsNeed.add(yesterdayMidday);
            }

            final Horaire lastHoraire = horaires.get(horaires.size() - 1);
            // on regarde si on a besoin du jour J
            if (lastHoraire.getTimestamp() < midday)
            {
                timestampsNeed.add(timestamp);
            }
        }
        else
        {
            timestampsNeed.add(yesterdayMidday);
            timestampsNeed.add(timestamp);
        }
        return timestampsNeed;
    }

    /**
     * Méthode en charge de charger les horaires d'un arrêt en provenance de la base.
     * @param arret Arret.
     * @param timestamp Date minimale.
     * @return Liste des horaires de l'arrêt.
     */
    List<Horaire> load(final Arret arret,
                       final Long timestamp)
    {
        final HoraireSearchCriteria crit =
            (HoraireSearchCriteria) this.beanFactory.getBean("horaireSearchCriteria");
        crit.setArret(arret);
        crit.setTimestampMin(timestamp
                             - DateUtils.MILLIS_PER_DAY);
        crit.setTimestampMax(DateHelper.getTimestampEndOfDay(timestamp));
        return this.getAll(crit);
    }

    /**
     * Setter pour arretService.
     * @param arretService Le arretService à écrire.
     */
    public void setArretService(final ArretService arretService)
    {
        this.arretService = arretService;
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

    /**
     * Setter pour horaireRetriever.
     * @param horaireRetriever Le horaireRetriever à écrire.
     */
    public void setHoraireRetriever(final HoraireRetriever horaireRetriever)
    {
        this.horaireRetriever = horaireRetriever;
    }

}
