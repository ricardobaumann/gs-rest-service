package hello;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/persisted")
public class PersistedPersonController {

	@Inject
	private PersistedPersonRepository persistedPersonRepository; 
	
	@RequestMapping(method=RequestMethod.GET)
	public Iterable<PersistedPerson> get(@RequestParam(value="name",required=false) String name) {
		if (name!=null) {
			return persistedPersonRepository.findByName(name);
		}
		return persistedPersonRepository.findAll();  
	}
	
	@RequestMapping(method=RequestMethod.GET,value="{id}")
	public PersistedPerson get(@PathVariable("id") Long id) {
		return persistedPersonRepository.findOne(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public PersistedPerson post(@RequestBody PersistedPerson person) {
		return persistedPersonRepository.save(person);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="{id}")
	public PersistedPerson put(@RequestBody PersistedPerson person, @PathVariable("id") Long id) {
		person.setId(id);
		return persistedPersonRepository.save(person);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="{id}")
	public void delete(@PathVariable("id") Long id) {
		persistedPersonRepository.delete(id);  
	}
	
}
