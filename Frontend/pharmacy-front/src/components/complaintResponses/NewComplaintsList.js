import React, { useEffect, useState } from 'react'
import ComplaintResponse from './ComplaintResponse'
import api from '../../app/api'

const NewComplaintsList = () => {
    const [complaints, setComplaints] = useState([])

    useEffect(() => {
        async function fetchComplaints() {
            api.get(`http://localhost:8080/api/complaints/`)
                .then((res) => {
                    setComplaints(res.data)
                })
        }
        fetchComplaints();
    }, [])

    return (
        <div>
            {complaints && complaints.map((c) => {
                return <ComplaintResponse key={c.id} complaint={c}></ComplaintResponse>
            })}
        </div>
    )
}

export default NewComplaintsList
