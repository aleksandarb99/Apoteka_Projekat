import React, { useState, useEffect } from "react";

import { Tab, Row, Col, Button, Table } from "react-bootstrap";

import axios from "../../app/api";
import AddAdvertismentModal from "./AddAdvertismentModal";

import moment from "moment";
import { useToasts } from "react-toast-notifications";

function AdvertismentTab({ pharmacyDetails }) {
  const { addToast } = useToasts();
  const [list, setList] = useState([]);
  const [addModalShow, setAddModalShow] = useState(false);
  const [refresh, setRefresh] = useState(false);

  async function fetchAdvertisment() {
    const request = await axios
      .get(`http://localhost:8080/api/sales/${pharmacyDetails.id}`)
      .then((res) => {
        setList(res.data);
      })
      .catch((err) => {
        addToast(err.response.data, {
          appearance: "error",
        });
      });

    return request;
  }

  useEffect(() => {
    if (pharmacyDetails?.id != undefined) {
      fetchAdvertisment();
    }
  }, [pharmacyDetails, refresh]);

  async function addAdverb(data) {
    const request = await axios
      .post(`http://localhost:8080/api/sales/${pharmacyDetails.id}`, data)
      .then((res) => {
        addToast(res.data, {
          appearance: "success",
        });
        setRefresh(!refresh);
      })
      .catch((err) => {
        addToast(err.response.data, {
          appearance: "error",
        });
      });

    return request;
  }

  let handleAddModalSave = (data) => {
    setAddModalShow(false);
    addAdverb(data);
  };

  let handleAddModalClose = () => {
    setAddModalShow(false);
  };

  return (
    <Tab.Pane eventKey="ninth">
      <h1 className="content-header">Advertisment</h1>
      <hr></hr>
      <Row>
        <Col>
          <Table bordered striped variant="dark">
            <thead>
              <tr>
                <th>#</th>
                <th>Medicine</th>
                <th>Type</th>
                <th>Discount(percent)</th>
                <th>Start date</th>
                <th>End date</th>
                <th>Content</th>
              </tr>
            </thead>
            <tbody>
              {list?.length != 0 &&
                list?.map((item, index) => (
                  <tr>
                    <td>{index + 1}</td>
                    <td>{item.medicineItem.medicine.name}</td>
                    <td>{item.type}</td>
                    <td>{item.discountPercent}</td>
                    <td>{moment(item.startDate).format("DD MMM YYYY")}</td>
                    <td>{moment(item.endDate).format("DD MMM YYYY")}</td>
                    <td>{item.advertisementText}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Col>
      </Row>
      <Row>
        <AddAdvertismentModal
          show={addModalShow}
          onHide={handleAddModalClose}
          handleAdd={handleAddModalSave}
          priceListId={pharmacyDetails?.priceListId}
        />
        <Col className="center">
          <Button
            variant="secondary"
            onClick={() => {
              setAddModalShow(true);
            }}
          >
            Add
          </Button>
        </Col>
      </Row>
      <hr></hr>
    </Tab.Pane>
  );
}

export default AdvertismentTab;
