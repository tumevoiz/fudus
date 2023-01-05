import axios from "axios";

const restaurantsEndpoint = 'http://localhost:8080/restaurants'
const restaurantEndpoint = 'http://localhost:8080/restaurant/'
const searchEndpoint = 'http://localhost:8080/search/restaurant/'

export const fetchRestaurants = async () => {
    return await axios.get(restaurantsEndpoint);
}

export const fetchRestaurant = async (slug) => {
    return await axios.get(restaurantEndpoint+slug);
}

export const fetchMenu = async (slug) => {
    return await axios.get(restaurantEndpoint+slug+"/food");
}

export const fetchRestaurantsByFilterApi = async (filter) => {
    const instance = axios.create({
        baseURL: 'http://localhost:8080'
    });

    return await instance.post(searchEndpoint, {query: filter})
}

export const addRestaurant = async (restaurant) => {
    const instance = axios.create({
        baseURL: 'http://localhost:8080'
    });

    instance.defaults.headers.common['Authorization'] = `Bearer ${token}`
    return await instance.post(restaurantEndpoint, {restaurant})
}
