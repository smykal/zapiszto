import React, { Component } from "react";
import { DictCategories } from "../../../types/types";
import Service from '../../../services/exercises';
import AddDictCategoryPerUser from "./AddDictCategoryPerUser";
import Options from './Options';
import { withTranslation } from "react-i18next";

type Props = {
    t: any;
};

type State = {
    dictCategories: DictCategories[];
};

class ShowDictCategory extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            dictCategories: []
        };
    }

    componentDidMount() {
        this.loadDictCategories();
    }

    loadDictCategories() {
        Service.getDictCategory()
            .then(response => {
                this.setState({ dictCategories: response.data });
            })
            .catch(error => {
                console.error('Error loading dict quantity types:', error);
            });
    }

    render() {
        const { dictCategories } = this.state;
        const { t } = this.props;

        return (
            <div>
                <AddDictCategoryPerUser dictCategory={dictCategories} />
                <table style={{ minWidth: '650px', width: '100%', borderCollapse: 'collapse' }}>
                    <thead>
                        <tr>
                            <th>{t("table.id")}</th>
                            <th>{t("table.name")}</th>
                            <th>{t("table.description")}</th>
                            <th>{t("table.options")}</th>
                        </tr>
                    </thead>
                    <tbody>
                        {dictCategories.map((row) => (
                            <tr key={row.id} style={{ borderBottom: '1px solid #ddd' }}>
                                <td>{row.id}</td>
                                <td>{row.name}</td>
                                <td>{row.description}</td>
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
