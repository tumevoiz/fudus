import React, {useEffect, useState} from 'react';
import './CheckOrders.css';
import {useDispatch, useSelector} from "react-redux";
import App from "../App";
import allActions from "../../actions/actions";
import {Redirect, useHistory, useLocation} from "react-router-dom";
import * as R from "ramda";
import CheckOrdersItem from "./CheckOrderItem";

function CheckOrders() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn)
    const userUuid = useSelector(state => state.user.user.uuid)
    const token = useSelector(state => state.user.token)
    const orders = useSelector(state => state.orders)
    const [errorMsg, setErrorMsg] = useState("")
    const dispatch = useDispatch()
    const history = useHistory()
    const location = useLocation()

    useEffect(() => {
        dispatch(allActions.orderActions.fetchOrders(token, userUuid))
        console.log(orders)
    }, [dispatch])

    if (!isLoggedIn) {
        return <Redirect to={{
            pathname: "/login",
            state: {previousLocation: location.pathname}
        }}/>
    } else {
        return (
            <App>
                <div className={"orderHistoryContainer"}>
                    <div className="orderHistoryWrapper">
                        <h1>Historia zamówień:</h1>
                        {!R.isEmpty(orders) ? orders.orders.map((orderItem, index) => <CheckOrdersItem item={orderItem} key={index}/>) : <p>"Brak zamówień w historii.</p>}
                        <p className={"errorMessage"}>{errorMsg}</p>
                        <div className={"otherDetailsWrapper"}>
                            <div className={"totalPriceWrapper"}>
                            </div>
                        </div>
                        <div className={"orderButtons"}>
                            <button className={"btn btn-dark ActionButtonReversed"} onClick={history.goBack}>Powrót
                            </button>
                        </div>
                    </div>
                </div>
            </App>
        )
    }
}

export default CheckOrders