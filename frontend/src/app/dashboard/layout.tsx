import { cookies } from 'next/headers';
import { redirect } from 'next/navigation';

export default async function DashboardLayout({ children }: { children: React.ReactNode }) {
    if (!(await cookies()).get('accessToken')?.value) {
        redirect('/auth/login');
    }
    return <>{children}</>;
}