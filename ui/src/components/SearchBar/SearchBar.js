import './SearchBar.css';

function SearchBar() {
    return (
        <div className={"SearchBar"}>
            <input className={"searchBox"} type="text" placeholder="Na co masz ochotę?"/>
        </div>
    );
}

export default SearchBar;