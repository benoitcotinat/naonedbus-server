package net.naonedbus.security;

/**
 * Liste des clients de naonedbus.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public enum NaonedbusClient
{
    /**
     * Cas où le client est inconnu.
     */
    UNKONWN,
    /**
     * Application mobile android naonedbus.
     */
    NAONEDBUS,
    /**
     * Application mobile iOS naonedbus.
     */
    NAONEDBUS_IOS,

    /**
     * Clé pour les messages d'administration.
     */
    NAONEDBUS_SERVICE,

    /**
     * Compte twitter du trafic de la tan.
     */
    TWITTER_TAN_ACTUS("tan_trafic"),

    /**
     * Compte twitter du trafic de la tan.
     */
    TWITTER_TAN_TRAFIC("reseau_tan"),

    /**
     * Compte twitter @TANinfos.
     */
    TWITTER_TAN_INFOS("TANinfos");

    private String account;

    private NaonedbusClient()
    {
    }
    private NaonedbusClient(final String account)
    {
        this.account = account;
    }

    /**
     * Méthode en charge de récupérer le l'instance de l'enum correspondant au nom d'un compte twitter.
     * @param account Nom du compte twitter.
     * @return
     */
    public final static NaonedbusClient getFromAccount(final String account)
    {
        for (final NaonedbusClient naonedbusClient : NaonedbusClient.values())
        {
            if (account.equalsIgnoreCase(naonedbusClient.getAccount()))
            {
                return naonedbusClient;
            }
        }
        return UNKONWN;
    }

    public String getAccount()
    {
        return this.account;
    }
}
