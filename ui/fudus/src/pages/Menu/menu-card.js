import meat_and_chips_img from "./../../meat_and_chips.jpeg"
import './menu-card.css';

function MenuCard() {
    return (
        <div className="MenuCard" className={"menuCardStyle"}>
            <img src={meat_and_chips_img} className={"menuCardImg"} alt={"meat and chips"}></img>
            <h2>Stek z pieczonymi ziemniaczkami</h2>
            <p className={"cardFoodName"}>39 zł</p>
        </div>
    );
}

export default MenuCard;