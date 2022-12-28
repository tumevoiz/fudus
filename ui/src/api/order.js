import axios from "axios";

const orderEndpoint = 'http://localhost:8080/ordering'

export const placeOrder = async (order) => {
    return await axios.post(orderEndpoint, {
        params: {order},
        headers: {
            "Content-Type": "application/json",
        }
    });
}