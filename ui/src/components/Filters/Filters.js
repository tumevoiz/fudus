import './Filters.css';
import React, {useState, useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import * as R from "ramda";

function Filters() {
    const [isActive, setActive] = useState(-1);
    const categories = useSelector(state => state.categories.categories);
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(allActions.categoriesActions.fetchCategories())
    }, [dispatch])

    function toggleCategory(index, categoryName) {
        if (index === isActive) {
            setActive(-1)
            dispatch(allActions.restaurantActions.fetchRestaurants())
        } else {
            setActive(index)
            dispatch(allActions.restaurantActions.fetchRestaurantsByFilter(categoryName))
        }
    }

    const createFilterTab = (category, index) => {
        return <div key={index} className={index === isActive? "filterTag active" : "filterTag"} onClick={() => toggleCategory(index, category.name)}>
            <img src={require("./../../assets/icons/" + category.icon + ".png")} alt={"Category icon."}/>
            <p>{category.name}</p>
            {index === isActive? <button type="button" className="btn-close" aria-label="Close"/> : ""}
        </div>
    }
    
    return (
        <div className={"filtersWrapper"}>
            {!R.isEmpty(categories) ? categories.map((category, index) => createFilterTab(category, index)) : <p>Waiting...</p>}
        </div>
    );
}

export default Filters;