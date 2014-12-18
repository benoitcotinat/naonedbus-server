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


import net.naonedbus.dao.criteria.SearchCriteria;
import net.naonedbus.dao.criteria.dictionnary.maker.common.AbstractCriteriaMaker;
import net.naonedbus.dao.criteria.dictionnary.maker.utils.CriteriaMakerUtils;
import net.naonedbus.model.criteria.impl.ArretSearchCriteria;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;

/**
 * Artisan de recherche pour les arrets.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class ArretSearchCriteriaMaker
    extends AbstractCriteriaMaker
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -3360356580720981191L;

    /**
     * Constructeur par défaut.
     */
    public ArretSearchCriteriaMaker()
    {
        super(ArretSearchCriteria.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transform(final Criteria criteria,
                          final SearchCriteria searchCrit)
    {
        final ArretSearchCriteria crit = (ArretSearchCriteria) searchCrit;

        // Code de la ligne de l'arrêt, nécessite le code du sens
        if (!StringUtils.isEmpty(crit.getCodeLigne())
            && !StringUtils.isEmpty(crit.getCodeSens()))
        {
            final Criteria critSens = criteria.createCriteria("sens",
                                                              "sens");
            critSens.createCriteria("ligne",
                                    "ligne");
            CriteriaMakerUtils.addCritere(criteria,
                                          "sens.code",
                                          crit.getCodeSens());
            CriteriaMakerUtils.addCritere(criteria,
                                          "ligne.code",
                                          crit.getCodeLigne());
        }
    }
}
