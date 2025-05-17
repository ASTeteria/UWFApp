package wushu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;


public record LoginRequestDTO(
        @NotBlank(message = "Ім'я не може бути пустим ")
        String username,

        @NotBlank(message = "Password не може бути пустим")
        String password) {


}
