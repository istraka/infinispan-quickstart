package org.train;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@ApplicationPath("/")
public class TrainApplication extends Application {

    @Path("/")
    public static class TrainResource {
        private static final Logger log = Logger.getLogger(TrainResource.class.getName());


        private static final String CONTAINER_JNDI_NAME = "java:jboss/infinispan/container/server";
        private static final String CACHE_JNDI_NAME = "java:jboss/infinispan/cache/server/default";

        @Resource(lookup = CONTAINER_JNDI_NAME)
        private CacheContainer container;

        @Resource(lookup = CACHE_JNDI_NAME)
        private Cache<String, String> cache;

        @GET
        @Path("/info")
        @Produces(MediaType.TEXT_PLAIN)
        public String doGET() {
            cache.put("a","b");
            cache.put("c","d");
            cache.put("a","d");
            return cache.get("a");
        }
    }


}