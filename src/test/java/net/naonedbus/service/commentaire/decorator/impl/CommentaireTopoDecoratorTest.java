/**
 *
 */
package net.naonedbus.service.commentaire.decorator.impl;

import junit.framework.Assert;
import net.naonedbus.model.Arret;
import net.naonedbus.model.Commentaire;
import net.naonedbus.model.Ligne;
import net.naonedbus.model.Sens;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.junit.Test;

/**
 * Description de la classe.
 *
 * @author Netapsys
 * @version $Revision$ $Date$
 *
 */
public class CommentaireTopoDecoratorTest
{

    @Test
    public void testDecorateTooLong()
    {
        final String nomLigne = "42";
        final String nomSens = "SensDebutSensFin";
        final String nomArret = "ArretDebutArretFin";
        final String message =
            "Ceci est le début d'un très long message de 120 caractères 01234567890123456789012345678901234567890123456789012345 FIN";

        final Ligne ligne = new Ligne();
        ligne.setNom(nomLigne);
        final Sens sens = new Sens();
        sens.setNom(nomSens);
        final Arret arret = new Arret();
        arret.setNom(nomArret);
        final Commentaire commentaire = new Commentaire();
        commentaire.setLigne(ligne);
        commentaire.setSens(sens);
        commentaire.setArret(arret);
        commentaire.setMessage(message);

        new CommentaireTopoDecorator().decorate(commentaire);

        final String expected = "L.42 "
                                + NaonedbusConstants.SENS
                                + " "
                                + "Sens"
                                + NaonedbusConstants.TRONCATURE
                                + ", "
                                + "ArretDebu"
                                + NaonedbusConstants.TRONCATURE
                                + ".\n"
                                + message;
        Assert.assertEquals(expected,
                            commentaire.getMessage());
    }
    @Test
    public void testDecorateNormal()
    {
        final String nomLigne = "42";
        final String nomSens = "sens";
        final String nomArret = "arret";
        final String message = "message";

        final Ligne ligne = new Ligne();
        ligne.setNom(nomLigne);
        final Sens sens = new Sens();
        sens.setNom(nomSens);
        final Arret arret = new Arret();
        arret.setNom(nomArret);
        final Commentaire commentaire = new Commentaire();
        commentaire.setLigne(ligne);
        commentaire.setSens(sens);
        commentaire.setArret(arret);
        commentaire.setMessage(message);

        new CommentaireTopoDecorator().decorate(commentaire);

        final String expected = "L. 42 "
                                + NaonedbusConstants.SENS
                                + " "
                                + nomSens
                                + ", "
                                + nomArret
                                + ".\n"
                                + message;
        Assert.assertEquals(expected,
                            commentaire.getMessage());
    }
    @Test
    public void testDecorateNoArret()
    {
        final String nomLigne = "42";
        final String nomSens = "sens";
        final String message = "message";

        final Ligne ligne = new Ligne();
        ligne.setNom(nomLigne);
        final Sens sens = new Sens();
        sens.setNom(nomSens);
        final Commentaire commentaire = new Commentaire();
        commentaire.setLigne(ligne);
        commentaire.setSens(sens);
        commentaire.setMessage(message);

        new CommentaireTopoDecorator().decorate(commentaire);

        final String expected = "L. 42 "
                                + NaonedbusConstants.SENS
                                + " "
                                + nomSens
                                + ".\n"
                                + message;
        Assert.assertEquals(expected,
                            commentaire.getMessage());
    }
    @Test
    public void testDecorateNoSens()
    {
        final String nomLigne = "42";
        final String message = "message";

        final Ligne ligne = new Ligne();
        ligne.setNom(nomLigne);
        final Commentaire commentaire = new Commentaire();
        commentaire.setLigne(ligne);
        commentaire.setMessage(message);

        new CommentaireTopoDecorator().decorate(commentaire);

        final String expected = "L. 42"
                                + ".\n"
                                + message;
        Assert.assertEquals(expected,
                            commentaire.getMessage());
    }
    @Test
    public void testDecorateNoTopo()
    {
        final String message = "message";

        final Commentaire commentaire = new Commentaire();
        commentaire.setMessage(message);

        new CommentaireTopoDecorator().decorate(commentaire);

        final String expected = message;
        Assert.assertEquals(expected,
                            commentaire.getMessage());
    }

    /**
     * Test method for {@link net.naonedbus.service.commentaire.decorator.impl.CommentaireTopoDecorator#appendIfSubstring(java.lang.String, int)}.
     */
    @Test
    public void testAppendIfSubstringTooLong()
    {
        Assert.assertEquals("1234\u2026",
                            new CommentaireTopoDecorator().appendIfSubstring("123456789",
                                                                             5));
    }
    @Test
    public void testAppendIfSubstringTooShort()
    {
        Assert.assertEquals("12345",
                            new CommentaireTopoDecorator().appendIfSubstring("12345",
                                                                             8));
    }
    @Test
    public void testAppendIfSubstringEquals()
    {
        Assert.assertEquals("12345",
                            new CommentaireTopoDecorator().appendIfSubstring("12345",
                                                                             5));
    }

}
