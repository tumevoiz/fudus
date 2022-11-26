import {
    FETCH_USER_START,
    FETCH_USER_SUCCESS,
    FETCH_USER_FAILURE
} from '../actionTypes'

import {fetchUser as fetchUserApi } from "../api/user";

const fetchUser = () => async dispatch => {
    try{
        dispatch({type: FETCH_USER_START})

        const user = await fetchUserApi()
        dispatch({
            type: FETCH_USER_SUCCESS,
            payload: user,
        })
        console.log('fetch user', user)
    } catch (err) {
        dispatch({
            type: FETCH_USER_FAILURE,
            payload: err,
            error: true,
        })
        console.log("error")
    }
}

export default {
    fetchUser
}