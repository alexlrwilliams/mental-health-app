import {authHeader, emailHeader} from "@/js/request_helpers";

export async function get(path, email, token, options, headers) {
    return request("GET", path, null, email, token, options, {'content-type': 'application/json', ...headers});
}

export async function post(path, body, email, token, options, headers) {
    return request("POST", path, body, email, token, options, {'content-type': 'application/json', ...headers});
}

export async function put(path, body, email, token, options, headers) {
    return request("PUT", path, body, email, token, options, headers);
}

async function request(requestType, path, body, email, token, options, headers) {
    const config = {
        method: requestType,
        headers: new Headers({
            'Accept': 'application/json',
            'email': emailHeader(email),
            'Authorization': authHeader(token),
            ...headers
        }),
        mode: 'cors',
        body,
        ...options
    };
    return await fetch(`${process.env.VUE_APP_API_URL}/${path}`, config);
}