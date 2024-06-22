
import React from 'react'
import { Map, GoogleApiWrapper, Marker } from 'google-maps-react';
import Container from 'react-bootstrap/Container';
export class GetMap extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            latitude: this.props.coordinates.latitude,
            longitude: this.props.coordinates.longitude
        }
    }
    render() {
        return (

            <div style={{ width: '100%', height: "430px", position: 'relative' }}>
                <Map
                    google={this.props.google}
                    zoom={12}
                    initialCenter={{ lat: this.props.coordinates.latitude, lng: this.props.coordinates.longitude }}>
                    <Marker position={{ lat: this.props.coordinates.latitude, lng: this.props.coordinates.longitude }} />
                </Map>

            </div >
        )
    }
}
export default GoogleApiWrapper({
    apiKey: "AIzaSyC0qUoEfkO1hNszSpuKil7nHtBGjdzXiYg"
})(GetMap);

