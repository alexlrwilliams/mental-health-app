import {get, post} from "@/js/http-requests";
import {handleRequest} from "@/js/request_helpers";


export async function getChatHistory(chatId) {
    return await get(`chat/${chatId}`)
        .then(handleRequest)
        .then((data) => {
            return data.chatHistory
        });
}

export async function joinChat(chatId) {
    return await post(`chat/${chatId}/join`);
}

export async function messageChat(chatId, user, content) {
    let body = JSON.stringify({content, user});
    return await post(`chat/${chatId}/message`, body)
}