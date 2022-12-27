import {ADD_MENU_ITEM_TO_BASKET, REMOVE_MENU_ITEM_FROM_BASKET} from "../actionTypes";
import * as R from "ramda";

const initialState = {
    basket: {}
}

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

const removeItemFromBasket = (id, basket) => {
    console.log(basket)
    if (basket === undefined) {
        return {}
    }

    let foundId = R.findIndex(R.propEq('id', id))(basket)
    console.log('id ' + foundId)
    if (id === -1) {
        return basket
    } else {
        if (basket[foundId].count === 1){
            basket = R.remove(R.lastIndexOf(foundId), 1, basket)
            if (R.isEmpty(basket)){
                return {}
            }
            return basket
        } else {
            basket[foundId].count -= 1
            console.log(basket)
            return basket
        }
    }
}

const basketReducer = (state = initialState, action) => {
    switch (action.type) {
        case ADD_MENU_ITEM_TO_BASKET:
            console.log(state.basket)
            return {
                ...state,
                basket: addItemToBasket(action.payload, state.basket),
    }
        case REMOVE_MENU_ITEM_FROM_BASKET:
            return {
                ...state,
                basket: removeItemFromBasket(action.payload, state.basket),
            }
        default:
            return state
    }
}

export default basketReducer
