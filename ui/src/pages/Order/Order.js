import React from 'react';
import './Order.css';
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import {ErrorMessage, Field, Form, Formik} from "formik";
import {Link, Redirect, useLocation} from "react-router-dom";
import App from "../App";

function Order() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn);
    const itemsInBasket = useSelector(state => state.basket.basket);
    const dispatch = useDispatch()
    const location = useLocation()

    return (
        <App>
            <div className={"OrderContainer"}>
                <div className="order-wrapper">
                    
                </div>
            </div>
        </App>
    )
}

export default Order