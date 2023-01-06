import React, {useState} from 'react';
import './AddRestaurant.css';
import {useDispatch, useSelector} from "react-redux";
import allActions from "../../actions/actions";
import {useHistory, useLocation} from "react-router-dom";
import {ErrorMessage, Field, Form, Formik} from "formik";
import * as R from "ramda";
import App from "../App";

const AddRestaurant = () => {
    const isLoggedIn = useSelector(state => state.user.isLoggedIn)
    const token = useSelector(state => state.user.token)
    const [errorMsg, setErrorMsg] = useState("")
    const dispatch = useDispatch()
    const history = useHistory()
    const location = useLocation()

    function handleCancel(event) {
        event.preventDefault()
        history.goBack()
    }

    const convertToBase64 = (file) => {
        return new Promise((resolve, reject) => {
            const fileReader = new FileReader();
            fileReader.readAsDataURL(file);
            fileReader.onload = () => {
                resolve(fileReader.result);
            };
            fileReader.onerror = (error) => {
                reject(error);
            };
        });
    };

    const handleFileUpload = async (e) => {
        const file = e.target.files[0];
        return await convertToBase64(file);
    };

    return (
        <App>
            <div className='AppColumn'>
                <div className={"RegisterContainer"}>
                    <div className="register-wrapper">
                        <h1>Dodaj restaurację</h1>
                        <Formik
                            initialValues={{name: '', slug: '', description: '', categories: [], img: '', rating: 0}}
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
                                if (R.isEmpty(values.categories)) {
                                    errors.categories = 'Pole wymagane.';
                                }
                                if (!values.img) {
                                    errors.img = 'Pole wymagane.';
                                }
                                if (!values.rating) {
                                    errors.rating = 'Pole wymagane.';
                                }
                                return errors;
                            }}
                            onSubmit={async (values, {setSubmitting}) => {
                                console.log(values)
                                setErrorMsg("")
                                setSubmitting(true)
                                let result = await convertToBase64(values.img)
                                if (!result.isError()){
                                    let outcome = {
                                        name: values.name,
                                        slug: values.slug,
                                        description: values.description,
                                        imgBase64: result,
                                        rating: values.rating,
                                    }
                                    console.log(values)
                                    const errorResponse = await dispatch(allActions.restaurantActions.addRestaurant(token, outcome))
                                    if (!errorResponse) {
                                        location.state.push("/")
                                    } else {
                                        setSubmitting(false)
                                        setErrorMsg(errorResponse.toString())
                                    }
                                } else {
                                    setErrorMsg("Wystąpił problem z konwertowaniem obrazu miau...")
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
                                        <label>Kategorie:</label>
                                        <Field type="text" name="categories" className={"form-control"}/>
                                        <ErrorMessage className={"errorMessage"} name="categories" component="div"/>
                                    </div>
                                    <div className={"mb-3"}>
                                        <label>Obrazek:</label>
                                        <Field type="file" name="img" accept=".jpeg, .png, .jpg" className={"form-control"} onChange={(e) => handleFileUpload(e)}/>
                                        <ErrorMessage className={"errorMessage"} name="img" component="div"/>
                                    </div>
                                    <div className={"mb-3"}>
                                        <label>Ilość gwiazdek:</label>
                                        <Field type="number" step="0.1" name="rating" className={"form-control"}/>
                                        <ErrorMessage className={"errorMessage"} name="rating" component="div"/>
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

export default AddRestaurant