export function authHeader(token) {
    let user = JSON.parse(localStorage.getItem('user'));
    if (token) {
        return `Bearer ${token}`;
    } else if (user && user.accessToken) {
        return `Bearer ${user.accessToken}`;
    }
}

export function emailHeader(email) {
    let user = JSON.parse(localStorage.getItem('user'));
    if (email) {
        return email;
    } else if (user && user.email) {
        return user.email;
    }
}

export function handleRequest(response) {
    if (response.ok) {
        return response.json();
    }
    return Promise.reject(response);
}