import React, { useState, useEffect } from "react";

import { Search, Reply } from "react-bootstrap-icons";

import { Tab, Row, Col, Button, Table, Form } from "react-bootstrap";

import axios from "../../app/api";

import "../../styling/pharmaciesAndMedicines.css";
import "../../styling/pharmacy.css";
import AddingWorkerModal from "./AddingWorkerModal";
import DetailsOfWorkerModal from "./DetailsOfWorkerModal";
import { useToasts } from "react-toast-notifications";

function DisplayWorkers({ idOfPharmacy }) {
  const { addToast } = useToasts();
  const [pharmacies, setPharmacies] = useState([]);
  const [pharamcyNameMap, setPharamcyNameMap] = useState([]);

  const [workers, setWorkers] = useState([]);
  const [showedWorkers, setShowedWorkers] = useState([]);

  const [selectedRowId, setSelectedRowId] = useState(-1);

  const [selectedWorker, setSelectedWorker] = useState(-1);

  const [addModalShow, setAddModalShow] = useState(false);
  const [detailsModalShow, setDetailsModalShow] = useState(false);

  // -------------------
  const [fsearch, setFSearch] = useState("");
  const [filterGrade, setFilterGrade] = useState("");
  const [filterPharmacyName, setFilterPharmacyName] = useState("");

  async function fetchNames() {
    const request = await axios.get(
      `http://localhost:8080/api/workplace/pharmacies/all/`
    );
    setPharamcyNameMap(request.data);
    return request;
  }

  useEffect(() => {
    async function fetchPharmacies() {
      const request = await axios.get("http://localhost:8080/api/pharmacy/");
      setPharmacies(request.data);

      return request;
    }
    fetchPharmacies();
  }, []);

  async function fetchWorkers() {
    const request = await axios
      .get(`http://localhost:8080/api/workplace/bypharmacyid/${idOfPharmacy}`)
      .then((resp) => {
        setWorkers(resp.data);
      });

    return request;
  }

  async function addWorker(id, dto) {
    const request = await axios
      .post(
        `http://localhost:8080/api/workplace/addworker/bypharmacyid/${idOfPharmacy}/${id}`,
        dto
      )
      .then((res) => {
        fetchWorkers();
        addToast(res.data, {
          appearance: "success",
        });
      })
      .catch((err) => {
        addToast(err.response.data, {
          appearance: "error",
        });
      });

    return request;
  }

  async function removeWorker() {
    const request = await axios
      .delete(
        `http://localhost:8080/api/workplace/removeworker/bypharmacyid/${idOfPharmacy}/${selectedRowId}`
      )
      .then((res) => {
        fetchWorkers();
        addToast(res.data, {
          appearance: "success",
        });
      })
      .catch((err) => {
        addToast(err.response.data, {
          appearance: "error",
        });
      });
    return request;
  }

  useEffect(() => {
    if (idOfPharmacy != undefined) {
      fetchWorkers();
      fetchNames();
    }
  }, [idOfPharmacy]);

  let handleClick = (requestId, workerId) => {
    setSelectedRowId(requestId);
    setSelectedWorker(workerId);
  };

  let handleRemove = () => {
    removeWorker();
    setSelectedRowId(-1);
    setSelectedWorker(-1);
  };

  let handleAddModalSave = (selectedMedicineId, dto) => {
    setAddModalShow(false);
    addWorker(selectedMedicineId, dto);
  };

  let handleInfo = () => {
    setDetailsModalShow(true);
  };

  const formSearch = (event) => {
    event.preventDefault();

    if (fsearch.length === 0) {
      fetchWorkers();
    } else {
      axios
        .get(`http://localhost:8080/api/workplace/search/${idOfPharmacy}`, {
          params: { searchValue: fsearch },
        })
        .then((resp) => {
          setWorkers(resp.data);
        });
    }
  };

  const resetSearch = function () {
    setFSearch("");
    fetchWorkers();
  };

  const formFilter = (event) => {
    event.preventDefault();
    filter();
  };

  useEffect(() => {
    filter();
  }, [workers]);

  // UseEffect za filter
  useEffect(() => {
    if (showedWorkers == null) setShowedWorkers(workers);
    else {
      if (filterPharmacyName === "" && filterGrade == "") return;

      if (filterPharmacyName != "") {
        const result = showedWorkers.filter((worker) => {
          if (pharamcyNameMap[worker.worker.id].includes(filterPharmacyName))
            return true;
          return false;
        });
        if (showedWorkers.length != result.length) setShowedWorkers(result);
      }

      if (filterGrade != "") {
        if (filterGrade === "LOW") {
          const result = showedWorkers.filter(
            (worker) => worker.worker.avgGrade < 3
          );
          if (showedWorkers.length != result.length) setShowedWorkers(result);
        } else if (filterGrade === "MEDIUM") {
          const result = showedWorkers.filter(
            (worker) => worker.worker.avgGrade == 3
          );
          if (showedWorkers.length != result.length) setShowedWorkers(result);
        } else if (filterGrade === "HIGH") {
          const result = showedWorkers.filter(
            (worker) => worker.worker.avgGrade > 3
          );
          if (showedWorkers.length != result.length) setShowedWorkers(result);
        }
      }
    }
  }, [showedWorkers]);

  let filter = () => {
    setShowedWorkers(null);
  };

  return (
    <Tab.Pane eventKey="second">
      <Row>
        <Col>
          <Form onSubmit={formSearch}>
            <Form.Group className="my__flex" style={{ marginTop: "20px" }}>
              <Form.Control
                type="text"
                name="searchValue"
                value={fsearch}
                onChange={(e) => setFSearch(e.target.value)}
                placeholder="Enter name or lastname..."
              />

              <Button type="submit" className="my__search__buttons">
                <Search />
              </Button>
              <Button
                className="my__search__buttons my__flex"
                onClick={resetSearch}
              >
                Reset <Reply />
              </Button>
            </Form.Group>
          </Form>
        </Col>

        <Col>
          <Form onSubmit={formFilter} className="my__div__filter">
            <Form.Group controlId="gradeSelect">
              <Form.Label>Grade</Form.Label>
              <Form.Control
                as="select"
                onChange={(event) => setFilterGrade(event.target.value)}
                defaultValue=""
              >
                <option value="">Not Selected</option>
                <option value="LOW">Low</option>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
              </Form.Control>
            </Form.Group>
            <Form.Group controlId="pharmacySelect">
              <Form.Label>Pharmacy name</Form.Label>
              <Form.Control
                as="select"
                onChange={(event) => setFilterPharmacyName(event.target.value)}
                defaultValue={""}
              >
                <option value="">Not Selected</option>
                {pharmacies?.length != 0 &&
                  pharmacies?.map((item, index) => (
                    <option value={item.name}>{item.name}</option>
                  ))}
              </Form.Control>
            </Form.Group>
            <Button type="submit" className="my__search__buttons">
              <Search />
            </Button>
          </Form>
        </Col>
      </Row>

      <Row>
        <Col>
          <Table bordered striped hover variant="dark">
            <thead>
              <tr>
                <th>#</th>
                <th>Type</th>
                <th>Firstname</th>
                <th>Lastname</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Avg. grade</th>
              </tr>
            </thead>
            <tbody>
              {" "}
              {showedWorkers?.length != 0 &&
                showedWorkers?.map((item, index) => (
                  <tr
                    onClick={() => {
                      handleClick(item.id, item.worker.id);
                    }}
                    className={`${
                      selectedRowId == item.id ? "selectedRow" : "pointer"
                    } `}
                  >
                    <td>{index + 1}</td>
                    <td>{item?.worker?.role?.name}</td>
                    <td>{item?.worker?.firstName}</td>
                    <td>{item?.worker?.lastName}</td>
                    <td>{item?.worker?.email}</td>
                    <td>{item?.worker?.telephone}</td>
                    <td>{item?.worker?.avgGrade}</td>
                  </tr>
                ))}
            </tbody>
          </Table>

          <div className="center">
            {idOfPharmacy != -1 && (
              <div>
                <Button
                  onClick={() => {
                    setAddModalShow(true);
                    setSelectedRowId(-1);
                  }}
                  variant="success"
                >
                  Add worker
                </Button>
                <Button
                  disabled={selectedRowId == -1}
                  onClick={handleRemove}
                  variant="danger"
                >
                  Remove worker
                </Button>

                <AddingWorkerModal
                  workersLength={workers?.length}
                  idOfPharmacy={idOfPharmacy}
                  show={addModalShow}
                  onHide={() => {
                    setAddModalShow(false);
                  }}
                  handleAdd={handleAddModalSave}
                />
              </div>
            )}
            <Button
              disabled={selectedRowId == -1}
              onClick={handleInfo}
              variant="info"
            >
              Show pharmacies
            </Button>
            <DetailsOfWorkerModal
              map={pharamcyNameMap}
              workerId={selectedWorker}
              show={detailsModalShow}
              handleClose={() => {
                setDetailsModalShow(false);
              }}
              onHide={() => {
                setDetailsModalShow(false);
              }}
            />
          </div>
        </Col>
      </Row>
    </Tab.Pane>
  );
}

export default DisplayWorkers;
