import './Navigation.css';
import Button from "../Button/Button";
import {Link} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";

function Navigation() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn);
    const username = useSelector(state => state.user.user.username);
    const dispatch = useDispatch()

    const handleLogout = (event) => {
        dispatch(allActions.userActions.logoutUser())
        event.preventDefault();
    };

    const getUsername = () => {
        return (<p>{username}</p>)
    }

    return (
        <div className={"navigationBar"}>
            <Link to={{pathname: `/`}}>
                <h1>Fuduś</h1>
            </Link>
            <div className={"loggedInNav"}>
                <Link to={{pathname: `/order`}}>
                    <i className={"bi bi-basket3-fill"} style={{fontSize: 25}}/>
                </Link>
                {isLoggedIn ? (
                    <div>
                        {getUsername}
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