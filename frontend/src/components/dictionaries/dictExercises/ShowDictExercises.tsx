import { Component } from "react";
import { DictExercises } from "../../../types/types";
import Service from '../../../services/exercises'

type Props = {};
type State = {
    dictExercises: DictExercises[]
};

export default class ShowDictExercises extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            dictExercises: []
        };
    }

    componentDidMount() {
        this.loadDictExercises();
    }

    loadDictExercises() {
        Service.getDictExercises()
            .then(response => {
                this.setState({ dictExercises: response.data });
            })
            .catch(error => {
                console.error('Error loading dict exercises:', error);
            });
    }

    render() {
        return (
            <div>
                <ul>
                    {this.state.dictExercises.map((exercise) => (
                        <li>
                            <p>{exercise.id} | {exercise.dict} | {exercise.dict_id} | {exercise.name}</p>
                        </li>
                    ))}
                    </ul>
            </div>
        )
        
    }
}