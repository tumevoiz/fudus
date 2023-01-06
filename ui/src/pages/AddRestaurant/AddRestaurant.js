import React, {useState} from 'react';
import './AddRestaurant.css';
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import {Redirect, useHistory, useLocation} from "react-router-dom";
import {ErrorMessage, Field, Form, Formik} from "formik";
import App from "../App";

const AddRestaurant = () => {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn)
    const isAdmin = useSelector(state => state.user.user.role);
    const token = useSelector(state => state.user.token)
    const [errorMsg, setErrorMsg] = useState("")
    const dispatch = useDispatch()
    const history = useHistory()
    const location = useLocation()
    const [field, setField] = useState('')

    function handleCancel(event) {
        event.preventDefault()
        history.goBack()
    }

    const convertToBase64 = (file) => {
        return new Promise((resolve, reject) => {
            const fileReader = new FileReader()
            fileReader.readAsDataURL(file)
            fileReader.onload = () => {
                resolve(fileReader.result)
            }
            fileReader.onerror = (error) => {
                reject(error);
            }
        })
    }

    const handleFileUpload = async (event) => {
        const file = event.target.files[0]
        //check the size of image
        if (file?.size / 1024 / 1024 < 2) {
            const base64 = await convertToBase64(file)
            setField(base64.replace(/^(.*;base64,)/, ""))
        } else {
            setErrorMsg('Image size must be of 2MB or less')
        }
    }

    if (!isLoggedIn || !isAdmin) {
        return <Redirect to={{
            pathname: "/login",
            state: {previousLocation: location.pathname}
        }}/>
    } else {
        return (
            <App>
                <div className='AppColumn'>
                    <div className={"RegisterContainer"}>
                        <div className="register-wrapper">
                            <h1>Dodaj restaurację</h1>
                            <Formik
                                initialValues={{name: '', slug: '', description: ''}}
                                validate={values => {
                                    const errors = {};
                                    if (!values.name) {
                                        errors.name = 'Pole wymagane.';
                                    }
                                    if (!values.slug) {
                                        errors.slug = 'Pole wymagane.';
                                    }
                                    if (!values.description) {
                                        errors.description = 'Pole wymagane.';
                                    }
                                    return errors;
                                }}
                                onSubmit={async (values, {setSubmitting}) => {
                                    setErrorMsg("")
                                    setSubmitting(true)
                                    let outcome = {
                                        uuid: "",
                                        name: values.name,
                                        slug: values.slug,
                                        description: values.description,
                                        imageBase64: field,
                                        rating: 0,
                                    }
                                    const errorResponse = await dispatch(allActions.restaurantActions.addRestaurant(token, outcome))
                                    if (!errorResponse) {
                                        location.state.push("/")
                                    } else {
                                        setSubmitting(false)
                                        setErrorMsg(errorResponse.toString())
                                    }
                                }}
                            >
                                {({isSubmitting}) => (
                                    <Form className={"AddRestaurantForm"}>
                                        <div className={"mb-3"}>
                                            <label>Nazwa:</label>
                                            <Field type="text" name="name" className={"form-control"}/>
                                            <ErrorMessage className={"errorMessage"} name="name" component="div"/>
                                        </div>
                                        <div className={"mb-3"}>
                                            <label>Slug:</label>
                                            <Field type="text" name="slug" className={"form-control"}/>
                                            <ErrorMessage className={"errorMessage"} name="slug" component="div"/>
                                        </div>
                                        <div className={"mb-3"}>
                                            <label>Opis:</label>
                                            <Field type="text" name="description" className={"form-control"}/>
                                            <ErrorMessage className={"errorMessage"} name="description"
                                                          component="div"/>
                                        </div>
                                        <div className={"mb-3"}>
                                            <label>Obrazek:</label>
                                            <Field type="file" name="img" accept=".png" className={"form-control"}
                                                   onChange={(e) => handleFileUpload(e)}/>
                                            <ErrorMessage className={"errorMessage"} name="img" component="div"/>
                                        </div>
                                        <p className={"errorMessage"}>{errorMsg}</p>
                                        <div className={"actionButtons"}>
                                            <button className={"btn btn-dark ActionButtonReversed"}
                                                    onClick={handleCancel}>Anuluj
                                            </button>
                                            <button className={"btn btn-dark ActionButtonReversed"} type={"submit"}>
                                                {isSubmitting ?
                                                    <div className="spinner-border text-danger" role="status">
                                                        <span className="visually-hidden">Loading...</span>
                                                    </div> : "Dodaj"
                                                }
                                            </button>
                                        </div>
                                    </Form>
                                )}
                            </Formik>
                        </div>
                    </div>
                </div>
            </App>
        )
    }
}

export default AddRestaurant