package com.example;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.mockito.Mockito;

public class MyResourceTest {

    @Provider
    public static class TestDepProvider implements ContextResolver<Dep>{

      public Dep getContext(Class c)
      {
        if (c.equals(Dep.class))
        {
          return Mockito.mock(Dep.class);
        }
        return null;
      }

    }

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        ResourceConfig rc = new ResourceConfig(TestDepProvider.class).packages("com.example");
        // start the server
        server = Main.startServer(rc);
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        String responseMsg = target.path("myresource").request().get(String.class);
        assertEquals("Got it!", responseMsg);
    }
}
