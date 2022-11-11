import React from 'react';
import ReactDOM from 'react-dom/client';
import {Provider} from 'react-redux'
import store from './store'
import './index.css';
import './assets/fonts/Martel_Sans/MartelSans-Regular.ttf';
import reportWebVitals from './reportWebVitals';
import {createBrowserRouter, RouterProvider} from 'react-router-dom';
import Search from './pages/Search/Search';
import Menu from './pages/Menu/Menu';
import Error from './pages/Error/Error';

const router = createBrowserRouter([
    {
        path: "/",
        element: <Search/>,
        errorElement: <Error/>
    },
    {
        path: "Menu",
        element: <Menu/>,
    },
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Provider store={store}>
        <RouterProvider router={router}/>
    </Provider>
);


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
