package wushu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wushu.dto.UserDTO;
import wushu.entity.CompetitionApplication;
import wushu.entity.User;
import wushu.exception.NotFoundException;
import wushu.mapper.UserMapper;
import wushu.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElse(null);
    }

    //    public UserDTO updateUserByUsername(String username, UserDTO userDTO) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));
//
//        // Оновлюйте лише ті поля, які присутні в DTO
//        if (userDTO.username() != null) {
//            user.setUsername(userDTO.username());
//        }
//        if (userDTO.password() != null) {
//            user.setPassword(userDTO.password());
//        }
//        // Оновлюйте інші поля за потреби
//
//        User updatedUser = userRepository.save(user);
//        return userMapper.toDto(updatedUser);
//    }
    @Transactional
    public UserDTO updateUserByUsername(String username, UserDTO userDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));
        user.setUsername(userDTO.username());
        if (userDTO.password() != null && !userDTO.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.password())); // Шифруємо пароль
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        userRepository.deleteById(id);
    }


}
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final UserMapper userMapper;
//
//    @Transactional(readOnly = true)
//    public List<UserDTO> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        return users.stream()
//                .map(userMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional(readOnly = true)
//    public UserDTO findByUsername(String username) {
//        User user = userRepository.findByUsername(username);
//        return user != null ? userMapper.toDTO(user) : null;
//    }
//
//    public UserDTO updateUserByUsername(String username, UserDTO userDTO) {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new NotFoundException("User not found with username: " + username);
//        }
//
//        // Оновлюйте лише ті поля, які присутні в DTO
//        if (userDTO.username() != null) {
//            user.setUsername(userDTO.username());
//        }
//        if (userDTO.password() != null) {
//            user.setPassword(userDTO.password());
//        }
//        // Оновлюйте інші поля за потреби
//
//        User updatedUser = userRepository.save(user);
//        return userMapper.toDTO(updatedUser);
//    }
//}