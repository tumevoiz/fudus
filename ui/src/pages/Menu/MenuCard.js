import React from 'react';
import './MenuCard.css';
import Button from "../../components/Button/Button";
import {useState} from "react";
import allActions from "../../actions/actions";
import {useDispatch} from "react-redux";

function MenuCard({menuItem}) {
    const [isAdded, setIsAdded] = useState(false);
    const dispatch = useDispatch()

    function addToCart() {
        setIsAdded(current => !current);
        if (isAdded) {
            dispatch(allActions.basketActions.removeMenuItemFromBasket(menuItem.id))
        } else {
            dispatch(allActions.basketActions.addMenuItemToBasket(menuItem))
        }

        console.log("Added to cart!")
    }

    return (
        <div className={"menuCardStyle"}>
            <div>
                <img src={menuItem.img} className={"menuCardImg"} alt={"food menu"}/>
            </div>
            <div>
                <h2>{menuItem.name}</h2>
            </div>
            <div className={"lowerPart"}>
                <div>
                    <p className={"priceTag"}>{menuItem.price} zł</p>
                </div>
                <div>
                    <Button text={isAdded ? "✓" : "+"} style={isAdded ? "AddButton added" :"AddButton"} onClick={addToCart}/>
                </div>
            </div>
        </div>
    );
}

export default MenuCard;