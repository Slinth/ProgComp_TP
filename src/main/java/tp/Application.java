package tp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tp.model.Authority;
import tp.model.AuthorityType;
import tp.model.Property;
import tp.model.User;
import tp.repository.PropertyRepository;
import tp.repository.UserRepository;
import tp.service.PropertyService;
import tp.service.UserDetailsServiceImpl;

import java.util.Date;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(PropertyService service, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return (args) -> {
            /*//save some users
            User user = new User("slinth", "dorian@email", bCryptPasswordEncoder.encode("orange12"), new Authority(AuthorityType.ROLE_LOUEUR));
            userRepository.save(user);
            user = new User("antbns", "antoine@email", bCryptPasswordEncoder.encode("blblbl"), new Authority(AuthorityType.ROLE_LOCATAIRE));
            userRepository.save(user);

            //save a few properties
            service.saveProperty(new Property("maison", 650.50, "15 rue Louis Pasteur", 4, 0, 1));
            service.saveProperty(new Property("appartement", 320.0, "fdk,fdkg,nfdkng", 2, 0, 1));
            service.saveProperty(new Property("maison", 1040.66, "blblblblblblbl", 6, 0, 1));
            service.saveProperty(new Property("appartement", 300.7, "skurtskurtskurt", 2, 0, 2));
            service.saveProperty(new Property("maison", 860.20, "djfhjfhjdf", 5, 0, 2));
            service.saveProperty(new Property("appartement", 430.5, "djfhjfhjdf", 3, 0, 2));*/

            // fetch all properties
            log.info("Properties found with findAll():");
            log.info("-------------------------------");
            for (Property property : service.findAllProperties().getPropertyList()) {
                log.info(property.toString());
            }
            log.info("");

            // fetch an individual property by address
            Property property = service.findPropertyByAddress("15 rue Louis Pasteur");
            log.info("Property found with findById(\"15 rue Louis Pasteur\"):");
            log.info("--------------------------------");
            log.info(property.toString());
            log.info("");

            // fetch all properties sorted by price
            log.info("Properties found with findAllPropertiesSortedByPriceASC():");
            log.info("--------------------------------------------");
            service.findAllPropertiesSortedByPriceASC().getPropertyList().forEach(bauer -> log.info(bauer.toString()));
            log.info("");

            // fetch all properties sorted by price
            log.info("Properties found with findAllPropertiesSortedByPriceDESC():");
            log.info("--------------------------------------------");
            service.findAllPropertiesSortedByPriceDESC().getPropertyList().forEach(bauer -> log.info(bauer.toString()));
            log.info("");

            // fetch all properties sorted by capacity
            log.info("Properties found with findAllPropertiesSortedByCapacityASC():");
            log.info("--------------------------------------------");
            service.findAllPropertiesSortedByCapacityASC().getPropertyList().forEach(bauer -> log.info(bauer.toString()));
            log.info("");

            // fetch all properties sorted by capacity
            log.info("Properties found with findAllPropertiesSortedByCapacityDESC():");
            log.info("--------------------------------------------");
            service.findAllPropertiesSortedByCapacityDESC().getPropertyList().forEach(bauer -> log.info(bauer.toString()));
            log.info("");

            // fetch properties by max price
            log.info("Properties found with findPropertiesByMaxPrice(700):");
            log.info("--------------------------------------------");
            service.findPropertiesByMaxPrice(700.0).getPropertyList().forEach(bauer -> log.info(bauer.toString()));
            log.info("");
        };
    }

}