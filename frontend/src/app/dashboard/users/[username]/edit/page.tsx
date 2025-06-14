import { getUserByUsername, updateUser } from '../../../../../services/api.service';
import styles from './edit.module.css';

interface EditUserPageProps {
    params: { username: string };
}

const EditUserPage: React.FC<EditUserPageProps> = async ({ params }) => {
    const user = await getUserByUsername(params.username);

    const handleSubmit = async (formData: FormData) => {
        'use server';
        try {
            const userData = {
                id: user.id,
                username: formData.get('username') as string,
                email: formData.get('email') as string,
                password: formData.get('password') as string,
                roles: (formData.get('roles') as string).split(',').map((role) => role.trim()),
            };
            await updateUser(params.username, userData);
            return { success: true };
        } catch (error) {
            console.error('Update user error:', error);
            return { error: error instanceof Error ? error.message : 'Update failed' };
        }
    };

    return (
        <div className={styles.container}>
            <h1>Edit User: {user.username}</h1>
            <form action={handleSubmit} className={styles.form}>
                <input type="text" name="username" defaultValue={user.username} required />
                <input type="email" name="email" defaultValue={user.email} required />
                <input type="password" name="password" placeholder="New password (optional)" />
                <input type="text" name="roles" defaultValue={user.roles.join(', ')} required />
                <button type="submit">Update</button>
            </form>
        </div>
    );
};

export default EditUserPage;