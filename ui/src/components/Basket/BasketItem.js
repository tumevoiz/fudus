import './Basket.css';
import React from 'react';
import {useDispatch} from "react-redux";
import allActions from "../../actions/actions";

function BasketItem({basketItem}) {
    const dispatch = useDispatch()

    const removeItemFromBasket = (event) => {
        event.preventDefault()
        dispatch(allActions.basketActions.removeMenuItemFromBasket(basketItem.uuid))
    }

    const addItemToBasket = (event) => {
        event.preventDefault()
        dispatch(allActions.basketActions.addMenuItemToBasket(basketItem))
    }

    return (
        <div className={'BasketItem'}>
            <div><img src={`data:image/png;base64,${basketItem.imageBase64}`}/></div>
            <div><p>{basketItem.name}</p></div>
            <div className={"itemsCount"}>
                <p>{basketItem.count}</p>
                <div className="amountButtons">
                    <button type="button" className="btn" onClick={addItemToBasket}>+</button>
                    <button type="button" className="btn" onClick={removeItemFromBasket}>-</button>
                </div>
            </div>
            <div className={"itemPrice"}><p>{basketItem.price * basketItem.count} zł</p></div>
        </div>
    );
}

export default BasketItem;