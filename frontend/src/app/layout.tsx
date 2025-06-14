import { Inter } from 'next/font/google';
import Header from '../components/Header';
import Footer from '../components/Footer';
import './globals.css';

const inter = Inter({ subsets: ['latin'] });

export const metadata = {
    title: 'Ukrainian Wushu Federation',
    description: 'Official website of the Ukrainian Wushu Federation',
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
    return (
        <html lang="en" className={inter.className}>
        <body>
        <Header />
        <main className="container">{children}</main>
        <Footer />
        </body>
        </html>
    );
}