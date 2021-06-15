import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

import {
  Tab,
  Row,
  Col,
  Container,
  Card,
  ListGroup,
  ListGroupItem,
  Pagination,
  Nav,
  Form,
  Button,
} from "react-bootstrap";
import { StarFill, Search, Reply } from "react-bootstrap-icons";
import Dropdown from "react-bootstrap/Dropdown";

import axios from "../../app/api";

import "../../styling/pharmaciesAndMedicines.css";

function PharmaciesView() {
  const [pharmacies, setPharmacies] = useState([]);
  const [backup, setBackup] = useState([]);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedPharmacies, setShowedPharmacies] = useState([]);
  const [fsearch, setFSearch] = useState("");
  const [filterGrade, setFilterGrade] = useState("");
  const [filterDistance, setFilterDistance] = useState("");
  const [sorter, setSorter] = useState("none");
  const [ascDesc, setAscDesc] = useState("none");
  const [reload, setReload] = useState(false);
  const [triggered, setTriggered] = useState("none");
  let div = document.getElementById("my__transition__div");
  let blocker = document.getElementById("my__blocker");

  useEffect(() => {
    async function fetchPharmacies() {
      const request = await axios.get("/api/pharmacy/");
      setPharmacies(request.data);
      setBackup(request.data);

      return request;
    }
    fetchPharmacies();
  }, []);

  const formSearch = (event) => {
    event.preventDefault();

    if (fsearch.length === 0) {
      axios.get("/api/pharmacy/").then((resp) => {
        setPharmacies(resp.data);
        setBackup(resp.data);
      }).catch(() => { });
    } else {
      axios
        .get("/api/pharmacy/search", {
          params: { searchValue: fsearch },
        })
        .then((resp) => {
          setPharmacies(resp.data);
          setBackup(resp.data);
        }).catch(() => { });
    }
  };

  const formFilter = (event) => {
    event.preventDefault();

    if (filterGrade === "" && filterDistance === "") {
      setPharmacies(backup);
      return;
    }

    var filtered = [];

    if (filterGrade !== "") {
      for (let i = 0; i < backup.length; i++) {
        if (filterGrade === "HIGH" && backup[i].avgGrade > 3) {
          filtered.push(backup[i]);
        } else if (filterGrade === "MEDIUM" && backup[i].avgGrade === 3) {
          filtered.push(backup[i]);
        } else if (filterGrade === "LOW" && backup[i].avgGrade < 3) {
          filtered.push(backup[i]);
        }
      }

      setPharmacies(filtered);
      if (filtered.length === 0) return;
    }

    if (filterDistance !== "") {
      if (navigator.geolocation) {
        // Ako odredjeni browser podrzava geolocation
        navigator.geolocation.getCurrentPosition((position) => {
          let filteringList = filtered.length !== 0 ? [...filtered] : backup;
          filtered = [];
          for (let i = 0; i < filteringList.length; i++) {
            if (
              filterDistance === "5LESS" &&
              calculateDistance(
                filteringList[i].address,
                position.coords.longitude,
                position.coords.latitude
              ) <= 5
            ) {
              filtered.push(filteringList[i]);
            } else if (
              filterDistance === "10LESS" &&
              calculateDistance(
                filteringList[i].address,
                position.coords.longitude,
                position.coords.latitude
              ) <= 10
            ) {
              filtered.push(filteringList[i]);
            } else if (
              filterDistance === "10HIGHER" &&
              calculateDistance(
                filteringList[i].address,
                position.coords.longitude,
                position.coords.latitude
              ) > 10
            ) {
              filtered.push(filteringList[i]);
            }
          }

          setPharmacies(filtered);
        });
      }
    }
  };

  const calculateDistance = (address, lon2, lat2) => {
    let lat1 = address.location.latitude;
    let lon1 = address.location.longitude;

    if (lat1 == lat2 && lon1 == lon2) {
      return 0;
    } else {
      var radlat1 = (Math.PI * lat1) / 180;
      var radlat2 = (Math.PI * lat2) / 180;
      var theta = lon1 - lon2;
      var radtheta = (Math.PI * theta) / 180;
      var dist =
        Math.sin(radlat1) * Math.sin(radlat2) +
        Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
      if (dist > 1) {
        dist = 1;
      }
      dist = Math.acos(dist);
      dist = (dist * 180) / Math.PI;
      dist = dist * 60 * 1.1515 * 1.609344;
      console.log(dist);
      return dist;
    }
  };

  const resetSearch = function () {
    axios
      .get("/api/pharmacy/")
      .then((resp) => {
        setPharmacies(resp.data);
        setBackup(resp.data);
      })
      .catch(() => setPharmacies([]));
    setFSearch("");
  };

  useEffect(() => {
    let maxNumber = Math.floor(pharmacies?.length / 12) - 1;
    if (pharmacies?.length / 12 - 1 > maxNumber) {
      maxNumber = maxNumber + 1;
    }
    setMaxPag(maxNumber);
  }, [pharmacies, reload]);

  useEffect(() => {
    let first = pagNumber * 12;
    let max = pharmacies.length < first + 12 ? pharmacies?.length : first + 12;
    setShowedPharmacies(pharmacies?.slice(first, max));
  }, [pharmacies, pagNumber, reload]);

  let handleSlideLeft = () => {
    if (pagNumber !== 0) {
      setPugNummber(pagNumber - 1);
    }
  };

  let handleSlideRight = () => {
    if (pagNumber !== maxPag) {
      setPugNummber(pagNumber + 1);
    }
  };

  const doSorting = (type, value) => {
    if (type === "sorter") setSorter(value);
    else setAscDesc(value);

    if (value === "none") return;
    if (type === "sorter" && ascDesc === "none") return;
    if (type === "ascDesc" && sorter === "none") return;

    pharmacies.sort(function (ph1, ph2) {
      if (
        (type === "sorter" && value === "grade") ||
        (type === "ascDesc" && sorter === "grade")
      )
        return ph1?.avgGrade - ph2?.avgGrade;
      if (
        (type === "sorter" && value === "pharmacy name") ||
        (type === "ascDesc" && sorter === "pharmacy name")
      )
        return ph1?.name.localeCompare(ph2?.name);
      if (
        (type === "sorter" && value === "city name") ||
        (type === "ascDesc" && sorter === "city name")
      )
        return ph1?.address?.city.localeCompare(ph2?.address?.city);
    });
    if (
      (type === "ascDesc" && value === "descending") ||
      (type === "sorter" && ascDesc === "descending")
    )
      pharmacies.reverse();
    setReload(!reload);
  };

  function hideBlocker() {
    blocker.classList.remove("blocker");
    div.classList.remove("my__transition__div__move");
  }

  return (
    <Tab.Pane eventKey="first">
      <div id="my__blocker" onClick={() => hideBlocker()}></div>
      <div id="my__transition__div" className="my__flex">
        <Form
          onSubmit={formSearch}
          style={{
            display: triggered === "search" ? "block" : "none",
            width: "50%",
          }}
        >
          <Form.Group className="my__flex">
            <Form.Control
              type="text"
              name="searchValue"
              value={fsearch}
              onChange={(e) => setFSearch(e.target.value)}
              placeholder="Enter name or place..."
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
        <div
          className="my__flex"
          style={{ display: triggered === "sorter" ? "flex" : "none" }}
        >
          <Form.Label style={{ marginRight: "20px" }}>Sorter: </Form.Label>
          <Dropdown>
            <Dropdown.Toggle
              className="my__search__buttons"
              id="dropdown-basic"
            >
              {sorter}
            </Dropdown.Toggle>

            <Dropdown.Menu>
              <Dropdown.Item
                onClick={() => {
                  doSorting("sorter", "none");
                }}
              >
                none
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  doSorting("sorter", "pharmacy name");
                }}
              >
                pharmacy name
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  doSorting("sorter", "city name");
                }}
              >
                city name
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  doSorting("sorter", "grade");
                }}
              >
                grade
              </Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
          <Dropdown>
            <Dropdown.Toggle
              className="my__search__buttons"
              id="dropdown-basic"
            >
              {ascDesc}
            </Dropdown.Toggle>

            <Dropdown.Menu>
              <Dropdown.Item
                onClick={() => {
                  doSorting("ascDesc", "none");
                }}
              >
                none
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  doSorting("ascDesc", "ascending");
                }}
              >
                ascending
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  doSorting("ascDesc", "descending");
                }}
              >
                descending
              </Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </div>
        <div style={{ display: triggered === "filter" ? "block" : "none" }}>
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
            <Form.Group controlId="distanceSelect">
              <Form.Label>Distance</Form.Label>
              <Form.Control
                as="select"
                onChange={(event) => setFilterDistance(event.target.value)}
                defaultValue={""}
              >
                <option value={""}>Not Selected</option>
                <option value="5LESS">5km(Less)</option>
                <option value="10LESS">10km(Less)</option>
                <option value="10HIGHER">10km(Higher)</option>
              </Form.Control>
            </Form.Group>
            <Button type="submit" className="my__search__buttons">
              <Search />
            </Button>
          </Form>
        </div>
      </div>

      <div className="my__flex" style={{ paddingTop: "30px" }}>
        <Button
          className="my__search__buttons"
          onClick={() => {
            div.classList.add("my__transition__div__move");
            blocker.classList.add("blocker");
            setTriggered("search");
          }}
        >
          Search
        </Button>
        <Button
          className="my__search__buttons"
          onClick={() => {
            div.classList.add("my__transition__div__move");
            blocker.classList.add("blocker");
            setTriggered("sorter");
          }}
        >
          Sorter
        </Button>
        <Button
          className="my__search__buttons"
          onClick={() => {
            div.classList.add("my__transition__div__move");
            blocker.classList.add("blocker");
            setTriggered("filter");
          }}
        >
          Filter
        </Button>
      </div>
      <Container fluid>
        <Row>
          {showedPharmacies.length === 0 && (
            <Row className="justify-content-center m-3 align-items-center">
              <h3>No result!</h3>
            </Row>
          )}
          {showedPharmacies &&
            showedPharmacies.map((pharmacy, index) => {
              return (
                <Col className="my__flex" key={index} lg={3} md={6} sm={12}>
                  <Nav.Link
                    as={Link}
                    className="my__nav__link__card"
                    to={`/pharmacy/${pharmacy.id}`}
                  >
                    <Card className="my__card" style={{ width: "18rem" }}>
                      <Card.Body>
                        <Card.Title>{pharmacy.name}</Card.Title>
                        <Card.Text>{pharmacy.description}</Card.Text>
                      </Card.Body>
                      <ListGroup className="list-group-flush">
                        <ListGroupItem className="my__flex">
                          {[...Array(Math.ceil(pharmacy.avgGrade))].map(() => (
                            <StarFill className="my__star" />
                          ))}
                        </ListGroupItem>
                        <ListGroupItem className="my__flex">
                          {pharmacy.address.street +
                            ", " +
                            pharmacy.address.city}
                        </ListGroupItem>
                      </ListGroup>
                    </Card>
                  </Nav.Link>
                </Col>
              );
            })}
        </Row>

        <Row className="my__row__pagination">
          <Col className="my__flex">
            <Pagination size="lg">
              <Pagination.Prev
                disabled={pagNumber === 0}
                onClick={handleSlideLeft}
              />
              <Pagination.Item disabled>{pagNumber}</Pagination.Item>
              <Pagination.Next
                disabled={pagNumber === maxPag}
                onClick={handleSlideRight}
              />
            </Pagination>
          </Col>
        </Row>
      </Container>
    </Tab.Pane>
  );
}

export default PharmaciesView;
