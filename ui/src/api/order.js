import axios from "axios";

const orderEndpoint = 'http://localhost:8080/ordering'

export const placeOrder = async (token, order) => {
    const instance = axios.create({
        baseURL: 'http://localhost:8080'
    });

    instance.defaults.headers.common['Authorization'] = `Bearer ${token}`
    return await instance.post(orderEndpoint, {basket: order})
}