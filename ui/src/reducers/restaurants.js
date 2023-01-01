import {FETCH_RESTAURANTS_SUCCESS} from "../actionTypes";
import * as R from "ramda";

const initialState = {}

function filterFetchedRestaurants(filters, restaurants) {
    console.log('filters in filters restaurants: ' + filters)
    if (R.isEmpty(filters)) {
        return restaurants
    } else {
        const result = R.innerJoin(
            (record, filter) => R.includes(filter.id, record.categories),
            restaurants,
            filters
        );
        console.log(result)
        return result
    }
}

const restaurantsReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_RESTAURANTS_SUCCESS:
            return {
                ...state,
                restaurants: filterFetchedRestaurants(action.payload.filters, action.payload.restaurants),
            }
        default:
            return state
    }
}

export default restaurantsReducer


