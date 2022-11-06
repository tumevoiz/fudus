import './App.css';
import MenuCard from './pages/Menu/menu-card';

let items=['Item 1','Item 2','Item 3','Item 4','Item 5'];
let itemList=[];
items.forEach((item,index)=>{
    // itemList.push( <li key={index}>{item}</li>)
    itemList.push( <MenuCard key={index}>{item}</MenuCard>)
})

function App() {
  return (
    <div className="App">
      <header className="App-header">
          {itemList}
      </header>
    </div>
  );
}

export default App;
