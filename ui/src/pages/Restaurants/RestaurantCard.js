import './RestaurantCard.css'
import {Link} from "react-router-dom"
import starIcon from './../../assets/icons/star.png'
import timeIcon from './../../assets/icons/time.png'

function RestaurantCard({restaurant}) {
    const linkParams = {
        pathname: `/menu/${restaurant.name}`,
        state: {restaurant: restaurant},
    }

    function convertTime(deliveryTime) {
        if (deliveryTime > 60) {
            return (deliveryTime / 60) + " h " + deliveryTime % 60 + " min"
        }
        return deliveryTime + " min"
    }

    return (
        <Link to={linkParams}>
            <div className={"restaurantCardStyle"}>
                <img src={restaurant.img} className={"restaurantCardImg"} alt={"meat and chips"}/>
                <h2>{restaurant.name}</h2>
                <div className={"restaurantDescription"}>
                </div>
                <div className={"restaurantDetails"}>
                    <div>
                        <img src={starIcon}/><p className={"starsTag"}>{restaurant.rating}</p>
                    </div>
                    <div>
                        <img src={timeIcon}/><p className={"timeTag"}>{convertTime(restaurant.deliveryTime)}</p>
                    </div>
                </div>
            </div>
        </Link>
    );
}

export default RestaurantCard;