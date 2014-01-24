package net.naonedbus.utils.introspection;

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


import java.lang.reflect.InvocationTargetException;

import net.naonedbus.exception.CommonException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe en charge de fournir des méthodes simplifiées d'introspection.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public final class BeanTool
{

    /** Afficher null dans le message de log si le bean est null. */
    protected static final String NULL = "null";

    /**
     * Logger par défaut.
     */
    private static final Logger LOG = LoggerFactory.getLogger(BeanTool.class);

    /**
     * Constructeur privé pour classe d'utilitaire.
     */
    private BeanTool()
    {
        // RAF.
    }

    /**
     * Retourne le nom de la classe de l'objet.
     * @param objet Objet.
     * @return Retourne le nom de la classe de l'objet.
     */
    protected static String getClassName(final Object objet)
    {
        if (objet == null)
        {
            return BeanTool.NULL;
        }
        else
        {
            return objet.getClass().getName();
        }
    }

    /**
     * Méthode indiquant si la propriété est définie et accessible pour le bean.
     * @param bean Objet à évaluer.
     * @param property Propriété de l'objet à évaluer.
     * @return : Indique que l'on peut accéder à la propriété.
     */
    public static boolean hasProperty(final Object bean,
                                      final String property)
    {
        if (bean == null)
        {
            return false;
        }
        try
        {
            PropertyUtils.getNestedProperty(bean,
                                            property);
        }
        catch (final NestedNullException e)
        {
            // Le bean ou sa propriété est null.
            return false;
        }
        catch (final NoSuchMethodException e)
        {
            // Aucun getter pour la propriété dans la classe
            return false;
        }
        catch (final IllegalAccessException e)
        {
            // Getter pour la propriété est non publique dans la classe
            return false;
        }
        catch (final InvocationTargetException e)
        {
            return false;
        }
        return true;
    }

    /**
     * Méthode retournant la valeur de la propriété de l'objet en cours.
     * @param bean Objet à évaluer.
     * @param propriete Propriété de l'objet à évaluer.
     * @return : Valeur de la propriété
     * @throws CommonException Erreur technique.
     */
    public static Object getPropriete(final Object bean,
                                      final String propriete)
        throws CommonException
    {

        try
        {
            return PropertyUtils.getNestedProperty(bean,
                                                   propriete);
        }
        catch (final NestedNullException e)
        {
            return null;
        }
        catch (final NoSuchMethodException e)
        {
            final String error = "Aucun getter pour la propriété "
                                 + propriete
                                 + " dans la classe   "
                                 + BeanTool.getClassName(bean);
            BeanTool.LOG.error(error,
                               e);
            throw new CommonException(error,
                                      e);

        }
        catch (final IllegalAccessException e)
        {
            final String error = "Le getter pour la propriété "
                                 + propriete
                                 + " dans la classe "
                                 + BeanTool.getClassName(bean)
                                 + " doit être publique";
            BeanTool.LOG.error(error,
                               e);
            throw new CommonException(error,
                                      e);
        }
        catch (final InvocationTargetException e)
        {
            final Throwable cause = e.getCause();
            BeanTool.LOG.error(cause.getMessage(),
                               cause);
            throw new CommonException(cause.getMessage(),
                                      e);
        }
    }
    /**
     * Définit la valeur de la propriété de l'objet en cours.
     * @param bean Objet à évaluer.
     * @param propertyName Propriété de l'objet à définir.
     * @param propertyValue Valeur à définir
     * @throws CommonException Erreur technique.
     */
    public static void setPropriete(final Object bean,
                                    final String propertyName,
                                    final Object propertyValue)
        throws CommonException
    {
        try
        {
            PropertyUtils.setNestedProperty(bean,
                                            propertyName,
                                            propertyValue);
        }
        catch (final NestedNullException e)
        {
            return;
        }
        catch (final IllegalArgumentException e)
        {
            final String error = "Le bean ou la propriété n'est pas défini : "
                                 + e.getMessage();
            BeanTool.LOG.error(error,
                               e);
            throw new CommonException(error,
                                      e);
        }
        catch (final NoSuchMethodException e)
        {
            final String error = "Aucun getter pour la propriété "
                                 + propertyName
                                 + " dans la classe   "
                                 + BeanTool.getClassName(bean);
            BeanTool.LOG.error(error,
                               e);
            throw new CommonException(error,
                                      e);
        }
        catch (final IllegalAccessException e)
        {
            final String error = "Le getter pour la propriété "
                                 + propertyName
                                 + " dans la classe "
                                 + BeanTool.getClassName(bean)
                                 + " doit être publique";
            BeanTool.LOG.error(error,
                               e);
            throw new CommonException(error,
                                      e);
        }
        catch (final InvocationTargetException e)
        {
            final Throwable cause = e.getCause();
            BeanTool.LOG.error(cause.getMessage(),
                               cause);
            throw new CommonException(cause.getMessage(),
                                      e);
        }
    }

    /**
     * Copie les propriétés de l'objet source vers l'objet cible; NOTE : Si l'un des deux objets
     * est null, aucune copie n'est effectuée.
     * @param destination Objet cible
     * @param source Objet source
     * @throws CommonException Erreur technique.
     */
    public static void copyProperties(final Object destination,
                                      final Object source)
        throws CommonException
    {
        if (source == null)
        {
            return;
        }
        if (destination == null)
        {
            return;
        }
        try
        {
            BeanUtils.copyProperties(destination,
                                     source);
        }
        catch (final IllegalAccessException e)
        {
            final String error = "Une des propriétés de l'objet source n'est pas accessible.";
            BeanTool.LOG.error(error,
                               e);
            throw new CommonException(error,
                                      e);
        }
        catch (final InvocationTargetException e)
        {
            final String error = "Erreur lors de la recopie des propriétés de l'objet source.";
            BeanTool.LOG.error(error,
                               e);
            BeanTool.LOG.error(e.getMessage(),
                               e);
            throw new CommonException(error,
                                      e);
        }
    }

}
