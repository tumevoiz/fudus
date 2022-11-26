import {
    FETCH_RESTAURANTS_START,
    FETCH_RESTAURANTS_SUCCESS,
    FETCH_RESTAURANTS_FAILURE,
    FETCH_MENU_START,
    FETCH_MENU_SUCCESS,
    FETCH_MENU_FAILURE
} from '../actionTypes'

import {fetchRestaurants as fetchRestaurantsApi } from "../api/restaurants";
import {fetchMenuByRestaurantId as fetchMenuByRestaurantIdApi } from "../api/restaurants";

const fetchRestaurants = () => async dispatch => {
    dispatch({type: FETCH_RESTAURANTS_START})

    try{
        const restaurants = await fetchRestaurantsApi()
        dispatch({
            type: FETCH_RESTAURANTS_SUCCESS,
            payload: restaurants,
        })
        console.log('fetch restaurants', restaurants)
    } catch (err) {
        dispatch({
            type: FETCH_RESTAURANTS_FAILURE,
            payload: err,
            error: true,
        })
        console.log("error")
    }
}

const fetchMenuByRestaurantId = (id) => async dispatch => {
    dispatch({type: FETCH_MENU_START})

    try{
        const menu = await fetchMenuByRestaurantIdApi(id)
        dispatch({
            type: FETCH_MENU_SUCCESS,
            payload: menu,
        })
        console.log('fetch restaurants', menu)
    } catch (err) {
        dispatch({
            type: FETCH_MENU_FAILURE,
            payload: err,
            error: true,
        })
        console.log("error")
    }
}

export default {
    fetchRestaurants,
    fetchMenuByRestaurantId,
}