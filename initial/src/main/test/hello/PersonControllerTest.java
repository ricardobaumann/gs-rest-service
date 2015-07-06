/**
 * 
 */
package hello;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestTemplate;

/**
 * @author ricardo
 *
 */
public class PersonControllerTest {

	
	private static final String URL = "http://localhost:8080/person";
	private RestTemplate restTemplate = new RestTemplate();
	private ConfigurableApplicationContext r;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		PersonController.PERSONS.clear();
		r = SpringApplication.run(Application.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		r.close();
	}

	/**
	 * Test method for {@link hello.PersonController#get()}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetEmpty() {
		List<Person> persons = restTemplate.getForObject(URL, List.class);
		Assert.assertNotNull(persons);
		Assert.assertEquals(persons.size(), 0);
	}
	
	@Test
	public void testGetSomething() {
		PersonController.PERSONS.put(1L, new Person("nome"));
		
		List<Person> persons = restTemplate.getForObject(URL, List.class);
		Assert.assertNotNull(persons);
		Assert.assertEquals(persons.size(), 1);
	}

	/**
	 * Test method for {@link hello.PersonController#get(java.lang.Long)}.
	 */
	@Test
	public void testGetIDEmpty() {
		
		Person person = restTemplate.getForObject(URL+"/1", Person.class);
		Assert.assertNull(person);
		
	}
	
	@Test
	public void testGetIDSomething() {
		
		PersonController.PERSONS.put(1L, new Person("nome"));
		
		Person person = restTemplate.getForObject(URL+"/1", Person.class);
		Assert.assertNotNull(person);
		Assert.assertEquals(person.getName(), "nome");
		
	}

	/**
	 * Test method for {@link hello.PersonController#post(hello.Person)}.
	 */
	@Test
	public void testPost() {
		Person person = new Person("posted");
		Person createdPerson = restTemplate.postForObject(URL, person, Person.class);
		Assert.assertNotNull(createdPerson);  
		Assert.assertEquals(person.getName(), createdPerson.getName());
	}

	

}
