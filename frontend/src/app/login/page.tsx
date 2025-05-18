'use client';

import { FC, useState } from 'react';
import { useRouter } from 'next/navigation';
import { loginAction } from './action';
import styles from './login.module.css';

const LoginPage: FC = () => {
    const [error, setError] = useState<string | null>(null);
    const router = useRouter();

    const handleSubmit = async (formData: FormData) => {
        const result = await loginAction(formData);
        if (result.error) {
            setError(result.error);
        } else {
            router.push('/dashboard');
        }
    };

    return (
        <div className={styles.container}>
            <h1>Вхід</h1>
            {error && <p className={styles.error}>{error}</p>}
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
                <button type="submit" className={styles.button}>Увійти</button>
            </form>
        </div>
    );
};

export default LoginPage;