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
package net.naonedbus.factory.impl;

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


import junit.framework.Assert;
import net.naonedbus.model.Arret;
import net.naonedbus.model.Commentaire;
import net.naonedbus.model.Ligne;
import net.naonedbus.model.Sens;
import net.naonedbus.model.criteria.impl.CommonCodeSearchCriteria;
import net.naonedbus.service.common.CodeService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Teste la factory de commentaire.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommentaireFactoryTest
{
    /**
     * Service des lignes mocké.
     */
    private CodeService<Ligne> ligneServiceMock;
    /**
     * Service des sens mocké.
     */
    private CodeService<Sens> sensServiceMock;
    /**
     * Service des arrets mocké.
     */
    private CodeService<Arret> arretServiceMock;

    /**
     * Factory à tester.
     */
    private CommentaireFactory factory;

    /**
     * Initialisation du test.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp()
    {
        this.factory = new CommentaireFactory();

        this.ligneServiceMock = Mockito.mock(CodeService.class);
        this.factory.setLigneService(this.ligneServiceMock);

        this.sensServiceMock = Mockito.mock(CodeService.class);
        this.factory.setSensService(this.sensServiceMock);

        this.arretServiceMock = Mockito.mock(CodeService.class);
        this.factory.setArretService(this.arretServiceMock);
    }

    /**
     * Finalisation du test.
     */
    @After
    public void tearDown()
    {
        this.ligneServiceMock = null;
        this.sensServiceMock = null;
        this.arretServiceMock = null;
        this.factory = null;
    }

    /**
     * Vérification de l'initialisation.
     */
    @Test
    public void testInit()
    {
        Assert.assertNotNull(this.factory);
        Assert.assertNotNull(this.ligneServiceMock);
        Assert.assertNotNull(this.sensServiceMock);
        Assert.assertNotNull(this.arretServiceMock);
    }

    /**
     * Teste la création d'un commentaire.
     */
    @Test
    public void test()
    {
        final String codeLigne = "42";
        final String codeSens = "1";
        final String codeArret = "NETA6";
        final String idClient = "android";
        final String message = "Frakin' clim !";

        final Ligne ligne = new Ligne();
        ligne.setCode(codeLigne);
        final Sens sens = new Sens();
        sens.setCode(codeSens);
        final Arret arret = new Arret();
        arret.setCode(codeArret);

        Mockito.when(this.ligneServiceMock.get(Matchers.anyString())).thenReturn(ligne);
        Mockito.when(this.sensServiceMock.get(Matchers.any(CommonCodeSearchCriteria.class)))
                .thenReturn(sens);
        Mockito.when(this.arretServiceMock.get(Matchers.any(CommonCodeSearchCriteria.class)))
                .thenReturn(arret);

        final Commentaire commentaire = this.factory.getInitializedObject(codeLigne,
                                                                          codeSens,
                                                                          codeArret,
                                                                          idClient,
                                                                          message);

        Assert.assertNotNull(idClient);
        Assert.assertNotNull(commentaire);
        Assert.assertEquals(codeLigne,
                            commentaire.getLigne().getCode());
        Assert.assertEquals(codeSens,
                            commentaire.getSens().getCode());
        Assert.assertEquals(codeArret,
                            commentaire.getArret().getCode());
        Assert.assertEquals(message,
                            commentaire.getMessage());
        Assert.assertNotNull(commentaire.getDatePublication());
    }
}
