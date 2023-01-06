import './SearchBar.css';
import {useDispatch} from "react-redux";
import allActions from "../../actions/actions";

function SearchBar() {
    const dispatch = useDispatch()

    const handleSubmit = (event) => {
        if(event.keyCode === 13 && event.shiftKey === false) {
            event.preventDefault();
            dispatch(allActions.restaurantActions.fetchRestaurantsByFilter(event.target.value))
        }
    }

    return (
        <form className={"SearchBar"}>
            <input
                type="text"
                className={"searchBox"}
                placeholder="Na co masz ochotę?"
                onKeyDown={handleSubmit}
            />
        </form>
    );
}

export default SearchBar;