import { redirect } from 'next/navigation';
import { cookies } from 'next/headers';
import { login } from '../../../services/api.service';
import styles from './login.module.css';
import {JSX} from "react";

const LoginPage: ({searchParams}: { searchParams: Promise<{ error?: string }> }) => Promise<JSX.Element> = async ({ searchParams }: { searchParams: Promise<{ error?: string }> }) => {
    const accessToken = (await cookies()).get('accessToken')?.value;
    if (accessToken) {
        redirect('/dashboard');
    }

    const resolvedSearchParams = await searchParams;
    const error = resolvedSearchParams.error;

    const handleSubmit = async (formData: FormData) => {
        'use server';
        try {
            const loginData = {
                username: formData.get('username') as string,
                password: formData.get('password') as string,
            };
            await login(loginData);
            (await cookies()).set('username', loginData.username, { httpOnly: true, secure: true }); // Сохраняем username
            redirect('/dashboard');
        } catch (error) {
            const errorMessage = error instanceof Error ? error.message : 'Login failed';
            redirect(`/auth/login?error=${encodeURIComponent(errorMessage)}`);
        }
    };

    return (
        <div className={styles.container}>
            <h1>Login</h1>
            {error && <p className={styles.error}>{error}</p>}
            <form action={handleSubmit} className={styles.form}>
                <input type="text" name="username" placeholder="Username" required />
                <input type="password" name="password" placeholder="Password" required />
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default LoginPage;