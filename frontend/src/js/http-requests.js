import {authHeader} from "@/js/auth_helpers";

const API_URL = "http://localhost:8081/api";

export async function get(path, email, token, options, headers) {
    return request("GET", path, null, email, token, options, headers);
}

export async function post(path, body, email, token, options, headers) {
    return request("POST", path, body, email, token, options, headers);
}

export async function put(path, body, email, token, options, headers) {
    return request("PUT", path, body, email, token, options, headers);
}

async function request(requestType, path, body, email, token, options, headers) {
    const config = {
        method: requestType,
        headers: new Headers({
            'Accept': 'application/json',
            'content-type': 'application/json',
            'email': email,
            'Authorization': authHeader(token),
            ...headers
        }),
        mode: 'cors',
        body,
        ...options
    };
    return await fetch(`${API_URL}/${path}`, config);
}