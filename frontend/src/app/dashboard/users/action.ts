'use server';

import { deleteUser } from '@/services/api.service';

export async function searchUsersAction(formData: FormData) {
    const username = formData.get('username') as string;
    return { username };
}

export async function deleteUserAction(username: string) {
    try {
        await deleteUser(username);
        return { success: true };
    } catch (error) {
        return { error: 'Не вдалося видалити користувача' };
    }
}