import React from 'react';
import './Login.css';
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import {ErrorMessage, Field, Form, Formik} from "formik";
import {Redirect} from "react-router-dom";

function Login() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn);
    const dispatch = useDispatch()

    if (isLoggedIn) {
        return <Redirect to={"/"}/>
    }
    return (
        <div className={"LoginContainer"}>
            <div className="login-wrapper">
                <h1>Zaloguj się</h1>
                <Formik
                    initialValues={{username: '', password: ''}}
                    validate={values => {
                        const errors = {};
                        if (!values.username) {
                            errors.username = 'Required';
                        }
                        if (!values.password) {
                            errors.password = 'Required';
                        } else if (
                            !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/i.test(values.password)
                        ) {
                            errors.password = 'Invalid password';
                        }
                        return errors;
                    }}
                    onSubmit = { async (values, {setSubmitting}) => {
                        dispatch(allActions.userActions.loginUser(values.username, values.password))
                        setTimeout(() => {
                            console.log(JSON.stringify(values, null, 2));
                            setSubmitting(false);
                        }, 400);
                    }}
                >
                    {({isSubmitting}) => (
                        <Form className={"LoginForm"}>
                            <div className={"mb-3"}>
                                <label>Login:</label>
                                <Field type="text" name="username" className={"form-control"} />
                                <ErrorMessage className={"errorMessage"} name="username" component="div" />
                            </div>
                            <div className={"mb-3"}>
                                <label>Hasło:</label>
                                <Field type="password" name="password" className={"form-control"} />
                                <ErrorMessage className={"errorMessage"} name="password" component="div"/>
                            </div>
                            <button className={"btn btn-dark ActionButtonReversed"} type={"submit"}>Zaloguj</button>
                            <hr/>
                        </Form>
                    )}
                </Formik>
            </div>
        </div>
    )
}

export default Login