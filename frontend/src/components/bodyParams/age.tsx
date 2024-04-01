import React, { Component } from "react";
import Service from "../../services/bodyParams";
import PostAge from "./postAge";

type Props = {}; 
type State = {
    age: number | null;
    loading: boolean;
};

export default class Age extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            age: null,
            loading: true
        }
    }

    componentDidMount() {
        this.getAge();
    }
    
    getAge() {
        Service.getAge()
            .then(response => {
                if (response.status === 200) {
                    const age = response.data.age;
                    this.setState({ age, loading: false });
                } else if (response.status === 204) {
                    this.setState({ age: null, loading: false });
                } else {
                    console.log("Nieoczekiwany kod stanu:", response.status);
                    this.setState({ loading: false });
                }
            })
            .catch(error => {
                console.log("Błąd pobierania płci:", error);
                this.setState({ loading: false });
            });
    }
    
    render() {
        const { age, loading } = this.state;
    
        if (loading) {
            return <p>Loading...</p>;
        }
    
        if (age !== null) {
            return (
                <div className="container">
                    <p>Age: {age}</p>
                </div>
            );
        }
    
        // Jeśli age jest null, wyświetl komponent PostAge
        return <PostAge />;
    } 
}
