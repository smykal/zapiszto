import { Component } from "react";
import { DictQuantityType } from "../../../types/types";
import Service from '../../../services/exercises'

type Props = {};
type State = {
    dictQuantityTypes: DictQuantityType[]
};

export default class ShowDictQuantityType extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            dictQuantityTypes: []
        };
    }

    componentDidMount() {
        this.loadDictQuantityTypes();
    }

    loadDictQuantityTypes() {
        Service.getDictQuantityType()
            .then(response => {
                this.setState({ dictQuantityTypes: response.data });
            })
            .catch(error => {
                console.error('Error loading dict quantity types:', error);
            });
    }

    render() {
        return (
            <div>
                <ul>
                    {this.state.dictQuantityTypes.map((quantityType) => (
                        <li>
                            <p>
                                {quantityType.id} | 
                                {quantityType.dict} | 
                                {quantityType.dict_id} | 
                                {quantityType.name} |
                                {quantityType.shortcut}
                                </p>
                        </li>
                    ))}
                    </ul>
            </div>
        )
    }
}