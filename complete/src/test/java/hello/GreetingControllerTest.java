/**
 * 
 */
package hello;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
public class GreetingControllerTest {

	private static final String URL = "http://localhost:8080/greeting";
	private RestTemplate restTemplate = new RestTemplate();
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

	private ConfigurableApplicationContext r;  

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		r = SpringApplication.run(Application.class);
		GreetingController.map.clear();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		r.close();
	}

	/**
	 * Test method for {@link hello.GreetingController#greeting(java.lang.String)}.
	 */
	@Test
	public void testGreetingDefault() {
		Greeting greeting = restTemplate.getForObject(URL, Greeting.class);
		Assert.assertNotNull(greeting);
		Assert.assertEquals(greeting.getContent(), "Hello, World!");  
	}

	
	@Test
	public void testGreetingByName() {
		String name = "alysson";
		Greeting greeting = restTemplate.getForObject(URL+"?name="+name, Greeting.class);
		Assert.assertNotNull(greeting);
		Assert.assertEquals(greeting.getContent(), "Hello, "+name+"!");
	}
	/**
	 * Test method for {@link hello.GreetingController#post(hello.Greeting)}.
	 */
	@Test
	public void testPost() {
		
		Greeting greeting = new Greeting();
		greeting.setContent("dudu");  
		
		Greeting createdGreeting = post(greeting);
		
		Assert.assertNotNull(createdGreeting);
		Assert.assertEquals(greeting.getContent(), createdGreeting.getContent());
		
		Assert.assertTrue(createdGreeting.getId()>0);
		
		Greeting gottenGreeting = get(createdGreeting.getId());
		
		Assert.assertNotNull(gottenGreeting);
		Assert.assertEquals(gottenGreeting.getContent(), createdGreeting.getContent());
		
		// Assert.assertEquals(gottenGreeting, createdGreeting);
	}

	private Greeting post(Greeting greeting) {
		return restTemplate.postForObject(URL, greeting, Greeting.class);
	}

	private Greeting get(Long id) {
		return restTemplate.getForObject(URL+"/"+id, Greeting.class);
	}
	
	/**
	 * Test method for {@link hello.GreetingController#get(java.lang.Long)}.
	 */
	@Test
	public void testGetID() {
		
		Long id = 1L;
		
		Greeting greeting = restTemplate.getForObject(URL+"/"+id, Greeting.class);
		Assert.assertNull(greeting);
		
		greeting = new Greeting(1L, "teste");
		greeting = post(greeting);
		
		greeting = restTemplate.getForObject(URL+"/"+greeting.getId(), Greeting.class);
		Assert.assertNotNull(greeting);
		Assert.assertEquals(greeting.getId(), id.longValue());
		
	}

	/**
	 * Test method for {@link hello.GreetingController#get(java.lang.Long, hello.Greeting)}.
	 */
	@Test
	public void testGetLongGreeting() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hello.GreetingController#delete(java.lang.Long)}.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

}
