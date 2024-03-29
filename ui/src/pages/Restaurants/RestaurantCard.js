import './RestaurantCard.css'
import {Link} from "react-router-dom"
import starIcon from './../../assets/icons/star.png'

function RestaurantCard({restaurant}) {
    const linkParams = {
        pathname: `/menu/${restaurant.slug}`,
        state: {restaurant: restaurant},
    }

    return (
        <Link to={linkParams}>
            <div className={"restaurantCardStyle"}>
                <img src={`data:image/png;base64,${restaurant.imageBase64}`} className={"restaurantCardImg"} alt={"Restaurant exemplary food"}/>
                <div className={"restaurantDetails"}>
                    <div>
                <h2>{restaurant.name}</h2>
                    </div>
                <div>
                    <img src={starIcon} alt={"stars icon"}/><p className={"starsTag"}>{restaurant.rating}</p>
                </div>
                </div>
                <div className={"restaurantDescription"}>
                    <p>{restaurant.description}</p>
                </div>
            </div>
        </Link>
    );
}

export default RestaurantCard;