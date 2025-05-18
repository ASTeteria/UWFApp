'use client';

import { FC, useState } from 'react';
import { useRouter } from 'next/navigation';
import DashboardLayout from '@/containers/DashboardLayout';
import { getUserByUsername } from '@/services/api.service';
import { getCurrentUser } from '@/utils/auth';
import { updateUserAction } from './action';
import styles from './edit.module.css';

const EditUserPage: FC<{ params: { username: string } }> = async ({ params }) => {
    const user = await getCurrentUser();
    const targetUser = await getUserByUsername(params.username);

    if (!user?.roles.includes('ROLE_SUPERADMIN')) {
        return <div>Доступ заборонено</div>;
    }

    if (!targetUser) {
        return <div>Користувача не знайдено</div>;
    }

    const EditForm: FC = () => {
        const [error, setError] = useState<string | null>(null);
        const [success, setSuccess] = useState<string | null>(null);
        const router = useRouter();

        const handleSubmit = async (formData: FormData) => {
            const result = await updateUserAction(params.username, formData);
            if (result.error) {
                setError(result.error);
                setSuccess(null);
            } else {
                setSuccess(result.success);
                setError(null);
                setTimeout(() => router.push('/dashboard/users'), 2000);
            }
        };

        return (
            <div className={styles.container}>
                <h1>Редагувати користувача: {targetUser.username}</h1>
                {error && <p className={styles.error}>{error}</p>}
                {success && <p className={styles.success}>{success}</p>}
                <form action={handleSubmit}>
                    <input
                        type="password"
                        name="password"
                        placeholder="Новий пароль (залиште порожнім, якщо без змін)"
                        className={styles.input}
                    />
                    <div>
                        <label>
                            <input
                                type="checkbox"
                                name="roles"
                                value="ROLE_USER"
                                defaultChecked={targetUser.roles.includes('ROLE_USER')}
                            />
                            Користувач
                        </label>
                        <label>
                            <input
                                type="checkbox"
                                name="roles"
                                value="ROLE_MODERATOR"
                                defaultChecked={targetUser.roles.includes('ROLE_MODERATOR')}
                            />
                            Модератор
                        </label>
                        <label>
                            <input
                                type="checkbox"
                                name="roles"
                                value="ROLE_ADMIN"
                                defaultChecked={targetUser.roles.includes('ROLE_ADMIN')}
                            />
                            Адмін
                        </label>
                        <label>
                            <input
                                type="checkbox"
                                name="roles"
                                value="ROLE_SUPERADMIN"
                                defaultChecked={targetUser.roles.includes('ROLE_SUPERADMIN')}
                            />
                            Суперадмін
                        </label>
                    </div>
                    <button type="submit" className={styles.button}>Зберегти</button>
                </form>
            </div>
        );
    };

    return (
        <DashboardLayout>
            <EditForm />
        </DashboardLayout>
    );
};

export default EditUserPage;