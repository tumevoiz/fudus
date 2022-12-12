import {ADD_MENU_ITEM_TO_BASKET, REMOVE_MENU_ITEM_FROM_BASKET} from '../actionTypes'

const addMenuItemToBasket = (id) => dispatch => {
    dispatch({
        type: ADD_MENU_ITEM_TO_BASKET,
        payload: id,
    })
}

const removeMenuItemFromBasket = (id) => dispatch => {
    dispatch({
        type: REMOVE_MENU_ITEM_FROM_BASKET,
        payload: id,
    })
}

export default {
    addMenuItemToBasket,
    removeMenuItemFromBasket
}