import axios from "axios";

const restaurantsEndpoint = 'http://localhost:8080/restaurants'
const restaurantEndpoint = 'http://localhost:8080/restaurant/'

export const fetchRestaurants = async () => {
    return await axios.get(restaurantsEndpoint);
}

export const fetchRestaurant = async (slug) => {
    return await axios.get(restaurantEndpoint+slug);
}

export const fetchMenu = async (slug) => {
    return await axios.get(restaurantEndpoint+slug+"/food");
}

export const addRestaurant = async (restaurant) => {
    return await axios.post(restaurantEndpoint, {
        params: {restaurant},
        headers: {
            "Content-Type": "application/json",
        }
    });
}
