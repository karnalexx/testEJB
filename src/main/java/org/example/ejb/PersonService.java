package org.example.ejb;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import org.example.entity.Person;

@Singleton
public class PersonService {
	
	private static final Map<UUID, Person> persons = new HashMap<UUID, Person>();
	
	@PostConstruct
	public void preparePersons() {
		Person person1 = new Person();
		person1.setUuid(UUID.randomUUID());
		person1.setFirstName("John");
		person1.setMiddleName("J");
		person1.setLastName("Johnsson");
		LocalDate birthday1 = LocalDate.of(1960, Month.JANUARY, 15);
		person1.setBirthday(birthday1);
		persons.put(person1.getUuid(), person1);

		Person person2 = new Person();
		person2.setUuid(UUID.randomUUID());
		person2.setFirstName("Sam");
		person2.setMiddleName("S");
		person2.setLastName("Samsson");
		LocalDate birthday2 = LocalDate.of(1999, Month.FEBRUARY, 25);
		person2.setBirthday(birthday2);
		persons.put(person2.getUuid(), person2);		
	}

	public List<Person> getAllPersons() {
		return persons.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());		
	}
	
	public Person getPersonByUuid(UUID uuid) {
		return persons.get(uuid);
	}
	
	public Person savePerson(Person person) {
		if (!persons.containsKey(person.getUuid())) {
			person.setUuid(UUID.randomUUID());
		}
		persons.put(person.getUuid(), person);
		return person;
	}

	public Map<UUID, Person> getPersons() {
		return persons;
	}	
}
