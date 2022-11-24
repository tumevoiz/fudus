import './Button.css';

function Button({text, style, onPress}) {
    return (
        <div className={style} onPress={onPress}>
            {text}
        </div>
    );
}

export default Button;