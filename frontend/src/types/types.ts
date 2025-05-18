export interface LoginRequestDTO {
    username: string;
    password: string;
}

export interface RegisterRequestDTO {
    username: string;
    password: string;
    roles: string[];
}

export interface UserDTO {
    id: number;
    username: string;
    password?: string;
    roles: string[];
}