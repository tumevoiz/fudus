import meat_and_chips_img from "./../../meat_and_chips.jpeg"
import './RestaurantCard.css'
import {Link} from "react-router-dom";

function RestaurantCard({name, stars, time}) {
    return (
        <Link to={{
            pathname: "/menu",
        }} >
        <div className={"restaurantCardStyle"}>
            <img src={meat_and_chips_img} className={"restaurantCardImg"} alt={"meat and chips"}/>
            <h2>{name}</h2>
            <div className={"restaurantDescription"}>
                <p className={"starsTag"}>{stars}</p>
                <p className={"timeTag"}>{time}</p>
            </div>
        </div>
        </Link>
    );
}

export default RestaurantCard;