import axios from 'axios'
import { Feature, Map, View } from 'ol'
import Point from 'ol/geom/Point'
import TileLayer from 'ol/layer/Tile'
import VectorLayer from 'ol/layer/Vector'
import { fromLonLat, transform } from 'ol/proj'
import OSM from 'ol/source/OSM'
import VectorSource from 'ol/source/Vector'
import Icon from 'ol/style/Icon'
import Style from 'ol/style/Style'
import React, { useEffect, useRef, useState } from 'react'

const MapElement = ({ onChange, defAddress }) => {
    const [map, setMap] = useState()
    const [featuresLayer, setFeaturesLayer] = useState()
    const [selectedCoord, setSelectedCoord] = useState(!!defAddress && !!defAddress.location ? [defAddress.location.longitude, defAddress.location.latitude] : [20.4820788, 44.7901252])
    const [address, setAddress] = useState({})
    const [location, setLocation] = useState({})

    const mapElement = useRef()

    const mapRef = useRef()
    mapRef.current = map

    useEffect(() => {
        let locationPoint = new Feature({
            type: 'icon',
            geometry: new Point(fromLonLat(selectedCoord)),
        });

        let iconStyle = new Style({
            image: new Icon(({
                anchor: [0.5, 1],
                src: "http://cdn.mapmarker.io/api/v1/pin?text=P&size=30&hoffset=1"
            }))
        });

        locationPoint.setStyle(iconStyle)

        let vectorSource = new VectorSource({
            features: [locationPoint]
        });

        const initFeatureLayer = new VectorLayer({
            source: vectorSource
        })

        const initMap = new Map({
            target: mapElement.current,
            layers: [
                new TileLayer({
                    source: new OSM()
                }),
                initFeatureLayer
            ],
            view: new View({
                center: fromLonLat(selectedCoord),
                zoom: 16,
                min: 4,
                max: 20
            }),
        })

        initMap.on('click', handleMapClick)
        setFeaturesLayer(initFeatureLayer);
        setMap(initMap);
    }, [])

    const handleMapClick = (event) => {

        let newCoordinates = event.coordinate;
        let transformed = transform(newCoordinates, "EPSG:3857", "EPSG:4326");

        setSelectedCoord(transformed)
        updateFields(transformed);
        console.log(transformed)
    }

    const updateFields = (coords) => {
        let url = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=" + coords[1] + "&lon=" + coords[0];
        axios
            .get(url)
            .then((response) => {
                let jsonData = response.data;
                parseJsonData(jsonData);
            }).catch(() => { });
    }

    const parseJsonData = (data) => {
        let addressData = data["address"]

        let l = {
            "longitude": data && data["lon"] ? data["lon"] : "",
            "latitude": data && data["lat"] ? data["lat"] : "",
        };

        let a = {
            "city": addressData && !!addressData["city"] ? addressData["city"] : "",
            "street": addressData && !!addressData["road"] ? `${addressData["road"]} ${!!addressData["house_number"] ? addressData["house_number"] : ""}` : "",
            "country": addressData && !!addressData["country"] ? addressData["country"] : "",
            "location": l
        };

        onChange(a)

        setLocation(l)
        setAddress(a)
    }

    useEffect(() => {

        if (!!selectedCoord && !!map) {

            let locationPoint = new Feature({
                type: 'icon',
                geometry: new Point(fromLonLat(selectedCoord)),
            });

            let iconStyle = new Style({
                image: new Icon(({
                    anchor: [0.5, 1],
                    src: "http://cdn.mapmarker.io/api/v1/pin?text=P&size=30&hoffset=1"
                }))
            });

            locationPoint.setStyle(iconStyle)

            let vectorSource = new VectorSource({
                features: [locationPoint]
            });

            const newFeatureLayer = new VectorLayer({
                source: vectorSource
            })

            map.removeLayer(featuresLayer);
            map.addLayer(newFeatureLayer);
            setFeaturesLayer(newFeatureLayer);
        }

    }, [selectedCoord])

    return (
        <div className="container-fluid">
            <div className="row h-100 w-100 mt-4">
                <div className="col-lg-12" style={{ height: '300px', width: '500px' }}>
                    <div className="h-100 w-100" id="pharmacyMap" ref={mapElement}></div>
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

export default MapElement
