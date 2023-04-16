import {get, post, put} from "@/js/http-requests";


export async function register(user) {
    let body = JSON.stringify(user);
    return await post('auth/register', body)
        .then(handleRequest);
}

export async function login(user) {
    let body = JSON.stringify(user);
    return await post('auth/login', body)
        .then(handleRequest);
}

export async function logout(email, token) {
    let body = JSON.stringify({email, token});
    return await post('auth/logout', body)
        .then(handleRequest);
}

export async function refreshTokens(email, token) {
    let body = JSON.stringify({email, token});
    return await post('auth/refresh', body)
        .then(handleRequest);
}


export async function getUser(email, token) {
    return await get(`users/email/${email}`, email, token)
        .then(handleRequest);
}

export async function updateUser(id, user, email, token) {
    let body = JSON.stringify(user);
    return await put(`users/${id}`, body, email, token)
        .then(handleRequest);
}

function handleRequest(response) {
    if (response.ok) {
        return response.json();
    }
    return Promise.reject(response);
}