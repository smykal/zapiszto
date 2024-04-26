import React, { Component } from 'react';
import AddExercise from './addExercise';


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
                {/* <GetExercises></GetExercises> */}
            </div>
        )
        
    }
}

export default Exercise;