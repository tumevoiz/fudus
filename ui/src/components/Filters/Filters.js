import './Filters.css';
import React, {useState, useEffect} from "react";
import american  from "./../../assets/icons/american.png";
import mexican  from "./../../assets/icons/mexican.png";
import italian  from "./../../assets/icons/italian.png";
import chinese  from "./../../assets/icons/chinese.png";
import japanese  from "./../../assets/icons/japanese.png";
import burger  from "./../../assets/icons/burger.png";
import sushi  from "./../../assets/icons/sushi.png";
import pizza  from "./../../assets/icons/pizza.png";
import {fetchCategories} from "../../api/categories";
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import * as R from "ramda";

function Filters() {
    const [filterParams, setFiltersParam] = useState([]);
    // const categories = [
    //     {name: 'amerykańska', img: american},
    //     {name: 'meksykańska', img: mexican},
    //     {name: 'włoska', img: italian},
    //     {name: 'chińska', img: chinese},
    //     {name: 'japońska', img: japanese},
    //     {name: 'burger', img: burger},
    //     {name: 'sushi', img: sushi},
    //     {name: 'pizza', img: pizza},
    // ];
    const categories = useSelector(state => state.categories.categories);
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(allActions.categoriesActions.fetchCategories)
        console.log('categories', categories)
    }, [dispatch, categories])

    // let categoriesList = [];
    // categories.forEach((category, index) => {
    //     categoriesList.push()
    // })

    function handleSubmit(event) {
        console.log('Submitted: ' + this.state.value);
        event.preventDefault();
    }

    function addFilter(filter) {
        setFiltersParam(current => [...current, filter]);
    }

    return (
        <div className={"filtersWrapper"}>
            <form onSubmit={handleSubmit}>
                {!R.isEmpty(categories) ? categories.map((category, index) => <div key={index} className={"filterTag"} onClick={() => addFilter(category.name)}><img src={category.img}/><p>{category.name}</p></div>) : <p>Waiting...</p>}
            </form>
        </div>
    );
}

export default Filters;