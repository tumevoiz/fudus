import './Search.css';
import SearchBar from "../../components/SearchBar/SearchBar";


function Search() {
  return (
    <div className="Search">
        <div className={ "banner" }>
            <h1>Twoje ulubione jedzenie w okolicy</h1>
            <p>Zamów teraz i otrzymaj zniżkę 20zł na następne zamówienie.
                Skorzystaj z wyszukiwarki, aby znaleźć swoją ulubioną restauracje!</p>
        </div>
        <div className={"searchTop"}>
            <SearchBar/>
        </div>
    </div>
  );
}

export default Search;
