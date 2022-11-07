import './Menu.css';
import MenuCard from './MenuCard';
import SearchBar from "../../components/SearchBar/SearchBar";

let items=['Item 1','Item 2','Item 3','Item 4','Item 5'];
let itemList=[];
items.forEach((item,index)=>{
    // itemList.push( <li key={index}>{item}</li>)
    itemList.push( <MenuCard key={index}>{item}</MenuCard>)
})

function Menu() {
  return (
    <div className="Menu">
        <div className={"searchTop"}>
            <SearchBar/>
        </div>
        <div className={"mainContainer"}>
            {itemList}
        </div>
    </div>
  );
}

export default Menu;
