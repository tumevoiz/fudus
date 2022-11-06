import meat_and_chips_img from "./../meat_and_chips.jpeg"
import './menu-card.css';

function MenuCard() {
    return (
        <div className="MenuCard" className={"menuCardStyle"}>
            <img src={meat_and_chips_img} className={"menuCardImg"} alt={"meat and chips"}></img>
            <p className={"cardFoodName"}>Stek z pieczonymi ziemniaczkami</p>
            <p>39 zł</p>
        </div>
    );
}

export default MenuCard;