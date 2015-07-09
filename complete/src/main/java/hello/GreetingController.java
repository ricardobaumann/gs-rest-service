package hello;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static Map<Long, Greeting> map = new HashMap<Long, Greeting>();
    

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping(value="/greeting",method=RequestMethod.POST)
    public Greeting post(@RequestBody Greeting greeting) {
    	greeting.setId(counter.incrementAndGet());  
    	map.put(greeting.getId(), greeting);
    	return greeting;
    }
    
    @RequestMapping(value="/greeting/{id}",method=RequestMethod.GET)
    public Greeting get(@PathVariable("id") Long id ) {
    	return map.get(id);
    }
    
    @RequestMapping(value="/greeting/{id}",method=RequestMethod.PUT)
    public Greeting get(@PathVariable("id") Long id, @RequestBody Greeting greeting) {
    	greeting.setId(id);
    	map.put(greeting.getId(), greeting);
    	return greeting;
    }
    
    @RequestMapping(value="/greeting/{id}",method=RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
    	map.remove(id);
    }
    
    
}
