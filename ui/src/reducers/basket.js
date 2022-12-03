import {ADD_MENU_ITEM_TO_BASKET, REMOVE_MENU_ITEM_FROM_BASKET} from "../actionTypes";
import * as R from "ramda";

const initialState = {}

const basketReducer = (state = initialState, action) => {
    switch (action.type) {
        case ADD_MENU_ITEM_TO_BASKET:
            return {
                ...state,
                basket: R.append(action.payload, state.basket),
            }
        case REMOVE_MENU_ITEM_FROM_BASKET:
            return {
                ...state,
                basket: R.remove(R.lastIndexOf(action.payload), 1, state.basket),
            }
        default:
            return state
    }
}

export default basketReducer
