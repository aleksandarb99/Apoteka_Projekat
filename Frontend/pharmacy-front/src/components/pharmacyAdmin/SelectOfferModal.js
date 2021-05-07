import { React, useState, useEffect } from "react";
import { Button, Table, Modal } from "react-bootstrap";

import moment from "moment";

import axios from "../../app/api";

function SelectOfferModal(props) {
  const [offers, setOffers] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(-1);

  let handleClick = (medicineId) => {
    setSelectedRowId(medicineId);
  };

  async function fetchOffer() {
    const request = await axios.get(
      `http://localhost:8080/api/suppliers/offers/byorderid/${props.orderId}`
    );
    setOffers(request.data);
    return request;
  }

  useEffect(() => {
    if (props.orderId != undefined) {
      fetchOffer();
    }
  }, [props.orderId]);

  return (
    <Modal
      onExited={() => {
        setSelectedRowId(-1);
      }}
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Choose offer
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Table bordered hover striped variant="dark">
          <thead>
            <tr>
              <th>#</th>
              <th>Price</th>
              <th>Delivery date</th>
              <th>Supplier</th>
            </tr>
          </thead>
          <tbody>
            {offers?.length != 0 &&
              offers?.map((item, index) => (
                <tr
                  onClick={() => {
                    handleClick(item.id);
                  }}
                  className={`${
                    selectedRowId == item.id ? "selectedRow" : "pointer"
                  }`}
                >
                  <td>{index + 1}</td>
                  <td>{item.price}</td>
                  <td>
                    {moment(item.deliveryDat).format("DD MMM YYYY   hh:mm a")}
                  </td>
                  <td>{item.worker}</td>
                </tr>
              ))}
          </tbody>
        </Table>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={props.onHide}>
          Close
        </Button>
        <Button
          disabled={selectedRowId == -1}
          variant="primary"
          onClick={() => {
            props.handleSelect(selectedRowId);
          }}
        >
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default SelectOfferModal;
