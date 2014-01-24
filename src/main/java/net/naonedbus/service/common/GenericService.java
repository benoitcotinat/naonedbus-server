package net.naonedbus.service.common;

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

import net.naonedbus.dao.criteria.SearchCriteria;
import net.naonedbus.model.BeanObject;

/**
 * Interface de définition du manager générique pour l'utilisation standard des POJOs (CRUD).
 * @author Benoît
 * @version $Revision$ $Date$
 * @param <BEAN> Bean Objet Métier.
 */
public interface GenericService<BEAN extends BeanObject>
{
    /**
     * Purge un objet de la session.
     * @param bean Bean à détacher de la session.
     */
    void dettach(final BEAN bean);

    /**
     * Reattache un objet à une session pour obtenir le lazyloading.
     * @param bean Objet à rattacher.
     * @return Objet rattaché.
     */
    BEAN reattach(final BEAN bean);

    /**
     * Réattache une liste d'objets à une session pour obtenir le lazyloading.
     * @param beans Liste des objets à rattacher.
     * @return Liste des objets rattachés.
     */
    List<BEAN> reattachAll(final List<BEAN> beans);

    /**
     * Méthode générique de listing de tous les objets d'un type particulier avec un critère de
     * recherche.
     * @param criteria Le critère de recherche.
     * @return La liste de tous les objets filtrés.
     */
    List<BEAN> getAll(final SearchCriteria criteria);

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
    BEAN get(final Integer id);

    /**
     * Méthode générique de récupération d'un objet d'un type particulier avec un critère de
     * recherche.
     * @param criteria Le critère de recherche.
     * @return La liste de tous les objets filtrés.
     */
    BEAN get(final SearchCriteria criteria);

    /**
     * Méthode générique de sauvegarde d'un objet d'un type particulier - doit gérer à la fois
     * update et insert.
     * @param object L'objet à sauvegarder.
     * @return BEAN.
     */
    BEAN save(final BEAN object);

    /**
     * Méthode générique de sauvegarde d'objets d'un type particulier - doit gérer à la fois
     * update et insert.
     * @param beans La liste d'objets à sauvegarder.
     * @return Liste des objets BEAN sauvegardés.
     */
    List<BEAN> saveAll(final List<BEAN> beans);

    /**
     * Méthode générique de suppression d'un objet d'un type particulier.
     * @param object L'objet à supprimer.
     */
    void remove(final BEAN object);

    /**
     * Méthode générique de suppression d'une liste d'objets d'un type particulier.
     * @param objects Les objets à supprimer.
     */
    void remove(final List<BEAN> objects);

}
