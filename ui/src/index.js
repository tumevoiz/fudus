import React from 'react';
import ReactDOM from 'react-dom/client';
import {Provider} from 'react-redux'
import './index.css';
import './assets/fonts/Martel_Sans/MartelSans-Regular.ttf';
import "bootstrap/dist/css/bootstrap.min.css";
import 'bootstrap-icons/font/bootstrap-icons.css';
// Pages
import Restaurants from "./pages/Restaurants/Restaurants";
import Menu from './pages/Menu/Menu';
import Login from './pages/Login/Login';
import Register from './pages/Register/Register';
import Order from "./pages/Order/Order";
import Error from "./pages/Error/Error";
import AddRestaurant from "./pages/AddRestaurant/AddRestaurant";
import {createBrowserHistory} from "history";
import {ConnectedRouter, connectRouter, routerMiddleware} from "connected-react-router";
import {configureStore} from "@reduxjs/toolkit";
import {Route, Switch,} from "react-router-dom";
import thunk from "redux-thunk";
import userReducer from "./reducers/user";
import restaurantsReducer from "./reducers/restaurants";
import menuReducer from "./reducers/menu";
import basketReducer from "./reducers/basket";
import categoriesReducer from "./reducers/categories";

const history = createBrowserHistory()

const rootReducer = (history) => ({
    user: userReducer,
    restaurants: restaurantsReducer,
    menu: menuReducer,
    basket: basketReducer,
    categories: categoriesReducer,
    router: connectRouter(history)
});

const preloadedState = {};
export const store = configureStore({
    middleware: [thunk, routerMiddleware(history)],
    reducer: rootReducer(history),
    preloadedState,
});

const routes = (
    <Switch>
        <Route path='/' component={Restaurants} exact/>
        <Route path='/menu/:restaurantSlug' component={Menu}/>
        <Route path='/login' component={Login}/>
        <Route path='/register' component={Register}/>
        <Route path='/order' component={Order}/>
        <Route path='/add/restaurant' component={AddRestaurant}/>
        <Route path='*' component={Error} />
    </Switch>
)

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Provider store={store}>
        <ConnectedRouter history={history}>
            {routes}
        </ConnectedRouter>
    </Provider>
);
