import './Search.css';
import SearchBar from "../../components/SearchBar/SearchBar";


function Search() {
    return (
        <div className="Search">
            <div className={"banner"}>
                <div className={"writing"}>
                    <h1>Twoje ulubione jedzenie w okolicy</h1>
                    <p>Zamów teraz i otrzymaj zniżkę 20zł na następne zamówienie.</p>
                    <p>Skorzystaj z wyszukiwarki, aby znaleźć swoją ulubioną restauracje!</p>
                </div>
                <div className={"searchTop"}>
                    <SearchBar/>
                </div>
            </div>
            <div className={"restaurants"}></div>
        </div>
    );
}

export default Search;
