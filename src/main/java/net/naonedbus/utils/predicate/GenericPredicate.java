package net.naonedbus.utils.predicate;

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


import net.naonedbus.exception.CommonException;
import net.naonedbus.model.BeanObject;
import net.naonedbus.utils.introspection.BeanTool;

import org.apache.commons.collections.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe en charge de fournir une implémentation particulière des prédicates. L'utilisation
 * simple est d'instancier cette classe avec le nom d'une propriété + une valeur et de passer
 * cette instance à une méthode de la classe utilitaire CollectionUtils (countMatches, select,
 * exists, filter, find, select)
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class GenericPredicate
    implements Predicate
{
    /**
     * Logger par défaut.
     */
    private static final Logger LOG = LoggerFactory.getLogger(GenericPredicate.class);

    /**
     * Caractère JOKER.
     */
    private static final String WILDCARD = "*";

    /**
     * Propriété a comparer.
     */
    private final String propriete;

    /**
     * Valeur de comparaison.
     */
    private final Object value;

    /**
     * Si true : alors renvoie true lorsque le caractere "*" est trouvé.
     */
    private boolean allValue;

    /**
     * Constructeur.
     * @param propriete Propriété à compararer
     * @param value : objet de référence (doit implémenter Comparable)
     */
    public GenericPredicate(final String propriete, final Object value)
    {
        this.propriete = propriete;
        this.value = value;
    }

    /**
     * Constructeur.
     * @param propriete Propriété à compararer
     * @param value Objet de référence (doit implémenter Comparable)
     * @param allValue Si true : alors renvoie true lorsque le caractere "*" est trouvé.
     */
    public GenericPredicate(final String propriete, final Object value, final boolean allValue)
    {
        this(propriete,
             value);
        this.allValue = allValue;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean evaluate(final Object objectToCompare)
    {
        try
        {
            final Object propertyValueAsObject = BeanTool.getPropriete(objectToCompare,
                                                                       this.propriete);
            if (this.value == null
                || propertyValueAsObject == null)
            {
                return this.value == propertyValueAsObject;
            }

            // Gestion du allValue si actif
            if (this.allValue
                && propertyValueAsObject instanceof String
                && GenericPredicate.WILDCARD.equals(propertyValueAsObject))
            {
                return true;
            }
            // La propriété est un bean du modèle métier
            if (propertyValueAsObject instanceof BeanObject)
            {
                return this.handleBeanObject(propertyValueAsObject);
            }
            else
            {
                // Préparation des valeurs
                final Comparable compObject = (Comparable) propertyValueAsObject;
                Comparable refObject = null;
                if (this.value instanceof BeanObject)
                {
                    refObject = (Comparable) BeanTool.getPropriete(this.value,
                                                                   this.propriete);
                }
                else
                {
                    refObject = (Comparable) this.value;
                }
                if (compObject.compareTo(refObject) == 0)
                {
                    return true;
                }
            }
        }
        catch (final CommonException e)
        {
            GenericPredicate.LOG.error(e.getMessage(),
                                       e);
        }
        return false;
    }

    /**
     * Méthode en charge de traiter le predicator sur un BeanObject.
     * @param propertyValueAsObject Valeur propriété.
     * @return Résultat de comparaison.
     */
    public boolean handleBeanObject(final Object propertyValueAsObject)
    {
        final BeanObject valueAsBeanObject = (BeanObject) this.value;
        final BeanObject propertyValueAsBeanObject = (BeanObject) propertyValueAsObject;

        // On compare les ids.
        final Integer propertyValueId = propertyValueAsBeanObject.getId();
        final Integer valueId = valueAsBeanObject.getId();

        if (propertyValueId == null)
        {
            return false;
        }
        return propertyValueId.equals(valueId);
    }

}
