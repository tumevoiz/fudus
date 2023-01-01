import {
    ADD_FILTER_SUCCESS,
    FETCH_CATEGORIES_FAILURE,
    FETCH_CATEGORIES_START,
    FETCH_CATEGORIES_SUCCESS,
    REMOVE_FILTER_SUCCESS
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
        console.log('fetch categories', categories)
    } catch (err) {
        dispatch({
            type: FETCH_CATEGORIES_FAILURE,
            payload: err,
            error: true,
        })
        console.log("error")
    }
}

const addFilter = (filter) => dispatch => {
    dispatch({
        type: ADD_FILTER_SUCCESS,
        payload: filter,
    })
}

const removeFilter = (filter) => dispatch => {
    dispatch({
        type: REMOVE_FILTER_SUCCESS,
        payload: filter,
    })
}

export default {
    fetchCategories,
    addFilter,
    removeFilter
}