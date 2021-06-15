import React, { useEffect, useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import { useToasts } from 'react-toast-notifications';
import api from '../../app/api';
import { getErrorMessage } from '../../app/errorHandler';
import Map from '../utilComponents/MapElement';

function EditPharmacyModal(props) {

    const [form, setForm] = useState({
        name: props.pharmacy.name || '',
        description: props.pharmacy.description || '',
        pointsForAppointment: props.pharmacy.pointsForAppointment || '',
        address: props.pharmacy.address
    })
    const [errors, setErrors] = useState({})
    const [address, setAddress] = useState({});
    const [pharmacyAdmins, setPharmacyAdmins] = useState([]);
    const [selected, setSelected] = useState();
    const { addToast } = useToasts();

    // useEffect(() => {
    //     async function fetchData() {
    //         await api
    //             .get('http://localhost:8080/api/users/?type=PHARMACY_ADMIN')
    //             .then((res) => {
    //                 setPharmacyAdmins(res.data);
    //             });
    //     }
    //     fetchData();
    // }, [])

    const showHandler = () => {
        setForm({
            ...form,
            name: props.pharmacy.name || '',
            description: props.pharmacy.description || '',
            pointsForAppointment: props.pharmacy.pointsForAppointment || '',
            address: props.pharmacy.address
        })
        setAddress(props.pharmacy.address)
        setSelected([])
    }

    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })

        if (!!errors[field]) setErrors({
            ...errors,
            [field]: null
        })
    }

    const validate = () => {
        const { name, description } = form
        const newErrors = {}
        // name errors
        if (!name || name === '') newErrors.name = 'Name cannot be blank!'
        else if (name.length > 40) newErrors.name = 'Name is too long!'
        // Description errors
        if (!description || description === '') newErrors.description = 'Description cannot be blank!'
        else if (description.length > 300) newErrors.description = 'Description is too long!'

        return newErrors
    }

    const validateAddress = () => {
        const { city, street, country } = address;

        // City errors
        if (!city || city === '') return false;
        // Street errors
        if (!street || street === '') return false;
        // Country errors
        if (!country || country === '') return false;

        return true;
    }

    const validateAdmin = () => {
        return (!!selected)
    }

    const handleSubmit = e => {
        e.preventDefault()
        e.stopPropagation()
        const newErrors = validate()

        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors)
        } else {
            if (!validateAddress())
                addToast("Please select valid address", { appearance: 'warning' })
            // else if (!validateAdmin())
            //     addToast("Please select pharmacy admin", { appearance: 'warning' })
            else
                sendPutRequest()
        }
    }

    const sendPutRequest = () => {
        let data = convertForm();
        api
            .put('http://localhost:8080/api/pharmacy/' + props.pharmacy.id, data)
            .then(() => {
                setForm({})
                addToast("Pharmacy updated successfully.", { appearance: 'success' });
                props.onSuccess()
                props.onHide()
            })
            .catch((err) => {
                addToast(getErrorMessage(err), { appearance: 'error' })
            })
    }

    const convertForm = () => {
        let data = {}
        data.id = props.pharmacy.id;
        data.pharmacyAdmin = selected;
        data.name = form.name;
        data.description = form.description;
        data.pointsForAppointment = form.pointsForAppointment;
        data.address = address
        return data;
    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" dialogClassName="modal-50w" centered onShow={showHandler}>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Edit pharmacy
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group>
                        <Form.Label>
                            Pharmacy admin
                        </Form.Label>
                        <Form.Control as="select" custom >
                            {!!props.pharmacy.admins && props.pharmacy.admins.map((pa) => {
                                return <option value={pa.id}>{pa.name}</option>
                            })}
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Name</Form.Label>
                        <Form.Control
                            type="text"
                            defaultValue={props.pharmacy.name}
                            isInvalid={!!errors.name}
                            onChange={e => setField('name', e.target.value)}
                        />
                        <Form.Control.Feedback type='invalid'>
                            {errors.name}
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="pharmacyDescription">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                            type="text"
                            defaultValue={props.pharmacy.description}
                            isInvalid={!!errors.description}
                            onChange={e => setField('description', e.target.value)}
                        />
                        <Form.Control.Feedback type='invalid'>
                            {errors.description}
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group>
                        <Form.Label>Points for appointment</Form.Label>
                        <Form.Control
                            type="number"
                            onChange={(event) => setField('pointsForAppointment', event.target.value)}
                            defaultValue={props.pharmacy.pointsForAppointment}
                            min={0}
                            max={100.00}
                            step={0.01}
                        />
                    </Form.Group>

                    <Map onChange={(address) => setAddress(address)} defAddress={props.pharmacy.address}></Map>

                    <Button variant="primary" onClick={handleSubmit}>Submit</Button>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    )
}

export default EditPharmacyModal;
