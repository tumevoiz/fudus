import {FETCH_RESTAURANTS_SUCCESS} from "../actionTypes";

const initialState = {}

const restaurantsReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_RESTAURANTS_SUCCESS:
            return {
                ...state,
                restaurants: action.payload,
            }
        default:
            return state
    }
}

export default restaurantsReducer


