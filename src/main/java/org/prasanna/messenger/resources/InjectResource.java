package org.prasanna.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectResource")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class InjectResource {
	
	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam, 
			@HeaderParam("authSessionID") String headerValue,
			@CookieParam("Idea-d9922461") String cookie){
		return "Matrix param : " + matrixParam + " Header Value : " + headerValue + "Cookie Value : " + cookie;
	}
	
	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers){
		String path = uriInfo.getAbsolutePath().toString();
		String cookies = headers.getCookies().toString();
		return "Path : " + path + " Cookies : " + cookies;
	}
}
