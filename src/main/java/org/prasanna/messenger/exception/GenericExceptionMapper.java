package org.prasanna.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.prasanna.messenger.model.ErrorMessage;
//@Provider registers ExceptionMapper to JAX-RS so that whenever exception occurs JAX-RS searches for registered exception mapper type
//@Provider // annotation removed because generic exception handling <Throwable> is not good programming practice
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),500,"http://www.javadoc.com");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.build();
	}

}
