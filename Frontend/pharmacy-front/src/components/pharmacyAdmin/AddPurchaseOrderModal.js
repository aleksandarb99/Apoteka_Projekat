import { React, useState } from "react";
import {
  Button,
  Row,
  Col,
  Container,
  Table,
  Modal,
  Form,
  ListGroup,
} from "react-bootstrap";
import { useToasts } from "react-toast-notifications";

function AddPurchaseOrderModal(props) {
  const { addToast } = useToasts();
  const [selectedRowId, setSelectedRowId] = useState(-1);
  const [selectedName, setSelectedName] = useState("");
  const [amount, setAmount] = useState(100);
  const [orders, setOrders] = useState([]);
  const [selectedItems, setSelectedItems] = useState([]);
  const [startDate, setStartDate] = useState(null);

  let handleClick = (medicineId, name) => {
    setSelectedRowId(medicineId);
    setSelectedName(name);
  };

  let handleRemove = (medicineId) => {
    setSelectedItems(selectedItems.filter((item) => item != medicineId));
    setOrders(orders.filter((item) => item.medicineId != medicineId));
  };

  let handleAdd = () => {
    setOrders([
      ...orders,
      { medicineId: selectedRowId, amount, name: selectedName },
    ]);
    setSelectedItems([...selectedItems, selectedRowId]);
    setSelectedRowId(-1);
    setSelectedName("");
  };

  let changeDate = (date) => {
    let array = date.split("-");
    let d = new Date(
      Number.parseInt(array[0]),
      Number.parseInt(array[1]) - 1,
      Number.parseInt(array[2])
    );
    d.setHours(0, 0, 0);
    let now = new Date();
    if (d.getTime() > now.getTime()) setStartDate(d);
    else {
      setStartDate(null);
      addToast("End date should be in future!", {
        appearance: "warning",
      });
    }
  };

  return (
    <Modal
      onExited={() => {
        setSelectedRowId(-1);
        setAmount(100);
        setOrders([]);
        setSelectedItems([]);
        setSelectedName("");
        setStartDate(null);
      }}
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Add order items
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <Row>
            <Col xs={12} md={8} lg={8}>
              <Table bordered hover striped variant="dark">
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Code</th>
                    <th>Name</th>
                    <th>Avg. grade</th>
                    <th>Amount</th>
                  </tr>
                </thead>
                <tbody>
                  {props?.medicineList?.length != 0 &&
                    props?.medicineList?.map((item, index) => (
                      <tr
                        onClick={() => {
                          if (!selectedItems.includes(item.medicine.id))
                            handleClick(item.medicine.id, item.medicine.name);
                        }}
                        className={`${
                          selectedRowId == item.medicine.id && "selectedRow"
                        }  ${
                          selectedItems.includes(item.medicine.id)
                            ? "disabledRow"
                            : "pointer"
                        } `}
                      >
                        <td>{index + 1}</td>
                        <td>{item.medicine.code}</td>
                        <td>{item.medicine.name}</td>
                        <td>{item.medicine.avgGrade}</td>
                        <td>{item.amount}</td>
                      </tr>
                    ))}
                </tbody>
              </Table>
              <Form.Group controlId="amountPicker" di>
                <Form.Label>Order amount</Form.Label>
                <Form.Control
                  type="number"
                  value={amount}
                  disabled={selectedRowId == -1}
                  onChange={(event) =>
                    setAmount(Number.parseInt(event.target.value))
                  }
                  min="1"
                />
              </Form.Group>
              <Button
                disabled={selectedRowId == -1}
                onClick={handleAdd}
                variant="success"
              >
                Add item
              </Button>
            </Col>
            <Col>
              <ListGroup variant="flush">
                {orders?.map((item) => (
                  <ListGroup.Item
                    onClick={() => handleRemove(item.medicineId)}
                    className="pointer"
                    variant="dark"
                  >
                    {item?.name} {item?.amount}
                  </ListGroup.Item>
                ))}
              </ListGroup>
            </Col>
          </Row>
          <hr></hr>
          <Row>
            <Col>
              <Form.Group controlId="datePicker" di>
                <Form.Label>Deadline date</Form.Label>
                <Form.Control
                  type="date"
                  onChange={(event) => changeDate(event.target.value)}
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
          disabled={orders?.length == 0 || startDate === null}
          variant="primary"
          onClick={() => {
            props.handleAdd({ orders, startDate });
          }}
        >
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default AddPurchaseOrderModal;
