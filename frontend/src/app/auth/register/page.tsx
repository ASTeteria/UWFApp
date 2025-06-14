import { redirect } from 'next/navigation';
import { cookies } from 'next/headers';
import { register } from '../../../services/api.service';
import styles from './register.module.css';

interface RegisterPageProps {
    searchParams: Promise<Record<string, string>>;
}

const RegisterPage: React.FC<RegisterPageProps> = async ({ searchParams }) => {
    const accessToken = (await cookies()).get('accessToken')?.value;
    const roles = (await cookies()).get('roles')?.value?.split(',') || [];
    const username = (await cookies()).get('username')?.value;

    if (!accessToken) {
        console.log('No access token, redirecting to /auth/login'); // Отладка
        redirect('/auth/login');
    }

    // Временное решение: считаем пользователя 'superadmin' суперадмином
    const isSuperAdmin = username === 'superadmin' || roles.includes('SUPERADMIN');
    console.log('RegisterPage: isSuperAdmin=', isSuperAdmin, 'roles=', roles, 'username=', username); // Отладка

    if (!isSuperAdmin) {
        console.log('Not SUPERADMIN, redirecting to /dashboard'); // Отладка
        redirect('/dashboard');
    }

    await searchParams; // Разрешаем searchParams, но не используем

    const handleSubmit = async (formData: FormData) => {
        'use server';
        console.log('handleSubmit started'); // Отладка
        const registerData = {
            username: formData.get('username') as string,
            email: formData.get('email') as string,
            password: formData.get('password') as string,
            roles: (formData.get('roles') as string)
                .split(',')
                .map((role) => role.trim())
                .filter((role) => role.length > 0),
        };
        console.log('Form submit data:', registerData); // Отладка
        try {
            console.log('Calling register'); // Отладка
            await register(registerData);
            console.log('Register successful'); // Отладка
            return {}; // Пустой объект для завершения действия
        } catch (error) {
            console.error('Register error:', error instanceof Error ? error.message : 'Registration failed'); // Отладка
            return {}; // Пустой объект, ошибки не отображаются
        }
        console.log('handleSubmit ended'); // Отладка: не должно выполняться
    };

    // Уникальный ключ для формы, сбрасываем всегда после действия
    const formKey = Date.now().toString();
    console.log('Rendering: formKey=', formKey); // Отладка

    return (
        <div className={styles.container}>
            <h1>Register New User</h1>
            <form key={formKey} action={handleSubmit} className={styles.form}>
                <input type="text" name="username" placeholder="Username" required />
                <input type="email" name="email" placeholder="Email" required />
                <input type="password" name="password" placeholder="Password" required />
                <input
                    type="text"
                    name="roles"
                    placeholder="Roles (comma-separated, e.g., ADMIN,USER)"
                    required
                />
                <button type="submit">Register</button>
            </form>
        </div>
    );
};

export default RegisterPage;