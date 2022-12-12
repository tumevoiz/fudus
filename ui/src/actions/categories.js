import {
    FETCH_CATEGORIES_START,
    FETCH_CATEGORIES_SUCCESS,
    FETCH_CATEGORIES_FAILURE
} from '../actionTypes'

import {fetchCategories as fetchCategoriesApi } from "../api/categories";

const fetchCategories = () => async dispatch => {
    dispatch({type: FETCH_CATEGORIES_START})

    try{
        const restaurants = await fetchCategoriesApi()
        dispatch({
            type: FETCH_CATEGORIES_SUCCESS,
            payload: restaurants,
        })
        console.log('fetch restaurants', restaurants)
    } catch (err) {
        dispatch({
            type: FETCH_CATEGORIES_FAILURE,
            payload: err,
            error: true,
        })
        console.log("error")
    }
}

export default {
    fetchCategories,
}