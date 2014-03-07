package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.ws.rs.ext.ContextResolver;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

  @Context
  private Providers provs;

  @Context
  private ContextResolver<Dep> directDepResolver;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        ContextResolver<Dep> res = provs.getContextResolver(Dep.class, MediaType.TEXT_PLAIN_TYPE);
        Dep dep = res.getContext(Dep.class);
        System.out.println("Built dep: "+dep+", direct dep: "+directDepResolver);
        dep.somethingINeed();
        return "Got it!";
    }
}
