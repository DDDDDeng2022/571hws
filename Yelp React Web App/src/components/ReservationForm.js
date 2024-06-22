import React, { useEffect, useState } from "react";
import '../App.css';
import Table from 'react-bootstrap/Table';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';

class ReservationForm extends React.Component {

    constructor() {
        super()
        this.state = {
            results: false,
            rerender: false
        }
    }
    handleDelete(e) {
        window.localStorage.removeItem(e);
        alert('Reservation cancelled!')
        document.getElementById(e).style.display = 'none'
        this.setState({
            rerender: true
        })

    }

    render() {
        const idArray = Object.keys(window.localStorage)
        var i = 0
        let self = this;
        var reservationList = Array.from(idArray).map(function (item) {
            i += 1
            const temp = JSON.parse(window.localStorage.getItem(item))
            const isDelete = (JSON.parse(window.localStorage.getItem(item)) == null) ? true : false
            return (
                <tr key={item} id={item}>
                    <td>{i}</td>
                    <td >{temp.name}</td>
                    <td>{temp.date}</td>
                    <td>{temp.hour + ":" + temp.min}</td>
                    <td>{temp.email}</td>
                    <td> <svg onClick={self.handleDelete.bind(self, item)} xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                        <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z" />
                        <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z" />
                    </svg>
                    </td>
                </tr >)
        })
        return (
            <div>
                <div style={{ maxWidth: '300px', margin: 'auto', borderRadius: '10px', marginTop: '50px', backgroundColor: 'white', color: '#e22929', textAlign: 'center', fontWeight: '600', display: (window.localStorage.length == 0) ? 'block' : 'none' }}>No reservation to show</div>
                <div style={{ marginTop: '20px', marginBottom: '20px', textAlign: 'center', fontWeight: '600', display: (window.localStorage.length > 0) ? 'block' : 'none' }}>List of your reservations</div>
                <Container>
                    <Row>
                        <Col md='2'></Col>
                        <Col md='8'>
                            <Table responsive hover style={{ backgroundColor: "white", textAlign: "center", borderRaduis: '20px', display: (window.localStorage.length > 0) ? '' : 'none' }}  >

                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Business Name</th>
                                        <th>Date</th>
                                        <th>Time</th>
                                        <th>E-mail</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {reservationList}
                                </tbody>
                            </Table></Col>
                        <Col md='2'></Col>
                    </Row>
                </Container>
            </div >
        );

    }
}

export default ReservationForm;