package epam.com.gymapplication;


import epam.com.gymapplication.config.AppConfig;
import epam.com.gymapplication.postprocessor.StorageInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {
    public static void main( String[] args ) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        StorageInitializer storageInitializer = context.getBean(StorageInitializer.class);
        storageInitializer.initializeStorage();


    }
}
