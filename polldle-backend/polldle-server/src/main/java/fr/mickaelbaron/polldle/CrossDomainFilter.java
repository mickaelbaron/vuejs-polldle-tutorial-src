/*********************************************************************************
* This file is part of CHEMANE Project.
* Copyright (C) 2016 LIAS - ISAE/ENSMA
*   Teleport 2 - 1 avenue Clement Ader
*   BP 40109 - 86961 Futuroscope Chasseneuil Cedex - FRANCE
* 
* CHEMANE is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* CHEMANE is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public License
* along with CHEMANE.  If not, see <http://www.gnu.org/licenses/>.
**********************************************************************************/
package fr.mickaelbaron.polldle;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * @author Walid KADRI
 * @author Mickael BARON
 */
@Provider
public class CrossDomainFilter implements ContainerResponseFilter {

	public CrossDomainFilter() {
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext cresp) throws IOException {
		cresp.getHeaders().add("Access-Control-Allow-Origin", "*");
		cresp.getHeaders().add("Access-Control-Allow-Credentials", "true");
		cresp.getHeaders().add("Access-Control-Allow-Methods", "GET, POST");
		cresp.getHeaders().add("Allow", "GET, POST");
		cresp.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, authorization");
	}
}
