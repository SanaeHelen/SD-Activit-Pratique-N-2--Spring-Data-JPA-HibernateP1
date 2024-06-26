package com.example.jpa;

import com.example.jpa.entities.Role;
import com.example.jpa.entities.User;
import com.example.jpa.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class JpaApplication {

    public static void main(String[] args) {

        SpringApplication.run(JpaApplication.class, args);

    }
    @Bean
    CommandLineRunner start(UserService userService){
        return args -> {
            User u = new User();
            u.setUsername("Sanae");
            u.setPassword("sana1234");
            userService.addNewUser(u);

            User u2 = new User();
            u2.setUsername("admin");
            u2.setPassword("admin1234");
            userService.addNewUser(u2);


            Stream.of("STUDENT","USER","ADMIN").forEach(r->{
                Role role1 = new Role();
                role1.setRoleName(r);
                userService.addNewRole(role1);
            });

            userService.addRoleToUser("Sanae","STUDENT");
            userService.addRoleToUser("Sanae","USER");
            userService.addRoleToUser("admin","USER");
            userService.addRoleToUser("admin","ADMIN");

            try {
                User user = userService.authenticate("Sanae","sana1234");
                System.out.println(user.getUserId());
                System.out.println(user.getUsername());
                System.out.println("Roles =>");
                user.getRoles().forEach(r -> {
                    System.out.println("Role => "+r);
                });

            }
            catch (Exception exception){
                exception.printStackTrace();
            }


        };


    }

}
