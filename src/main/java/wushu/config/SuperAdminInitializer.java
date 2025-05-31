package wushu.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wushu.entity.Role;
import wushu.entity.User;
import wushu.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SuperAdminInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {
        if (!userRepository.findByUsername("superadmin").isPresent()) {
            User superAdmin = new User();
            superAdmin.setUsername("superadmin");
            superAdmin.setEmail("superadmin@example.com");
            superAdmin.setPassword(passwordEncoder.encode("superpassword"));
            superAdmin.setRoles(List.of(Role.SUPERADMIN));
            userRepository.save(superAdmin);
            System.out.println("Створено суперкористувача superadmin");
        }

    }

}
