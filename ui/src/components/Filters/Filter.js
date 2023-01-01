import './Filters.css';
import React, {useState} from "react";
import {useDispatch} from "react-redux";
import allActions from "../../actions/actions";

function Filter({category, index}) {
    const [isActive, setActive] = useState(false);
    const dispatch = useDispatch()

    function toggleCategory() {
        console.log(isActive)
        if (isActive) {
            setActive(false)
            dispatch(allActions.categoriesActions.removeFilter(category))
        } else {
            setActive(true)
            dispatch(allActions.categoriesActions.addFilter(category))
        }
    }

    return (
        <div key={index} className={isActive? "filterTag active" : "filterTag"} onClick={toggleCategory}>
            <img src={require("./../../assets/icons/" + category.icon + ".png")}/>
            <p>{category.name}</p>
            {isActive? <button type="button" className="btn-close" aria-label="Close"/> : ""}
        </div>
    )
}

export default Filter