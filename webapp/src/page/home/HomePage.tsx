import React, {useState} from 'react';
import {
    TextField,
    Button,
    Card,
    CardContent,
    Typography,
    IconButton,
    List,
    ListItem,
    ListItemText,
    MenuItem,
    Select,
    FormControl,
    InputLabel,
    Box
} from '@mui/material';
import {Delete, Edit} from '@mui/icons-material';
import UserDetailsDialog from "../../components/home/UserDetailsDialog";

const initialUsers = [
    {id: 'user1', firstName: 'John', lastName: 'Doe', oib: '12345678901', cardStatus: 'REQUEST_NEW'},
    {id: 'user2', firstName: 'Jane', lastName: 'Smith', oib: '10987654321', cardStatus: 'APPROVED'},
];

const statuses = ['REQUEST_NEW', 'IN_PROGRESS', 'FAILED', 'APPROVED', 'REJECTED', 'DELIVERED', 'ACTIVE', 'NON_ACTIVE', 'CANCELLED'];

export default function HomePage() {
    const [users, setUsers] = useState(initialUsers);
    const [searchOib, setSearchOib] = useState('');
    const [newFirstName, setNewFirstName] = useState('');
    const [newLastName, setNewLastName] = useState('');
    const [newOib, setNewOib] = useState('');
    const [newStatus, setNewStatus] = useState('');
    const [statusDialogOpen, setStatusDialogOpen] = useState(false);
    const [selectedUser, setSelectedUser] = useState(null);

    const handleSearch = () => {
        alert(`This would perform a REST request for userId: ${searchOib}`);
    };

    const handleAddUser = () => {
        if (!newFirstName || !newLastName || !newOib || !newStatus) return;
        const newUser = {
            id: `${newFirstName.toLowerCase()}${newLastName.toLowerCase()}`,
            firstName: newFirstName,
            lastName: newLastName,
            oib: newOib,
            cardStatus: newStatus
        };
        setUsers([...users, newUser]);
        setNewFirstName('');
        setNewLastName('');
        setNewOib('');
        setNewStatus('');
    };

    const handleDeleteUser = (id) => {
        if (window.confirm("Are you sure you want to delete this user? This will delete the user and all card statuses!")) {
            setUsers(users.filter(user => user.id !== id));
        }
    };


    const handleUpdateStatus = () => {
        setUsers(users.map(user =>
            user.id === selectedUser.id ? {...user, status: newStatus} : user
        ));
        setStatusDialogOpen(false);
    };

    const handleOpenStatusDialog = (user) => {
        console.log("Opening dialog for user with id " + user.id)
        setSelectedUser(user);
        setStatusDialogOpen(true);
    };

    const handleUpdateUser = (updatedUser) => {
        setUsers((prev) =>
            prev.map((u) => (u.id === updatedUser.id ? updatedUser : u))
        );
        setSelectedUser(updatedUser);
    };

    const handleAddStatus = (newStatus) => {
        console.log("Add New status")
    };


    return (
        <div className="p-4 max-w-xl mx-auto">

            <Box display="flex" gap={2} alignItems="center" mb={2}>
                <TextField label="First Name" value={newFirstName} onChange={e => setNewFirstName(e.target.value)}
                           fullWidth size="small"/>
                <TextField label="Last Name" value={newLastName} onChange={e => setNewLastName(e.target.value)}
                           fullWidth size="small"/>
                <TextField label="OIB" value={newOib} onChange={e => setNewOib(e.target.value)} fullWidth size="small"/>
                <FormControl fullWidth size="small">
                    <InputLabel>Status</InputLabel>
                    <Select value={newStatus} onChange={e => setNewStatus(e.target.value)} label="Card Status">
                        {statuses.map(status => (
                            <MenuItem key={status} value={status}>{status}</MenuItem>
                        ))}
                    </Select>
                </FormControl>
                <Button variant="contained" disabled={!newFirstName || !newLastName || !newOib || !newStatus} onClick={handleAddUser}>Add</Button>
            </Box>

            <Box display="flex" gap={2} alignItems="center" mb={2}>
                <TextField
                    label="Search by User OIB"
                    value={searchOib}
                    onChange={e => setSearchOib(e.target.value)}
                    fullWidth
                    size="small"
                />
                <Button variant="contained" disabled={!searchOib} onClick={handleSearch}>
                    Search
                </Button>
            </Box>


            <Card>
                <CardContent>
                    { users.length > 0 ?
                        (
                            <List>
                                {users.map(user => (
                                    <ListItem key={user.id} secondaryAction={
                                        <>
                                            <IconButton onClick={() => handleOpenStatusDialog(user)}><Edit/></IconButton>
                                            <IconButton onClick={() => handleDeleteUser(user.id)}><Delete/></IconButton>
                                        </>
                                    }>
                                        <ListItemText primary={`${user.firstName} ${user.lastName}`}
                                                      secondary={`OIB: ${user.oib} | Card status: ${user.cardStatus}`}/>
                                    </ListItem>
                                ))}
                            </List>
                        ): (
                            <Typography variant="body1" color="textSecondary" align="center" sx={{ p: 2 }}>
                                Search users to get result
                            </Typography>
                        )
                    }

                </CardContent>
            </Card>

            {selectedUser && (
                <UserDetailsDialog
                    open={statusDialogOpen}
                    onClose={() => setStatusDialogOpen(false)}
                    user={selectedUser}
                    statuses={statuses}
                    onUpdateUser={handleUpdateUser}
                    onAddStatus={handleAddStatus}
                />
            )}
        </div>
    );
}