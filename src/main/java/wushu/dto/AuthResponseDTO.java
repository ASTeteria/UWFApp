package wushu.dto;

public record AuthResponseDTO(
        String accessToken,
        String refreshToken
) {
}
