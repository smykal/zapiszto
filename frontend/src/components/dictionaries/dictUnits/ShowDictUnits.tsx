import { Component } from "react";
import { DictUnits } from "../../../types/types";
import Service from '../../../services/exercises';
import AddDictUnitPerUser from "./AddDictUnitPerUser";
import Options from "./Options";

type Props = {};
type State = {
    dictUnits: DictUnits[];
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
        const { dictUnits } = this.state;
        return (
            <div>
                <AddDictUnitPerUser dictUnit={dictUnits} />
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
                        {dictUnits.map((row) => (
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
