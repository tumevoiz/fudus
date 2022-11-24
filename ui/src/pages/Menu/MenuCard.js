import meat_and_chips_img from "./../../meat_and_chips.jpeg"
import './MenuCard.css';
import Button from "../../components/Button/Button";
import {useState} from "react";

function MenuCard() {
    const [isAdded, setIsAdded] = useState(false);

    function addToCart() {
        setIsAdded(current => !current);

        console.log("Added to cart!")
    }

    return (
        <div className={"menuCardStyle"}>
            <img src={meat_and_chips_img} className={"menuCardImg"} alt={"meat and chips"}/>
            <h2>Stek z pieczonymi ziemniaczkami</h2>
            <div className={"lowerPart"}>
                <div>
                    <p className={"priceTag"}>39 zł</p>
                </div>
                <div>
                    <Button text={isAdded ? "✓" : "+"} style={isAdded ? "AddButton added" :"AddButton"} onClick={addToCart}/>
                </div>
            </div>
        </div>
    );
}

export default MenuCard;