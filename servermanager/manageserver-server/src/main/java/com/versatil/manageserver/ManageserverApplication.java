package com.versatil.manageserver;

import com.versatil.manageserver.model.Server;
import com.versatil.manageserver.model.Status;
import com.versatil.manageserver.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ManageserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageserverApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository serverRepository) {
        return args -> {
            serverRepository.save(new Server(null, "192.168.1.160", "Ubuntu Linux", "16 GB", "Personal PC", "http://localhost:8005/server/image/server1.png", Status.SERVER_UP));
            serverRepository.save(new Server(null, "192.168.1.58", "Windows 10", "8 GB", "Personal PC", "http://localhost:8005/server/image/server2.png", Status.SERVER_DOWN));
            serverRepository.save(new Server(null, "192.168.1.21", "MacOS Ventura", "32 GB", "Personal PC", "http://localhost:8005/server/image/server3.png", Status.SERVER_UP));
            serverRepository.save(new Server(null, "192.168.1.14", "Red Hat Entreprise Linux", "64 GB", "Mail Server", "http://localhost:8005/server/image/server4.png", Status.SERVER_DOWN));
            serverRepository.save(new Server(null, "192.168.1.23", "Oracle Server", "16 GB", "Server", "http://localhost:8005/server/image/server1.png", Status.SERVER_UP));
            serverRepository.save(new Server(null, "193.168.1.532", "MacOS Sonoma", "64 GB", "Personal PC", "http://localhost:8005/server/image/server2.png", Status.SERVER_UP));
        };
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}

