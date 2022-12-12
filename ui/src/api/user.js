import axios from "axios";

const loginEndpoint = 'http://localhost:8080/login'

export const loginUser = async (username, password) => {
    try {
        const res = await axios.post(loginEndpoint, {
            params: { username: username, password: password },
            headers: {
                "Content-Type": "application/json",
            }
        });
        return res
    } catch (err) {
        handleErrors(err);
    }
}

const handleErrors = (responseStatus, errorMessage) => {
    return {
        response: errorMessage,
        statusCode: responseStatus
    }
}
