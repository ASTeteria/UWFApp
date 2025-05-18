import UserContainer from '@/containers/UserContainer';

export default function UsersPage({
                                      searchParams,
                                  }: {
    searchParams: { page?: string; username?: string };
}) {
    return <UserContainer searchParams={searchParams} />;
}