import React, { memo } from 'react';
import '../App.css';
import Table from 'react-bootstrap/Table';
import axios from 'axios'


class Reviews extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            reviews: [],
            id: this.props.id

        }
    }
    componentDidMount() {
        axios({

            methods: 'get', url: 'https://serious-bliss-368206.wl.r.appspot.com/getReview?id=' + this.props.id
        })
            .then(response => {
                this.setState({
                    reviews: response.data
                })
            }).catch(err => {
                console.error(err);
            })
    }

    render() {
        var i = 0
        var comments = Array.from(this.state.reviews).map(function (item) {
            i += 1;
            if (i > 3) {
                return;
            }
            else {
                return (
                    <tr key={item.user.name} >
                        <td>
                            <ul style={{ listStyle: 'none' }}>
                                <li style={{ fontWeight: 'bold' }}>
                                    {item.user.name}
                                </li>
                                <li >
                                    Rating:{' '}{item.rating}/5
                                </li>
                                <li >
                                    <p style={{ marginTop: '20px' }}>{item.text}</p>
                                </li>
                                <li >
                                    {item.time_created}
                                </li>
                            </ul>
                        </td>
                    </tr>)
            }
        })
        return (
            <Table striped style={{ textAlign: 'left' }}>
                <thead>
                </thead>
                <tbody>
                    {comments}
                </tbody>
            </ Table>


        );

    }
}

export default Reviews;