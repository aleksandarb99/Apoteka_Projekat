import React, { useState, useEffect } from "react";

import { Tab, Row, Col, Dropdown, InputGroup, Form } from "react-bootstrap";

import axios from "../../app/api";

import { Line, Bar, Radar, Doughnut, PolarArea, Pie } from "react-chartjs-2";
import { useToasts } from "react-toast-notifications";

function DisplayReports({ pharmacyDetails }) {
  const { addToast } = useToasts();
  const [chartData, setChartData] = useState(null);

  const [selectedChart, setSelectedChart] = useState("Bar");
  const [selectedPeriod, setSelectedPeriod] = useState("Monthly");
  const [profitDisplay, setProfitDisplay] = useState("HiddenProfit");

  const [appointmentsData, setAppointmentsData] = useState(null);
  const [drugConsumptionData, setDrugConsumptionData] = useState(null);
  const [profitData, setProfitData] = useState(null);

  const [duration, setDuration] = useState(0);

  const properties = {
    height: 400,
    width: 600,
    options: {
      maintainAspectRatio: false,
      scales: { yAxes: [{ ticks: { beginAtZero: true } }] },
      plugins: {
        title: {
          display: true,
          text: "Appointments and medicine consumption",
        },
      },
    },
  };

  async function fetchDataOfAppointmentsForReport() {
    const request = await axios.get(
      `/api/appointment/report/${pharmacyDetails?.id}/${selectedPeriod}`
    ).catch(() => { });
    setAppointmentsData(!!request ? request.data : null);
    return request;
  }

  async function fetchDataForDrugConsumptionForReport() {
    const request = await axios.get(
      `/api/medicine-reservation/report/${pharmacyDetails?.id}/${selectedPeriod}`
    ).catch(() => { });
    setDrugConsumptionData(!!request ? request.data : null);
    return request;
  }

  async function fetchProfitReport() {
    const request = await axios.get(
      `/api/pharmacy/report/${pharmacyDetails?.id}/${selectedPeriod}/${duration}`
    ).catch(() => { });
    setProfitData(!!request ? request.data : null);
    return request;
  }

  useEffect(() => {
    if (pharmacyDetails != undefined) {
      fetchDataOfAppointmentsForReport();
      fetchDataForDrugConsumptionForReport();
      if (profitDisplay == "DisplayProfit" && duration != 0)
        fetchProfitReport();
    }
  }, [pharmacyDetails, selectedPeriod, profitDisplay, duration]);

  useEffect(() => {
    if (appointmentsData != null && drugConsumptionData != null) {
      if (profitDisplay === "DisplayProfit" && profitData != null) drawGraph();
      if (profitDisplay === "HiddenProfit") drawGraph();
    }
  }, [appointmentsData, drugConsumptionData, profitData, selectedChart]);

  let drawGraph = () => {
    let datasets = [];
    let labels = Object.keys(appointmentsData);

    let colors = genRandomColors(labels.length);
    let dataset1 = {
      label: "Number of appointments",
      data: Object.values(appointmentsData),
      backgroundColor: colors,
      borderColor: "black",
      borderWidth: 2,
    };
    datasets.push(dataset1);

    let colors2;
    if (
      selectedChart === "Bar" ||
      selectedChart === "Line" ||
      selectedChart === "Radar"
    )
      colors2 = genRandomColors(Object.keys(drugConsumptionData).length);
    else colors2 = colors;

    let dataset2 = {
      label: "Medicine consumption",
      data: Object.values(drugConsumptionData),
      backgroundColor: colors2,
      borderColor: "black",
      borderWidth: 2,
    };

    datasets.push(dataset2);

    if (profitDisplay === "DisplayProfit") {
      let colors3;
      if (
        selectedChart === "Bar" ||
        selectedChart === "Line" ||
        selectedChart === "Radar"
      )
        colors3 = genRandomColors(Object.keys(profitData).length);
      else colors3 = colors;

      let dataset3 = {
        label: "Profit",
        data: Object.values(profitData),
        backgroundColor: colors3,
        borderColor: "black",
        borderWidth: 2,
      };

      datasets.push(dataset3);
    }

    setChartData({ labels, datasets });
  };

  let genRandomColors = (length) => {
    if (
      selectedChart === "Bar" ||
      selectedChart === "Line" ||
      selectedChart === "Radar"
    ) {
      return "#" + Math.floor(Math.random() * 16777215).toString(16);
    }
    let array = [];
    let i;
    let randomColor;
    for (i = 0; i < length; i++) {
      randomColor = Math.floor(Math.random() * 16777215).toString(16);
      array.push("#" + randomColor);
    }
    return array;
  };

  let getMax = () => {
    if (selectedPeriod === "Monthly") return 12;
    else if (selectedPeriod === "Yearly") return 10;
    else return 4;
  };

  const charTypes = ["Line", "Bar", "Radar", "Doughnut", "PolarArea", "Pie"];
  const periods = ["Monthly", "Quarterly", "Yearly"];
  const profit = ["HiddenProfit", "DisplayProfit"];

  return (
    <Tab.Pane eventKey="sixth">
      <h1 className="content-header">Reports</h1>
      <hr></hr>
      <Row>
        <Col className="center">
          <h4>Average grade: {pharmacyDetails?.avgGrade}</h4>
        </Col>
        <Col className="center">
          <h4>
            Average grades of workers can be seen on tab 'Pharmacists and
            dermatologists'
          </h4>
        </Col>
      </Row>
      <hr></hr>
      <Row>
        <Col>
          <Dropdown>
            <Dropdown.Toggle variant="info" id="dropdown-basic">
              {selectedChart}
            </Dropdown.Toggle>

            <Dropdown.Menu>
              {charTypes.map((item) => (
                <Dropdown.Item
                  active={selectedChart == item}
                  onClick={() => {
                    setSelectedChart(item);
                  }}
                >
                  {item}
                </Dropdown.Item>
              ))}
            </Dropdown.Menu>
          </Dropdown>
        </Col>
        <Col>
          <Dropdown>
            <Dropdown.Toggle variant="info" id="dropdown-basic">
              {selectedPeriod}
            </Dropdown.Toggle>

            <Dropdown.Menu>
              {periods.map((item) => (
                <Dropdown.Item
                  active={selectedPeriod == item}
                  onClick={() => {
                    setSelectedPeriod(item);
                    setDuration(0);
                  }}
                >
                  {item}
                </Dropdown.Item>
              ))}
            </Dropdown.Menu>
          </Dropdown>
        </Col>
        <Col>
          <Dropdown>
            <Dropdown.Toggle variant="info" id="dropdown-basic">
              {profitDisplay}
            </Dropdown.Toggle>

            <Dropdown.Menu>
              {profit.map((item) => (
                <Dropdown.Item
                  active={profitDisplay == item}
                  onClick={() => {
                    if (duration == 0 && profitDisplay == "HiddenProfit")
                      addToast(
                        "Duration cannot be 0! Change it if u want to see profit!",
                        {
                          appearance: "warning",
                        }
                      );
                    else setProfitDisplay(item);
                  }}
                >
                  {item}
                </Dropdown.Item>
              ))}
            </Dropdown.Menu>
          </Dropdown>
        </Col>
        <Col>
          <InputGroup className="mb-3">
            <InputGroup.Prepend>
              <InputGroup.Text id="basic-addon3">
                Duration in past
              </InputGroup.Text>
            </InputGroup.Prepend>
            <Form.Control
              id="basic-url"
              type="number"
              value={duration}
              onChange={(event) =>
                setDuration(Number.parseInt(event.target.value))
              }
              max={getMax()}
              min="0"
            />
          </InputGroup>
        </Col>
      </Row>
      <hr></hr>
      <Row>
        <Col className="center">
          {selectedChart == "Line" && <Line data={chartData} {...properties} />}
          {selectedChart == "Bar" && <Bar data={chartData} {...properties} />}
          {selectedChart == "Radar" && (
            <Radar data={chartData} {...properties} />
          )}
          {selectedChart == "Doughnut" && (
            <Doughnut data={chartData} {...properties} />
          )}
          {selectedChart == "PolarArea" && (
            <PolarArea data={chartData} {...properties} />
          )}
          {selectedChart == "Pie" && <Pie data={chartData} {...properties} />}
        </Col>
      </Row>
      <hr></hr>
    </Tab.Pane>
  );
}

export default DisplayReports;
