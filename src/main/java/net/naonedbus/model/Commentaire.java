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
package net.naonedbus.model;

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


import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.naonedbus.model.common.ICommentaire;
import net.naonedbus.utils.constants.NaonedbusConstants;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Défini un commentaire "tweet-like".
 * @author Benoît
 * @version 2.0
 */
@XmlRootElement
@Entity(name = "commentaire")
public class Commentaire
    extends BeanObject
    implements Serializable, ICommentaire
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 7355608890196144713L;

    /**
     * Ligne concernée.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_ligne")
    private Ligne ligne;

    /**
     * Sens concerné.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_sens")
    private Sens sens;

    /**
     * Arrêt concerné.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_arret")
    private Arret arret;

    /**
     * Contenu du commentaire.
     */
    @NotNull(message = "Le contenu du commentaire ne peut pas être nul !")
    @NotEmpty(message = "Le contenu du commentaire ne peut pas être vide !")
    @Column(name = "message", length = NaonedbusConstants.COMMENTAIRE_TAILLE)
    private String message;

    /**
     * Date de post du commentaire.
     */
    @NotNull(message = "La date de publication du commentaire ne peut pas être nul !")
    @Column(name = "datePublication")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar datePublication;

    /**
     * Source du commentaire.
     */
    @Column(name = "source")
    @NotNull(message = "La source d'un commentaire ne peut être nulle !")
    @NotEmpty(message = "La source d'un commentaire ne peut être vide !")
    private String source;

    /**
     * Id du tweet correspondant au commentaire.
     */
    @Column(name = "tweetId")
    private Long tweetId;

    /*
     * Méthodes permettant de remplir le contrat commun.
     */
    @SuppressWarnings("unused")
    @Transient
    private String codeLigne;
    @SuppressWarnings("unused")
    @Transient
    private String codeSens;
    @SuppressWarnings("unused")
    @Transient
    private String codeArret;
    @SuppressWarnings("unused")
    @Transient
    private long timestamp;

    public String getNomLigne()
    {
        String nom = null;
        if (null != this.ligne)
        {
            nom = this.ligne.getNom();
        }
        return nom;
    }
    @Override
    public String getCodeLigne()
    {
        String code = null;
        if (null != this.ligne)
        {
            code = this.ligne.getCode();
        }
        return code;
    }
    @Override
    public void setCodeLigne(final String codeLigne)
    {
        this.codeLigne = codeLigne;
    }
    @Override
    public String getCodeSens()
    {
        String code = null;
        if (null != this.sens)
        {
            code = this.sens.getCode();
        }
        return code;
    }
    @Override
    public void setCodeSens(final String codeSens)
    {
        this.codeSens = codeSens;
    }
    @Override
    public String getCodeArret()
    {
        String code = null;
        if (null != this.arret)
        {
            code = this.arret.getCode();
        }
        return code;
    }
    @Override
    public void setCodeArret(final String codeArret)
    {
        this.codeArret = codeArret;
    }
    @Override
    public Long getTimestamp()
    {
        return this.datePublication.getTimeInMillis();
    }
    @Override
    public void setTimestamp(final Long timestamp)
    {
        this.timestamp = timestamp;
    }

    /*
     * Getters et Setters.
     */

    /**
     * Getter pour ligne.
     * @return Le ligne
     */
    @XmlTransient
    public Ligne getLigne()
    {
        return this.ligne;
    }

    /**
     * Setter pour ligne.
     * @param ligne Le ligne à écrire.
     */
    public void setLigne(final Ligne ligne)
    {
        this.ligne = ligne;
    }

    /**
     * Getter pour sens.
     * @return Le sens
     */
    @XmlTransient
    public Sens getSens()
    {
        return this.sens;
    }

    /**
     * Setter pour sens.
     * @param sens Le sens à écrire.
     */
    public void setSens(final Sens sens)
    {
        this.sens = sens;
    }

    /**
     * Getter pour arret.
     * @return Le arret
     */
    @XmlTransient
    public Arret getArret()
    {
        return this.arret;
    }

    /**
     * Setter pour arret.
     * @param arret Le arret à écrire.
     */
    public void setArret(final Arret arret)
    {
        this.arret = arret;
    }

    /**
     * Getter pour message.
     * @return Le message
     */
    @Override
    public String getMessage()
    {
        return this.message;
    }

    /**
     * Setter pour message.
     * @param message Le message à écrire.
     */
    @Override
    public void setMessage(final String message)
    {
        this.message = message;
    }

    /**
     * Getter pour datePublication.
     * @return Le datePublication
     */
    @XmlTransient
    public Calendar getDatePublication()
    {
        return this.datePublication;
    }

    /**
     * Setter pour datePublication.
     * @param datePublication Le datePublication à écrire.
     */
    public void setDatePublication(final Calendar datePublication)
    {
        this.datePublication = datePublication;
    }
    /**
     * Getter pour source.
     * @return Le source
     */
    @Override
    public String getSource()
    {
        return this.source;
    }
    /**
     * Setter pour source.
     * @param source Le source à écrire.
     */
    @Override
    public void setSource(final String source)
    {
        this.source = source;
    }
    /**
     * Getter pour tweetId.
     * @return Le tweetId
     */
    public Long getTweetId()
    {
        return this.tweetId;
    }
    /**
     * Setter pour tweetId.
     * @param tweetId Le tweetId à écrire.
     */
    public void setTweetId(final Long tweetId)
    {
        this.tweetId = tweetId;
    }

}
