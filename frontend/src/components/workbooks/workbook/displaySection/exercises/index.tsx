import { Component } from 'react';
import AddExercise from './addExercise';
import GetExercise from './getExercise';


type Props = {
    workbook_id: number,
    training_id: number
}

class Exercise extends Component<Props> {
    constructor(props: Props) {
        super(props) 
    }
    
    render() {
        const {workbook_id, training_id} = this.props;

        return (
            <div>
                <AddExercise workbook_id={workbook_id} training_id={training_id} />
                <GetExercise training_id={training_id}></GetExercise>
            </div>
        )
        
    }
}

export default Exercise;