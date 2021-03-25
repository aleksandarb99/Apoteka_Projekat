import React from "react";
import { Button } from "react-bootstrap";

export default function UserRow({ user, onClick, editClick, detailsClick, deleteClick }) {
	return (
		<tr onClick={onClick} key={user.id}>
			<td>{user.firstName}</td>
			<td>{user.lastName}</td>
			<td>{user.eMail}</td>
			<td>{user.phoneNumber}</td>
			<td>{user.firstName}</td>
			<td>
				<Button onClick={editClick}>Edit</Button>
				<Button variant="info">Details</Button>
				<Button variant="danger" onClick={deleteClick}>
					Delete
        </Button>
			</td>
		</tr>
	);
}
