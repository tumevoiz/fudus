import meat_and_chips_img from "./../../meat_and_chips.jpeg"
import './MenuCard.css';
import AddButton from "../../components/AddButton/AddButton";

function MenuCard() {
    return (
        <div className="MenuCard" className={"menuCardStyle"}>
            <img src={meat_and_chips_img} className={"menuCardImg"} alt={"meat and chips"}></img>
            <h2>Stek z pieczonymi ziemniaczkami</h2>
            <div className={"lowerPart"}>
                <div>
                    <p className={"priceTag"}>39 zł</p>
                </div>
                <div>
                    <AddButton></AddButton>
                </div>
            </div>

        </div>
    );
}

export default MenuCard;