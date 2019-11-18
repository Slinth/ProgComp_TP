package tp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/home2").setViewName("home2");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/properties").setViewName("properties");
        registry.addViewController("/properties/sort").setViewName("properties");
        registry.addViewController("/properties/filter").setViewName("properties");
        registry.addViewController("/properties/book").setViewName("booking");
        registry.addViewController("/profile").setViewName("profile");
    }

}