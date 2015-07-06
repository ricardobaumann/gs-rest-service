package hello;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/person")
public class PersonController {

	static final Map<Long, Person> PERSONS = new HashMap<Long, Person>();
	private static final AtomicLong ATOMIC_LONG = new AtomicLong(); 
	
	@RequestMapping(method=RequestMethod.GET)
	public Collection<Person> get() {
		return PERSONS.values();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{id}")
	public Person get(@PathVariable("id") Long id) {
		return PERSONS.get(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Person post(@RequestBody Person person) {
		person.setId(ATOMIC_LONG.incrementAndGet());
		PERSONS.put(person.getId(), person);
		return person;
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{id}")
	public Person put(@RequestBody Person person, @PathVariable("id") Long id) {
		person.setId(id);
		PERSONS.put(person.getId(), person);
		return person;
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="{id}")
	public void delete(@PathVariable("id") Long id) {
		PERSONS.remove(id);
	}
	
}
