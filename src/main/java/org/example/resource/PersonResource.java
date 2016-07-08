package org.example.resource;

import java.util.UUID;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.example.ejb.PersonService;
import org.example.entity.Person;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PersonResource {
	
	@EJB
	private PersonService personService;
	
	@GET
	@Path("/all")
	public Response getAllPersons() {
		return Response.ok(personService.getAllPersons()).build();
	}
	
	@GET
	public Response getPerson(@QueryParam("uuid") String uuid) {
		Person person = personService.getPersonByUuid(UUID.fromString(uuid));
		if (person==null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(person).build();
	}

	@PUT
	@Path("{uuid}")
	public Response editPerson(@PathParam("uuid") UUID uuid, Person person) { 
		if (personService.getPersonByUuid(uuid) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}		
		return Response.ok(personService.savePerson(person)).build();
	}
	
	@POST
	public Response savePerson(Person person) {
		return Response.ok(personService.savePerson(person)).build();
	}

}
