import React, { memo } from 'react';
import '../App.css';
import GetMap from './GetMap';
import Reviews from './Reviews';
import Business from './Business'
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import Col from 'react-bootstrap/Col';
function DetailCard(props) {
    const [value, setValue] = React.useState('bd');

    const handleChange = (event, newValue) => {
        setValue(newValue)
        document.getElementById('box').scrollIntoView(true);
    };

    return (
        <Box id='box' sx={{ width: '100%', typography: 'body1' }} style={{ backgroundColor: 'white', borderBottomLeftRadius: '20px', borderBottomRightRadius: '20px' }}>
            <TabContext value={value} >
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                    <TabList variant="scrollable" allowScrollButtonsMobile
                        scrollButtons onChange={handleChange} aria-label="lab API tabs example"
                        style={{
                            backgroundColor: "#ffd73f", color: 'black',
                        }} >
                        <Col md={2}></Col>
                        <Tab label="Business details" value="bd" style={{ textTransform: 'none', letterSpacing: '0' }} as={Col} md={3} />
                        <Tab label="Map location" value="m" style={{ textTransform: 'none', letterSpacing: '0' }} as={Col} md={3} />
                        <Tab label="Reviews" value="r" style={{ textTransform: 'none', letterSpacing: '0' }} as={Col} md={3} />
                        <Col md={1}></Col>
                    </TabList>
                </Box>
                <TabPanel value="bd" id='pad'>
                    <Business data={props.detail} />
                </TabPanel>
                <TabPanel value="m" id='padmap'>
                    <GetMap coordinates={props.detail.coordinates} />
                </TabPanel>
                <TabPanel value="r" >
                    <Reviews id={props.detail.id} />
                </TabPanel>
            </TabContext>
        </Box >
    );

}

export default DetailCard;
