import React, { Component } from "react";
import { DictQuantityType } from "../../../types/types";
import Service from '../../../services/exercises';
import AddDictQuantityTypePerUser from "./AddDictQuantityTypePerUser";
import Options from './Options';

type Props = {};
type State = {
    dictQuantityTypes: DictQuantityType[];
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
        const { dictQuantityTypes } = this.state;
        return (
            <div>
                <AddDictQuantityTypePerUser dictQuantityType={dictQuantityTypes} />
                <table style={{ minWidth: '650px', width: '100%', borderCollapse: 'collapse' }}>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Shortcut</th>
                            <th>Options</th>
                        </tr>
                    </thead>
                    <tbody>
                        {dictQuantityTypes.map((row) => (
                            <tr key={row.id} style={{ borderBottom: '1px solid #ddd' }}>
                                <td>{row.id}</td>
                                <td>{row.name}</td>
                                <td>{row.shortcut}</td>
                                <td>{row.dict === "PER_USER" ? <Options item={row.dict_id} /> : "menu niedostępne"}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    }
}
