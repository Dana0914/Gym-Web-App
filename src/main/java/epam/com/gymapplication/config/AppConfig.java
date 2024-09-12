package epam.com.gymapplication.config;


import epam.com.gymapplication.dao.TraineeDAO;
import epam.com.gymapplication.dao.TrainerDAO;
import epam.com.gymapplication.dao.TrainingTypeDAO;
import epam.com.gymapplication.dao.UserDAO;
import epam.com.gymapplication.dao.impl.TraineeDAOImpl;
import epam.com.gymapplication.dao.impl.TrainerDAOImpl;
import epam.com.gymapplication.dao.impl.TrainingTypeDAOImpl;
import epam.com.gymapplication.dao.impl.UserDAOImpl;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import epam.com.gymapplication.service.*;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "epam.com.gymapplication")
public class AppConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("epam.com.gymapplication.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }


    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/gym");
        dataSource.setUsername( "postgres" );
        dataSource.setPassword( "postgres" );
        return dataSource;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.use_sql_comments", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");


        return properties;
    }




    @Bean
    public PasswordGenerator passwordGenerator() {
        return new PasswordGenerator();
    }

    @Bean
    public UserProfileService userProfileUtils() {
        return new UserProfileService();
    }


    @Bean
    public TraineeDAO traineeDAO() {
        return new TraineeDAOImpl();
    }

    @Bean
    public TrainerDAO trainerDAO() {
        return new TrainerDAOImpl();
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl();
    }

    @Bean
    public TrainingTypeDAO trainingTypeDAO() {
        return new TrainingTypeDAOImpl();
    }

    @Bean
    public TraineeService traineeService() {
        return new TraineeService();
    }

    @Bean
    public TrainerService trainerService() {
        return new TrainerService();
    }

    @Bean
    public TrainingService trainingService() {
        return new TrainingService();
    }

    @Bean
    public TrainingTypeService trainingTypeService() {
        return new TrainingTypeService();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }


}
