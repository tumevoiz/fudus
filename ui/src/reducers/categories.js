import {
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
        default:
            return state
    }
}

export default basketReducer