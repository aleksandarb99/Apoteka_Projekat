import { React, useState, useEffect } from "react";
import { Button, Row, Col, Container, Modal, Form } from "react-bootstrap";
import { getErrorMessage } from "../../app/errorHandler";
import axios from "../../app/api";

import { useToasts } from "react-toast-notifications";

function AddAdvertismentModal(props) {
  const { addToast } = useToasts();

  const [endDate, setEndDate] = useState(null);
  const [discount, setDiscount] = useState(0);
  const [text, setText] = useState("");
  const [type, setType] = useState("0");
  const [medicineItems, setMedicineItems] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(0);

  async function fetchPriceList() {
    const request = await axios
      .get(`/api/pricelist/${props.priceListId}`)
      .then((res) => {
        setMedicineItems(res.data.medicineItems);
      })
      .catch((err) => {
        addToast(getErrorMessage(err), {
          appearance: "error",
        });
      });

    return request;
  }

  useEffect(() => {
    if (props.priceListId != undefined) {
      fetchPriceList();
    }
  }, [props.priceListId]);

  let changeDate = (date) => {
    let array = date.split("-");
    let d = new Date(
      Number.parseInt(array[0]),
      Number.parseInt(array[1]) - 1,
      Number.parseInt(array[2])
    );
    d.setHours(0, 0, 0);
    let now = new Date();
    if (d.getTime() > now.getTime()) setEndDate(d);
    else {
      setEndDate(null);
      addToast("End date should be in future!", {
        appearance: "warning",
      });
    }
  };

  return (
    <Modal
      onExited={() => {
        setEndDate(null);
        setDiscount(0);
        setSelectedRowId(0);
        setText("");
        setType("0");
      }}
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Add advertisment
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <Row>
            <Col>
              <Form.Group controlId="typeSelect">
                <Form.Label>Types</Form.Label>
                <Form.Control
                  as="select"
                  onChange={(event) => setType(event.target.value)}
                  defaultValue="0"
                >
                  <option value="0">Not Selected</option>
                  <option value="1">Promotion</option>
                  <option value="2">Sale</option>
                </Form.Control>
              </Form.Group>
              <Form.Group controlId="medicineSelect">
                <Form.Label>Medicine</Form.Label>
                <Form.Control
                  as="select"
                  onChange={(event) => setSelectedRowId(event.target.value)}
                  defaultValue="0"
                >
                  <option value="0">Not Selected</option>
                  {medicineItems?.length != 0 &&
                    medicineItems?.map((item, index) => (
                      <option value={item?.id}>{item?.medicine?.name}</option>
                    ))}
                </Form.Control>
              </Form.Group>
              <Form.Group controlId="datePicker" di>
                <Form.Label>End date</Form.Label>
                <Form.Control
                  type="date"
                  onChange={(event) => changeDate(event.target.value)}
                />
              </Form.Group>
              <Form.Group controlId="discountPicker" di>
                <Form.Label>Discount</Form.Label>
                <Form.Control
                  disabled={type != "2"}
                  type="number"
                  onChange={(event) => setDiscount(event.target.value)}
                  max="100"
                  min="0"
                />
              </Form.Group>
              <Form.Group controlId="contentPicker" di>
                <Form.Label>Content</Form.Label>
                <Form.Control
                  disabled={type != "1"}
                  type="text"
                  onChange={(event) => setText(event.target.value)}
                />
              </Form.Group>
            </Col>
          </Row>
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={props.onHide}>
          Close
        </Button>
        <Button
          disabled={type == "0" || selectedRowId == "0" || endDate == null}
          variant="primary"
          onClick={() => {
            props.handleAdd({
              type,
              selectedRowId,
              endDate: endDate.getTime(),
              discount,
              text,
            });
          }}
        >
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default AddAdvertismentModal;
