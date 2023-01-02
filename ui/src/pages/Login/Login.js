import React, {useState} from 'react';
import './Login.css';
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import {ErrorMessage, Field, Form, Formik} from "formik";
import {Link, useHistory, useLocation} from "react-router-dom";

function Login() {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn);
    const dispatch = useDispatch()
    const history = useHistory()
    const location = useLocation()
    const [errorMsg, setErrorMsg] = useState("")

    return (
        <div className={"LoginContainer"}>
            <div className="login-wrapper">
                <h1>Zaloguj się</h1>
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
                        setErrorMsg("")
                        setSubmitting(true)
                        const errorResponse = await dispatch(allActions.userActions.loginUser(values.username, values.password))
                        if (!errorResponse) {
                            const link = (location.state && location.state.previousLocation) || "/"
                            history.replace(link);
                        } else {
                            setSubmitting(false)
                            setErrorMsg(errorResponse.toString())
                        }
                    }}
                >
                    {({isSubmitting}) => (
                        <Form className={"LoginForm"}>
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
                            <p className={"errorMessage"}>{errorMsg}</p>
                            <button className={"btn btn-dark ActionButtonReversed"} type={"submit"}>{isSubmitting ?
                                <div className="spinner-border text-danger" role="status">
                                    <span className="visually-hidden">Loading...</span>
                                </div> : "Zaloguj"}</button>
                        </Form>
                    )}
                </Formik>
                <hr/>
                <div className={"changeSignAction"}>
                    <p>Nie masz jeszcze konta?</p>
                    <Link
                        to='/register'
                        id='registerLink'
                        className='btn btn-inverse btn-block btn-lg'
                    >Zarejesteruj się</Link>
                </div>
            </div>
        </div>
    )
}

export default Login