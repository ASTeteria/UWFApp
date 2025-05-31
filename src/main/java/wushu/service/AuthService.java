package wushu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wushu.dto.AuthResponseDTO;
import wushu.dto.LoginRequestDTO;
import wushu.dto.RegisterRequestDTO;
import wushu.entity.Role;
import wushu.entity.User;
import wushu.exception.NotFoundException;
import wushu.repository.UserRepository;
import wushu.util.JwtUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new AuthResponseDTO(accessToken, refreshToken);
    }
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isSuperAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_SUPERADMIN"));

        if (!isSuperAdmin) {
            throw new RuntimeException("Тільки супер адміністратор може реєструвати нових користувачів.");
        }
        if (userRepository.findByUsername(registerRequest.username()).isPresent()) {
            throw new RuntimeException("Ім'я вже існує");
        }
        if (userRepository.findByEmail(registerRequest.email()).isPresent()) {
            throw new RuntimeException("Email вже існує");
        }

        User user = new User();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));

        List<Role> roles = registerRequest.roles().stream()
                .map(String::toUpperCase)
                .filter(roleName -> roleName.equals("ADMIN") || roleName.equals("MODERATOR") || roleName.equals("USER"))
                .map(Role::valueOf)
                .collect(Collectors.toList());

        if (roles.isEmpty()) {
            roles.add(Role.USER);
        }
        user.setRoles(roles);

        userRepository.save(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new AuthResponseDTO(accessToken, refreshToken);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));
    }
}