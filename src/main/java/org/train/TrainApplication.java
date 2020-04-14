package org.train;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.logging.Level;

@ApplicationPath("/")
public class TrainApplication extends Application {

    @Path("/api")
    public static class TrainResource {
        private static final Logger log = Logger.getLogger(TrainResource.class.getName());

        public static final String KEY = TrainResource.class.getName();

//        private static final String CONTAINER_JNDI_NAME = "java:jboss/infinispan/container/mycontainer";
//        private static final String CACHE_JNDI_NAME = "java:jboss/infinispan/cache/server/default";
//
//        @Resource(lookup = CONTAINER_JNDI_NAME)
//        private CacheContainer container;
//
//        @Resource(lookup = CACHE_JNDI_NAME)
//        private Cache<String, String> cache;

        @GET
        @Path("/set")
        @Produces(MediaType.TEXT_PLAIN)
        public String doSET(@Context HttpServletRequest req, @QueryParam("value") int value) {

            HttpSession session = req.getSession(true);

            if (session.isNew()) {
                log.info("New session created: {0} with session data: {1}", new Object[]{session.getId(), value});
                session.setAttribute(KEY, value);
            } else {
                log.info("Session is not new, setting data: {0}", new Object[]{value});
                session.setAttribute(KEY, value);
            }

            // Make sure we can also perform a vanilla jndi lookup of cache
            return String.valueOf(value) + " has been set for session: " + session.getId();
        }

        @GET
        @Path("/delete")
        @Produces(MediaType.TEXT_PLAIN)
        public String doDEL(@Context HttpServletRequest req) {

            HttpSession session = req.getSession(false);

            if (session != null) {
                session.invalidate();
            }
            return "session: " + session.getId() + " has been invalidated";
        }

        @GET
        @Path("/get")
        @Produces(MediaType.TEXT_PLAIN)
        public String doGET(@Context HttpServletRequest req) {

            HttpSession session = req.getSession(false);

            if (session != null) {
                return "session: " + session.getId() + " value: " + session.getAttribute(KEY);
            } else {

                return "session does not exists";
            }
        }
    }



}