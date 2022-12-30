import {PLACE_ORDER_START, PLACE_ORDER_SUCCESS, PLACE_ORDER_FAILURE} from "../actionTypes";
import {placeOrder as placeOrderApi} from "../api/order";

const placeOrder = (order) => async dispatch => {
    try{
        dispatch({type: PLACE_ORDER_START})

        const response = await placeOrderApi(order)
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

export default {
    placeOrder
}