import {
    FETCH_CATEGORIES_FAILURE,
    FETCH_CATEGORIES_SUCCESS,
} from "../actionTypes";

const initialState = {
    categories: []
}

const basketReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_CATEGORIES_SUCCESS:
            return {
                ...state,
                categories: action.payload,
            }
        case FETCH_CATEGORIES_FAILURE:
            return {
                ...state,
            }
        default:
            return state
    }
}

export default basketReducer