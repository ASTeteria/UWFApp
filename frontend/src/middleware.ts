import { NextRequest, NextResponse } from 'next/server';
import { getCurrentUser } from '@/utils/auth';

export async function middleware(request: NextRequest) {
    const { pathname } = request.nextUrl;
    const user = await getCurrentUser();

    if (pathname.startsWith('/dashboard') || pathname === '/register') {
        if (!user) {
            return NextResponse.redirect(new URL('/sign-in', request.url));
        }
        if (pathname === '/register' && !user.roles.includes('ROLE_SUPERADMIN')) {
            return NextResponse.redirect(new URL('/dashboard', request.url));
        }
    }

    return NextResponse.next();
}

export const config = {
    matcher: ['/dashboard/:path*', '/register'],
};