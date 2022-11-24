import './Button.css';

function Button({text, style, onClick}) {
    return (
        <div className={style} onClick={onClick}>
            {text}
        </div>
    );
}

export default Button;