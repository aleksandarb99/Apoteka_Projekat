import React from "react";
import { Button } from "react-bootstrap";

export default function UserRow({ user, onClick, editClick, detailsClick, deleteClick }) {
	return (
		<tr onClick={onClick} key={user.id}>
			<td>{user.firstName}</td>
			<td>{user.lastName}</td>
			<td>{user.email}</td>
			<td>{user.telephone}</td>
			<td>{user.address.city + ' ' + user.address.street}</td>
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
