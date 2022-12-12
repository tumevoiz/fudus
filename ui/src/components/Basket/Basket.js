import './Basket.css';
import {Link} from "react-router-dom";
import {useSelector} from "react-redux";
import React from 'react';
import * as R from "ramda";

function Basket({menuItems, totalPrice}) {
    const basketItems = useSelector(state => state.basket);
    // const isBasketEmpty = R.isEmpty(basketItems)

    return (
        <div className={'Basket'}>
            <Link to='/basket'>
            </Link>
            {!R.isEmpty(basketItems) ? basketItems.basket.map((basketItem, index) => <div
                key={index}><p>{basketItem}</p></div>) : <p>Twój koszyk jest pusty.</p>}
        </div>
    );
}

export default Basket;