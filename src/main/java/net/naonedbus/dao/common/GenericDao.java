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
package net.naonedbus.dao.common;

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

import net.naonedbus.dao.criteria.SearchCriteria;
import net.naonedbus.model.BeanObject;

/**
 * DAO (Data Access Object) générique pour l'utilisation standard des POJOs (CRUD).
 * @param <BEAN> Bean Objet Métier.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public interface GenericDao<BEAN extends BeanObject>
    extends Serializable
{
    /**
     * Purge un objet de la session.
     * @param bean Bean à détacher de la session.
     */
    void dettach(final BEAN bean);

    /**
     * Reattach un objet à une session pour obtenir le lazyloading.
     * @param bean Objet à rattacher.
     * @return Objet rattaché.
     */
    BEAN reattach(final BEAN bean);

    /**
     * Méthode générique de listing de tous les objets d'un type particulier avec un critère de
     * recherche.
     * @param criteria Le critère de recherche.
     * @return La liste de tous les objets filtrés.
     */
    List<BEAN> getAll(SearchCriteria criteria);

    /**
     * Méthode générique de listing de tous les objets d'un type particulier.
     * @return La liste de tous les objets filtrés.
     */
    List<BEAN> getAll();

    /**
     * Méthode retournant le nombre de résultats des objets d'un type particulier avec un critère
     * de recherche.
     * @param criteria Le critère de recherche.
     * @return Nombre de résultats.
     */
    int nbResults(final SearchCriteria criteria);

    /**
     * Méthode générique de récupération d'un objet d'un type particulier, en fonction de son
     * identifiant.
     * @param id L'identifiant de l'objet (sa clef primaire).
     * @return L'objet recherché.
     */
    BEAN get(Integer id);

    /**
     * Méthode générique de sauvegarde d'un objet d'un type particulier - doit gérer à la fois
     * update et insert.
     * @param object L'objet à sauvegarder.
     * @return BEAN.
     */
    BEAN save(BEAN object);

    /**
     * Méthode générique de suppression d'un objet d'un type particulier, en fonction de son
     * identifiant.
     * @param object Objet à supprimer.
     */
    void remove(BEAN object);

}
