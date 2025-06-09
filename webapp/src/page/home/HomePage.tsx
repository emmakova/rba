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


const statuses: CardStatusDtoCardStatusEnum[] = Object.values(CardStatusDtoCardStatusEnum);

export default function HomePage() {
    const [noUsersText, setNoUsersText] = useState<string>('Try searching users by OIB to get some result');
    const [users, setUsers] = useState<UserDto[]>([]);
    const [searchOib, setSearchOib] = useState<string>('');
    const [newFirstName, setNewFirstName] = useState<string>('');
    const [newLastName, setNewLastName] = useState<string>('');
    const [newOib, setNewOib] = useState<string>('');
    const [statusDialogOpen, setStatusDialogOpen] = useState<boolean>(false);
    const [selectedUser, setSelectedUser] = useState<UserDto | null>(null);
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

    const handleDeleteUser = (oib : string) => {
        if (window.confirm("Are you sure you want to delete this user? This will delete the user and all card statuses!")) {
            userApi.deleteUser(oib);
            alert("User data deleted!");
        }
    };

    const handleOpenStatusDialog = async (user : UserDto) => {
        setSelectedUser(user);
        const response = await cardStatusApi.getCardStatusesForUser(user.id as number);
        setSelectedUserStatuses(response.data);
        setStatusDialogOpen(true);
    };

    const handleCloseStatusDialog = () => {
        setSelectedUser(null);
        setSelectedUserStatuses([]);
        setStatusDialogOpen(false);
    };

    const handleUpdateUser = async (updatedUser : UserDto) => {
        const response = await userApi.updateUser(updatedUser.id as number, updatedUser);
        const savedUser = response.data;

        setUsers((prev) =>
            prev.map((u) => (u.id === savedUser.id ? savedUser : u))
        );

        setSelectedUser(savedUser);
    };

    const handleAddStatus= async (newStatus : CardStatusDtoCardStatusEnum) => {
        const cardStatusRequest : CardStatusDto = {'userId': selectedUser?.id, 'cardStatus': newStatus };
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
                                                onClick={() => handleDeleteUser(user.oib as string)}><Delete/></IconButton>
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