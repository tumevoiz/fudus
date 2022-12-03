import './Basket.css';
import {Link} from "react-router-dom";
import React from 'react';

function BasketItem({basketItem}) {
    return (
        <div className={'Basket'}>
            <Link to='/basket'>
            </Link>
            <div key={index}><p>{basketItem}</p></div>
        </div>
    );
}

export default BasketItem;