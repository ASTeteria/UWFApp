'use server';

import { login } from '@/services/api.service';

export async function loginAction(formData: FormData) {
    const username = formData.get('username') as string;
    const password = formData.get('password') as string;

    if (username.length < 3) {
        return { error: 'Ім’я користувача має містити щонайменше 3 символи' };
    }
    if (password.length < 6) {
        return { error: 'Пароль має містити щонайменше 6 символів' };
    }

    try {
        await login({ username, password });
        return { success: true };
    } catch (error: any) {
        return { error: `Помилка авторизації: ${error.message || 'Невідома помилка'}` };
    }
}