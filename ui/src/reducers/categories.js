import {
    FETCH_CATEGORIES_FAILURE,
    FETCH_CATEGORIES_SUCCESS,
} from "../actionTypes";

const initialState = {
    categories: []
}

const categoriesReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_CATEGORIES_SUCCESS:
            return {
                categories: action.payload,
            }
        case FETCH_CATEGORIES_FAILURE:
            return {
                categories: state.categories,
            }
        default:
            return state
    }
}

export default categoriesReducer