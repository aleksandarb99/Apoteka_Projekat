import React from 'react'
import { Col, Nav, Row, Tab, TabPane } from 'react-bootstrap'
import SubmitComplaintForm from './SubmitComplaintForm'
import ComplaintHistory from './ComplaintHistory'

const ComplaintsPage = () => {
    return (
        <main className="home__page">
            <Tab.Container id="left-tabs-example" defaultActiveKey="first" unmountOnExit>
                <Row className="my-panel">
                    <Col sm={3} md={3} lg={2} xs={12} className="side__bar">
                        <Nav variant="pills" className="flex-column">
                            <Nav.Item>
                                <Nav.Link className="my__nav__link" eventKey="first">
                                    Submit a complaint
                                </Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link className="my__nav__link" eventKey="second">
                                    My complaints
                                </Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Col>
                    <Col className="my__container" sm={9} md={9} lg={10} xs={12}>
                        <Tab.Content>
                            <TabPane eventKey="first">
                                <SubmitComplaintForm></SubmitComplaintForm>
                            </TabPane>
                            <TabPane eventKey="second">
                                <ComplaintHistory></ComplaintHistory>
                            </TabPane>
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>
        </main>
    )
}

export default ComplaintsPage
