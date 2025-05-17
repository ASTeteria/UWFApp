package wushu.dto;

import java.util.List;

public record UserDTO(
        Long id,
        String username,
        String password,
        List<String> roles
) { }
