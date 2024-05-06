import { Component } from "react";
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import Service from '../../../services/exercises'


type Props = {
    itemToDelete: number,
};
type State = {};

export default class Delete extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
    }
    deleteExercise = (itemToDelete: number) => {
        Service.deleteDictExercise(itemToDelete)
            .then(() => {
                window.location.reload();
            })
            .catch(error => {
                console.error('Error deleting exercise:', error);
            });
    }

    render() {
        const { itemToDelete } = this.props
        return (
            <div>
                <Stack direction="row" spacing= {1}>
                <Button variant="outlined" onClick={() => this.deleteExercise(itemToDelete)}>Delete {itemToDelete}</Button>
                    <Button variant="outlined"> Edit {itemToDelete}</Button>                
                </Stack>
            </div>
        )  
    }
}