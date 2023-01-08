import {FETCH_ORDERS_SUCCESS, FETCH_ORDERS_FAILURE} from "../actionTypes";
import * as R from "ramda";

const initialState = {
    orders: []
}

const ordersReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_ORDERS_SUCCESS:
            return {
                ...state,
                orders: R.sort(R.descend(R.prop('creationDate')), R.filter(R.propEq('orderedBy', action.payload.userUuid), action.payload.orders))
            }
        case FETCH_ORDERS_FAILURE:
            return {
                ...state,
                orders: state.orders,
            }
        default:
            return state
    }
}

export default ordersReducer


