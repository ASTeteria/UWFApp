import { getCurrentUser as getUser } from '@/services/api.service';
import { UserDTO } from '@/types/types';

export async function getCurrentUser(): Promise<UserDTO | null> {
    return await getUser();
}