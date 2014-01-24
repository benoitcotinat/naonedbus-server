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


import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import net.naonedbus.model.Horaire;
import net.naonedbus.service.metier.HoraireService;
import net.naonedbus.utils.DateFormatHelper;
import net.naonedbus.utils.DateHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{"classpath*:config/applicationContext.xml" })
@Transactional(readOnly = false)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class HoraireServiceImplSessionTest
{
    @Resource(name = "horaireService")
    private HoraireService horaireService;

    @Test
    public void test()
    {
        final Long timeRequest =
            DateHelper.getTimestampStartOfDay(Calendar.getInstance().getTimeInMillis());
        // timeRequest += 30 * DateUtils.MILLIS_PER_DAY;
        final List<Horaire> horaires = this.horaireService.get("H",
                                                               "2",
                                                               "BCOI1",
                                                               timeRequest);

        final DateFormatHelper dfh = new DateFormatHelper();
        for (final Horaire horaire : horaires)
        {
            System.out.println(dfh.formatCompleteDateHour(horaire.getTimestamp()));
        }
    }

}
