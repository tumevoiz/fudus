import {
    FETCH_RESTAURANTS_START,
    FETCH_RESTAURANTS_SUCCESS,
    FETCH_RESTAURANTS_FAILURE,
    FETCH_MENU_START,
    FETCH_MENU_SUCCESS,
    FETCH_MENU_FAILURE, ADD_RESTAURANT_FAILURE, ADD_RESTAURANT_SUCCESS, ADD_RESTAURANT_START
} from '../actionTypes'

import {fetchRestaurants as fetchRestaurantsApi, fetchRestaurantsByFilterApi} from "../api/restaurants";
import {addRestaurant as addRestaurantApi } from "../api/restaurants";
import {fetchMenu as fetchMenuApi } from "../api/restaurants";

const fetchRestaurants = () => async dispatch => {
    dispatch({type: FETCH_RESTAURANTS_START})

    try{
        const response = await fetchRestaurantsApi()
        dispatch({
            type: FETCH_RESTAURANTS_SUCCESS,
            payload: response.data,
        })
    } catch (err) {
        dispatch({
            type: FETCH_RESTAURANTS_FAILURE,
            payload: err,
            error: true,
        })
    }
}

const fetchMenu = (slug) => async dispatch => {
    dispatch({type: FETCH_MENU_START})

    try{
        const response = await fetchMenuApi(slug)
        dispatch({
            type: FETCH_MENU_SUCCESS,
            payload: response.data,
        })
    } catch (err) {
        dispatch({
            type: FETCH_MENU_FAILURE,
            payload: err,
            error: true,
        })
    }
}

const fetchRestaurantsByFilter = (filter) => async dispatch => {
    dispatch({type: FETCH_RESTAURANTS_START})

    try{
        const response = await fetchRestaurantsByFilterApi(filter)
        dispatch({
            type: FETCH_RESTAURANTS_SUCCESS,
            payload: response.data,
        })
    } catch (err) {
        dispatch({
            type: FETCH_RESTAURANTS_FAILURE,
            payload: err,
            error: true,
        })
    }
}

const addRestaurant = ({token, restaurant}) => async dispatch => {
    dispatch({type: ADD_RESTAURANT_START})

    try{
        const response = await addRestaurantApi(token, restaurant)
        dispatch({
            type: ADD_RESTAURANT_SUCCESS,
            payload: response,
        })
    } catch (err) {
        dispatch({
            type: ADD_RESTAURANT_FAILURE,
            payload: err,
            error: true,
        })
    }
}

export default {
    fetchRestaurants,
    fetchMenu,
    addRestaurant,
    fetchRestaurantsByFilter
}