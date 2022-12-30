import './Basket.css';
import React from 'react';
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";

function BasketItem({basketItem}) {
    const dispatch = useDispatch()

    const removeItemFromBasket = (event) => {
        event.preventDefault()
        dispatch(allActions.basketActions.removeMenuItemFromBasket(basketItem.id))
    }

    const addItemToBasket = (event) => {
        event.preventDefault()
        dispatch(allActions.basketActions.addMenuItemToBasket(basketItem))
    }

    return (
        <div className={'BasketItem'}>
            <div><img src={basketItem.img}/></div>
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