import {
    FETCH_ORDERS_FAILURE,
    FETCH_ORDERS_START,
    FETCH_ORDERS_SUCCESS,
    PLACE_ORDER_FAILURE,
    PLACE_ORDER_START,
    PLACE_ORDER_SUCCESS
} from "../actionTypes";
import {fetchOrder as fetchOrderApi, placeOrder as placeOrderApi} from "../api/order";

const placeOrder = (token, order) => async dispatch => {
    try {
        dispatch({type: PLACE_ORDER_START})

        let response = await placeOrderApi(token, order)
        dispatch({
            type: PLACE_ORDER_SUCCESS,
            payload: response,
        })

    } catch (err) {
        dispatch({
            type: PLACE_ORDER_FAILURE,
            payload: err,
            error: true,
        })
        return err.message
    }
}

const fetchOrders = (token, userUuid) => async dispatch => {
    try {
        dispatch({type: FETCH_ORDERS_START})

        let response = await fetchOrderApi(token)
        console.log(response)
        dispatch({
            type: FETCH_ORDERS_SUCCESS,
            payload: {userUuid: userUuid, orders: response.data},
        })
    } catch (err) {
        console.log(err)
        dispatch({
            type: FETCH_ORDERS_FAILURE,
            payload: err,
            error: true,
        })
        return err.message
    }
}

export default {
    placeOrder,
    fetchOrders
}