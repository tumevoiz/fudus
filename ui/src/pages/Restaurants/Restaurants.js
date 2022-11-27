import React, {useEffect} from 'react';
import './Restaurants.css';
import SearchBar from "../../components/SearchBar/SearchBar";
import Navigation from "../../components/Navigation/Navigation";
import RestaurantCard from "./RestaurantCard";
import Filters from "../../components/Filters/Filters";
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import * as R from 'ramda'

const Restaurants = () => {
    const restaurants = useSelector(state => state.restaurants);
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(allActions.restaurantActions.fetchRestaurants())
        console.log('restaurants', restaurants)
    }, [dispatch])

    return (
        <div className="Search">
            <Navigation/>
            <div className={"banner"}>
                <div className={"writing"}>
                    <h1>Twoje ulubione jedzenie w okolicy</h1>
                    <p>Zamów teraz i otrzymaj zniżkę 20zł na następne zamówienie.</p>
                    <p>Skorzystaj z wyszukiwarki, aby znaleźć swoją ulubioną restaurację!</p>
                </div>
                <div className={"searchTop"}>
                    <SearchBar onSubmit/>
                </div>
            </div>
            <Filters/>
            <div className={"restaurantsCardsWrapper"}>
                {!R.isEmpty(restaurants) ? restaurants.restaurants.map((restaurant, index) => <RestaurantCard key={index} restaurant={restaurant} />): <p>Waiting...</p>}
            </div>
        </div>
    );
}

export default Restaurants;
