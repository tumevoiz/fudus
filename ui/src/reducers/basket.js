import {ADD_MENU_ITEM_TO_BASKET, CLEAN_BASKET, REMOVE_MENU_ITEM_FROM_BASKET} from "../actionTypes";
import * as R from "ramda";

const initialState = {
    basket: []
}

const addItemToBasket = (menuItem, basket) => {
    if (basket === undefined) {
        menuItem.count = 1
        return R.append(menuItem, basket)
    }

    let id = R.findIndex(R.propEq('id', menuItem.id))(basket)
    if (id !== -1) {
        let oldCount = basket[id].count
        const newMenuItem = { ...menuItem };
        basket = R.remove(id, 1, basket)
        newMenuItem.count = oldCount+1
        basket.splice(id, 0, newMenuItem);
        return basket
    } else {
        menuItem.count = 1
        menuItem.notes = ""
        return R.append(menuItem, basket)
    }
}

const removeItemFromBasket = (id, basket) => {
    if (basket === undefined) {
        return []
    }
    let foundId = R.findIndex(R.propEq('id', id))(basket)
    if (id === -1) {
        return basket
    } else {
        let oldCount = basket[foundId].count
        if (basket[foundId].count === 1) {
            basket = R.remove(R.lastIndexOf(foundId), 1, basket)
            if (R.isEmpty(basket)) {
                return []
            }
            return basket
        } else {
            let menuItem = basket[foundId]
            let newMenuItemToRemove = { ...menuItem };
            basket = R.remove(foundId, 1, basket)
            newMenuItemToRemove.count = oldCount-1
            basket.splice(foundId, 0, newMenuItemToRemove);
            return basket
        }
    }
}

const basketReducer = (state = initialState, action) => {
    switch (action.type) {
        case ADD_MENU_ITEM_TO_BASKET:
            return {
                basket: addItemToBasket(action.payload, state.basket),
            }
        case REMOVE_MENU_ITEM_FROM_BASKET:
            return {
                basket: removeItemFromBasket(action.payload, state.basket),
            }
        case CLEAN_BASKET:
            return {
                basket: [],
            }
        default:
            return state
    }
}

export default basketReducer
