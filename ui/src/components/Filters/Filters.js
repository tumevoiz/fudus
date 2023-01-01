import './Filters.css';
import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import * as R from "ramda";
import Filter from "./Filter";

function Filters() {
    const categories = useSelector(state => state.categories.categories);
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(allActions.categoriesActions.fetchCategories())
        console.log('categories', categories)
    }, [dispatch])

    return (
        <div className={"filtersWrapper"}>
            {!R.isEmpty(categories) ? categories.map((category, index) => <Filter category={category} key={index}/>) : <p>Waiting...</p>}
        </div>
    );
}

export default Filters;