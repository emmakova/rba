import React, {useEffect, useState} from 'react';
import {
    Dialog,
    DialogContent,
    DialogActions,
    TextField,
    Button,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    List,
    ListItem,
    ListItemText,
    Box,
    Typography, Divider,
} from '@mui/material';
import {CardStatusDto} from "../../api-client";

function UserDetailsDialog({open, onClose, user, userStatuses, statuses, onUpdateUser, onAddStatus}) {
    const [firstName, setFirstName] = useState(user.firstName);
    const [lastName, setLastName] = useState(user.lastName);
    const [oib, setOib] = useState(user.oib);
    const [newStatus, setNewStatus] = useState('');

    useEffect(() => {
        if (user) {
            setFirstName(user.firstName);
            setLastName(user.lastName);
            setOib(user.oib);
        }
    }, [user]);

    const handleUpdateUser = () => {
        onUpdateUser({
            ...user,
            firstName,
            lastName,
            oib,
        });
    };

    const handleAddStatus = () => {
        if (newStatus) {
            onAddStatus(newStatus);
            setNewStatus('');
        }
    };

    return (
        <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>

            <DialogContent dividers>
                <Box mb={3}>
                    <Typography variant="h6" gutterBottom>User Info</Typography>
                    <Box display="flex" gap={2} mb={2}>
                        <TextField
                            label="First Name"
                            value={firstName}
                            onChange={e => setFirstName(e.target.value)}
                            fullWidth
                            size="small"
                        />
                        <TextField
                            label="Last Name"
                            value={lastName}
                            onChange={e => setLastName(e.target.value)}
                            fullWidth
                            size="small"
                        />
                        <TextField
                            label="OIB"
                            value={oib}
                            onChange={e => setOib(e.target.value)}
                            fullWidth
                            size="small"
                        />
                        <Button
                            size="small" variant="contained"
                            disabled={firstName == user.firstName && lastName == user.lastName && user.oib == oib}
                            onClick={handleUpdateUser}>Update</Button>
                    </Box>
                </Box>

                <Divider/>

                <Box>
                    <Typography variant="h6" gutterBottom>Card statuses</Typography>
                    <Box display="flex" gap={2} alignItems="center" mt={1}>
                        <FormControl style={{minWidth: 200, flexGrow: 1}}>
                            <InputLabel>New Status</InputLabel>
                            <Select
                                value={newStatus}
                                onChange={e => setNewStatus(e.target.value)}
                                label="New Status"
                                size="small"
                            >
                                {statuses.map(status => (
                                    <MenuItem key={status} value={status}>{status}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        <Button
                            variant="outlined"
                            onClick={handleAddStatus}
                            disabled={!newStatus}
                        >
                            Update
                        </Button>
                    </Box>
                    {userStatuses.length > 0 ?
                        (
                            <List dense>
                                {userStatuses.map((status: CardStatusDto) => (
                                    <ListItem key={status.id}>
                                        <ListItemText primary={status.cardStatus}
                                                      secondary={status.createdAt}/>
                                    </ListItem>
                                ))}
                            </List>
                        ) : (
                            <Typography variant="body1" color="textSecondary" align="center" sx={{p: 2}}>
                                Users have no statuses
                            </Typography>
                        )
                    }
                </Box>
            </DialogContent>

            <DialogActions>
                <Button onClick={onClose}>Close</Button>
            </DialogActions>
        </Dialog>
    );
}

export default UserDetailsDialog;