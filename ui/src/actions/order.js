import {PLACE_ORDER_START, PLACE_ORDER_SUCCESS, PLACE_ORDER_FAILURE} from "../actionTypes";
import {placeOrder as placeOrderApi} from "../api/order";

const placeOrder = (token, order) => async dispatch => {
    try{
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

export default {
    placeOrder
}