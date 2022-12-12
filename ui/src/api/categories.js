import categories from './mockCategories'

export const fetchCategories = async () => {
    return new Promise(resolve => {
        resolve(categories)
    })
}