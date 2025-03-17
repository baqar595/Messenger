package org.example;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException, ServletException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080); // Set server port

        // Set up temporary deployment directory
        String webAppDir = new File("src/main/webapp/").getAbsolutePath();
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", webAppDir);

        System.out.println("Starting server at http://localhost:8080/");

        // Add compiled classes directory as resources
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        // Add servlets manually (if not using annotations)
        tomcat.addServlet("", "MessageServlet", new com.example.messaging.servlet.MessageServlet());
        ctx.addServletMappingDecoded("/message", "MessageServlet");

        tomcat.addServlet("", "UserServlet", new UserServlet());
        ctx.addServletMappingDecoded("/user", "UserServlet");

        tomcat.start();
        tomcat.getServer().await();
    }


    }

