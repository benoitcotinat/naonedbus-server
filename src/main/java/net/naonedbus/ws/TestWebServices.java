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
package net.naonedbus.ws;

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
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.naonedbus.model.Arret;
import net.naonedbus.model.common.IArret;

/**
 * Description de la classe.
 * @author Benoît
 * @version $Revision$ $Date$
 */
@Path("naoWSTest")
public class TestWebServices
    implements Serializable
{

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 1128720829264317708L;

    @GET
    @Path("list")
    @Produces(
    {MediaType.APPLICATION_JSON })
    public List<String> getTestList()
    {
        final List<String> ret = new ArrayList<String>();

        ret.add("AAA aaa");
        ret.add("BBB bbb");
        ret.add("CCC ccc");
        ret.add("DDD ddd");
        ret.add("EEE eee");

        return ret;
    }

    @GET
    @Path("array")
    @Produces(
    {MediaType.APPLICATION_JSON })
    public String[] getTestArray()
    {
        final String[] ret = new String[5];

        ret[0] = "AAA aaa";
        ret[1] = "BBB bbb";
        ret[2] = "CCC ccc";
        ret[3] = "DDD ddd";
        ret[4] = "EEE eee";

        return ret;
    }

    @GET
    @Path("arret")
    @Produces(
    {MediaType.APPLICATION_JSON })
    public IArret getTestArret()
    {
        final IArret ret = new Arret();

        ret.setId(1);
        ret.setCode("CODE");
        ret.setNom("MyArret");

        return ret;
    }

    @GET
    @Path("arrets")
    @Produces(
    {MediaType.APPLICATION_JSON })
    public List<Arret> getTestArrets()
    {
        final List<Arret> ret = new ArrayList<Arret>();

        final Arret arret1 = new Arret();
        arret1.setId(1);
        arret1.setCode("CODE1");
        arret1.setNom("MyArret1");
        ret.add(arret1);

        final Arret arret2 = new Arret();
        arret2.setId(2);
        arret2.setCode("CODE2");
        arret2.setNom("MyArret2");
        ret.add(arret2);

        return ret;
    }
}
