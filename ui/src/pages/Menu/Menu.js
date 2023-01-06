import React, {useEffect} from 'react';
import './Menu.css';
import MenuCard from './MenuCard';
import SearchBar from "../../components/SearchBar/SearchBar";
import App from './../App'
import {useDispatch, useSelector} from "react-redux";

import allActions from "../../actions/actions";
import * as R from "ramda";
import Sidebar from "../../components/Sidebar/Sidebar";

function Menu(props) {
    let restaurant = {}
    if (props.location.state.restaurant) {
        restaurant = props.location.state.restaurant
    }

    const menu = useSelector(state => state.menu);
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(allActions.restaurantActions.fetchMenu(restaurant.slug))
    }, [dispatch])

    return (
        <App>
            <div className='AppColumn'>
                <div className="Menu">
                    <div className={"searchTop"}>
                        <SearchBar/>
                    </div>
                    <div className={"restaurantInfo"}>
                        <div className={"rowUpper"}>
                            <h1>{restaurant.name}</h1>
                            <div>
                                <i className={"bi bi-star"} style={{fontSize: 25}}/><h2>{restaurant.rating}</h2>
                            </div>
                        </div>
                        <div className={"rowLower"}>
                            <p>{restaurant.description}</p>
                        </div>
                    </div>
                    <div className={"menuCardsWrapper"}>
                        {!R.isEmpty(menu) ? menu.menu.map((menuItem, index) => <MenuCard key={index}
                                                                                         menuItem={menuItem}/>) :
                            <p>Waiting...</p>}
                    </div>
                </div>
            </div>
            <div className='SidebarColumn'>
                <Sidebar/>
            </div>
        </App>
    );
}

export default Menu;
