import { Component } from "react";
import { withTranslation } from "react-i18next";
import Service from "../../services/bodyParams";
import { BmrType } from "../../types/types";

type Props = {
    t: any;
};
type State = {
    bmrList: BmrType[] | null;
    date: string | null;
    loading: boolean;
}

class GetBmr extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            bmrList: [],
            date: null,
            loading: true
        };
    }

    componentDidMount() {
        Service.getBmr().then(
            (response) => {
                if (response.status === 200) {
                    const bmrList = response.data;
                    const date = bmrList.length > 0 ? bmrList[0].insert_date : null;
                    this.setState({ bmrList, date, loading: false });
                } else if (response.status === 204) {
                    this.setState({ bmrList: null, date: null, loading: false });
                } else {
                    console.log("Unexpected status code:", response.status);
                    this.setState({ loading: false });
                }
            },
            (error) => {
                console.log("Error fetching BMR:", error);
            }
        )
    }

    render() {
        const { bmrList, date, loading } = this.state;
        const { t } = this.props;

        if (loading) {
            return <p>{t("loading")}...</p>;
        }

        if (!bmrList) {
            return <p>{t("no_data")}</p>;
        }

        return (
            <div>
                {date && <p><strong>{t("bmr_values_calculated_for")}:</strong> {date}</p>}
                {bmrList.map((bmr, index) => (
                    <div key={index}>
                        <p>
                            <strong> {t("bmr")}</strong> {bmr.bmr}
                            <strong> {t("description")}:</strong> {bmr.category}
                        </p>
                    </div>
                ))}
            </div>
        );
    }
}

export default withTranslation("global")(GetBmr);
