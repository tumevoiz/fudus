import './Navigation.css';
import Button from "../Button/Button";
import {Link} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import * as R from "ramda";
import {OverlayTrigger, Popover} from "react-bootstrap";

function Navigation() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn);
    const isAdmin = useSelector(state => state.user.user.role);
    const username = useSelector(state => state.user.user.email);
    const basket = useSelector(state => state.basket.basket);
    const dispatch = useDispatch()

    const handleLogout = (event) => {
        dispatch(allActions.userActions.logoutUser())
        event.preventDefault();
    };

    const getUsername = () => {
        return (<p>{username}</p>)
    }

    const handleRedirectToOrder = () => {
        if (R.isEmpty(basket)) {
            return <OverlayTrigger
                trigger="click"
                placement="bottom"
                overlay={
                    <Popover>
                        <Popover.Body>
                            <p>Twój koszyk jest pusty...</p>
                        </Popover.Body>
                    </Popover>
                }
            >
                <button className={"orderBtnEmpty"}>
                    <i className={"bi bi-basket3-fill"} style={{fontSize: 25}}/>
                </button>
            </OverlayTrigger>
        } else {
            return <Link to={{pathname: `/add/order`}}>
                <i className={"bi bi-basket3-fill"} style={{fontSize: 25}}/>
            </Link>
        }
    }

    return (
        <div className={"navigationBar"}>
            <Link to={{pathname: `/`}}>
                <h1>Fuduś</h1>
            </Link>
            <div className={"loggedInNav"}>
                {handleRedirectToOrder()}
                {isLoggedIn && isAdmin &&
                    <div className={"addRestaurantBtnWrapper"}>
                        <Link to={{pathname: `/add/restaurant`}}>
                            <Button text={"Dodaj restaurację"} style={"ActionButton"} component={Link} to="/add/restaurant"/>
                        </Link>
                    </div>
                }
                {isLoggedIn ? (
                    <div className={"actionNavRow"}>
                        <Link to={{pathname: `/orders`}}>
                            <Button text={"Twoje zamówienia"} style={"ActionButton"} component={Link} to="/orders"/>
                        </Link>
                        {getUsername()}
                        <Button text={"Wyloguj"} style={"ActionButton"} onClick={handleLogout}/>
                    </div>
                ) : (
                    <Link to={{pathname: `/Login`}}>
                        <Button text={"Zaloguj"} style={"ActionButton"} component={Link} to="/Login"/>
                    </Link>
                )}
            </div>
        </div>
    );
}

export default Navigation;