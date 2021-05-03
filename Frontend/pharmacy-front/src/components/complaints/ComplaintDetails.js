import React from 'react'

const ComplaintDetails = ({ complaint }) => {
    return (
        <div>
            <p>{complaint.content}</p>
        </div>
    )
}

export default ComplaintDetails
