import './Search.css';
import SearchBar from "../../components/SearchBar/SearchBar";
import Navigation from "../../components/Navigation/Navigation";
import RestaurantCard from "./RestaurantCard";
import Filters from "../../components/Filters/Filters";

function Search() {
    let restaurants = [
        {name: 'PizzaHut', stars: 4.8, time: 30},
        {name: 'Neko', stars: 5.0, time: 25},
        {name: 'Chinkalia', stars: 4.9, time: 20},
        {name: 'PoRzeczka', stars: 4.6, time: 41},
        {name: 'Saray Kebab', stars: 2.7, time: 15},
    ];
    let restaurantList = [];
    restaurants.forEach((restaurant) => {
        // restaurantList.push( <li key={index}>{restaurant}</li>)
        restaurantList.push(<RestaurantCard name={restaurant.name} stars={restaurant.stars} time={restaurant.time}/>)
    })

    return (
        <div className="Search">
            <Navigation />
            <div className={"banner"}>
                <div className={"writing"}>
                    <h1>Twoje ulubione jedzenie w okolicy</h1>
                    <p>Zamów teraz i otrzymaj zniżkę 20zł na następne zamówienie.</p>
                    <p>Skorzystaj z wyszukiwarki, aby znaleźć swoją ulubioną restaurację!</p>
                </div>
                <div className={"searchTop"}>
                    <SearchBar />
                </div>
            </div>
            <Filters />
            <div className={"restaurantsCardsWrapper"}>
                {restaurantList}
            </div>
        </div>
    );
}

export default Search;
