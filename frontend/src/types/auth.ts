export interface LoginRequestDTO {
    username: string;
    password: string;
}

export interface RegisterRequestDTO {
    username: string;
    email: string;
    password: string;
    roles: string[];
}

export interface AuthResponseDTO {
    accessToken: string;
    refreshToken: string;
}