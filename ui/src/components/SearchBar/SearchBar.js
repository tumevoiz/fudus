import './SearchBar.css';
import {useDispatch} from "react-redux";
import allActions from "../../actions/actions";

function SearchBar() {
    const dispatch = useDispatch()

    function handleSubmit(event) {
        console.log('Submitted: ' + this.state.value);
        dispatch(allActions.restaurantActions.fetchRestaurantsByFilter())
        event.preventDefault();
    }

    function handleChange(event) {
         // this.setState({value: event.target.value});
        console.log('Changed: ' + event.target.value)
    }

    return (
        <form className={"SearchBar"} onSubmit={handleSubmit}>
            <input
                onChange={handleChange}
                type="text"
                className={"searchBox"}
                placeholder="Na co masz ochotę?"
            />
        </form>
    );
}

export default SearchBar;