import {ADD_MENU_ITEM_TO_BASKET, REMOVE_MENU_ITEM_FROM_BASKET} from "../actionTypes";
import * as R from "ramda";

const initialState = {}

const addItemToBasket = (menuItem, basket) => {
    console.log(basket)
    if (basket === undefined) {
        menuItem.count = 1
        return R.append(menuItem, basket)
    }

    let id = R.findIndex(R.propEq('id', menuItem.id))(basket)
    console.log('id' + id)
    if (id !== -1) {
        basket[id].count += 1
        console.log(basket)
        return basket
    } else {
        menuItem.count = 1
        console.log(menuItem)
        return R.append(menuItem, basket)
    }
}

const basketReducer = (state = initialState, action) => {
    switch (action.type) {
        case ADD_MENU_ITEM_TO_BASKET:
            console.log(state.basket)
            return {
                ...state,
                // basket: R.append(action.payload, state.basket),
                basket: addItemToBasket(action.payload, state.basket),
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
