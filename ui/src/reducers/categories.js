import {
    ADD_FILTER_SUCCESS,
    FETCH_CATEGORIES_FAILURE,
    FETCH_CATEGORIES_SUCCESS,
    REMOVE_FILTER_SUCCESS,
} from "../actionTypes";
import * as R from "ramda";

const initialState = {
    filters: [],
    categories: []
}

const categoriesReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_CATEGORIES_SUCCESS:
            return {
                filters: state.filters,
                categories: action.payload,
            }
        case ADD_FILTER_SUCCESS:
            return {
                filters: R.uniq(R.append(action.payload, state.filters)),
                categories: state.categories,
            }
        case REMOVE_FILTER_SUCCESS:
            return {
                filters: R.without(R.of(action.payload), state.filters),
                categories: state.categories,
            }
        case FETCH_CATEGORIES_FAILURE:
            return {
                filters: state.filters,
                categories: state.categories,
            }
        default:
            return state
    }
}

export default categoriesReducer