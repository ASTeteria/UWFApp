'use client';

import { FC, useState } from 'react';
import { useRouter } from 'next/navigation';
import { registerAction } from './action';
import styles from './register.module.css';

const RegisterPage: FC = () => {
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState<string | null>(null);
    const router = useRouter();

    const handleSubmit = async (formData: FormData) => {
        const result = await registerAction(formData);
        if (result.error) {
            setError(result.error);
            setSuccess(null);
        } else {
            setSuccess(result.success);
            setError(null);
            setTimeout(() => router.push('/dashboard'), 2000);
        }
    };

    return (
        <div className={styles.container}>
            <h1>Реєстрація користувача</h1>
            {error && <p className={styles.error}>{error}</p>}
            {success && <p className={styles.success}>{success}</p>}
            <form action={handleSubmit}>
                <input
                    type="text"
                    name="username"
                    placeholder="Ім’я користувача"
                    required
                    className={styles.input}
                />
                <input
                    type="password"
                    name="password"
                    placeholder="Пароль"
                    required
                    className={styles.input}
                />
                <div className={styles.checkbox}>
                    <label>
                        <input type="checkbox" name="roles" value="ROLE_USER" />
                        Користувач
                    </label>
                    <label>
                        <input type="checkbox" name="roles" value="ROLE_MODERATOR" />
                        Модератор
                    </label>
                    <label>
                        <input type="checkbox" name="roles" value="ROLE_ADMIN" />
                        Адмін
                    </label>
                    <label>
                        <input type="checkbox" name="roles" value="ROLE_SUPERADMIN" />
                        Суперадмін
                    </label>
                </div>
                <button type="submit" className={styles.button}>Зареєструвати</button>
            </form>
        </div>
    );
};

export default RegisterPage;