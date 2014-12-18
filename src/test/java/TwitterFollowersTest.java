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
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.naonedbus.context.NaonedbusTwitterFactory;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * <p>
 * Copyright (C) 2011 Romain Guefveneu
 * </p>
 * <p>
 * This file is part of naonedbus.
 * </p>
 * <p>
 * Naonedbus is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * </p>
 * <p>
 * Naonedbus is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * </p>
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 * </p>
 */

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class TwitterFollowersTest
{
    /**
     * Récupération des IDs des followers de @tan_nantes.
     */
    @Test
    public void test()
        throws TwitterException,
            IOException
    {
        final Twitter twitterTanNantes = new NaonedbusTwitterFactory().getObject("0Uvm4N9RECh1dGv11v6rQ",
                                                                                 "yQUU34fvutVW6eVqIo2lragFaRAmQxBboAQwEjzhIE",
                                                                                 "46602184-nr59LJbnNnUlkel2oedZwIElkxRYQ6XQLvzYfa2mA",
                                                                                 "AG8VLku2A3QF89StUUF34fMoZ3QpOr8J2TKSxCT78");
        final Twitter twitterNaonedbusLive = new NaonedbusTwitterFactory().getObject("0Uvm4N9RECh1dGv11v6rQ",
                                                                                     "yQUU34fvutVW6eVqIo2lragFaRAmQxBboAQwEjzhIE",
                                                                                     "2798085759-6fnKPlWCJ5k13Dkc5uDuiTdsBYnvJfCa4LCDwVj",
                                                                                     "HiWIbf04oesDCwZ7ukmN5ks72A0PingaXiSQFYazDJ0h5");

        // Récupération des followers de tan_nantes
        final Set<Long> tanNantesFollowersIds = this.getFollowersIds(twitterTanNantes);
        final Set<Long> naonedbusLiveFollowersIds = this.getFollowersIds(twitterNaonedbusLive);
        final Collection<Long> subtract = CollectionUtils.subtract(tanNantesFollowersIds,
                                                                   naonedbusLiveFollowersIds);



        /* ******************* */
        this.step2Tweet(twitterTanNantes);
        this.step2AddFollowers(twitterNaonedbusLive,
                               tanNantesFollowersIds);
        // sendDM(twitterTanNantes);
    }

    private void step2Tweet(final Twitter twitterTanNantes)
        throws TwitterException
    {
        twitterTanNantes.updateStatus("");
    }

    private void step2AddFollowers(final Twitter twitterNaonedbusLive,
                       final Set<Long> followersIds)
        throws TwitterException
    {
        for (final Long followerId : followersIds)
        {
            twitterNaonedbusLive.createFriendship(followerId,
                                                  true);
        }
    }

    /**
     * @param twitter
     * @return
     * @throws TwitterException
     */
    private Set<Long> getFollowersIds(final Twitter twitter)
        throws TwitterException
    {
        final Set<Long> ids = new HashSet<Long>();
        IDs followersIDs = twitter.getFollowersIDs(-1L);
        do
        {
            ids.addAll(Arrays.asList(ArrayUtils.toObject(followersIDs.getIDs())));
            followersIDs = twitter.getFollowersIDs(followersIDs.getNextCursor());
        } while (followersIDs.hasNext());
        return ids;
    }

    private void sendDM(final Twitter twitter)
        throws TwitterException
    {
        final ResponseList<User> searchUsers = twitter.searchUsers("benoit.cotinat",
                                                                   1);
        final User user = searchUsers.get(0);
        System.out.println(">"
                           + user.getScreenName()
                           + " "
                           + user.getId());

        final DirectMessage dm = twitter.sendDirectMessage(user.getId(),
                                                           "Test d'envoi de DM");

        System.out.println(dm.getSenderScreenName()
                           + " -> "
                           + dm.getRecipientScreenName());

    }
}
