import React, {useState} from 'react';
import './Register.css';
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import {ErrorMessage, Field, Form, Formik} from "formik";
import Button from "../../components/Button/Button";
import {Redirect} from "react-router-dom";

function Register() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn);
    const dispatch = useDispatch()
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    if (isLoggedIn) {
        return <Redirect to={"/"}/>
    }
    return (
        <div className={"RegisterContainer"}>
            <div className="Register-wrapper">
                <h1>Zaloguj się</h1>
                <Formik
                    initialValues={{Register: '', password: ''}}
                    validate={values => {
                        const errors = {};
                        if (!values.username) {
                            errors.Register = 'Required';
                        } else if (
                            !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.username)
                        ) {
                            errors.Register = 'Invalid username address';
                        }
                        return errors;
                    }}
                    onSubmit={(values, {setSubmitting}) => {
                        setTimeout(() => {
                            alert(JSON.stringify(values, null, 2));
                            setSubmitting(false);
                        }, 400);
                    }}
                >
                    {({isSubmitting}) => (
                        <Form className={"RegisterForm"}>
                            <Field type="text" name="username" onChange={e => setUsername(e.target.value)}/>
                            <ErrorMessage name="username" component="div" onChange={e => setPassword(e.target.value)}/>
                            <Field type="password" name="password"/>
                            <ErrorMessage name="password" component="div"/>
                            <Button text="Zaloguj" style={'ActionButtonReversed'} onClick={async () => {
                                dispatch(allActions.userActions.RegisterUser(username, password))
                            }}>
                            </Button>
                        </Form>
                    )}
                </Formik>
            </div>
        </div>
    )
}

export default Register