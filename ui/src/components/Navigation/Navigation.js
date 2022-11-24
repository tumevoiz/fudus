import './Navigation.css';
import Button from "../Button/Button";

function Navigation() {
    return (
        <div className={"navigationBar"}>
            <h1>Fuduś</h1>
            <Button text={"Zaloguj się"} style={"ActionButton"} onPress={null}/>
        </div>
    );
}

export default Navigation;