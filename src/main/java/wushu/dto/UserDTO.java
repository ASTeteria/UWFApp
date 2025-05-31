package wushu.dto;

import java.util.List;

public record UserDTO(
        Long id,
        String username,
        String email,
        String password,
        List<String> roles
) { }
