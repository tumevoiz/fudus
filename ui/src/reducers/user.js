import {LOG_OUT_USER_SUCCESS, LOGIN_USER_FAILURE, LOGIN_USER_SUCCESS} from "../actionTypes";

const initialState = {
    user: {},
    token: "",
    isAdmin: false,
    isLoggedIn: false
}

const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case LOGIN_USER_SUCCESS:
            return {
                ...state,
                user: action.payload.customer,
                token: action.payload.token,
                isAdmin: action.payload.customer.role === "Admin",
                isLoggedIn: true,
            }
        case LOGIN_USER_FAILURE:
            return {
                ...state,
                user: {},
                token: "",
                isAdmin: false,
                isLoggedIn: false,
            }
        case LOG_OUT_USER_SUCCESS:
            return {
                ...state,
                user: {},
                token: "",
                isAdmin: false,
                isLoggedIn: false,
            }
        default:
            return state
    }
}

export default userReducer


