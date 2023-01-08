import React from 'react';
import './CheckOrders.css';

function CheckOrdersItem(item, index) {
    console.log(item)
    return (
        <div key={index} className={"orderItem shadow-sm"}>
            <div>
                <p>Data: </p><p>{item.item.creationDate}</p>
            </div>
            <div>
                <p>Opłacone: </p><p>{item.item.hasPaid ? "Tak" : "Nie"}</p>
            </div>
        </div>
    )
}

export default CheckOrdersItem