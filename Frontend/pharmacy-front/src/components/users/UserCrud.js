import React from 'react'
import UserTable from './UserTable'
import UserSearchPanel from './UserSearchPanel'

const UserCrud = props => {
    return (
        <div>
            <UserSearchPanel> </UserSearchPanel>
            <UserTable initialUserType={"PHARMACIST"}> </UserTable>
        </div>
    )
}

export default UserCrud