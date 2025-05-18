import { cookies } from 'next/headers';
import { LoginRequestDTO, RegisterRequestDTO, UserDTO } from '@/types/types';

const API_URL = 'http://owu.linkpc.net';


const getHeaders = async () => {
    const cookieStore = await cookies();
    const accessToken = cookieStore.get('accessToken')?.value;
    return {
        'Content-Type': 'application/json',
        'Origin': 'http://localhost:3000',
        ...(accessToken ? { Authorization: `Bearer ${accessToken}` } : {}),
    };
};

export async function login(data: LoginRequestDTO) {
    try {
        console.log('Attempting login with:', { endpoint: `${API_URL}/auth/sign-in`, data });
        const res = await fetch(`${API_URL}/auth/sign-in`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Origin': 'http://localhost:3000',
            },
            body: JSON.stringify(data),
        });
        const contentType = res.headers.get('content-type') || 'unknown';
        const body = await res.text();
        console.log('Login response:', { status: res.status, headers: Object.fromEntries(res.headers.entries()), contentType, body });

        if (!res.ok) throw new Error(`Login failed: ${res.status}`);
        if (!contentType.includes('application/json')) {
            throw new Error(`Expected JSON, but received ${contentType}`);
        }

        const result = JSON.parse(body);
        const cookieStore = await cookies();
        cookieStore.set('accessToken', result.accessToken, { path: '/', httpOnly: true });
        cookieStore.set('refreshToken', result.refreshToken, { path: '/', httpOnly: true });
        cookieStore.set('username', data.username, { path: '/', httpOnly: true });
        return result;
    } catch (error) {
        console.error('Login error:', error);
        throw error;
    }
}

export async function register(data: RegisterRequestDTO) {
    try {
        console.log('Attempting register with:', { endpoint: `${API_URL}/api/auth/register`, data });
        const res = await fetch(`${API_URL}/api/auth/register`, {
            method: 'POST',
            headers: await getHeaders(),
            body: JSON.stringify(data),
        });
        const contentType = res.headers.get('content-type') || 'unknown';
        const body = await res.text();
        console.log('Register response:', { status: res.status, headers: Object.fromEntries(res.headers.entries()), contentType, body });

        if (!res.ok) throw new Error(`Registration failed: ${res.status}`);
        if (!contentType.includes('application/json')) {
            throw new Error(`Expected JSON, but received ${contentType}`);
        }

        const result = JSON.parse(body);
        const cookieStore = await cookies();
        cookieStore.set('accessToken', result.accessToken, { path: '/', httpOnly: true });
        cookieStore.set('refreshToken', result.refreshToken, { path: '/', httpOnly: true });
        cookieStore.set('username', data.username, { path: '/', httpOnly: true });
        return result;
    } catch (error) {
        console.error('Register error:', error);
        throw error;
    }
}

export async function getCurrentUser(): Promise<UserDTO | null> {
    try {
        const cookieStore = await cookies();
        const username = cookieStore.get('username')?.value;
        if (!username) return null;
        console.log('Fetching current user:', { endpoint: `${API_URL}/api/users/${username}` });
        const res = await fetch(`${API_URL}/api/users/${username}`, { headers: await getHeaders() });
        const contentType = res.headers.get('content-type') || 'unknown';
        const body = await res.text();
        console.log('Get current user response:', { status: res.status, contentType, body });

        if (!res.ok) return null;
        if (!contentType.includes('application/json')) {
            throw new Error(`Expected JSON, but received ${contentType}`);
        }

        return JSON.parse(body);
    } catch (error) {
        console.error('Get current user error:', error);
        return null;
    }
}

export async function getUsers({ page = 1 }: { page?: number }) {
    try {
        const url = new URL(`${API_URL}/api/users`);
        url.searchParams.set('page', String(page));
        console.log('Fetching users:', { endpoint: url.toString() });
        const res = await fetch(url.toString(), { headers: await getHeaders() });
        const contentType = res.headers.get('content-type') || 'unknown';
        const body = await res.text();
        console.log('Get users response:', { status: res.status, contentType, body });

        if (!res.ok) throw new Error(`Failed to fetch users: ${res.status}`);
        if (!contentType.includes('application/json')) {
            throw new Error(`Expected JSON, but received ${contentType}`);
        }

        const data = JSON.parse(body);
        return { data, totalPages: Math.ceil(data.length / 10) || 1 };
    } catch (error) {
        console.error('Get users error:', error);
        throw error;
    }
}

export async function getUserByUsername(username: string) {
    try {
        console.log('Fetching user by username:', { endpoint: `${API_URL}/api/users/${username}` });
        const res = await fetch(`${API_URL}/api/users/${username}`, { headers: await getHeaders() });
        const contentType = res.headers.get('content-type') || 'unknown';
        const body = await res.text();
        console.log('Get user by username response:', { status: res.status, contentType, body });

        if (!res.ok) return null;
        if (!contentType.includes('application/json')) {
            throw new Error(`Expected JSON, but received ${contentType}`);
        }

        return JSON.parse(body);
    } catch (error) {
        console.error('Get user by username error:', error);
        return null;
    }
}

export async function updateUser(username: string, data: UserDTO) {
    try {
        console.log('Updating user:', { endpoint: `${API_URL}/api/users/${username}`, data });
        const res = await fetch(`${API_URL}/api/users/${username}`, {
            method: 'PUT',
            headers: await getHeaders(),
            body: JSON.stringify(data),
        });
        const contentType = res.headers.get('content-type') || 'unknown';
        const body = await res.text();
        console.log('Update user response:', { status: res.status, contentType, body });

        if (!res.ok) throw new Error(`Failed to update user: ${res.status}`);
        if (!contentType.includes('application/json')) {
            throw new Error(`Expected JSON, but received ${contentType}`);
        }

        return JSON.parse(body);
    } catch (error) {
        console.error('Update user error:', error);
        throw error;
    }
}

export async function deleteUser(username: string) {
    try {
        console.log('Deleting user:', { endpoint: `${API_URL}/api/users/${username}` });
        const res = await fetch(`${API_URL}/api/users/${username}`, {
            method: 'DELETE',
            headers: await getHeaders(),
        });
        const contentType = res.headers.get('content-type') || 'unknown';
        const body = await res.text();
        console.log('Delete user response:', { status: res.status, contentType, body });

        if (!res.ok) throw new Error(`Failed to delete user: ${res.status}`);
    } catch (error) {
        console.error('Delete user error:', error);
        throw error;
    }
}