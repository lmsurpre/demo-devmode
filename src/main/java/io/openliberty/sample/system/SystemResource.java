// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.sample.system;

import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@RequestScoped
@Path("/properties")
public class SystemResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Timed(name = "getPropertiesTime", description = "Time needed to get the JVM system properties")
	@Counted(absolute = true, description = "Number of times the JVM system properties are requested")
	public Response getProperties() throws NamingException, SQLException {
		InitialContext ctxt = new InitialContext();
		DataSource datasource = (DataSource) ctxt.lookup("jdbc/myDB");
		try (Connection c = datasource.getConnection()) {
			c.isValid(2);
		}

		return Response.ok(System.getProperties()).build();
	}

}
