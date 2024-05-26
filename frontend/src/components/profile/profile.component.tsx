import { Component } from "react";
import { Navigate } from "react-router-dom";
import AuthService from "../../services/auth.service";
import IUser from "../../types/user.type";
import GetInvitations from "./invitations/GetInvitations";
import { withTranslation } from "react-i18next";


type Props = {
  t: any;
};

type State = {
  redirect: string | null,
  userReady: boolean,
  currentUser: IUser & { accessToken: string }
}
class Profile extends Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      currentUser: { accessToken: "" }
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();


    if (!currentUser) this.setState({ redirect: "/home" });
    this.setState({ currentUser: currentUser, userReady: true })
  }

  render() {

    if (this.state.redirect) {
      return <Navigate to={this.state.redirect} />
    }
    const { t } = this.props;

    const { currentUser } = this.state;

    return (
      <div className="container">
        {(this.state.userReady) ?
          <div className="alinka" >
            <header className="jumbotron">
              <h3>
                <strong >{currentUser.username}</strong> Profile
              </h3>
            </header>
            <p>
              <strong>Token:</strong>{" "}
              {currentUser.accessToken.substring(0, 20)} ...{" "}
              {currentUser.accessToken.substr(currentUser.accessToken.length - 20)}
            </p>
            <p>
              <strong>Id:</strong>{" "}
              {currentUser.id}
            </p>
            <p>
              <strong>Email:</strong>{" "}
              {currentUser.email}
            </p>
            <strong>Authorities:</strong>
            <ul>
              {currentUser.roles &&
                currentUser.roles.map((role, index) => <li key={index}>{role}</li>)}
            </ul>
          </div> : null}

        <GetInvitations />

      </div>
    );
  }
}
export default withTranslation("global")(Profile)