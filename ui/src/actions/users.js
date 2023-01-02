import {
    LOGIN_USER_START,
    LOGIN_USER_SUCCESS,
    LOGIN_USER_FAILURE,
    LOG_OUT_USER_SUCCESS,
    REGISTER_USER_START,
    REGISTER_USER_SUCCESS,
    REGISTER_USER_FAILURE
} from '../actionTypes'

import {loginUser as loginUserApi, registerUser as registerUserApi} from "../api/user";

const loginUser = (username, password) => async dispatch => {
    try{
        dispatch({type: LOGIN_USER_START})
        const response = await loginUserApi(username, password)
        dispatch({
            type: LOGIN_USER_SUCCESS,
            payload: response.data,
            error: false,
        })
        return
    } catch (err) {
        dispatch({
            type: LOGIN_USER_FAILURE,
            payload: err,
            error: true,
        })
        return Error("Ups... Logowanie nie powiodło sie. Sprawdź dane i spróbuj ponownie miau")
    }
}

const logoutUser = () => dispatch => {
    dispatch({type: LOG_OUT_USER_SUCCESS})
}

const registerUser = (username, password, email, address, city) => async dispatch => {
    try{
        dispatch({type: REGISTER_USER_START})

        const response = await registerUserApi(username, password, email, address, city)
        dispatch({
            type: REGISTER_USER_SUCCESS,
            payload: response,
        })

    } catch (err) {
        dispatch({
            type: REGISTER_USER_FAILURE,
            payload: err,
            error: true,
        })
        return Error("Ups... Logowanie nie powiodło sie. Sprawdź dane i spróbuj ponownie miau")
    }
}

export default {
    loginUser,
    logoutUser,
    registerUser
}