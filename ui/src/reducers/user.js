import {LOGIN_USER_SUCCESS, LOG_OUT_USER_SUCCESS, LOGIN_USER_FAILURE} from "../actionTypes";

const initialState = {
    user: {},
    isLoggedIn: false
}

const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case LOGIN_USER_SUCCESS:
            return {
                ...state,
                user: action.payload,
                isLoggedIn: true
            }
        case LOGIN_USER_FAILURE:
            return {
                ...state,
                user: {},
                isLoggedIn: false,
            }
        case LOG_OUT_USER_SUCCESS:
            return {
                ...state,
                user: {},
                isLoggedIn: false
            }
        default:
            return state
    }
}

export default userReducer


