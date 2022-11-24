import './Filters.css';
import {useState} from "react";
import american  from "./../../assets/icons/american.png";
import mexican  from "./../../assets/icons/mexican.png";
import italian  from "./../../assets/icons/italian.png";
import chinese  from "./../../assets/icons/chinese.png";
import japanese  from "./../../assets/icons/japanese.png";
import burger  from "./../../assets/icons/burger.png";
import sushi  from "./../../assets/icons/sushi.png";
import pizza  from "./../../assets/icons/pizza.png";

function Filters() {
    const [filterParams, setFiltersParam] = useState([]);
    const categories = [
        {name: 'amerykańska', img: american},
        {name: 'meksykańska', img: mexican},
        {name: 'włoska', img: italian},
        {name: 'chińska', img: chinese},
        {name: 'japońska', img: japanese},
        {name: 'burger', img: burger},
        {name: 'sushi', img: sushi},
        {name: 'pizza', img: pizza},
    ];
    let categoriesList = [];
    categories.forEach((category) => {
        // restaurantList.push( <li key={index}>{restaurant}</li>)
        //restaurantList.push(<RestaurantCard name={restaurant.name} stars={restaurant.stars} time={restaurant.time}></RestaurantCard>)
        categoriesList.push(<div className={"filterTag"} onClick={() => addFilter(category.name)}><img src={category.img}/><p>{category.name}</p></div>)
    })

    function handleSubmit(event) {
        console.log('Submitted: ' + this.state.value);
        event.preventDefault();
    }

    function addFilter(filter) {
        setFiltersParam(current => [...current, filter]);
    }

    return (
        <div className={"filtersWrapper"}>
            <form onSubmit={handleSubmit}>
                {categoriesList}
            </form>
        </div>
    );
}

export default Filters;