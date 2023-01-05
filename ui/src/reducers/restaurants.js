import {FETCH_RESTAURANTS_FAILURE, FETCH_RESTAURANTS_SUCCESS} from "../actionTypes";

const initialState = {
    restaurants: []
}

const restaurantsReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_RESTAURANTS_SUCCESS:
            return {
                ...state,
                restaurants: action.payload,
            }
        case FETCH_RESTAURANTS_FAILURE:
            return {
                ...state,
                restaurants: state.restaurants,
            }
        default:
            return state
    }
}

export default restaurantsReducer


