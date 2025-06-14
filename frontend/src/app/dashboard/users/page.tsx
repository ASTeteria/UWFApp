import { redirect } from 'next/navigation';
import { cookies } from 'next/headers';
import { getUsers, deleteUser, updateUser, getUserByUsername } from '@/services/api.service';
import { UserDTO } from '@/types/user';
import styles from './users.module.css';
import Link from 'next/link';
import { revalidatePath } from 'next/cache';

export const revalidate = 0;

interface UsersPageProps {
    searchParams: Promise<{ page?: string; size?: string; sort?: string; search?: string; error?: string; success?: string }>;
}

const UsersPage: React.FC<UsersPageProps> = async ({ searchParams }) => {
    const cookieStore = await cookies();
    const accessToken = (await cookieStore.get('accessToken'))?.value;
    const roles = (await cookieStore.get('roles'))?.value?.split(',').filter(role => role.trim()) || [];
    const username = (await cookieStore.get('username'))?.value || '';

    console.log('Cookies:', { accessToken: !!accessToken, roles, username });

    if (!accessToken) {
        console.log('No access token, redirecting to /auth/login');
        redirect('/auth/login');
    }

    const isSuperAdmin = username === 'superadmin' || roles.includes('SUPERADMIN');
    console.log('UsersPage: isSuperAdmin=', isSuperAdmin, 'roles=', roles, 'username=', username);

    const resolvedSearchParams = await searchParams;
    const page = parseInt(resolvedSearchParams.page || '0', 10);
    const size = parseInt(resolvedSearchParams.size || '10', 10);
    const sort = resolvedSearchParams.sort || 'id,asc';
    const search = resolvedSearchParams.search || '';
    const error = resolvedSearchParams.error || '';
    const success = resolvedSearchParams.success || '';

    console.log('Fetching users: page=', page, 'size=', size, 'sort=', sort, 'search=', search);
    const { content: users, totalPages } = await getUsers(page, size, sort, search, accessToken);
    console.log('Users fetched:', users.length, 'totalPages:', totalPages);

    const handleDelete = async (id: number) => {
        'use server';
        console.log('handleDelete started: id=', id);
        const cookieStore = await cookies();
        const accessToken = (await cookieStore.get('accessToken'))?.value;
        const params = new URLSearchParams({
            page: page.toString(),
            size: size.toString(),
            sort,
            ...(search && { search }),
            success: 'User deleted successfully',
        });
        try {
            await deleteUser(id, accessToken);
            console.log('Delete successful');
            revalidatePath(`/dashboard/users?${params.toString()}`);
        } catch (error) {
            console.error('Delete error:', error instanceof Error ? error.message : 'Deletion failed');
            params.set('success', '');
            params.set('error', 'Failed to delete user');
        }
        console.log('Redirecting to:', `/dashboard/users?${params.toString()}`);
        redirect(`/dashboard/users?${params.toString()}`);
    };

    const handleEdit = async (formData: FormData) => {
        'use server';
        console.log('handleEdit started');
        const cookieStore = await cookies();
        const accessToken = (await cookieStore.get('accessToken'))?.value;
        const originalUsername = formData.get('originalUsername') as string;
        const username = (formData.get('username') as string)?.trim();
        const email = (formData.get('email') as string)?.trim();
        const password = (formData.get('password') as string)?.trim();
        const rolesInput = (formData.get('roles') as string)?.trim();

        console.log('Form data:', { originalUsername, username, email, password: password ? '***' : null, rolesInput });

        const validRoles = ['ADMIN', 'MODERATOR', 'USER', 'SUPERADMIN'];
        const roles = rolesInput
            ? rolesInput
                .split(',')
                .map((role) => role.trim().toUpperCase())
                .filter((role) => validRoles.includes(role))
            : ['USER'];

        if (!originalUsername || !username || !email || roles.length === 0) {
            console.error('Edit error: Invalid input data', { originalUsername, username, email, roles });
            const params = new URLSearchParams({
                page: page.toString(),
                size: size.toString(),
                sort,
                ...(search && { search }),
                error: 'All fields are required except password',
            });
            redirect(`/dashboard/users?${params.toString()}`);
        }

        // Проверка уникальности username
        if (username !== originalUsername) {
            try {
                console.log('Checking username uniqueness:', username);
                await getUserByUsername(username, accessToken);
                console.error('Edit error: Username already exists', { username });
                const params = new URLSearchParams({
                    page: page.toString(),
                    size: size.toString(),
                    sort,
                    ...(search && { search }),
                    error: 'Username already exists',
                });
                redirect(`/dashboard/users?${params.toString()}`);
            } catch (error) {
                if (!(error instanceof Error && error.message.includes('404'))) {
                    console.error('Edit error: Failed to check username', error);
                    const params = new URLSearchParams({
                        page: page.toString(),
                        size: size.toString(),
                        sort,
                        ...(search && { search }),
                        error: 'Failed to check username availability',
                    });
                    redirect(`/dashboard/users?${params.toString()}`);
                }
            }
        }

        // Валидация email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            console.error('Edit error: Invalid email format', { email });
            const params = new URLSearchParams({
                page: page.toString(),
                size: size.toString(),
                sort,
                ...(search && { search }),
                error: 'Invalid email format',
            });
            redirect(`/dashboard/users?${params.toString()}`);
        }

        const editData: UserDTO = {
            id: null,
            username,
            email,
            password: password || null,
            roles,
        };
        console.log('Edit submit data:', { originalUsername, data: editData });
        try {
            const updatedUser = await updateUser(originalUsername, editData, accessToken);
            console.log('Edit successful:', updatedUser);
            const params = new URLSearchParams({
                page: page.toString(),
                size: size.toString(),
                sort,
                ...(search && { search }),
            });
            console.log('Revalidating:', `/dashboard/users?${params.toString()}`);
            revalidatePath(`/dashboard/users?${params.toString()}`);
            console.log('Redirecting to:', `/dashboard/users?${params.toString()}`);
            redirect(`/dashboard/users?${params.toString()}`);
        } catch (error) {
            const errorMessage = error instanceof Error ? error.message : 'Edit failed';
            console.error('Edit error:', errorMessage);
            const params = new URLSearchParams({
                page: page.toString(),
                size: size.toString(),
                sort,
                ...(search && { search }),
                error: errorMessage.includes('email')
                    ? 'Email already exists or invalid'
                    : errorMessage.includes('username')
                        ? 'Username already exists'
                        : 'Failed to update user',
            });
            redirect(`/dashboard/users?${params.toString()}`);
        }
    };

    console.log('Rendering users:', users.map(u => ({ number: page * size + users.indexOf(u) + 1, username: u.username })));

    return (
        <div className={styles.container}>
            <h1 className={styles.title}>Users</h1>
            {error && (
                <div className={styles.error}>
                    {error}
                </div>
            )}
            {success && (
                <div className={styles.success}>
                    {success}
                </div>
            )}
            <form action="/dashboard/users" method="GET" className={styles.searchForm}>
                <input
                    type="text"
                    name="search"
                    placeholder="Search by username..."
                    defaultValue={search}
                    className={styles.searchInput}
                />
                <input type="hidden" name="page" value="0" />
                <input type="hidden" name="size" value={size.toString()} />
                <input type="hidden" name="sort" value={sort} />
                <button type="submit" className={styles.searchButton}>Search</button>
            </form>
            <div className={styles.tableWrapper}>
                <table className={styles.table}>
                    <thead>
                    <tr>
                        <th className={styles.th}>#</th>
                        <th className={styles.th}>Username</th>
                        <th className={styles.th}>Email</th>
                        <th className={styles.th}>Roles</th>
                        <th className={styles.th}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {users.map((user, index) => (
                        <tr key={user.id || index} className={styles.tr}>
                            <td className={styles.td}>{page * size + index + 1}</td>
                            <td className={styles.td}>{user.username}</td>
                            <td className={styles.td}>{user.email}</td>
                            <td className={styles.td}>{user.roles?.join(', ') || ''}</td>
                            <td className={styles.td}>
                                {isSuperAdmin ? (
                                    <div className={styles.actions}>
                                        <form action={handleDelete.bind(null, user.id!)} className={styles.actionForm}>
                                            <button type="submit" className={styles.deleteButton}>
                                                Delete
                                            </button>
                                        </form>
                                        <form action={handleEdit} className={styles.actionForm}>
                                            <input
                                                type="hidden"
                                                name="originalUsername"
                                                value={user.username}
                                            />
                                            <input
                                                type="text"
                                                name="username"
                                                defaultValue={user.username}
                                                placeholder="Username"
                                                required
                                                className={styles.input}
                                            />
                                            <input
                                                type="email"
                                                name="email"
                                                defaultValue={user.email}
                                                placeholder="Email"
                                                required
                                                className={styles.input}
                                            />
                                            <input
                                                type="text"
                                                name="roles"
                                                defaultValue={user.roles?.join(', ') || ''}
                                                placeholder="Roles (e.g., USER,MODERATOR)"
                                                required
                                                className={styles.input}
                                            />
                                            <input
                                                type="password"
                                                name="password"
                                                placeholder="New Password (optional)"
                                                className={styles.input}
                                            />
                                            <button type="submit" className={styles.button}>
                                                Save
                                            </button>
                                        </form>
                                    </div>
                                ) : (
                                    <span>No actions available</span>
                                )}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
            <div className={styles.pagination}>
                <Link
                    href={{
                        pathname: '/dashboard/users',
                        query: { page: page - 1, size, sort, search },
                    }}
                    className={`${styles.pageLink} ${page <= 0 ? styles.disabled : ''}`}
                >
                    Previous
                </Link>
                <span className={styles.pageInfo}>Page {page + 1} of {totalPages}</span>
                <Link
                    href={{
                        pathname: '/dashboard/users',
                        query: { page: page + 1, size, sort, search },
                    }}
                    className={`${styles.pageLink} ${page >= totalPages - 1 ? styles.disabled : ''}`}
                >
                    Next
                </Link>
            </div>
        </div>
    );
};

export default UsersPage;