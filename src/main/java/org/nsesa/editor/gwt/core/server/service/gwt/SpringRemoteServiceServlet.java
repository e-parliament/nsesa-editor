package org.nsesa.editor.gwt.core.server.service.gwt;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring integration for a GWT {@link RemoteServiceServlet}.
 *
 * @author <a href="mailto:p.luppens@gmail.com">Philip Luppens</a>
 * @version $Id:$
 */

public class SpringRemoteServiceServlet extends RemoteServiceServlet implements ApplicationContextAware, ServletContextAware, Controller {

    protected ApplicationContext applicationContext;
    protected ServletContext servletContext;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // dispatch as POST
        doPost(request, response);
        // do not dispatch to any view
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setServletContext(ServletContext context) {
        this.servletContext = context;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public String getServletName() {
        return "gwt-spring";
    }
}
