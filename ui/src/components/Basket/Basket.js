import './Basket.css';
import {Link} from "react-router-dom";
import {useSelector} from "react-redux";
import React from 'react';
import * as R from "ramda";
import BasketItem from "./BasketItem";

function Basket({menuItems}) {
    const basketItems = useSelector(state => state.basket.basket);
    const totalPrice = useSelector(state => state.basket.totalPrice);

    return (
        <div className={'Basket'}>
            {!R.isEmpty(basketItems) ? basketItems.map((basketItem, index) => <BasketItem basketItem={basketItem}/>) : <p>Twój koszyk jest pusty.</p>}
            <h1>{totalPrice}</h1>
            {!R.isEmpty(basketItems) ?
                <Link to='/basket'>
                    <button className={"btn btn-dark ActionButtonReversed"}>Przejdź do kasy</button>
                </Link> : ""
            }
        </div>
    );
}

export default Basket;