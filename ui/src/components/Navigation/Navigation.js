import './Navigation.css';
import Button from "../Button/Button";
import {Link} from "react-router-dom";
import {useSelector} from "react-redux";

function Navigation() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn);
    const username = useSelector(state => state.user.user.username);

    return (
        <div className={"navigationBar"}>
            <Link to={{pathname: `/`}}>
                <h1>Fuduś</h1>
            </Link>
            {isLoggedIn ? (
                <div>{username}</div>
            ) : (
                <Link to={{pathname: `/Login`}}>
                    <Button text={"Zaloguj się"} style={"ActionButton"} component={Link} to="/Login"/>
                </Link>
            )}
        </div>
    );
}

export default Navigation;