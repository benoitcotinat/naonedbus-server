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
package net.naonedbus.scraper.impl;

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


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.naonedbus.exception.NaonedbusException;
import net.naonedbus.model.Horaire;
import net.naonedbus.scraper.NaonedbusScraper;
import net.naonedbus.scraper.builder.HorairesBuidler;
import net.naonedbus.scraper.processor.HoraireProcessor;
import net.naonedbus.scraper.processor.HoraireTerminusProcessor;
import net.naonedbus.scraper.wraper.NaonedbusScraperWrapper;
import net.naonedbus.scraper.wraper.impl.HoraireScraperWrapper;
import net.naonedbus.scrapper.common.ScraperExecutor;
import net.naonedbus.utils.DateFormatHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webharvest.runtime.ScraperContext;
import org.webharvest.runtime.variables.Variable;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class HorairesScraper
    implements NaonedbusScraper<Horaire>
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -695271541288876959L;

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * 
     */
    @Resource(name = "scraperExecutor")
    private ScraperExecutor horaireScraperExecutor;

    /**
     * Helper de foramttage des dates.
     */
    @Resource(name = "dateFormatHelper")
    private DateFormatHelper dateFormatHelper;

    /**
     * Processor de récupération des terminus.
     */
    @Resource(name = "horaireTerminusProcessor")
    private HoraireTerminusProcessor horaireTerminusProcessor;

    /**
     * Processor de récupération des horaires.
     */
    @Resource(name = "horaireProcessor")
    private HoraireProcessor horaireProcessor;

    /**
     * Builder des horaires avec les infos récupérées du site.
     */
    @Resource(name = "horairesBuilder")
    private HorairesBuidler horairesBuilder;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Horaire> scrap(final NaonedbusScraperWrapper wrapper)
        throws NaonedbusException
    {
        final HoraireScraperWrapper horaireWrapper = (HoraireScraperWrapper) wrapper;

        final ScraperContext context =
            this.horaireScraperExecutor.execute(this.dateFormatHelper.formatForURL(horaireWrapper
                                                        .getCalendar()),
                                                horaireWrapper.getArret().getCode(),
                                                horaireWrapper.getArret().getSens().getCode(),
                                                horaireWrapper
                                                        .getArret()
                                                        .getSens()
                                                        .getLigne()
                                                        .getCode());

        // Récupération de la liste des terminus et des horaires
        final Variable varsContent = (Variable) context.get("content");
        final Variable varsHoraires = (Variable) context.get("horaires");

        if (this.log.isDebugEnabled())
        {
            final StringBuffer sb = new StringBuffer();
            sb.append("Donnees recuperees pour l'arret ");
            sb.append(horaireWrapper.getArret().getCode());
            sb.append("-");
            sb.append(horaireWrapper.getArret().getSens().getCode());
            sb.append("-");
            sb.append(horaireWrapper.getArret().getSens().getLigne().getCode());
            sb.append(" le ");
            sb.append(this.dateFormatHelper.formatForURL(horaireWrapper.getCalendar()));
            sb.append(" :\n");
            sb.append("\t");
            sb.append(varsContent.toList().size());
            sb.append(" terminus\n\t");
            sb.append(varsHoraires.toList().size());
            sb.append(" horaires\n\t");
        }

        final Map<String, String> terminus =
            this.horaireTerminusProcessor.process(varsContent.toString());
        final Map<String, List<String>> heuresMinutes =
            this.horaireProcessor.process(varsHoraires.toList());

        // Construction des Horaires.
        final List<Horaire> horaires = this.horairesBuilder.build(horaireWrapper.getArret(),
                                                                  horaireWrapper.getCalendar(),
                                                                  heuresMinutes,
                                                                  terminus);

        return horaires;
    }

}
