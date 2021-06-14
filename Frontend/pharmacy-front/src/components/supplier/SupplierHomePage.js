import React, { useEffect, useState } from "react";
import { Col, Nav, Row, Tab, TabPane } from "react-bootstrap";
import { useSelector } from "react-redux";
import api from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import SetPasswordModal from "../utilComponents/modals/SetPasswordModal";
import SupplierStock from "../supplier/stock/SupplierStock";
import NewOrders from "../supplier/orders/NewOrders";
import SupplierOffers from "./offers/SupplierOffers";

const SupplierHomePage = () => {
    const [showModalPWChange, setShowModalPWChange] = useState(false);

    useEffect(() => {
        let id = getIdFromToken();
        api.get("http://localhost:8080/api/users/" + id)
            .then((res) => {
                setShowModalPWChange(!res.data.passwordChanged)
            })
    }, [])

    return (
        <div>
            <main className="home__page">
                <Tab.Container unmountOnExit>
                    <Row className="my-panel">
                        <Col sm={3} md={3} lg={2} xs={12} className="side__bar">
                            <Nav variant="pills" className="flex-column">
                                <Nav.Item>
                                    <Nav.Link className="my__nav__link" eventKey="first">
                                        New orders
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
                                <TabPane eventKey="first">
                                    <NewOrders></NewOrders>
                                </TabPane>
                                <TabPane eventKey="second">
                                    <SupplierOffers></SupplierOffers>
                                </TabPane>
                                <TabPane eventKey="third">
                                    <SupplierStock></SupplierStock>
                                </TabPane>
                            </Tab.Content>
                        </Col>
                    </Row>
                </Tab.Container>
            </main>
            <SetPasswordModal show={showModalPWChange} onPasswordSet={() => setShowModalPWChange(false)}></SetPasswordModal>
        </div >
    )
}

export default SupplierHomePage;
