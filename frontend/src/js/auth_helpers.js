export function authHeader(token) {
    let user = JSON.parse(localStorage.getItem('user'));
    if (token) {
        return `Bearer ${token}`;
    } else if (user && user.accessToken) {
        return `Bearer ${user.accessToken}`;
    }
}