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
package net.naonedbus.dao.criteria.dictionnary.maker.common;

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

import net.naonedbus.dao.criteria.SearchCriteria;
import net.naonedbus.dao.criteria.dictionnary.maker.utils.CriteriaMakerUtils;
import net.naonedbus.model.criteria.impl.CommonCodeSearchCriteria;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;

/**
 * Classe abstraite de base pour les artisans des critères de recherche.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommonCodeSearchCriteriaMaker
    extends AbstractCriteriaMaker
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -4110449095770174380L;

    /**
     * Constructeur par défaut.
     */
    public CommonCodeSearchCriteriaMaker()
    {
        this(CommonCodeSearchCriteria.class);
    }

    /**
     * @param supportedCriteriaClass
     */
    @SuppressWarnings("rawtypes")
    public CommonCodeSearchCriteriaMaker(final Class supportedCriteriaClass)
    {
        super(supportedCriteriaClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transform(final Criteria criteria,
                          final SearchCriteria searchCrit)
    {
        final CommonCodeSearchCriteria crit = (CommonCodeSearchCriteria) searchCrit;

        // Code de l'entité
        if (!StringUtils.isEmpty(crit.getCode()))
        {
            CriteriaMakerUtils.addCritere(criteria,
                                          "code",
                                          crit.getCode());
        }
    }

}
