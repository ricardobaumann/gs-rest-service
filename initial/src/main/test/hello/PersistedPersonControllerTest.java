package hello;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class PersistedPersonControllerTest {


	private static final String URL = "http://localhost:8080/persisted";
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
	 * Test method for {@link hello.PersistedPersonController#get()}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetEmpty() {
		List<PersistedPerson> persons = restTemplate.getForObject(URL, List.class);
		Assert.assertNotNull(persons);
		Assert.assertEquals(persons.size(), 0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetSomething() {
		testPost();
		
		List<PersistedPerson> persons = restTemplate.getForObject(URL, List.class);
		Assert.assertNotNull(persons);
		Assert.assertEquals(persons.size(), 1);
	}

	/**
	 * Test method for {@link hello.PersistedPersonController#get(java.lang.Long)}.
	 */
	@Test(expected=HttpClientErrorException.class)
	public void testGetIDEmpty() {
		
		PersistedPerson person = restTemplate.getForObject(URL+"/1", PersistedPerson.class);
		Assert.assertNull(person);
		
	}
	
	@Test
	public void testGetIDSomething() {
		
		testPost();
		
		PersistedPerson person = restTemplate.getForObject(URL+"/1", PersistedPerson.class);
		Assert.assertNotNull(person);
		Assert.assertEquals(person.getName(), "posted");
		
	}

	/**
	 * Test method for {@link hello.PersistedPersonController#post(hello.PersistedPerson)}.
	 */
	@Test
	public void testPost() {
		PersistedPerson person = new PersistedPerson("posted");
		PersistedPerson createdPersistedPerson = restTemplate.postForObject(URL, person, PersistedPerson.class);
		Assert.assertNotNull(createdPersistedPerson);  
		Assert.assertEquals(person.getName(), createdPersistedPerson.getName());
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testGetByName() {
		List<PersistedPerson> persons = restTemplate.getForObject(URL+"?name=posted", List.class);
		Assert.assertNotNull(persons);
		Assert.assertEquals(persons.size(), 0);
		
		testPost();
		
		persons = restTemplate.getForObject(URL+"?name=posted", List.class);
		Assert.assertNotNull(persons);
		Assert.assertEquals(persons.size(), 1);
		
		persons = restTemplate.getForObject(URL+"?name=not_posted", List.class);
		Assert.assertNotNull(persons);
		Assert.assertEquals(persons.size(), 0);
	}
	
}
