import React, {useState} from 'react';
import './Register.css';
import {useDispatch} from "react-redux";
import allActions from "../../actions/actions";
import {ErrorMessage, Field, Form, Formik} from "formik";
import {Link, useHistory} from "react-router-dom";

function Register() {
    const dispatch = useDispatch()
    const history = useHistory()
    const [errorMsg, setErrorMsg] = useState("")

    return (
        <div className={"RegisterContainer"}>
            <div className="register-wrapper">
                <h1>Zarejestruj się</h1>
                <Formik
                    initialValues={{username: '', password: '', email: '', address: '', city: ''}}
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
                        if (!values.email) {
                            errors.email = 'Pole wymagane.';
                        } else if (
                            !/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/i.test(values.email)
                        ) {
                            errors.email = 'Wprowadź poprawny email.';
                        }
                        if (!values.address) {
                            errors.address = 'Pole wymagane.';
                        }
                        if (!values.city) {
                            errors.city = 'Pole wymagane.';
                        }
                        return errors;
                    }}
                    onSubmit={async (values, {setSubmitting}) => {
                        setErrorMsg("")
                        setSubmitting(true)
                        const errorResponse = await dispatch(allActions.userActions.registerUser(values))
                        if (!errorResponse) {
                            history.push("/login")
                        } else {
                            setSubmitting(false)
                            setErrorMsg(errorResponse.toString())
                        }
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
                            <div className={"mb-3"}>
                                <label>Email:</label>
                                <Field type="text" name="email" className={"form-control"}/>
                                <ErrorMessage className={"errorMessage"} name="email" component="div"/>
                            </div>
                            <div className={"mb-3"}>
                                <label>Adres:</label>
                                <Field type="text" name="address" className={"form-control"}/>
                                <ErrorMessage className={"errorMessage"} name="address" component="div"/>
                            </div>
                            <div className={"mb-3"}>
                                <label>Miasto:</label>
                                <Field type="text" name="city" className={"form-control"}/>
                                <ErrorMessage className={"errorMessage"} name="city" component="div"/>
                            </div>
                            <p className={"errorMessage"}>{errorMsg}</p>
                            <button className={"btn btn-dark ActionButtonReversed"} type={"submit"}>
                                {isSubmitting ?
                                <div className="spinner-border text-danger" role="status">
                                    <span className="visually-hidden">Loading...</span>
                                </div> : "Załóż konto"}</button>
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