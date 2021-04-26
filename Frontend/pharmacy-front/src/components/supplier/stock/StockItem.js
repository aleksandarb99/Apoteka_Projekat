import React from 'react'

const StockItem = ({ stockItem }) => {
    return (
        <div>
            <p>{`${stockItem.medicineName}  --  ${stockItem.amount}`}</p>
        </div>
    )
}

export default StockItem
