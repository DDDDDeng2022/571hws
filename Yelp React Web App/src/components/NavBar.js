import React from 'react';
import '../App.css';
import Nav from 'react-bootstrap/Nav';
import { NavLink } from "react-router-dom";
class NavBar extends React.Component {

    render() {
        return (
            <Nav id='nav' variant="pills" defaultActiveKey="/SearchForm">
                <Nav.Item id='navitem'>
                    <NavLink to="/search" activeClassName="selectedNav">Search</NavLink>
                </Nav.Item>
                <Nav.Item id='navitem'>
                    <NavLink to="/bookings" activeClassName="selectedNav">My Bookings</NavLink>
                </Nav.Item>
            </Nav>

        );
    }

}

export default NavBar;
