import user from "./mockUser";

export const fetchUser = async () => {
    return new Promise(resolve => {
        resolve(user)
    })
}