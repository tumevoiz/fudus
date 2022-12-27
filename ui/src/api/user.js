import axios from "axios";
import user from "../reducers/user";

const loginEndpoint = 'http://localhost:8080/login'
const registerEndpoint = 'http://localhost:8080/login'

export const loginUser = async (username, password) => {
    try {
        const res = await axios.post(loginEndpoint, {
            params: { username: username, password: password },
            headers: {
                "Content-Type": "application/json",
            }
        });
        return res
        // return new Promise(resolve => {
        //     resolve({username: username, password: password})
        // })
    } catch (err) {
        return err
    }
}

export const registerUser = async (username, password) => {
    try {
        const res = await axios.post(registerEndpoint, {
            params: { username: username, password: password },
            headers: {
                "Content-Type": "application/json",
            }
        });
        return res
        // return new Promise(resolve => {
        //     resolve({username: username, password: password})
        // })
    } catch (err) {
        return err
    }
}