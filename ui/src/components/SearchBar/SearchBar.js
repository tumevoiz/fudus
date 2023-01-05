import './SearchBar.css';
import {useDispatch} from "react-redux";
import allActions from "../../actions/actions";

function SearchBar() {
    const dispatch = useDispatch()

    const handleSubmit = (event) => {
        if(event.keyCode === 13 && event.shiftKey === false) {
            event.preventDefault();
            console.log(event.target.values)
            dispatch(allActions.restaurantActions.fetchRestaurantsByFilter(event.target.value))
        }
    }

    function handleChange(event) {
        console.log('Changed: ' + event.target.value)
    }

    return (
        <form className={"SearchBar"}>
            <input
                onChange={handleChange}
                type="text"
                className={"searchBox"}
                placeholder="Na co masz ochotę?"
                onKeyDown={handleSubmit}
            />
        </form>
    );
}

export default SearchBar;