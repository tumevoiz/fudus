import React, {useState} from 'react';
import './MenuCard.css';
import Button from "../../components/Button/Button";
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
    }

    return (
        <div className={"menuCardStyle"}>
            <div>
                <img src={`data:image/png;base64,${menuItem.imageBase64}`} className={"menuCardImg"} alt={"food menu"}/>
            </div>
            <div>
                <h2>{menuItem.name}</h2>
            </div>
            <div className={"lowerPart"}>
                <div>
                    <p className={"priceTag"}>{menuItem.price} zł</p>
                </div>
                <div>
                    <Button text={isAdded ? "✓" : "+"} style={isAdded ? "AddButton added" : "AddButton"} onClick={addToCart}/>
                </div>
            </div>
        </div>
    );
}

export default MenuCard;