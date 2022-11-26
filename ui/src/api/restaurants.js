import restaurants from './mockRestaurants'
import menu from './mockMenu'
import * as R from 'ramda'
import {propEq} from "ramda";

export const fetchRestaurants = async () => {
    return new Promise(resolve => {
        resolve(restaurants)
    })
}

export const fetchMenuByRestaurantId = async (id) => {
    return new Promise(resolve => {
        const menuItems = R.filter(propEq('restaurantId', id), menu)
        resolve(menuItems)
    })
}