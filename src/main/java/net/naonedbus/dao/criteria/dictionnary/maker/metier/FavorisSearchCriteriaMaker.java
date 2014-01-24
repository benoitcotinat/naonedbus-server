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
package net.naonedbus.dao.criteria.dictionnary.maker.metier;

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
import net.naonedbus.dao.criteria.dictionnary.maker.common.AbstractCriteriaMaker;
import net.naonedbus.dao.criteria.dictionnary.maker.utils.CriteriaMakerUtils;
import net.naonedbus.model.criteria.impl.FavorisSearchCriteria;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;

/**
 * Artisan de recherche pour les favoris.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class FavorisSearchCriteriaMaker
    extends AbstractCriteriaMaker
    implements Serializable
{
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 6145071598612008309L;

    /**
     * Constructeur par défaut.
     */
    public FavorisSearchCriteriaMaker()
    {
        super(FavorisSearchCriteria.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transform(final Criteria criteria,
                          final SearchCriteria searchCrit)
    {
        final FavorisSearchCriteria crit = (FavorisSearchCriteria) searchCrit;

        // Identifiant des favoris de l'utilisateur.
        if (!StringUtils.isEmpty(crit.getIdentifiant())
            && !StringUtils.isEmpty(crit.getIdentifiant()))
        {
            CriteriaMakerUtils.addCritere(criteria,
                                          "identifiant",
                                          crit.getIdentifiant());
        }

    }

}
