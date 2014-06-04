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
package net.naonedbus.context;

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


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class NaonedbusTwitterFactory
{
    /**
     * Return an instance (possibly shared or independent) of the object managed by this factory.
     * <p>
     * As with a {@link BeanFactory}, this allows support for both the Singleton and Prototype
     * design pattern.
     * <p>
     * If this FactoryBean is not fully initialized yet at the time of the call (for example
     * because it is involved in a circular reference), throw a corresponding
     * {@link FactoryBeanNotInitializedException}.
     * <p>
     * As of Spring 2.0, FactoryBeans are allowed to return <code>null</code> objects. The factory
     * will consider this as normal value to be used; it will not throw a
     * FactoryBeanNotInitializedException in this case anymore. FactoryBean implementations are
     * encouraged to throw FactoryBeanNotInitializedException themselves now, as appropriate.
     * @return an instance of the bean (can be <code>null</code>)
     */
    public Twitter getObject(final String consumerKey,
                             final String consumerSecret,
                             final String accessToken,
                             final String accessTokenSecret)
    {
        final ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret)
                .setIncludeMyRetweetEnabled(true);

        final TwitterFactory tf = new TwitterFactory(cb.build());
        final Twitter twitter = tf.getInstance();
        return twitter;
    }

}
