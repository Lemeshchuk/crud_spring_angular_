package com.nix.lemeshuk.config;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebApplicationInitializerImpl implements WebApplicationInitializer {

    private static final String CXF_SERVLET_NAME = "cxfServlet";
    private static final String CXF_SERVLET_MAPPING = "/api/*";

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        
        appContext.register(RootConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(appContext));

        ServletRegistration.Dynamic cxfServlet = servletContext.addServlet(CXF_SERVLET_NAME, new CXFServlet());

        cxfServlet.addMapping(CXF_SERVLET_MAPPING);
    }
}
