package org.example.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.example.entity.Person;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PersonResource {

	static final Map<UUID, Person> persons = new HashMap<UUID, Person>();
	
	static {
		Person person1 = new Person();
		person1.setUuid(UUID.randomUUID());
		person1.setFirstName("John");
		person1.setMiddleName("J");
		person1.setLastName("Johnsson");

		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.set(1960, Calendar.JANUARY, 15, 0, 0, 0);
		person1.setBirthday(calendar.getTime());
		persons.put(person1.getUuid(), person1);

		Person person2 = new Person();
		person2.setUuid(UUID.randomUUID());
		person2.setFirstName("Sam");
		person2.setMiddleName("S");
		person2.setLastName("Samsson");
		calendar.set(1999, Calendar.FEBRUARY, 25, 0, 0, 0);
		person2.setBirthday(calendar.getTime());
		persons.put(person2.getUuid(), person2);
	}

	@GET
	@Path("/all")
	public Response getAllPersons() {
		List<Person> personList = new ArrayList<Person>();
		personList.addAll(persons.values());
		GenericEntity<List<Person>> list = new GenericEntity<List<Person>>(personList) {
		};
		return Response.ok(list).build();
	}

	@PUT
	@Path("{uuid}")
	public Response editPerson(@PathParam("uuid") UUID uuid, Person person) {
		if (!persons.containsKey(uuid)) {
			return Response.status(Status.NOT_FOUND).build();
		}
		persons.put(person.getUuid(), person);
		return Response.ok(person).build();
	}

}
