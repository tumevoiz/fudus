import {
    FETCH_CATEGORIES_FAILURE,
    FETCH_CATEGORIES_START,
    FETCH_CATEGORIES_SUCCESS,
} from '../actionTypes'

import {fetchCategories as fetchCategoriesApi} from "../api/categories";

const fetchCategories = () => async dispatch => {
    dispatch({type: FETCH_CATEGORIES_START})

    try {
        const categories = await fetchCategoriesApi()
        dispatch({
            type: FETCH_CATEGORIES_SUCCESS,
            payload: categories.data,
        })
    } catch (err) {
        dispatch({
            type: FETCH_CATEGORIES_FAILURE,
            payload: err,
            error: true,
        })
    }
}

export default {
    fetchCategories
}