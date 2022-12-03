import React, {useEffect} from 'react';
import './Menu.css';
import MenuCard from './MenuCard';
import SearchBar from "../../components/SearchBar/SearchBar";
import App from './../App'
import {useDispatch, useSelector} from "react-redux";

import allActions from "../../actions/actions";
import * as R from "ramda";

function Menu(props) {
    let restaurant = {}
    if (props.location.state.restaurant) {
        restaurant = props.location.state.restaurant
    }

    const menu = useSelector(state => state.menu);
    console.log('before render', menu)
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(allActions.restaurantActions.fetchMenuByRestaurantId(restaurant.id))
        console.log('menu', menu)
    }, [dispatch])

    return (
        <App>
            <div className="Menu">
                <div className={"searchTop"}>
                    <SearchBar/>
                </div>
                <div className={"restaurantName"}><h1>{restaurant.name}</h1></div>
                <div className={"menuCardsWrapper"}>
                    {!R.isEmpty(menu) ? menu.menu.map((menuItem, index) => <MenuCard key={index}
                                                                                     menuItem={menuItem}/>) :
                        <p>Waiting...</p>}
                </div>
            </div>
        </App>
    );
}

export default Menu;
