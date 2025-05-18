import { FC, ReactNode } from 'react';
import Link from 'next/link';

interface DashboardLayoutProps {
    children: ReactNode;
}

const DashboardLayout: FC<DashboardLayoutProps> = ({ children }) => {
    return (
        <div style={{ padding: '20px' }}>
            <nav>
                <ul>
                    <li>
                        <Link href="/dashboard">Дашборд</Link>
                    </li>
                    <li>
                        <Link href="/dashboard/users">Користувачі</Link>
                    </li>
                </ul>
            </nav>
            {children}
        </div>
    );
};

export default DashboardLayout;