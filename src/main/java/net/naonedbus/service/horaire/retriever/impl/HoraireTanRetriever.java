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
package net.naonedbus.service.horaire.retriever.impl;

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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.model.Arret;
import net.naonedbus.model.Horaire;
import net.naonedbus.scraper.NaonedbusScraper;
import net.naonedbus.scraper.wraper.impl.HoraireScraperWrapper;
import net.naonedbus.service.horaire.retriever.HoraireRetriever;
import net.naonedbus.utils.comparator.GenericComparator;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HoraireTanRetriever
    implements HoraireRetriever
{
    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Scraper des horaires.
     */
    @Resource(name = "horaireScraper")
    private NaonedbusScraper<Horaire> horaireScraper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Horaire> retrieve(final Long timestamp,
                                  final Arret arret)
    {
        final List<Horaire> horaires = new ArrayList<Horaire>();

        final Calendar date = Calendar.getInstance(NaonedbusConstants.TIMEZONE,
                                                   NaonedbusConstants.LOCALE);
        date.setTimeInMillis(timestamp);

        final HoraireScraperWrapper wraper = new HoraireScraperWrapper();
        wraper.setCalendar(date);
        wraper.setArret(arret);

        try
        {
            horaires.addAll(this.horaireScraper.scrap(wraper));
        }
        catch (final NaonedbusException e)
        {
            this.log.error("Erreur lors de la récupération des horaires pour l'arrêt "
                                   + arret.getCode(),
                           e);
        }

        Collections.sort(horaires,
                         new GenericComparator<Horaire>("timestamp"));

        return horaires;
    }

    /**
     * Setter pour horaireScraper.
     * @param horaireScraper Le horaireScraper à écrire.
     */
    public void setHoraireScraper(final NaonedbusScraper<Horaire> horaireScraper)
    {
        this.horaireScraper = horaireScraper;
    }

}
