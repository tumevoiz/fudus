import axios from "axios";

const loginEndpoint = 'http://localhost:8080/login'
const registerEndpoint = 'http://localhost:8080/register'

export const loginUser = async (username, password) => {
    return await axios.post(loginEndpoint, {username: username, password: password})
}

export const registerUser = async (username, password, email, address, city) => {
    return await axios.post(registerEndpoint, {
        username: username,
        password: password,
        email: email,
        address: address,
        city: city
    })
}