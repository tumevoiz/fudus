import './RestaurantCard.css'
import {Link} from "react-router-dom";

function RestaurantCard({restaurant}) {
    console.log('restaurantCard', restaurant)
     const linkParams ={
         pathname: `/menu/${restaurant.name}`,
         state: {restaurant: restaurant},
     }
    return (
        <Link to={linkParams} >
            <div className={"restaurantCardStyle"}>
                <img src={restaurant.img} className={"restaurantCardImg"} alt={"meat and chips"}/>
                <h2>{restaurant.name}</h2>
                <div className={"restaurantDescription"}>
                    <p className={"starsTag"}>{restaurant.stars}</p>
                    <p className={"timeTag"}>{restaurant.deliveryTime}</p>
                </div>
            </div>
        </Link>
    );
}

export default RestaurantCard;