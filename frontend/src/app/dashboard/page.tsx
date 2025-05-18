import Link from 'next/link';
import DashboardLayout from '@/containers/DashboardLayout';

export default function DashboardPage() {
    return (
        <DashboardLayout>
            <h1>Дашборд</h1>
            <nav>
                <ul>
                    <li>
                        <Link href="/dashboard/users">Управління користувачами</Link>
                    </li>
                </ul>
            </nav>
        </DashboardLayout>
    );
}