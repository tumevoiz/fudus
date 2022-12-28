import axios from "axios";

const categoriesEndpoint = 'http://localhost:8080/categories'

export const fetchCategories = async () => {
        return await axios.get(categoriesEndpoint);
}