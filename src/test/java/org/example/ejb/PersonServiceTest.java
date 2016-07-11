package org.example.ejb;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.example.entity.Person;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UUID.class)
public class PersonServiceTest {
	
	private static PersonService personService;
	
	@BeforeClass
	public static void before() {
		personService = new PersonService();
		personService.preparePersons();
		mockStatic(UUID.class);			
	}

	@Test
	public void getAllPersons() {				
		List<Person> result = personService.getAllPersons();	
		
		assertFalse(result.isEmpty());
	}
	
	@Test
	public void getPersonByUuid() throws Exception {
		Person mockPerson = mock(Person.class);
		UUID uuid = UUID.randomUUID();
		when(mockPerson.getUuid()).thenReturn(uuid);
		personService.getPersons().put(uuid, mockPerson);
		
		Person result = personService.getPersonByUuid(mockPerson.getUuid());
		
		assertThat(result, is(mockPerson));
	}
	
	@Test
	public void savePerson_whenPersonNotFound_shouldSetNewUuidToPerson() throws Exception {
		Person person = new Person();
		
		Person result = personService.savePerson(person);
		
		assertNotNull(result.getUuid());
	}
	
	@Test
	public void savePerson_whenPersonFound_doNotSetNewUuidToPerson() throws Exception {
		Map<UUID, Person> persons = personService.getPersons();		
		Person person = persons.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList()).get(0);		
		UUID uuid = person.getUuid();
		person.setFirstName("New FN");
		
		Person result = personService.savePerson(person);
		
		assertNotNull(result.getUuid());
		assertThat(result.getUuid(), is(uuid));
	}
}
