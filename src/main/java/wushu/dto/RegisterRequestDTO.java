package wushu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;


public record RegisterRequestDTO(
        @NotBlank(message = "Ім'я не може бути пустим ")
        String username,


        @NotBlank(message = "Email не може бути пустим")
        @Email(message = "Невалідний формат email")
        String email,

        @NotBlank(message = "Password не може бути пустим")
        String password,

        List<String> roles


) {

    }

