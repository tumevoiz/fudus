import {
    FETCH_RESTAURANTS_START,
    FETCH_RESTAURANTS_SUCCESS,
    FETCH_RESTAURANTS_FAILURE,
    FETCH_MENU_START,
    FETCH_MENU_SUCCESS,
    FETCH_MENU_FAILURE
} from '../actionTypes'

import {fetchRestaurants as fetchRestaurantsApi } from "../api/restaurants";
import {fetchMenu as fetchMenuApi } from "../api/restaurants";

const fetchRestaurants = (filters) => async dispatch => {
    dispatch({type: FETCH_RESTAURANTS_START})

    try{
        const response = await fetchRestaurantsApi()
        dispatch({
            type: FETCH_RESTAURANTS_SUCCESS,
            payload: {filters: filters, restaurants: response},
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
            payload: response,
        })
    } catch (err) {
        dispatch({
            type: FETCH_MENU_FAILURE,
            payload: err,
            error: true,
        })
    }
}

export default {
    fetchRestaurants,
    fetchMenu,
}