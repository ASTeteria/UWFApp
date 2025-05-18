import { FC } from 'react';
import Link from 'next/link';

interface PaginationProps {
    currentPage: number;
    totalPages: number;
    baseUrl: string;
}

const Pagination: FC<PaginationProps> = ({ currentPage, totalPages, baseUrl }) => {
    const pages = Array.from({ length: totalPages }, (_, i) => i + 1);

    return (
        <div>
            {pages.map(page => (
                <Link
                    key={page}
                    href={`${baseUrl}?page=${page}`}
                    style={{
                        margin: '0 5px',
                        fontWeight: page === currentPage ? 'bold' : 'normal',
                    }}
                >
                    {page}
                </Link>
            ))}
        </div>
    );
};

export default Pagination;