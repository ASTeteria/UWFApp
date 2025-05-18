import './globals.css';

export const metadata = {
    title: 'UWF App',
    description: 'User Management App',
};

export default function RootLayout({
                                       children,
                                   }: {
    children: React.ReactNode;
}) {
    return (
        <html lang="uk">
        <body>{children}</body>
        </html>
    );
}