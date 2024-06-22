import React from 'react';
import '../App.css';
import Table from 'react-bootstrap/Table';
import DetailCard from './DetailsCard';
import axios from 'axios'
class ResultsTable extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            yelpData: this.props.yelpData,
            detail: [],
            status1: true,
            status2: false
        };
    }
    componentDidMount() {
        this.setState({
            yelpData: this.props.yelpData,
        })
    }


    async getDetail(e) {
        await axios({
            methods: 'get', url: 'https://serious-bliss-368206.wl.r.appspot.com/testAPI?id=' + e
        })
            .then(response => {
                this.setState({
                    detail: response.data,
                    status1: false,
                    status2: true
                })
            }
            )
            .catch(err => {
                console.error(err);
            });
    };
    resetStatus = e => {
        this.setState({
            status1: true,
            status2: false
        })

    }
    render() {
        var i = 0
        let self = this;
        var tempData = Array.from(this.state.yelpData).map(function (item) {
            i += 1;
            if (i > 10) {
                return;
            }
            else {
                return (
                    <tr key={item.id} onClick={self.getDetail.bind(self, item.id)} >
                        <td>{i}</td>
                        <td ><img src={item.image_url} style={{ width: '70px', height: '70px' }} /></td>
                        <td>{item.name}</td>
                        <td>{item.rating}</td>
                        <td>{parseInt((item.distance) / 1600)}</td>
                    </tr>)
            }

        })
        return (
            <div>
                <Table id='yelprank' striped style={{ backgroundColor: "white", display: this.state.status1 ? '' : 'none', borderRadius: '20px', marginBottom: '10px' }
                } responsive="md" >
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Image</th>
                            <th>Business Name</th>
                            <th>Rating</th>
                            <th>Distance (miles)</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tempData}
                    </tbody>
                </Table>
                <div style={{ maxWidth: '1000px', width: '98%', display: this.state.status2 ? 'block' : 'none', margin: 'auto' }}>
                    < div style={{ paddingTop: '15px', borderTopLeftRadius: '20px', borderTopRightRadius: '20px', height: '60px', position: 'relative', backgroundColor: "white", textAlign: 'center', display: this.state.status2 ? 'block' : 'none' }}>
                        <span style={{ position: 'absolute', left: "5px", top: '5px', padding: '2px' }}><svg onClick={this.resetStatus} style={{ position: "absolute", width: '100 %' }}
                            xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor" className="bi bi-arrow-left" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
                        </svg></span>
                        <p style={{ fontWeight: "600", margin: "0" }}>{this.state.detail.name}</p>
                    </div>
                    {
                        (this.state.status2) ? (<div id='card' style={{ display: this.state.status2 ? 'block' : 'none', textAlign: "center", paddingBottom: '10px', paddingLeft: '0px', paddingRight: '0px', }}>
                            <DetailCard detail={this.state.detail} />
                        </div>) : <div></div>
                    }
                </div>
            </div>
        );

    }
}
export default ResultsTable;
