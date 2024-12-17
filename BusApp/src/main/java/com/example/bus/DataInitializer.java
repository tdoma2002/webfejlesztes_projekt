package com.example.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Ellenőrizd, hogy van-e már admin felhasználó, ha nincs, akkor hozz létre egyet
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Jelszó kódolása
            admin.addRole("ADMIN");  // Role hozzáadása
            userRepository.save(admin);
            System.out.println("Admin felhasználó létrehozva: admin/admin123");
            String username = "admin";
			System.out.println("Felhasználó betöltése: " + username);

        }
    }
}
