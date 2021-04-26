import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';
import api from '../../app/api';
import { getIdFromToken } from '../../app/jwtTokenUtils';
import SetPasswordModal from '../utilComponents/modals/SetPasswordModal'

const SupplierHomePage = () => {
    const [isPasswordSet, setIsPasswordSet] = useState(false);
    useEffect(() => {
        let id = getIdFromToken();
        api.get("http://localhost:8080/api/users/" + id)
            .then((res) => {
                setIsPasswordSet(res.data.passwordChanged)
            })
    }, [])

    return (
        <div>

            <SetPasswordModal show={!isPasswordSet} onPasswordSet={() => setIsPasswordSet(true)}></SetPasswordModal>
        </div>
    )
}

export default SupplierHomePage