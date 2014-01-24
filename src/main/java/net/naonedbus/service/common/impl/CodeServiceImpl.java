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
package net.naonedbus.service.common.impl;

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
import java.util.List;

import net.naonedbus.dao.common.GenericDao;
import net.naonedbus.model.BeanObjectCode;
import net.naonedbus.model.criteria.impl.CommonCodeSearchCriteria;
import net.naonedbus.service.common.CodeService;

/**
 * Classe de service comune aux entités ayant un code.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CodeServiceImpl<BEAN extends BeanObjectCode>
    extends GenericServiceImpl<BEAN>
    implements CodeService<BEAN>, Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 2047634074678620573L;

    /**
     * Constructeur.
     * @param genericDao
     */
    public CodeServiceImpl(final GenericDao<BEAN> genericDao)
    {
        super(genericDao);
    }

    /**
     * {@inheritDoc}
     */
    public BEAN get(final String code)
    {
        final CommonCodeSearchCriteria criteria = new CommonCodeSearchCriteria();
        criteria.setCode(code);

        final List<BEAN> beans = this.getAll(criteria);

        BEAN bean = null;
        if (!beans.isEmpty())
        {
            bean = beans.get(0);
        }
        return bean;
    }
}
