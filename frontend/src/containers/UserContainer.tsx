'use client';

import { FC, useState } from 'react';
import Link from 'next/link';
import Table from '@/components/Table';
import SearchBar from '@/components/SearchBar';
import Pagination from '@/components/Pagination';
import { getUsers } from '@/services/api.service';
import { searchUsersAction, deleteUserAction } from '@/app/dashboard/users/action';
import styles from './UserContainer.module.css';

const UserContainer: FC<{ searchParams: { page?: string; username?: string } }> = async ({
                                                                                             searchParams,
                                                                                         }) => {
    const page = Number(searchParams.page) || 1;
    const users = await getUsers({ page });

    let filteredUsers = users.data;
    if (searchParams.username) {
        filteredUsers = users.data.filter(user =>
            user.username.toLowerCase().includes(searchParams.username.toLowerCase())
        );
    }

    const UserContainerClient: FC = () => {
        const [key, setKey] = useState(0);

        const handleSearch = async (formData: FormData) => {
            const result = await searchUsersAction(formData);
            return result;
        };

        const handleDelete = async (username: string) => {
            const result = await deleteUserAction(username);
            if (result.success) {
                setKey(prev => prev + 1);
            }
        };

        return (
            <div className={styles.container}>
                <h1>Користувачі</h1>
                <SearchBar onSearch={handleSearch} />
                <Table
                    headers={['ID', 'Ім’я користувача', 'Ролі', 'Редагувати']}
                    rows={filteredUsers.map(user => [
                        user.id,
                        user.username,
                        user.roles.join(', '),
                        <Link href={`/dashboard/users/edit/${user.username}`}>Редагувати</Link>,
                    ])}
                    onDelete={handleDelete}
                />
                <Pagination
                    currentPage={page}
                    totalPages={users.totalPages}
                    baseUrl="/dashboard/users"
                />
            </div>
        );
    };

    return <UserContainerClient key={key} />;
};

export default UserContainer;