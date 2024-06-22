import React from 'react';
import ReactDOM from 'react-dom/client';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Stack from 'react-bootstrap/Stack';
import axios from 'axios'
import TextField from '@mui/material/TextField';
import Autocomplete, { autocompleteClasses } from '@mui/material/Autocomplete';
import '../App.css';
import ResultsTable from './ResultsTable';
import Container from 'react-bootstrap/Container';

function ShowDetail() {

    return (
        <div className='remand'>No results available</div>
    );

}



class SearchForm extends React.Component {

    constructor() {
        super()
        this.state = {
            keyword: '',
            distance: 10,
            category: 'Default',
            location: '',
            locationCopy: '',
            autoChecked: false,
            topMatch: [],
            yelpData: [],
            showOrNot: false
        }
    }
    autoComplete = async (e) => {
        const target = e.target
        const newKeyword = target.value
        this.setState({
            keyword: newKeyword,
            topMatch: []

        });
        if (newKeyword == null || newKeyword == undefined) {
        }
        else {
            await axios({
                methods: 'get', url: 'https://serious-bliss-368206.wl.r.appspot.com/getComplete?text=' + newKeyword
            })
                .then(response => {
                    var temptarray = [];
                    for (var i = 0; i < response.data.length; i++) {
                        temptarray.push(response.data[i])
                    }
                    this.setState({
                        topMatch: temptarray
                    })
                }).catch(err => {
                    console.error(err);
                })

        }
    }
    handleForm = e => {
        const target = e.target
        const value = target.type === 'checkbox' ? target.checked : target.value
        const name = target.name
        this.setState({
            [name]: value
        }
        )
        if (name == 'location') {
            this.setState({
                locationCopy: value
            })
        }
        if (target.type === 'checkbox') {
            document.getElementById('location').disabled = target.checked
            this.setState({
                location: ''
            })
            this.getIp();
        }
    }

    handleSubmit = e => {
        console.log('https://serious-bliss-368206.wl.r.appspot.com/getYelp?keyword=' + this.state.keyword +
            '&distance=' + this.state.distance + '&category=' + this.state.category
            + '&location=' + this.state.locationCopy + '&autoChecked=' + this.state.autoChecked)
        axios
            .get('https://serious-bliss-368206.wl.r.appspot.com/getYelp?keyword=' + this.state.keyword +
                '&distance=' + this.state.distance + '&category=' + this.state.category
                + '&location=' + this.state.locationCopy + '&autoChecked=' + this.state.autoChecked
            )
            .then(response => {
                this.setState({
                    yelpData: response.data,
                    showOrNot: true
                })
                const part2 = ReactDOM.createRoot(document.getElementById('part2'));
                if (response.data == '') {
                    part2.render(<React.StrictMode>
                        <ShowDetail />
                    </React.StrictMode>);
                } else {

                    part2.render(<React.StrictMode>
                        <ResultsTable yelpData={response.data} />
                    </React.StrictMode>);

                }
            }
            )
            .catch(err => {
                console.error(err);
            });
        e.preventDefault();
    };

    getIp = async (e) => {
        await axios({
            methods: 'get', url: 'https://ipinfo.io/?token=5d1e1011c371ca'
        })
            .then(response => {
                const address = response.data.loc
                this.setState({
                    locationCopy: address
                })
            }).catch(err => {
                console.error(err);
            })
    }



    Clear = e => {
        const { keyword, distance, category, location, autoChecked, topMatch, yelpData, showOrNot } = this.state
        this.setState({
            keyword: '',
            distance: 10,
            category: 'Default',
            location: '',
            locationCopy: '',
            autoChecked: false,
            topMatch: [],
            yelpData: [],
            showOrNot: false

        });
        document.getElementById('location').disabled = false
    }
    render() {
        return (
            <div style={{ marginLeft: '10px', marginRight: '10px', marginTop: '20px' }}>
                < Stack gap={2} className="col-md-5 mx-auto" id="search">
                    <div className="mb-3" >
                        <h4 className='head'>Business Search</h4>
                    </div >
                    <Form onSubmit={this.handleSubmit} >
                        <Form.Group>
                            <Form.Label>keyword<span style={{ color: 'red' }}>*</span></Form.Label>
                            <Autocomplete
                                filterOptions={(x) => x}
                                freeSolo={true}
                                value={this.state.keyword}
                                onChange={async (e, newKey) => {
                                    this.setState({
                                        keyword: newKey,
                                    });
                                    if (newKey == null || newKey == undefined) { }
                                    else {
                                        await axios({
                                            methods: 'get', url: 'https://serious-bliss-368206.wl.r.appspot.com/getComplete?text=' + newKey
                                        })
                                            .then(response => {
                                                var temptarray = [];
                                                for (var i = 0; i < response.data.length; i++) {
                                                    temptarray.push(response.data[i])
                                                }
                                                this.setState({
                                                    topMatch: temptarray
                                                })
                                            }).catch(err => {
                                                console.error(err);
                                            })
                                    }
                                }
                                }
                                onInputChange={this.autoComplete}
                                options={this.state.topMatch}
                                renderInput={(params) => <TextField required={true} className='form-control' {...params} />}
                            />
                        </Form.Group>
                        <Container style={{ paddingLeft: '0px', marginLeft: '0px', marginTop: '20px' }}>
                            <Row className="mb-3">
                                <Col md={6}>
                                    <Form.Group as={Col} className="mb-3" responsive='md' >
                                        <Form.Label>Distance</Form.Label>
                                        <Form.Control type="text" placeholder="10" name="distance" id="distance" value={this.state.distance} onChange={this.handleForm} />
                                    </Form.Group></Col>
                                <Col md={6}>
                                    <Form.Group as={Col} className="mb-3" responsive='md' >
                                        <Form.Label>Category<span style={{ color: 'red' }}>*</span></Form.Label>
                                        <Form.Select aria-label="Default select example" name="category" id="category" style={{ width: '80%' }} value={this.state.category} onChange={this.handleForm}>
                                            <option>Default</option>
                                            <option>Arts & Entertainment</option>
                                            <option>Health & Medical </option>
                                            <option>Hotel & Travel</option>
                                            <option>Food</option>
                                            <option>Professional Services</option>
                                        </Form.Select>

                                    </Form.Group></Col>
                            </Row>
                        </Container>
                        <Form.Group className="mb-3" >
                            <Form.Label>Location<span style={{ color: 'red' }}>*</span></Form.Label>
                            <Form.Control type="text" id="location" required={true}
                                name="location" value={this.state.location} onChange={this.handleForm} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicCheckbox">
                            <Form.Check type="checkbox" label="Auto-detect my location" id="auto-detect" name="autoChecked" checked={this.state.autoChecked} onChange={this.handleForm} />
                        </Form.Group>
                        <div style={{ textAlign: 'center' }}>

                            <Button variant="danger" type="submit"> Submit</Button>
                            <Button variant="primary" onClick={this.Clear} style={{ marginLeft: '20px' }} >
                                Clear
                            </Button>
                        </div>

                    </Form >
                </Stack >
                < Container id='part2' style={{ display: this.state.showOrNot ? '' : 'none', textAlign: 'center', verticalAlign: 'center', paddingLeft: '0px', paddingRight: '0px' }} >
                </Container >
            </div >
        );


    }
}
export default SearchForm;