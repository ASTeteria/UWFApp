import { FC } from 'react';

interface SearchBarProps {
    onSearch: (formData: FormData) => Promise<{ username: string }>;
}

const SearchBar: FC<SearchBarProps> = ({ onSearch }) => {
    return (
        <form action={onSearch}>
            <input
                type="text"
                name="username"
                placeholder="Пошук за ім’ям користувача"
                style={{ padding: '10px', margin: '10px 0', width: '100%' }}
            />
            <button type="submit" style={{ padding: '10px' }}>
                Пошук
            </button>
        </form>
    );
};

export default SearchBar;