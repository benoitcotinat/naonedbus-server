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
package net.naonedbus.scrapper.common;

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


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;

import net.naonedbus.exception.NaonedbusException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.ScraperContext;

/**
 * Classe en charge d'exécuter un fichier webharvest.
 * @author Benoît
 * @version $Revision$ $Date$
 */
public class ScraperExecutor
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -5637405006946389505L;

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger("NAONEDBUS");

    /**
     * Clé de l'url dans les fichiers properties.
     */
    private String keyUrl;

    /**
     * Fichier de config web harvest.
     */
    private org.springframework.core.io.Resource scrapperFile;

    /**
     * Répertoire de travail de web harvest.
     */
    private String workingDirectory;

    /**
     * Méthode en charge d'exécuter un fichier web harvest avec des arguments.
     * @param args Liste des arguments.
     * @return Le contexte d'exécution, permettant de récupérer les variables.
     * @throws NaonedbusException En cas d'erreur sur la récupération des données.
     */
    public ScraperContext execute(final String... args)
        throws NaonedbusException
    {
        if (this.log.isDebugEnabled())
        {
            final StringBuilder sb = new StringBuilder();
            sb.append("Recuperation du contexte pour les arguments :\n");
            for (final String arg : args)
            {
                sb.append("\t");
                sb.append(arg.toString());
                sb.append("\n");
            }
            this.log.debug(sb.toString());
        }

        final String url = MessageFormat.format(this.keyUrl,
                                                args);

        ScraperConfiguration config;
        try
        {
            config = new ScraperConfiguration(this.scrapperFile.getFile().getAbsolutePath());
        }
        catch (final FileNotFoundException e)
        {
            throw new NaonedbusException("Le fichier "
                                                 + this.scrapperFile
                                                 + " n'a pas été trouvé.",
                                         e);
        }
        catch (final IOException e)
        {
            throw new NaonedbusException("Le fichier "
                                                 + this.scrapperFile
                                                 + " n'a pas été trouvé.",
                                         e);
        }

        final Scraper scraper = new Scraper(config,
                                            this.workingDirectory);

        scraper.addVariableToContext("url",
                                     url);

        if (this.log.isDebugEnabled())
        {
            final StringBuilder sb = new StringBuilder();
            sb.append("Execution du fichier webharvest ");
            sb.append(this.scrapperFile);
            sb.append(".\n");
            sb.append("Parametres d'execution : \n");
            sb.append("\turl=");
            sb.append(url);
            this.log.debug(sb.toString());
        }

        scraper.setDebug(true);
        scraper.execute();
        scraper.stopExecution();

        return scraper.getContext();
    }

    /**
     * Setter pour keyUrl.
     * @param keyUrl Le keyUrl à écrire.
     */
    public void setKeyUrl(final String keyUrl)
    {
        this.keyUrl = keyUrl;
    }

    /**
     * Setter pour scrapperFile.
     * @param scrapperFile Le scrapperFile à écrire.
     */
    public void setScrapperFile(final org.springframework.core.io.Resource scrapperFile)
    {
        this.scrapperFile = scrapperFile;
    }

    /**
     * Setter pour workingDirectory.
     * @param workingDirectory Le workingDirectory à écrire.
     */
    public void setWorkingDirectory(final String workingDirectory)
    {
        this.workingDirectory = workingDirectory;
    }
}
