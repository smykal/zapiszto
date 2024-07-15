// Profile.js
import React, { Component } from "react";
import { Navigate } from "react-router-dom";
import AuthService from "../../services/auth.service";
import IUser from "../../types/user.type";
import GetInvitations from "./invitations/GetInvitations";
import { withTranslation } from "react-i18next";
import LanguageService from "../../services/languages";
import DeleteAccount from "./crud/DeleteAccount";
import Modal from "../../constants/Modal";
import UpdatePassword from "./crud/UpdatePassword";

type Props = {
  t: any;
  i18n: any;
};

type State = {
  redirect: string | null,
  userReady: boolean,
  currentUser: IUser & { accessToken: string },
  languageReady: boolean,
  showModal: boolean
}

class Profile extends Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      currentUser: { accessToken: "" },
      languageReady: false,
      showModal: false
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

      await this.props.i18n.changeLanguage(langCode);

      this.setState({ languageReady: true });
    } catch (err) {
      console.error('Error fetching user language:', err);
    }
  }

  handleOpenModal = () => {
    this.setState({ showModal: true });
  }

  handleCloseModal = () => {
    this.setState({ showModal: false });
  }

  render() {
    if (this.state.redirect) {
      return <Navigate to={this.state.redirect} />
    }

    const { t } = this.props;
    const { currentUser, userReady, languageReady, showModal } = this.state;

    if (!userReady || !languageReady) {
      return <div>Loading...</div>;
    }

    return (
      <div className="alinka">
        <header>
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
          <strong>{"Id: "}</strong>{currentUser.id}
        </p>
        <p>
          <strong>{"Email: "}</strong>{currentUser.email}
        </p>
        <strong>Authorities:</strong>
        <ul>
          {currentUser.roles &&
            currentUser.roles.map((role, index) => <li key={index}>{role}</li>)}
        </ul>
        <GetInvitations />
        <DeleteAccount /> {/* Add DeleteAccount component here */}
        <button onClick={this.handleOpenModal}>Update Password</button>
        <Modal show={showModal} onClose={this.handleCloseModal} title="Update Password">
          <UpdatePassword />
        </Modal>
      </div>
    );
  }
}

export default withTranslation("global")(Profile);
