import {ADD_MENU_ITEM_TO_BASKET, CLEAN_BASKET, REMOVE_MENU_ITEM_FROM_BASKET} from '../actionTypes'

const addMenuItemToBasket = (menuItem) => dispatch => {
    dispatch({
        type: ADD_MENU_ITEM_TO_BASKET,
        payload: menuItem,
    })
}

const removeMenuItemFromBasket = (id) => dispatch => {
    dispatch({
        type: REMOVE_MENU_ITEM_FROM_BASKET,
        payload: id,
    })
}

const cleanBasket = () => dispatch => {
    dispatch({
        type: CLEAN_BASKET,
    })
}

export default {
    addMenuItemToBasket,
    removeMenuItemFromBasket,
    cleanBasket
}
