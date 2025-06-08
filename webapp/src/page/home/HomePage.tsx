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
    Box
} from '@mui/material';
import {Delete, Edit} from '@mui/icons-material';
import UserDetailsDialog from "../../components/home/UserDetailsDialog";
import { userApi, cardStatusApi } from '../../api/apiClient';
import {
    CardStatusDto,
    CardStatusDtoCardStatusEnum,
    UserDto
} from "../../api/clients";


const statuses: string[] = Object.values(CardStatusDtoCardStatusEnum);

export default function HomePage() {
    const [noUsersText, setNoUsersText] = useState('Try searching users by OIB to get some result');
    const [users, setUsers] = useState<UserDto[]>([]);
    const [searchOib, setSearchOib] = useState('');
    const [newFirstName, setNewFirstName] = useState('');
    const [newLastName, setNewLastName] = useState('');
    const [newOib, setNewOib] = useState('');
    const [statusDialogOpen, setStatusDialogOpen] = useState(false);
    const [selectedUser, setSelectedUser] = useState<UserDto>(null);
    const [selectedUserStatuses, setSelectedUserStatuses] = useState<CardStatusDto[]>([]);


    const handleSearch = async () => {
        const response = await userApi.searchUsers({oib: searchOib});
        const retrievedUsers = response.data;
        setUsers(retrievedUsers)
        retrievedUsers.length == 0 ? setNoUsersText('No users found :/') : setNoUsersText('')
    };

    const handleAddUser = async () => {
        if (!newFirstName || !newLastName || !newOib) return;

        const newUser: UserDto = {
            firstName: newFirstName,
            lastName: newLastName,
            oib: newOib
        };

        const response = await userApi.createUser(newUser);

        setNewFirstName('');
        setNewLastName('');
        setNewOib('');
    };

    const handleDeleteUser = (oib) => {
        if (window.confirm("Are you sure you want to delete this user? This will delete the user and all card statuses!")) {
            userApi.deleteUser(oib);
            alert("User data deleted!");
        }
    };

    const handleOpenStatusDialog = (user) => {
        console.log("Opening dialog for user with id " + user.id)
        setSelectedUser(user);
        setStatusDialogOpen(true);
    };

    const handleCloseStatusDialog = () => {
        setSelectedUser(null);
        setSelectedUserStatuses([]);
        setStatusDialogOpen(false);
    };

    const handleUpdateUser = (updatedUser) => {
        setUsers((prev) =>
            prev.map((u) => (u.id === updatedUser.id ? updatedUser : u))
        );
        setSelectedUser(updatedUser);
    };

    const handleAddStatus= async (newStatus) => {
        console.log("Add New status ", newStatus, selectedUser.id);
        const cardStatusRequest : CardStatusDto = {'userId': selectedUser.id, 'cardStatus': newStatus as CardStatusDtoCardStatusEnum};
        const response = await cardStatusApi.createCardStatus(cardStatusRequest);
        const allUserStatuses = response.data;
        setSelectedUserStatuses(allUserStatuses);

        const updatedUser = {
            ...selectedUser,
            lastCardStatus: allUserStatuses[0]
        };

        setSelectedUser(updatedUser);
        setUsers((prev) =>
            prev.map((u) => (u.id === updatedUser.id ? updatedUser : u))
        );
    };


    return (
        <div className="p-4 max-w-xl mx-auto">

            <Box display="flex" gap={2} alignItems="center" mb={2}>
                <TextField label="First Name" value={newFirstName} onChange={e => setNewFirstName(e.target.value)}
                           fullWidth size="small"/>
                <TextField label="Last Name" value={newLastName} onChange={e => setNewLastName(e.target.value)}
                           fullWidth size="small"/>
                <TextField label="OIB" value={newOib} onChange={e => setNewOib(e.target.value)} fullWidth size="small"/>
                <Button variant="contained" disabled={!newFirstName || !newLastName || !newOib}
                        onClick={handleAddUser}>Add</Button>
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
                    {users.length > 0 ?
                        (
                            <List>
                                {users.map(user => (
                                    <ListItem key={user.id} secondaryAction={
                                        <>
                                            <IconButton
                                                onClick={() => handleOpenStatusDialog(user)}><Edit/></IconButton>
                                            <IconButton
                                                onClick={() => handleDeleteUser(user.oib)}><Delete/></IconButton>
                                        </>
                                    }>
                                        <ListItemText primary={`${user.firstName} ${user.lastName}`}
                                                      secondary={`OIB: ${user.oib} | Card status: ${user.lastCardStatus?.cardStatus ?? '---'}`}/>
                                    </ListItem>
                                ))}
                            </List>
                        ) : (
                            <Typography variant="body1" color="textSecondary" align="center" sx={{p: 2}}>
                                {noUsersText}
                            </Typography>
                        )
                    }

                </CardContent>
            </Card>

            {selectedUser && (
                <UserDetailsDialog
                    open={statusDialogOpen}
                    onClose={handleCloseStatusDialog}
                    user={selectedUser}
                    userStatuses={selectedUserStatuses}
                    statuses={statuses}
                    onUpdateUser={handleUpdateUser}
                    onAddStatus={handleAddStatus}
                />
            )}
        </div>
    );
}