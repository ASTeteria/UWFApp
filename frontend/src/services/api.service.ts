import { cookies } from 'next/headers';
import { AuthResponseDTO, LoginRequestDTO, RegisterRequestDTO } from '../types/auth';
import { UserDTO, PaginatedUsersResponse } from '../types/user';
import { ErrorDTO, ValidationErrorDTO } from '../types/error';

const API_BASE_URL = 'http://localhost:8080/api';

const handleResponse = async <T>(response: Response): Promise<T> => {
    if (response.status === 204) {
        console.log('API response: 204 No Content');
        return undefined as T;
    }

    // Читаем тело ответа один раз
    const responseText = await response.text();
    let responseData;
    try {
        responseData = responseText ? JSON.parse(responseText) : {};
    } catch {
        responseData = {};
    }

    if (!response.ok) {
        console.error('API error:', {
            status: response.status,
            statusText: response.statusText,
            responseData,
            responseText,
        });
        if (response.status === 400 && responseData.message) {
            throw new Error((responseData as ErrorDTO).message);
        }
        if (response.status === 400 && !responseData.message) {
            throw responseData as ValidationErrorDTO;
        }
        if (response.status === 401) {
            throw new Error('Unauthorized');
        }
        throw new Error(
            `Network response was not ok: ${response.status} ${response.statusText} - ${
                responseData.message || JSON.stringify(responseData)
            }`,
        );
    }

    console.log('API response:', JSON.stringify(responseData, null, 2));
    return responseData as T;
};

export const login = async (data: LoginRequestDTO): Promise<AuthResponseDTO & { roles?: string[] }> => {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });
    const result = await handleResponse<AuthResponseDTO & { roles?: string[] }>(response);
    console.log('Login response:', JSON.stringify(result, null, 2));
    const cookieStore = await cookies();
    cookieStore.set('accessToken', result.accessToken, { httpOnly: true, secure: true, sameSite: 'strict' });
    cookieStore.set('refreshToken', result.refreshToken, { httpOnly: true, secure: true, sameSite: 'strict' });
    const validRoles = result.roles?.filter(role => role.trim()).map(role => role.startsWith('ROLE_') ? role : `ROLE_${role}`) || ['ROLE_USER'];
    console.log('Saving roles to cookies:', validRoles);
    cookieStore.set('roles', validRoles.join(','), { httpOnly: true, secure: true, sameSite: 'strict' });
    cookieStore.set('username', data.username, { httpOnly: true, secure: true, sameSite: 'strict' });
    return result;
};

export const register = async (data: RegisterRequestDTO): Promise<AuthResponseDTO> => {
    console.log('Register request data:', data);
    const accessToken = (await cookies()).get('accessToken')?.value;
    console.log('Register accessToken:', accessToken);
    const response = await fetch(`${API_BASE_URL}/auth/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${accessToken || ''}`,
        },
        body: JSON.stringify(data),
    });
    const result = await handleResponse<AuthResponseDTO>(response);
    console.log('Register response:', result);
    return result;
};

export const getUsers = async (page: number = 0, size: number = 10, sort: string = 'id,asc', search?: string, accessToken?: string): Promise<PaginatedUsersResponse> => {
    const params = new URLSearchParams({ page: page.toString(), size: size.toString(), sort });
    if (search) params.append('search', search);
    console.log('getUsers params:', params.toString());
    const response = await fetch(`${API_BASE_URL}/users?${params}`, {
        headers: { Authorization: `Bearer ${(await cookies()).get('accessToken')?.value}` },
        cache: 'no-store',
    });
    return handleResponse<PaginatedUsersResponse>(response);
};

export const getUserByUsername = async (username: string, accessToken: string | undefined): Promise<UserDTO> => {
    const response = await fetch(`${API_BASE_URL}/users/${username}`, {
        headers: { Authorization: `Bearer ${(await cookies()).get('accessToken')?.value}` },
        cache: 'no-store',
    });
    return handleResponse<UserDTO>(response);
};

export const updateUser = async (username: string, data: UserDTO, accessToken: string | undefined): Promise<UserDTO> => {
    const validRoles = ['ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER', 'ROLE_SUPERADMIN'];
    const filteredRoles = data.roles?.filter(role => validRoles.includes(role)) || [];
    const requestBody = {
        username: data.username,
        email: data.email,
        password: data.password || null,
        roles: filteredRoles.length > 0 ? filteredRoles : ['ROLE_USER'],
    };
    console.log('updateUser request:', { username, data: requestBody });
    try {
        const response = await fetch(`${API_BASE_URL}/users/${username}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${(await cookies()).get('accessToken')?.value}`,
            },
            body: JSON.stringify(requestBody),
        });
        return await handleResponse<UserDTO>(response);
    } catch (error) {
        console.error('updateUser error:', error instanceof Error ? error.message : error);
        throw error;
    }
};

export const deleteUser = async (id: number, accessToken: string | undefined): Promise<void> => {
    console.log('deleteUser request: id=', id);
    const response = await fetch(`${API_BASE_URL}/users/${id}`, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${(await cookies()).get('accessToken')?.value}` },
        cache: 'no-store',
    });
    await handleResponse<void>(response);
};