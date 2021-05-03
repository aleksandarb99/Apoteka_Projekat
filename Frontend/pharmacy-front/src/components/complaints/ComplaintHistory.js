import React, { useEffect, useState } from 'react'
import api from '../../app/api'
import { getIdFromToken } from '../../app/jwtTokenUtils'
import ComplaintDetails from './ComplaintDetails'

const ComplaintHistory = () => {
    const [complaints, setComplaints] = useState([])

    useEffect(() => {
        async function fetchComplaints() {
            api.get(`http://localhost:8080/api/complaints/${getIdFromToken()}`)
                .then((res) => {
                    setComplaints(res.data)
                })
        }
        fetchComplaints();
    }, [])

    return (
        <div>
            {complaints && complaints.map((c) => {
                return <ComplaintDetails key={c.id} complaint={c}></ComplaintDetails>
            })}
        </div>
    )
}

export default ComplaintHistory
