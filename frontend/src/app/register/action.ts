'use server';

import { register } from '@/services/api.service';
import { getCurrentUser } from '@/utils/auth';

export async function registerAction(formData: FormData) {
    const user = await getCurrentUser();
    if (!user?.roles.includes('ROLE_SUPERADMIN')) {
        return { error: 'Доступ заборонено' };
    }

    const username = formData.get('username') as string;
    const password = formData.get('password') as string;
    const roles = formData.getAll('roles') as string[];

    if (username.length < 3) {
        return { error: 'Ім’я користувача має містити щонайменше 3 символи' };
    }
    if (password.length < 6) {
        return { error: 'Пароль має містити щонайменше 6 символів' };
    }
    if (roles.length === 0) {
        return { error: 'Виберіть хоча б одну роль' };
    }

    try {
        await register({ username, password, roles });
        return { success: 'Користувача успішно зареєстровано' };
    } catch (error) {
        return { error: 'Не вдалося зареєструвати користувача' };
    }
}