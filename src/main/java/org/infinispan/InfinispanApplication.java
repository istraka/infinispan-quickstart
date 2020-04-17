package org.infinispan;

import org.jboss.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Properties;

@ApplicationPath("/")
public class InfinispanApplication extends Application {

    @Path("/")
    public static class WebResource {
        private static final Logger log = Logger.getLogger(WebResource.class.getName());

        public static final String KEY = WebResource.class.getName();

        @PUT
        public void doPUT(@Context HttpServletRequest req, @QueryParam("value") String value) {
            HttpSession session = req.getSession(true);

            if (session.isNew()) {
                log.info("New session created: {0} with session data: {1}", new Object[]{session.getId(), value});
                session.setAttribute(KEY, value);
            } else {
                log.info("Session is not new, setting data: {0}", new Object[]{value});
                session.setAttribute(KEY, value);
            }
        }

        @DELETE
        public void doDEL(@Context HttpServletRequest req) {
            HttpSession session = req.getSession(false);

            if (session != null) {
                session.invalidate();
            }
            log.info("session: " + session.getId() + " has been invalidated");
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Properties doGET(@Context HttpServletRequest req) {
            HttpSession session = req.getSession(false);

            if (session != null) {
                Properties properties = new Properties();
                properties.setProperty("value", (String) session.getAttribute(KEY));
                return properties;
            } else {
                log.info("session does not exists");
                return new Properties();
            }
        }
    }



}