import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import repository.*;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class RepositoryConfig {

    @Bean
    @Primary
    public Properties jdbsProps(){
        Properties repoProps=new Properties();
        try {
            repoProps.load(getClass().getResourceAsStream("/bd.properties"));
            System.out.println("Properties set. ");
            repoProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);

        }
        return  repoProps;
    }

    @Bean(name="ParticipantRepo")
    public ParticipantRepository createRepositoryParticipant(Properties jdbcProps){
        return new ParticipantRepository(jdbcProps);
    }

    @Bean(name="InscriereRepo")
    public InscrieriRepository createRepositoryInscrieri(Properties jdbcProps){
        return new InscrieriRepository(jdbcProps);
    }

    @Bean(name="UserRepo")
    public UserRepository createRepositoryUser(Properties jdbcProps){
        return new UserRepository(jdbcProps);
    }

    @Bean(name="ProbatRepo")
    public ProbaRepository createRepositoryProba(Properties jdbcProps){
        return new ProbaRepository(jdbcProps);
    }

}
