package hikingapp;

import hikingapp.creation.IAppInitializer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class HikingApp implements WebMvcConfigurer {

    @Autowired
    IAppInitializer appInitializer;

    public static void main(String[] args) {
        SpringApplication.run(HikingApp.class, args);
    }

    @PostConstruct
    public void init() {
        appInitializer.initialize();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("--- addResourceHandlers");
        registry.addResourceHandler("/webjars/**")//
                .addResourceLocations("/webjars/");
    }

    @Bean("messageSource")
    public MessageSource messageSource() {
        var r = new ReloadableResourceBundleMessageSource();
        r.setBasenames("classpath:hike", "classpath:member", "classpath:category");
        return r;
    }
}
