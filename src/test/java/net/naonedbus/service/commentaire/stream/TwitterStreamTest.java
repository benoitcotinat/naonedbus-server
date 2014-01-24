package net.naonedbus.service.commentaire.stream;

import org.apache.commons.lang.StringUtils;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreamTest
{

    public static void main(final String[] args)
        throws TwitterException
    {
        final ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("0Uvm4N9RECh1dGv11v6rQ")
                .setOAuthConsumerSecret("yQUU34fvutVW6eVqIo2lragFaRAmQxBboAQwEjzhIE")
                .setOAuthAccessToken("257439518-0MfbVQfrQdk0OoxDdoTbT4vfTPNlnD9e37SGjN4D")
                .setOAuthAccessTokenSecret("5NfbhnsSGg1tXHLcji8rv6UW5OU4TwfXXJOgtXAStuA");

        final StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(final Status status)
            {
                System.out.println(!StringUtils.isEmpty(status.getInReplyToScreenName())
                                   + " - "
                                   + status.isRetweet()
                                   + " @"
                                   + status.getUser().getScreenName()
                                   + " - "
                                   + status.getText());
            }

            @Override
            public void onDeletionNotice(final StatusDeletionNotice statusDeletionNotice)
            {
                System.out.println("Got a status deletion notice id:"
                                   + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(final int numberOfLimitedStatuses)
            {
                System.out.println("Got track limitation notice:"
                                   + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(final long userId,
                                   final long upToStatusId)
            {
                System.out.println("Got scrub_geo event userId:"
                                   + userId
                                   + " upToStatusId:"
                                   + upToStatusId);
            }

            @Override
            public void onStallWarning(final StallWarning warning)
            {
                System.out.println("Got stall warning:"
                                   + warning);
            }

            @Override
            public void onException(final Exception ex)
            {
                ex.printStackTrace();
            }
        };

        final FilterQuery filter = new FilterQuery(new long[]{2649991L });

        final TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
        final TwitterStream ts = tf.getInstance();
        ts.addListener(listener);
        ts.filter(filter);
        ts.retweet();

    }
}
