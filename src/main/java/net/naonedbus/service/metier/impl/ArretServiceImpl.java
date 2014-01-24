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


import java.util.List;

import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.model.Arret;
import net.naonedbus.model.criteria.impl.ArretSearchCriteria;
import net.naonedbus.model.criteria.impl.ArretsWithHorairesSearchCriteria;
import net.naonedbus.service.common.impl.GenericServiceImpl;
import net.naonedbus.service.metier.ArretService;

/**
 * Service spécifique aux horaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class ArretServiceImpl
    extends GenericServiceImpl<Arret>
    implements ArretService
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 170992159965480129L;

    /**
     * Constructeur.
     * @param genericDao
     */
    public ArretServiceImpl(final GenericDao<Arret> genericDao)
    {
        super(genericDao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Arret get(final String codeLigne,
                     final String codeSens,
                     final String codeArret)
    {
        final ArretSearchCriteria crit = new ArretSearchCriteria();
        crit.setCode(codeArret);
        crit.setCodeSens(codeSens);
        crit.setCodeLigne(codeLigne);
        final List<Arret> arrets = this.getAll(crit);

        Arret arret = null;
        if (arrets.size() > 0)
        {
            arret = arrets.get(0);
        }
        return arret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Arret> getArretsWithHoraires()
    {
        final ArretsWithHorairesSearchCriteria crit = new ArretsWithHorairesSearchCriteria();
        return this.getAll(crit);
    }
}
