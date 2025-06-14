export interface UserDTO {
    id: number | null;
    username: string;
    email: string;
    password?: string | null;
    roles?: string[];
}

export interface PaginatedUsersResponse {
    content: UserDTO[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
}