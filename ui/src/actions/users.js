import {
    LOGIN_USER_START,
    LOGIN_USER_SUCCESS,
    LOGIN_USER_FAILURE
} from '../actionTypes'

import {loginUser as fetchUserApi } from "../api/user";

const loginUser = () => async dispatch => {
    try{
        dispatch({type: LOGIN_USER_START})

        const user = await fetchUserApi()
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

export default {
    loginUser
}