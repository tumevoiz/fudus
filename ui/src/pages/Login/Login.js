import React, {useState} from 'react';
import './Login.css';
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import {ErrorMessage, Field, Form, Formik} from "formik";
import App from "../App";

function Login() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn);
    const dispatch = useDispatch()
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    return (
        <App>
            <div className="login-wrapper">
                <h1>Please Log In</h1>
                <Formik
                    initialValues={{login: '', password: ''}}
                    validate={values => {
                        const errors = {};
                        if (!values.username) {
                            errors.login = 'Required';
                        } else if (
                            !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.username)
                        ) {
                            errors.login = 'Invalid username address';
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
                        <Form className={"LoginForm"}>
                            <div></div>
                            <Field type="text" name="username" onChange={e => setUsername(e.target.value)}/>
                            <ErrorMessage name="username" component="div" onChange={e => setPassword(e.target.value)}/>
                            <div></div>
                            <Field type="password" name="password"/>
                            <ErrorMessage name="password" component="div"/>
                            <button type="submit" disabled={isSubmitting} onClick={async () => {
                                dispatch(allActions.userActions.loginUser(username, password))
                            }}>
                                Log in
                            </button>
                        </Form>
                    )}
                </Formik>
            </div>
        </App>
    )
}

export default Login