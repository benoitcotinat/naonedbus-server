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


import javax.annotation.Resource;

import net.naonedbus.model.Commentaire;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{"classpath*:config/applicationContext.xml" })
@Transactional
public class CommentaireFactorySessionTest
{
    /**
     * 
     */
    @Resource(name = "commentaireFactory")
    private CommentaireFactory commentaireFactory;

    /**
     * Test method for
     * {@link net.naonedbus.factory.impl.CommentaireFactory#getInitializedObject(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testGetInitializedObject()
    {
        final Commentaire commentaire = this.commentaireFactory.getInitializedObject("52",
                                                                                     "1",
                                                                                     null,
                                                                                     "naonedbus",
                                                                                     "test");
        System.out.println(commentaire.getLigne().getId()
                           + "-"
                           + commentaire.getLigne().getCode());
        System.out.println(commentaire.getSens().getId()
                           + "-"
                           + commentaire.getSens().getCode());
    }
    /**
     * Setter pour commentaireFactory.
     * @param commentaireFactory Le commentaireFactory à écrire.
     */
    public void setCommentaireFactory(final CommentaireFactory commentaireFactory)
    {
        this.commentaireFactory = commentaireFactory;
    }

}
