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

        const user = await loginUserApi(username, password)
        dispatch({
            type: LOGIN_USER_SUCCESS,
            payload: user,
        })
        console.log('logging user', user)
    } catch (err) {
        dispatch({
            type: LOGIN_USER_FAILURE,
            payload: err,
            error: true,
        })
        console.log("error")
    }
}

const logoutUser = () => dispatch => {
    dispatch({type: LOG_OUT_USER_SUCCESS})
}

const registerUser = (username, password) => async dispatch => {
    try{
        dispatch({type: REGISTER_USER_START})

        const user = await registerUserApi(username, password)
        dispatch({
            type: REGISTER_USER_SUCCESS,
            payload: user,
        })
        console.log('registering user', user)
    } catch (err) {
        dispatch({
            type: REGISTER_USER_FAILURE,
            payload: err,
            error: true,
        })
        console.log("error")
    }
}

export default {
    loginUser,
    logoutUser,
    registerUser
}