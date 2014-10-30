import java.util.HashMap;
import java.util.Map;

import net.naonedbus.utils.SecurityHelper;

/**
 *
 */

/**
 * Description de la classe.
 *
 * @author Netapsys
 * @version $Revision$ $Date$
 *
 */
public class SecurityHelperTest
{

    /**
     * @param args
     */
    public static void main(final String[] args)
    {
        final Map<String, String> signatures = new HashMap<>();
        signatures
                .put("naonedbus_service",
                     "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdDBuzrV6dmu0UOVl5CTFA9BIdq/N0VenMoxau0vFuJ55i1IigHl19nRyow35xDB0PC701BIQMz5HL8Z2ICDgzX8NoUcUdGL0thIDVXt8dXjR22jOK0FMgwLB6KnI2yt10/haY5EjstVb/r/EUxwZtlGyrUS1nNYFys9qMePKa+wIDAQAB");
        final SecurityHelper helper = new SecurityHelper();
        helper.setSignatures(signatures);

        final Boolean validateSignedMessage =
            helper
                .validateSignedMessage("NAONEDBUS_SERVICE",
                                           "zHKrr/Teckllcx0Rq2zgRoH1ikzk1IopoUM7PQhobSElKqjJrvS4u5YtcJuvbioGq233witiLQQcEgRzJekSbasV7H8sgB0eiHJScaFo4YkqsK9R5WFrcqejJAz+TeMD6W33s6Hia58gzSBMKtU6/z1e0Xaj/tnDG/nRaTkMoOg=",
                                       "Hello world v2 :)");
        System.out.println(validateSignedMessage);

    }

}
