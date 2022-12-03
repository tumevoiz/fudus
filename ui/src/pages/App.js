import React from 'react'

import Sidebar from './../components/Sidebar/Sidebar'
import Navigation from "../components/Navigation/Navigation";
import './App.css'

const App = ({children}) => (
    <div className='AppContainer'>
        <div className={'NavRow'}>
            <Navigation/>
        </div>
        <div className='AppRow'>
            <div className='AppColumn'>
                {children}
            </div>
            <div className='SidebarColumn'>
                <Sidebar/>
            </div>
        </div>
    </div>
)

export default App