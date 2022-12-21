import {
    LOGIN_USER_START,
    LOGIN_USER_SUCCESS,
    LOGIN_USER_FAILURE,
    LOG_OUT_USER_SUCCESS
} from '../actionTypes'

import {loginUser as fetchUserApi } from "../api/user";

const loginUser = (username, password) => async dispatch => {
    try{
        dispatch({type: LOGIN_USER_START})

        const user = await fetchUserApi(username, password)
        dispatch({
            type: LOGIN_USER_SUCCESS,
            payload: user,
        })
        console.log('fetch user', user)
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

export default {
    loginUser,
    logoutUser
}