import { Component } from "react";
import Service from '../../../../../services/exercises'
import { withTranslation } from "react-i18next";



type Props = {
    exerciseId: number
    trainingId: number
    t: any
};
type State = {};

class Options extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
    }
    render() {
        const { exerciseId, trainingId , t } = this.props
        return (
            <div>
                {/* <button onClick={() => this.delete(item)}>Delete {item}</button> */}
                <button>{t("buttons.delete")}               </button>
                <button>{t("buttons.edit")}     {exerciseId}</button>
                <button>{t("buttons.move_up")}              </button>
                <button>{t("buttons.move_down")}            </button>
                <button>{t("buttons.archive")} {exerciseId}</button>
            </div>
        )  
    }
}

export default withTranslation("global")(Options)