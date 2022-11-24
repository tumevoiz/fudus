import './SearchBar.css';

function SearchBar() {
    function handleSubmit(event) {
        console.log('Submitted: ' + this.state.value);
        event.preventDefault();
    }

    function handleChange(event) {
         // this.setState({value: event.target.value});
        console.log('Changed: ' + event.target.value)
    }

    return (
        <div className={"SearchBar"}>
            <form onSubmit={handleSubmit} onChange={handleChange}>
                <input className={"searchBox"} type="text" placeholder="Na co masz ochotę?"/>
            </form>
        </div>
    );
}

export default SearchBar;