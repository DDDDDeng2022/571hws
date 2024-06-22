import Table from 'react-bootstrap/Table';
import Carousel from 'react-bootstrap/Carousel';
import Button from 'react-bootstrap/Button';
import React, { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';
import * as formik from 'formik';
import Alert from 'react-bootstrap/Alert';
import { dark } from '@mui/material/styles/createPalette';
import * as yup from 'yup';
import moment from 'moment'
const { Formik } = formik;
const schema = yup.object().shape({
    email: yup.string()
        .email('Email must be a valid email address')
        .required("Email is required"),
});
class Business extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            show: false,
            key: this.props.data.id,
            // reserved: false,
            reserved: (Object.keys(window.localStorage).indexOf(this.props.data.id) == -1) ? false : true,
            validated: false,
            speed: '100000',
        }
    }

    handleSubmit = e => {
        e.preventDefault();
        const form = e.target;
        if (form.checkValidity() === false) {
            e.preventDefault();
            e.stopPropagation();
            this.setState({
                validated: true,
            });
        } else {
            this.setState({
                validated: true,
                reserved: true
            });
            const temp = { name: this.props.data.name, email: e.target[0].value, date: e.target[1].value, hour: e.target[2].value, min: e.target[3].value }
            window.localStorage.setItem(this.state.key, JSON.stringify(temp));
            alert('Reservation Created!');
            this.setState({ show: false })
        }
    }

    render() {
        const resetSpeed = () => {
            this.setState({
                speed: 1000
            })
        }
        const handleClose = () => this.setState({ show: false });
        const handleShow = () => {
            if (this.state.reserved == false) {
                this.setState({ show: true })
            } else {
                window.localStorage.removeItem(this.props.data.id);
                alert('Reservation cancelled!')
                this.setState({
                    reserved: false
                });
            }
        }
        const data = this.props.data
        var len = 0
        var category = Array.from(data.categories).map(function (item) {
            len += 1
            if (typeof (data.categories) != 'undefined' && len < data.categories.length) {
                return item.title + " | "
            } else {
                return item.title
            }

        })
        let self = this
        var photo = Array.from(data.photos).map(function (item) {

            return (<Carousel.Item interval={self.state.speed}>
                <img
                    className='center-block'
                    src={item}
                    style={{ height: '200px', width: '200px' }} />
            </Carousel.Item>
            )
        })
        const twitter = "https://twitter.com/intent/tweet?text=Check%20" + data.name + "%20on%20Yelp.&url=" + data.url;
        const facebook = "https://www.facebook.com/sharer/sharer.php?u=" + data.url + "&quote=20%"

        return (
            <div id="business" style={{ paddingBottom: '20px' }}>
                <Container fluid="md">
                    <Row>
                        <Col md={6}>
                            <dl>
                                <dt>Address</dt>
                                <dd>{(typeof (data.location.display_address) == 'undefined' || data.location.display_address == '') ? "N/A" : data.location.display_address}</dd>
                            </dl>
                        </Col>
                        <Col md={6}>
                            <dl>
                                <dt>Category</dt>
                                <dd>{(typeof (category) == 'undefined' || category == '') ? "N/A" : category}</dd>
                            </dl>
                        </Col>
                    </Row>
                    <Row>
                        <Col md={6}>
                            <dl>
                                <dt>Phone</dt>
                                <dd>{(typeof (data.display_phone) == 'undefined' || data.display_phone == '') ? "N/A" : data.display_phone}</dd>
                            </dl>
                        </Col>
                        <Col md={6}>
                            <dl>
                                <dt>Price range</dt>
                                <dd>{(typeof (data.price) == 'undefined' || data.price == '') ? "N/A" : data.price}</dd>
                            </dl>
                        </Col>
                    </Row>
                    <Row>
                        <Col md={6}>
                            <dl>
                                <dt>Status</dt>
                                <dd>{
                                    (typeof (data.hours[0].is_open_now) == 'undefined') ? "N/A" : (data.hours[0].is_open_now ? <p style={{ color: 'green' }} >Open Now</p> : <p style={{ color: 'red' }} >Close</p>)}
                                </dd>
                            </dl>
                        </Col>
                        <Col md={6}>
                            <dl>
                                <dt>Visit yelp for more</dt>
                                <dd><a href={data.url} target="_blank">Business Link</a></dd>
                            </dl>
                        </Col>
                    </Row>
                </Container>
                {this.state.reserved ? <Button variant="primary" onClick={handleShow} >Cancel reservation</Button> : <Button variant="danger" onClick={handleShow} >Reserve Now</Button>}
                <div style={{ height: '50px', lineHeight: '50px', verticalAlign: "bottom" }}>Share on:{' '}
                    <a href={twitter} target="_blank"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-twitter" viewBox="0 0 16 16">
                        <path d="M5.026 15c6.038 0 9.341-5.003 9.341-9.334 0-.14 0-.282-.006-.422A6.685 6.685 0 0 0 16 3.542a6.658 6.658 0 0 1-1.889.518 3.301 3.301 0 0 0 1.447-1.817 6.533 6.533 0 0 1-2.087.793A3.286 3.286 0 0 0 7.875 6.03a9.325 9.325 0 0 1-6.767-3.429 3.289 3.289 0 0 0 1.018 4.382A3.323 3.323 0 0 1 .64 6.575v.045a3.288 3.288 0 0 0 2.632 3.218 3.203 3.203 0 0 1-.865.115 3.23 3.23 0 0 1-.614-.057 3.283 3.283 0 0 0 3.067 2.277A6.588 6.588 0 0 1 .78 13.58a6.32 6.32 0 0 1-.78-.045A9.344 9.344 0 0 0 5.026 15z" />
                    </svg></a>
                    {' '}
                    <a href={facebook} style={{ color: 'blue' }} target="_blank">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-facebook-square-fill" viewBox="0 0 16 16">
                            <path d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951z" />
                        </svg></a>
                </div>
                <Carousel variant="dark" onClick={resetSpeed} class='center-block' pause={false}>
                    {photo}
                </Carousel>
                <Modal show={this.state.show} onHide={handleClose} backdrop="static"
                    keyboard={false}>
                    <Modal.Header >
                        <Modal.Title>Reservation form</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Formik
                            validationSchema={schema}
                            onSubmit={console.log}
                            initialValues={{
                                email: '',
                                date: '',
                                hour: '',
                                min: ''
                            }}>
                            {({
                                handleSubmit,
                                handleChange,
                                handleBlur,
                                values,
                                touched,
                                isValid,
                                errors,
                            }) => (
                                <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>

                                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                        <div style={{ textAlign: 'center', fontWeight: "700" }}>{data.name}</div>
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="validationCustom01">
                                        <Form.Label>Email</Form.Label>
                                        <Form.Control
                                            type="email"
                                            name="email"
                                            value={values.email}
                                            onChange={handleChange}
                                            isInvalid={!!errors.email}
                                            hasValidation
                                            required
                                        />

                                        <Form.Control.Feedback type="invalid">
                                            {errors.email == undefined ? 'Email is required' : errors.email}
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                    <Form.Group
                                        className="mb-3"
                                        controlId="exampleForm.ControlTextarea1"
                                    >
                                        <Form.Label>Date</Form.Label>
                                        <Form.Control type="date"
                                            min={moment().format('YYYY-MM-DD')}
                                            required
                                            name='date'
                                            values={values.date}
                                            onChange={handleChange}
                                            isInvalid={!!errors.date}
                                            hasValidation
                                        />
                                        <Form.Control.Feedback type='invalid'>Date is required</Form.Control.Feedback>
                                    </Form.Group>
                                    {/* <Row className="mb-3"> */}

                                    <Form.Group className="mb-3" controlId="validationCustom01">
                                        <Form.Label>Time</Form.Label>
                                        <div>
                                            <Form.Select hasValidation
                                                values={values.hour} name='hour'
                                                isInvalid={!!errors.hour} required as={Col} style={{ display: 'inline-block', width: '60px', backgroundImage: 'none', padding: '0' }} onChange={handleChange}>
                                                <option style={{ display: 'none' }}></option>
                                                <option>10</option>
                                                <option>11</option>
                                                <option>12</option>
                                                <option>13</option>
                                                <option>14</option>
                                                <option>15</option>
                                                <option>16</option>
                                                <option>17</option>
                                            </Form.Select>
                                            {" : "}
                                            <Form.Select hasValidation appearance='none'
                                                values={values.min} name='min' required isInvalid={!!errors.min} as={Col} style={{ display: 'inline-block', width: '60px', backgroundImage: 'none', padding: '0' }} onChange={handleChange}>

                                                <option style={{ display: 'none' }}></option>
                                                <option>00</option>
                                                <option>15</option>
                                                <option>30</option>
                                                <option>45</option>
                                            </Form.Select>
                                            {' '}
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-clock" viewBox="0 0 16 16">
                                                <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z" />
                                                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z" />
                                            </svg></div>
                                        <Form.Control.Feedback type="invalid">
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                    <div style={{ textAlign: 'center' }}><Button variant="danger" type='submit'>Submit</Button></div>
                                </Form>
                            )}
                        </Formik>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="dark" onClick={handleClose}>Close</Button>
                    </Modal.Footer>
                </Modal >
            </div >
        );
    }
}
export default Business;