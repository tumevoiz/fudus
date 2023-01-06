import React, {useState} from 'react';
import './Order.css';
import {useDispatch, useSelector} from "react-redux";
import App from "../App";
import allActions from "../../actions/actions";
import {Redirect, useHistory, useLocation} from "react-router-dom";
import * as R from "ramda";

function Order() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn)
    const user = useSelector(state => state.user.user)
    const token = useSelector(state => state.user.token)
    const itemsInBasket = useSelector(state => state.basket.basket)
    const [isSubmitting, setSubmitting] = useState(false)
    const [errorMsg, setErrorMsg] = useState("")
    const dispatch = useDispatch()
    const history = useHistory()
    const location = useLocation()

    function transformBasketItemToOrder(item) {
        let orderItem = {}
        orderItem.food = item.uuid
        orderItem.amount = item.count
        orderItem.notes = item.notes
        return orderItem
    }

    const handleConfirm = async () => {
        let order = R.map(transformBasketItemToOrder, itemsInBasket);
        console.log(order)
        const errorResponse = await dispatch(allActions.orderActions.placeOrder(token, order))
        if (!errorResponse) {
            dispatch(allActions.basketActions.cleanBasket())
            history.push("/")
        } else {
            setErrorMsg(errorResponse)
        }
    }

    function handleCancel(event) {
        event.preventDefault()
        history.goBack()
    }

    function removeItemFromBasket(basketItem) {
        dispatch(allActions.basketActions.removeMenuItemFromBasket(basketItem.id))
    }

    function addItemToBasket(basketItem) {
        dispatch(allActions.basketActions.addMenuItemToBasket(basketItem))
    }

    let totalPrice = R.reduce((x, y) => x + parseInt(y.count) * parseInt(y.price), 0, itemsInBasket)

    const order = itemsInBasket.map((basketItem, index) => {
        return <div key={index} className={"orderItem shadow-sm"}>
            <div>
                <img src={basketItem.imageBase64}/>
            </div>
            <div className={"orderItemDetails"}>
                <div className={"upperOrderRow"}>
                    <h2>{basketItem.name}</h2>
                    <div className={"countChanger"}>
                        <button onClick={() => removeItemFromBasket(basketItem)}>-</button>
                        <p>{basketItem.count}</p>
                        <button onClick={() => addItemToBasket(basketItem)}>+</button>
                    </div>
                    <h2>{basketItem.price} zł</h2>
                </div>
                <div className={"lowerOrderRow"}>
                    <p>{basketItem.description}</p>
                    <p>{basketItem.notes}</p>
                </div>
            </div>
        </div>
    })
    if (!isLoggedIn) {
        return <Redirect to={{
            pathname: "/login",
            state: {previousLocation: location.pathname}
        }}/>
    } else {
        return (
            <App>
                <div className={"OrderContainer"}>
                    <div className="orderWrapper">
                        <h1>Podsumowanie zamówienia:</h1>
                        {order}
                        <div className={"otherDetailsWrapper"}>
                            <div className={"deliveryAddressWrapper"}>
                                <h3>Adres dostawy:</h3>
                                <p>{user.address}</p>
                                <p>{user.city}</p>
                            </div>
                            <div className={"totalPriceWrapper"}>
                                <div>
                                    <p>Do zapłaty:</p><p className={"value"}>{Math.round(totalPrice)} zł</p>
                                </div>
                                <div>
                                    <p>Rabat: </p><p className={"value"}>-{Math.round(totalPrice * 0.2)} zł</p>
                                </div>
                                <div>
                                    <p>Całość do zapłaty: </p>
                                    <h4 className={"value"}>{Math.round(totalPrice) - Math.round(totalPrice * 0.2)} zł</h4>
                                </div>
                            </div>
                        </div>
                        <p className={"errorMessage"}>{errorMsg}</p>
                        <div className={"orderButtons"}>
                            <button className={"btn btn-dark ActionButtonReversed"} onClick={handleCancel}>Anuluj
                            </button>
                            <button className={"btn btn-dark ActionButtonReversed"}
                                    onClick={handleConfirm}>{isSubmitting ?
                                <div className="spinner-border text-danger" role="status">
                                    <span className="visually-hidden">Loading...</span>
                                </div> : "Zamów"}</button>
                        </div>
                    </div>
                </div>
            </App>
        )
    }
}

export default Order