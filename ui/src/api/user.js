import axios from "axios";

const loginEndpoint = 'http://localhost:8080/login'
const registerEndpoint = 'http://localhost:8080/login'

export const loginUser = async (username, password) => {
    return await axios.post(loginEndpoint, {
        params: {username: username, password: password},
        headers: {
            "Content-Type": "application/json",
        }
    });
}

export const registerUser = async (username, password) => {
    return await axios.post(registerEndpoint, {
        params: {username: username, password: password},
        headers: {
            "Content-Type": "application/json",
        }
    });
}