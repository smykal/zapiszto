import React, { Component } from "react";

type ShowShowProps = {
    parameter: string; // lub odpowiedni typ
};

export default class ShowShow extends Component<ShowShowProps> {
    render() {
        return (
            <div className="lista">
                {/* Zawartość komponentu ShowShow */}
                {this.props.parameter}
            </div>
        );
    }
}
