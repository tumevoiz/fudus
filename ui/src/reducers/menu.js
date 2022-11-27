import {FETCH_MENU_SUCCESS} from "../actionTypes";

const initialState = {}

const menuReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_MENU_SUCCESS:
            return {
                ...state,
                menu: action.payload,
            }
        default:
            return state
    }
}

export default menuReducer


