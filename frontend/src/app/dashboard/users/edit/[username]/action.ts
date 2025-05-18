'use server';

import { updateUser, getUserByUsername } from '@/services/api.service';
import { getCurrentUser } from '@/utils/auth';

export async function updateUserAction(username: string, formData: FormData) {
    const user = await getCurrentUser();
    if (!user?.roles.includes('ROLE_SUPERADMIN')) {
        return { error: 'Доступ заборонено' };
    }

    const targetUser = await getUserByUsername(username);
    if (!targetUser) {
        return { error: 'Користувача не знайдено' };
    }

    const password = formData.get('password') as string;
    const roles = formData.getAll('roles') as string[];

    if (password && password.length < 6) {
        return { error: 'Пароль має містити щонайменше 6 символів' };
    }
    if (roles.length === 0) {
        return { error: 'Виберіть хоча б одну роль' };
    }

    try {
        await updateUser(username, { username, password, roles });
        return { success: 'Користувача успішно оновлено' };
    } catch (error) {
        return { error: 'Не вдалося оновити користувача' };
    }
}