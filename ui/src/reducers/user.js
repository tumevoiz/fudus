import {FETCH_USER_SUCCESS, LOG_OUT_USER_SUCCESS} from "../actionTypes";

const initialState = {}

const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_USER_SUCCESS:
            return {
                ...state,
                user: action.payload,
                loggedIn: true
            }
        case LOG_OUT_USER_SUCCESS:
            return {
                ...state,
                user: {},
                loggedIn: false
            }
        default:
            return state
    }
}

export default userReducer


