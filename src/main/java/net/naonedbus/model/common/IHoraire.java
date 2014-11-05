package net.naonedbus.model.common;

/**
 * Interface commune exposant le contrat pour les horaires.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public interface IHoraire
    extends IBeanObject
{
    /**
     * Getter pour time.
     * @return Le time
     */
    Long getTimestamp();

    /**
     * Setter pour time.
     * @param time Le time à écrire.
     */
    void setTimestamp(final Long timestamp);

    /**
     * Getter pour terminus.
     * @return Le terminus
     */
    String getTerminus();

    /**
     * Setter pour terminus.
     * @param terminus Le terminus à écrire.
     */
    void setTerminus(final String terminus);
}
