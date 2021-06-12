import React from 'react'
import { Col, Nav, Row, Tab, TabPane } from 'react-bootstrap'
import NewComplaintsList from './NewComplaintsList'
import ComplaintResponsesHistory from './ComplaintResponsesHistory'

const ComplaintsPage = () => {
    return (
        <main style={{ padding: '0px' }}>
            <Tab.Container id="left-tabs-example" defaultActiveKey="first" unmountOnExit>
                <Row className="my-panel">
                    <Col sm={3} md={3} lg={2} xs={12} className="side__bar">
                        <Nav variant="pills" className="flex-column">
                            <Nav.Item>
                                <Nav.Link className="my__nav__link" eventKey="first">
                                    New complaints
                                </Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link className="my__nav__link" eventKey="second">
                                    History
                                </Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Col>
                    <Col className="my__container" sm={9} md={9} lg={10} xs={12}>
                        <Tab.Content>
                            <TabPane eventKey="first">
                                <NewComplaintsList></NewComplaintsList>
                            </TabPane>
                            <TabPane eventKey="second">
                                <ComplaintResponsesHistory></ComplaintResponsesHistory>
                            </TabPane>
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>
        </main>
    )
}

export default ComplaintsPage
