import React, { useEffect, useState } from 'react'
import OSM from 'ol/source/OSM'
import { Feature, Map, View } from 'ol'
import TileLayer from 'ol/layer/Tile'
import Vector from 'ol/source/Vector'
import Style from 'ol/style/Style'
import Circle from 'ol/style/Circle'
import Icon from 'ol/style/Icon'
import { fromLonLat, transform } from 'ol/proj'
import Point from 'ol/geom/Point'
import axios from 'axios'
import Fill from 'ol/style/Fill'
import Stroke from 'ol/style/Stroke'
import Projection from 'ol/proj/Projection'
import VectorLayer from 'ol/layer/Vector'

const Location = ({ onChange, defAddress }) => {
    const [address, setAddress] = useState({})
    const [location, setLocation] = useState({})
    const [locationPoint, setLocationPoint] = useState()
    const [coordinates, setCoordinates] = useState([20.4820788, 44.7901252])
    const [map, setMap] = useState()

    useEffect(() => {
        if (!!defAddress) {
            setAddress(defAddress)
            setCoordinates([defAddress.location.longitude, defAddress.location.latitude])
        }

        let lp = new Feature({
            type: 'icon',
            geometry: new Point(fromLonLat(coordinates))
        });
        setLocationPoint(lp)
    }, [])

    useEffect(() => {
        if (locationPoint && !map)
            initMap()
    }, [locationPoint])

    const initMap = () => {

        let iconStyle = new Style({
            image: new Icon(({
                anchor: [0.5, 1],
                src: "http://cdn.mapmarker.io/api/v1/pin?text=P&size=30&hoffset=1"
            }))
        });

        locationPoint.setStyle(iconStyle)

        let vectorSource = new Vector({
            features: [locationPoint]
        });

        let vectorLayer = new VectorLayer({
            source: vectorSource
        })

        let m = new Map({
            target: document.getElementById('pharmacyMap'),
            layers: [
                new TileLayer({
                    source: new OSM()
                }),
                vectorLayer
            ],
            view: new View({
                center: fromLonLat(coordinates),
                zoom: 16,
                min: 4,
                max: 20
            }),
        })

        m.on('singleclick', (event) => {
            let newCoordinates = event.coordinate;
            let transformed = transform(newCoordinates, "EPSG:3857", "EPSG:4326");
            locationPoint.getGeometry().setCoordinates(transformed)
            let callString = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat="
                + transformed[1] + "&lon=" + transformed[0];
            setCoordinates(transformed)
            setNewLocation(callString);
        });

        setMap(m)
    }

    const setNewLocation = (callString) => {
        axios
            .get(callString)
            .then((response) => {
                let jsonData = response.data;

                parseJsonData(jsonData);
            });
    }

    const parseJsonData = (data) => {
        let addressData = data["address"]

        let l = {
            "longitude": data ? data["lon"] : "",
            "latitude": data ? data["lat"] : "",
        };

        let a = {
            "city": addressData ? addressData["city"] : "",
            "street": addressData ? `${addressData["road"]} ${addressData["house_number"]}` : "",
            "country": addressData ? addressData["country"] : "",
            "location": l
        };

        onChange(a)

        setLocation(l)
        setAddress(a)
    }

    return (
        <div className="container-fluid">
            <div className="row h-100 w-100 mt-4">
                <div className="col-lg-12" style={{ height: '300px', width: '500px' }}>
                    <div className="h-100 w-100" id="pharmacyMap"></div>
                </div>
            </div>
            <div className="row w-100 mt-4">
                <div className="col-lg-6">
                    <label for="longPharmacy">Longitude</label>
                    <input className="form-control" id="longPharmacy" value={location.longitude} defaultValue={defAddress ? defAddress.location.longitude : ""} disabled />
                </div>
                <div className="col-lg-6">
                    <label for="latPharmacy">Latitude</label>
                    <input className="form-control" id="latPharmacy" value={location.latitude} defaultValue={defAddress ? defAddress.location.latitude : ""} disabled />
                </div>
            </div>
            <div className="row w-100 mt-4">
                <div className="col-lg-4">
                    <label for="streetPharmacy">Street</label>
                    <input className="form-control" id="streetPharmacy" value={address.street} defaultValue={defAddress ? defAddress.street : ""} disabled />
                </div>
                <div className="col-lg-4">
                    <label for="cityPharmacy">City</label>
                    <input className="form-control" id="cityPharmacy" value={address.city} defaultValue={defAddress ? defAddress.city : ""} disabled />
                </div>
                <div className="col-lg-4">
                    <label for="countryPharmacy">Country</label>
                    <input className="form-control" id="countryPharmacy" value={address.country} defaultValue={defAddress ? defAddress.country : ""} disabled />
                </div>
            </div>
        </div>
    )
}

export default Location
