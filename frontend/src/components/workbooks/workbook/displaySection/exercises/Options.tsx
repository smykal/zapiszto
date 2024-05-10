import { Component } from "react";
import Service from '../../../../../services/exercises'


type Props = {
    exerciseId: number,
    trainingId: number
};
type State = {};

export default class Options extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
    }
    render() {
        const { exerciseId, trainingId } = this.props
        return (
            <div>
                {/* <button onClick={() => this.delete(item)}>Delete {item}</button> */}
                <button>Delete</button>
                <button>Edit {exerciseId}</button>
                <button>Move Up</button>
                <button>Move Down</button>
                <button>Archive {exerciseId}</button>
            </div>
        )  
    }
}