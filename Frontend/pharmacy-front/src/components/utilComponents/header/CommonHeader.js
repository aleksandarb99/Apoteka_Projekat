import React, { useEffect, useState } from 'react'
import AdminHeader from './AdminHeader'
import UnregistredHeader from './UnregistredHeader'
import PatientHeader from './PatientHeader'
import SupplierHeader from './SupplierHeader'
import PharmacyAdminHeader from './PharmacyAdminHeader'
import DermatologistHeader from './DermatologistHeader'
import PharmacistHeader from './PharmacistHeader'
import { getUserTypeFromToken } from '../../../app/jwtTokenUtils'
import { useSelector } from 'react-redux'

const CommonHeader = () => {
    const user = useSelector(state => state.user);
    let userType = getUserTypeFromToken();
    useEffect(() => {
        userType = getUserTypeFromToken();
    }, [user])

    let header;
    switch (userType) {
        case 'ADMIN':
            header = <AdminHeader></AdminHeader>
            break;
        case 'PHARMACIST':
            header = <PharmacistHeader></PharmacistHeader>
            break;
        case 'DERMATOLOGIST':
            header = <DermatologistHeader></DermatologistHeader>
            break;
        case 'SUPPLIER':
            header = <SupplierHeader></SupplierHeader>
            break;
        case 'PATIENT':
            header = <PatientHeader></PatientHeader>
            break;
        case 'PHARMACY_ADMIN':
            header = <PharmacyAdminHeader></PharmacyAdminHeader>
            break;
        default:
            header = <UnregistredHeader></UnregistredHeader>
            break;

    }

    return (
        <div>
            {header}
        </div>
    )
}

export default CommonHeader