package tasks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by grigo on 3/9/17.
 */
@Configuration
@ComponentScan(basePackages="tasks")
public class SortingTaskAutowireConfig {
    @Bean
    @Primary
    public Properties jdbcProperties(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(getClass().getResourceAsStream("/bd.config"));
            System.out.println("Properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);

        }
        return serverProps;
    }
}
