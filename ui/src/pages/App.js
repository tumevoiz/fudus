import React from 'react'

import Navigation from "../components/Navigation/Navigation";
import './App.css'

const App = ({children}) => (
    <div className='AppContainer'>
        <div className={'NavRow'}>
            <Navigation/>
        </div>
        <div className='AppRow'>
            {children}
        </div>
    </div>
)

export default App