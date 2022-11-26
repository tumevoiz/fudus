import React from 'react';
import ReactDOM from 'react-dom/client';
import {Provider} from 'react-redux'
import './index.css';
import './assets/fonts/Martel_Sans/MartelSans-Regular.ttf';
import reportWebVitals from './reportWebVitals';
// Pages
import Restaurants from "./pages/Restaurants/Restaurants";
import Menu from './pages/Menu/Menu';
import {createBrowserHistory} from "history";
import {ConnectedRouter, connectRouter, routerMiddleware} from "connected-react-router";
import {configureStore} from "@reduxjs/toolkit";
import {
    Switch,
    Route,
} from "react-router-dom";
import thunk from "redux-thunk";
import userReducer from "./reducers/user";
import restaurantsReducer from "./reducers/restaurants";
import menuReducer from "./reducers/menu";

const history = createBrowserHistory()

const rootReducer = (history) => ({
    user: userReducer,
    restaurants: restaurantsReducer,
    menu: menuReducer,
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
        <Route path='/' component={Restaurants} exact />
        <Route path='/menu/:restaurantSlug' component={Menu} />
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
