import Link from 'next/link';
import { cookies } from 'next/headers';
import { redirect } from 'next/navigation';
import styles from './Header.module.css';

const Header: React.FC = async () => {
    const handleLogout = async () => {
        'use server';
        (await cookies()).delete('accessToken');
        (await cookies()).delete('refreshToken');
        (await cookies()).delete('roles');
        redirect('/auth/login');
    };

    const isAuthenticated = !!(await cookies()).get('accessToken')?.value;
    const roles = (await cookies()).get('roles')?.value?.split(',') || [];
    console.log('Header roles:', roles); // Отладка: какие роли доступны

    // Временное решение для тестирования: считаем пользователя 'superadmin' суперадмином
    const isSuperAdmin = (await cookies()).get('username')?.value === 'superadmin' || roles.includes('SUPERADMIN');

    return (
        <header className={styles.header}>
            <div className={styles.container}>
                <Link href="/dashboard" className={styles.logo}>
                    Ukrainian Wushu Federation
                </Link>
                <nav className={styles.nav}>
                    {isAuthenticated ? (
                        <>
                            {isSuperAdmin && (
                                <>
                                    <Link href="/dashboard/users">Users</Link>
                                    <Link href="/auth/register" className={styles.registerButton}>
                                        Register New User
                                    </Link>
                                </>
                            )}
                            <form action={handleLogout}>
                                <button type="submit" className={styles.logoutButton}>
                                    Logout
                                </button>
                            </form>
                        </>
                    ) : (
                        <Link href="/auth/login">Login</Link>
                    )}
                </nav>
            </div>
        </header>
    );
};

export default Header;