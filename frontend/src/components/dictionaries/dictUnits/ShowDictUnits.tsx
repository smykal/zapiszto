import { Component } from "react";
import { DictUnits } from "../../../types/types";
import Service from '../../../services/exercises'

type Props = {};
type State = {
    dictUnits: DictUnits[]
};

export default class ShowDictUnits extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            dictUnits: []
        };
    }

    componentDidMount() {
        this.loadDictUnits();
    }

    loadDictUnits() {
        Service.getDictUnits()
            .then(response => {
                this.setState({ dictUnits: response.data });
            })
            .catch(error => {
                console.error('Error loading dict units:', error);
            });
    }

    render() {
        return (
            <div>
                <ul>
                    {this.state.dictUnits.map((unit) => (
                        <li>
                            <p>
                                {unit.id} | 
                                {unit.dict} | 
                                {unit.dict_id} | 
                                {unit.name} |
                                {unit.shortcut}
                            </p>
                        </li>
                    ))}
                </ul>
            </div>
        )
    }
}