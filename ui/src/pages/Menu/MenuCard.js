import React from 'react';
import './MenuCard.css';
import Button from "../../components/Button/Button";
import {useState} from "react";

function MenuCard({menu}) {
    const [isAdded, setIsAdded] = useState(false);

    function addToCart() {
        setIsAdded(current => !current);

        console.log("Added to cart!")
    }

    return (
        <div className={"menuCardStyle"}>
            <img src={menu.img} className={"menuCardImg"} alt={"meat and chips"}/>
            <h2>{menu.name}</h2>
            <div className={"lowerPart"}>
                <div>
                    <p className={"priceTag"}>{menu.price} zł</p>
                </div>
                <div>
                    <Button text={isAdded ? "✓" : "+"} style={isAdded ? "AddButton added" :"AddButton"} onClick={addToCart}/>
                </div>
            </div>
        </div>
    );
}

export default MenuCard;