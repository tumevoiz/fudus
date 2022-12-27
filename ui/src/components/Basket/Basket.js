import './Basket.css';
import {Link} from "react-router-dom";
import {useSelector} from "react-redux";
import React from 'react';
import * as R from "ramda";
import Button from "../Button/Button";
import BasketItem from "./BasketItem";

function Basket({menuItems, totalPrice}) {
    const basketItems = useSelector(state => state.basket);

    return (
        <div className={'Basket'}>
            {!R.isEmpty(basketItems) ? basketItems.basket.map((basketItem, index) => <BasketItem basketItem={basketItem}/>) : <p>Twój koszyk jest pusty.</p>}
            <hr />
            {!R.isEmpty(basketItems) ?
                <Link to='/basket'>
                    <button className={"btn btn-dark ActionButtonReversed"}>Przejdź do kasy</button>
                </Link> : ""
            }
        </div>
    );
}

export default Basket;