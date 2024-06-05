import React, { Component } from "react";
import Service from '../../../services/exercises'

type Props = {
    item: number,
};
type State = {};

export default class Options extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
    }

    delete = (item: number) => {
        Service.deleteDictUnits(item)
            .then(() => {
                window.location.reload();
            })
            .catch(error => {
                console.error('Error deleting unit:', error);
            });
    }

    render() {
        const { item } = this.props
        return (
            <div>
                <button onClick={() => this.delete(item)}>Delete {item}</button>
                <button>Edit {item}</button>
                <button>Archive {item}</button>
            </div>
        )
    }
}