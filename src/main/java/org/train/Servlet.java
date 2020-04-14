package org.train;

import java.io.IOException;
import org.jboss.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/servlet")
public class Servlet extends HttpServlet {

    protected static final Logger log = Logger.getLogger(Servlet.class.getName());
    public static final String KEY = TrainApplication.class.getName();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        if (req.getParameter("set") != null) {
            int value = Integer.valueOf(req.getParameter("value"));
            if (session.isNew()) {
                log.info("New session created: {0} with session data: {1}", new Object[]{session.getId(), value});
                log.info("New session created: {0} with session data: {1}", new Object[]{session.getId(), value});
                session.setAttribute(KEY, value);
            } else {
                log.info("Session is not new, setting data: {0}", new Object[]{value});
                session.setAttribute(KEY, value);
            }
        }

        // Invalidate?
        if (req.getParameter("delete") != null) {
            if (session != null) {
                session.invalidate();
            }
            log.info("session: " + session.getId() + " has been invalidated");
        }

        // Invalidate?
        if (req.getParameter("get") != null) {
            if (session != null) {
                log.info("session: " + session.getId() + " value: " + session.getAttribute(KEY));
            } else {

                log.info("session does not exists");
            }
        }
    }
}
