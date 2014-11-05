package net.naonedbus.security;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Wrapper contenant le modulo et l'exposant d'une clé.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class CommonKey
    implements Serializable
{
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -3114030143437689532L;

    /**
     * Modulo.
     */
    private BigInteger modulo;

    /**
     * Exposant.
     */
    private BigInteger exposant;

    /**
     * Getter pour modulo.
     * @return Le modulo
     */
    public BigInteger getModulo()
    {
        return this.modulo;
    }

    /**
     * Setter pour modulo.
     * @param modulo Le modulo à écrire.
     */
    public void setModulo(final BigInteger modulo)
    {
        this.modulo = modulo;
    }

    /**
     * Getter pour exposant.
     * @return Le exposant
     */
    public BigInteger getExposant()
    {
        return this.exposant;
    }

    /**
     * Setter pour exposant.
     * @param exposant Le exposant à écrire.
     */
    public void setExposant(final BigInteger exposant)
    {
        this.exposant = exposant;
    }
}
