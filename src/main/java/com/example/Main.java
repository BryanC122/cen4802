
package com.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class Main {

    public static void main(String[] args) throws LifecycleException {
        try (InputStream config =
                     Main.class.getClassLoader()
                             .getResourceAsStream("logging.properties")) {

            if (config != null) {
                LogManager.getLogManager().readConfiguration(config);
            } else {
                System.err.println("logging.properties was not found.");
            }

        } catch (IOException e) {
            System.err.println("Unable to load logging configuration: "
                    + e.getMessage());
        }

        Tomcat tomcat = new Tomcat();

        tomcat.setPort(8080);
        tomcat.getConnector();

        Context context = tomcat.addContext("", null);

        Tomcat.addServlet(context, "taskServlet", new TaskServlet());
        context.addServletMappingDecoded("/tasks", "taskServlet");

        tomcat.start();
        tomcat.getServer().await();
    }
}
