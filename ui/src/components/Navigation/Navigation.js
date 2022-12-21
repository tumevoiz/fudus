import './Navigation.css';
import Button from "../Button/Button";
import {Link} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";

function Navigation() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn);
    const username = useSelector(state => state.user.user.username);
    const dispatch = useDispatch()

    const handleClick = (event) => {
        dispatch(allActions.userActions.logoutUser())
        event.preventDefault();
    };

    return (
        <div className={"navigationBar"}>
            <Link to={{pathname: `/`}}>
                <h1>Fuduś</h1>
            </Link>
            {isLoggedIn ? (
                <div>{username}
                    <Button text={"Wyloguj"} style={"ActionButton"} onClick={handleClick}/>
                </div>
            ) : (
                <Link to={{pathname: `/Login`}}>
                    <Button text={"Zaloguj"} style={"ActionButton"} component={Link} to="/Login"/>
                </Link>
            )}
        </div>
    );
}

export default Navigation;