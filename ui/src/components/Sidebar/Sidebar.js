import './Sidebar.css';
import Basket from "../Basket/Basket";

function Sidebar() {
    return (
        <div className={'Sidebar'}>
            <h2>Twoje zamówienie</h2>
            <Basket />
        </div>
    );
}

export default Sidebar;