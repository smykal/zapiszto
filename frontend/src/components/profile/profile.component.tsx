import { Component } from "react";
import { Navigate } from "react-router-dom";
import AuthService from "../../services/auth.service";
import IUser from "../../types/user.type";
import GetInvitations from "./invitations/GetInvitations";
import { withTranslation } from "react-i18next";
import LanguageService from "../../services/languages";

type Props = {
  t: any;
  i18n: any;
};

type State = {
  redirect: string | null,
  userReady: boolean,
  currentUser: IUser & { accessToken: string },
  languageReady: boolean
}

class Profile extends Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      currentUser: { accessToken: "" },
      languageReady: false
    };
  }

  async componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    if (!currentUser) {
      this.setState({ redirect: "/home" });
      return;
    }

    this.setState({ currentUser: currentUser, userReady: true });

    try {
      const res = await LanguageService.getLanguage();
      const langCode = res.data.languageCode;

      console.log('Fetched language code:', langCode); // Log fetched language code
      
      await this.props.i18n.changeLanguage(langCode); // Change the language
      console.log('Language changed to:', langCode); // Log after changing language

      this.setState({ languageReady: true }); // Set language ready to true after setting the language
    } catch (err) {
      console.error('Error fetching user language:', err);
    }
  }

  render() {
    if (this.state.redirect) {
      return <Navigate to={this.state.redirect} />
    }

    const { t } = this.props;
    const { currentUser, userReady, languageReady } = this.state;

    if (!userReady || !languageReady) {
      return <div>Loading...</div>; // Show a loading indicator until everything is ready
    }

    return (
      <div className="container">
        <div className="alinka">
          <header className="jumbotron">
            <h3>
              <strong>{currentUser.username}</strong> Profile
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
        </div>
        <GetInvitations />
      </div>
    );
  }
}

export default withTranslation("global")(Profile);