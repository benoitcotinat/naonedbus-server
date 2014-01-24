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
import net.naonedbus.model.criteria.impl.CommentaireSearchCriteria;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

/**
 * Artisan de recherche pour les commentaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireSearchCriteriaMaker
    extends AbstractCriteriaMaker
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -7647154110372571097L;

    /**
     * Constructeur par défaut.
     */
    public CommentaireSearchCriteriaMaker()
    {
        super(CommentaireSearchCriteria.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unused")
    @Override
    public void transform(final Criteria criteria,
                          final SearchCriteria searchCrit)
    {
        final CommentaireSearchCriteria crit = (CommentaireSearchCriteria) searchCrit;

        if (!StringUtils.isEmpty(crit.getCodeLigne()))
        {
            final Criteria critLigne = criteria.createCriteria("ligne",
                                                               "ligne",
                                                               CriteriaSpecification.LEFT_JOIN);
            criteria.add(Restrictions.or(Restrictions.eq("ligne.code",
                                                         crit.getCodeLigne()),
                                         Restrictions.isNull("ligne")));

            if (!StringUtils.isEmpty(crit.getCodeSens()))
            {
                final Criteria critSens =
                    criteria.createCriteria("sens",
                                            "sens",
                                            CriteriaSpecification.LEFT_JOIN);
                criteria.add(Restrictions.or(Restrictions.eq("sens.code",
                                                             crit.getCodeSens()),
                                             Restrictions.isNull("sens")));

                if (!StringUtils.isEmpty(crit.getCodeArret()))
                {
                    final Criteria critArret =
                        criteria.createCriteria("arret",
                                                "arret",
                                                CriteriaSpecification.LEFT_JOIN);
                    criteria.add(Restrictions.or(Restrictions.eq("arret.code",
                                                                 crit.getCodeArret()),
                                                 Restrictions.isNull("arret")));
                }
            }
        }

        if (crit.getDatePublication() != null)
        {
            criteria.add(Restrictions.gt("datePublication",
                                         crit.getDatePublication()));
        }

    }
}
