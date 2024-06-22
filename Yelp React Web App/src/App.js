import React from 'react';
import './App.css';
import NavBar from './components/NavBar';
import SearchForm from './components/SearchForm';
import ReservationForm from './components/ReservationForm'
import ResultsTable from './components/ResultsTable';
import axios from 'axios'
import {
  BrowserRouter,
  Switch,
  Route,
  Link
} from "react-router-dom";

class App extends React.Component {

  render() {
    return (
      <div>
        <BrowserRouter>
          <NavBar />
          <Switch>
            <Route exact path='/' />
            <Route path="/search">
              <SearchForm />
            </Route>
            <Route path='/bookings'><ReservationForm /></Route>
          </Switch>
        </BrowserRouter>
      </div >
    );
  }

}
export default App;
