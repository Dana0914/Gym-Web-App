package epam.com.gymapplication;


import epam.com.gymapplication.config.WebConfig;
import epam.com.gymapplication.controller.TraineeController;
import epam.com.gymapplication.controller.TrainerController;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.File;


public class App  {
    public static void main( String[] args ) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        AnnotationConfigWebApplicationContext cxt = new AnnotationConfigWebApplicationContext();
        cxt.register(WebConfig.class);

        cxt.register(TraineeController.class);
        cxt.register(TrainerController.class);


        tomcat.getConnector();
        tomcat.addWebapp("/", new File("src/main/webapp").getAbsolutePath());
        tomcat.start();
        tomcat.getServer().await();



    }
}
