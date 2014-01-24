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
package net.naonedbus.dao.hibernate.constants;

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


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test des constantes associées à la gestion du DAO générique.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class GenericDaoHibernateCstesTest
{
    /**
     * GenericDaoHibernateCstes à tester.
     */
    private GenericDaoHibernateCstes cstes;

    /**
     * Méthode en charge d'initialiser les données de test.
     */
    @Before
    public void init()
    {
        this.cstes = new GenericDaoHibernateCstes();
    }

    /**
     * Méthode en charge de purger les données de test.
     */
    @After
    public void end()
    {
        this.cstes = null;
    }

    /**
     * Méthode en charge de tester l'initialisation des données de test.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.cstes);
    }

    /**
     * Méthode en charge de tester la valeur des constantes.
     */
    @Test
    public void testConstantes()
    {
        Assert.assertEquals("L'entity manager a été fermé",
                            GenericDaoHibernateCstes.ENTITY_MANAGER_CLOSED);
        Assert.assertEquals("Le bean n'est pas managé par l'entity manager",
                            GenericDaoHibernateCstes.BEAN_UNMANAGED);
        Assert.assertEquals("Aucune transaction disponible",
                            GenericDaoHibernateCstes.NO_TRANSACTION_AVAILABLE);
        Assert.assertEquals("L'entité n'est plus présente en base",
                            GenericDaoHibernateCstes.ENTITY_NOT_FOUND_DB);
        Assert.assertEquals("L'entité ne peut pas être persistée",
                            GenericDaoHibernateCstes.ENTITY_ERROR_PERSIST);
    }
}
