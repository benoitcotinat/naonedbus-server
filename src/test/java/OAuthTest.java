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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

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

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class OAuthTest
{
    // @org.junit.Test
    public void test()
        throws TwitterException,
            IOException
    {
        // final ConfigurationBuilder cb = new ConfigurationBuilder();
        // cb.setDebugEnabled(true)
        // .setOAuthConsumerKey("0Uvm4N9RECh1dGv11v6rQ")
        // .setOAuthConsumerSecret("yQUU34fvutVW6eVqIo2lragFaRAmQxBboAQwEjzhIE")
        // .setOAuthAccessToken("257439518-0MfbVQfrQdk0OoxDdoTbT4vfTPNlnD9e37SGjN4D")
        // .setOAuthAccessTokenSecret("5NfbhnsSGg1tXHLcji8rv6UW5OU4TwfXXJOgtXAStuA");
        // final TwitterFactory tf = new TwitterFactory(cb.build());
        // final Twitter twitter = tf.getInstance();
        // twitter.updateStatus("Hello, world !");

        final Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("0Uvm4N9RECh1dGv11v6rQ",
                                 "yQUU34fvutVW6eVqIo2lragFaRAmQxBboAQwEjzhIE");
        final RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken)
        {
            System.out.println("Open the following URL and grant access to your account:");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
            final String pin = br.readLine();
            try
            {
                if (pin.length() > 0)
                {
                    accessToken = twitter.getOAuthAccessToken(requestToken,
                                                              pin);
                }
                else
                {
                    accessToken = twitter.getOAuthAccessToken();
                }
            }
            catch (final TwitterException te)
            {
                if (401 == te.getStatusCode())
                {
                    System.out.println("Unable to get the access token.");
                }
                else
                {
                    te.printStackTrace();
                }
            }
        }
        // persist to the accessToken for future reference.
        OAuthTest.storeAccessToken(twitter.verifyCredentials().getId(),
                              accessToken);
        // Status status = twitter.updateStatus(args[0]);
        // System.out.println("Successfully updated the status to [" + status.getText() + "].");
        System.exit(0);
    }
    private static void storeAccessToken(final long useId,
                                         final AccessToken accessToken)
    {
        // store accessToken.getToken()
        // store accessToken.getTokenSecret()
        System.out.println("useId="
                           + useId);
        System.out.println(accessToken.getToken());
        System.out.println(accessToken.getTokenSecret());
    }
}
