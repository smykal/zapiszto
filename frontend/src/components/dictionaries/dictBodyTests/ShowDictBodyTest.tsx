import { Component } from "react";
import { DictBodyTest } from "../../../types/types";
import Service from '../../../services/dict/DictBodyTestService';
import AddDictBodyTestPerUser from "./AddDictBodyTestPerUser";
import Options from './Options';
import { withTranslation } from "react-i18next";

type Props = {
    t: any;
};

type State = {
    dictBodyTest: DictBodyTest[];
};

class ShowDictCategory extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            dictBodyTest: []
        };
    }

    componentDidMount() {
        this.loadDictBodyTest();
    }

    loadDictBodyTest() {
        Service.getDictBodyTest()
            .then(response => {
                this.setState({ dictBodyTest: response.data });
            })
            .catch(error => {
                console.error('Error loading dict quantity types:', error);
            });
    }

    render() {
        const { dictBodyTest } = this.state;
        const { t } = this.props;

        return (
            <div>
                <AddDictBodyTestPerUser dictBodyTest={dictBodyTest} />
                <table style={{ minWidth: '650px', width: '100%', borderCollapse: 'collapse' }}>
                    <thead>
                        <tr>
                            <th>{t("table.id")}</th>
                            <th>{t("table.name")}</th>
                            <th>{t("table.description")}</th>
                            <th>{t("table.purpose")}</th>
                            <th>{t("table.options")}</th>
                        </tr>
                    </thead>
                    <tbody>
                        {dictBodyTest.map((row) => (
                            <tr key={row.id} style={{ borderBottom: '1px solid #ddd' }}>
                                <td>{row.id}</td>
                                <td>{row.name}</td>
                                <td>{row.description}</td>
                                <td>{row.purpose}</td>
                                <td>{row.dict === "PER_USER" ? <Options item={row.dict_id} /> : "menu niedostępne"}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default withTranslation("global")(ShowDictCategory);
