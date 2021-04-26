import React, { useEffect, useState } from 'react'
import { Col, Nav, Row, Tab } from 'react-bootstrap';
import { useSelector } from 'react-redux';
import api from '../../app/api';
import { getIdFromToken } from '../../app/jwtTokenUtils';
import SetPasswordModal from '../utilComponents/modals/SetPasswordModal'

const SupplierHomePage = () => {
    const [isPasswordSet, setIsPasswordSet] = useState(false);
    useEffect(() => {
        let id = getIdFromToken();
        api.get("http://localhost:8080/api/users/" + id)
            .then((res) => {
                setIsPasswordSet(res.data.passwordChanged)
            })
    }, [])

    return (
        <div>
            <main className="unregistered__home__page">
                <Tab.Container defaultActiveKey="first">
                    <Row className="my-panel">
                        <Col sm={3} md={3} lg={2} xs={12} className="sideBar">
                            <Nav variant="pills" className="flex-column">
                                <Nav.Item>
                                    <Nav.Link className="my__nav__link" eventKey="first">
                                        New requests
                                    </Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                    <Nav.Link className="my__nav__link" eventKey="second">
                                        My offers
                                    </Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                    <Nav.Link className="my__nav__link" eventKey="third">
                                        Stock
                                    </Nav.Link>
                                </Nav.Item>
                            </Nav>
                        </Col>
                        <Col className="my__container" sm={9} md={9} lg={10} xs={12}>
                            <Tab.Content>

                            </Tab.Content>
                        </Col>
                    </Row>
                </Tab.Container>
            </main>
            <SetPasswordModal show={!isPasswordSet} onPasswordSet={() => setIsPasswordSet(true)}></SetPasswordModal>
        </div>
    )
}

export default SupplierHomePage