import React from 'react';
import './Register.css';
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import {ErrorMessage, Field, Form, Formik} from "formik";
import {Link, Redirect} from "react-router-dom";

function Register() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn);
    const dispatch = useDispatch()

    if (isLoggedIn) {
        return <Redirect to={"/"}/>
    }
    return (
        <div className={"RegisterContainer"}>
            <div className="register-wrapper">
                <h1>Zarejestruj się</h1>
                <Formik
                    initialValues={{username: '', password: ''}}
                    validate={values => {
                        const errors = {};
                        if (!values.username) {
                            errors.username = 'Pole wymagane.';
                        }
                        if (!values.password) {
                            errors.password = 'Pole wymagane.';
                        } else if (
                            !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/i.test(values.password)
                        ) {
                            errors.password = 'Hasło musi zawierać min. 8 znaków, w tym cyfre i znak specjalny.';
                        }
                        return errors;
                    }}
                    onSubmit={async (values, {setSubmitting}) => {
                        dispatch(allActions.userActions.registerUser(values.username, values.password))
                        setTimeout(() => {
                            console.log(JSON.stringify(values, null, 2));
                            setSubmitting(false);
                        }, 400);
                    }}
                >
                    {({isSubmitting}) => (
                        <Form className={"RegisterForm"}>
                            <div className={"mb-3"}>
                                <label>Login:</label>
                                <Field type="text" name="username" className={"form-control"}/>
                                <ErrorMessage className={"errorMessage"} name="username" component="div"/>
                            </div>
                            <div className={"mb-3"}>
                                <label>Hasło:</label>
                                <Field type="password" name="password" className={"form-control"}/>
                                <ErrorMessage className={"errorMessage"} name="password" component="div"/>
                            </div>
                            <button className={"btn btn-dark ActionButtonReversed"} type={"submit"}>Załóż konto</button>
                        </Form>
                    )}
                </Formik>
                <hr/>
                <div className={"changeSignAction"}>

                    <p>Masz już konto?</p>
                    <Link
                        to='/login'
                        id='registerLink'
                        className='btn btn-inverse btn-block btn-lg'
                    >Zaloguj się</Link>
                </div>
            </div>
        </div>
    )
}

export default Register