import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "./App.css";
import { Menu } from "./components/language/Menu";
import { useTranslation } from "react-i18next";
import AuthService from "./services/auth.service";
import IUser from './types/user.type';
import Login from "./components/login/login.component";
import Register from "./components/register/register.component";
import Home from "./components/home/home.component";
import Profile from "./components/profile/profile.component";
import BoardUser from "./components/board/board-user.component";
import BoardModerator from "./components/board/board-moderator.component";
import BoardAdmin from "./components/board/board-admin.component";
import BodyParams from "./components/bodyParams";
import Training from "./components/workbooks";
import Dictionaries from "./components/dictionaries";
import TrainerView from "./components/viewTrainer/TrainerView";
import Clients from "./components/viewTrainer/clients/Clients";
import EventBus from "./common/EventBus";

const App = () => {
  const [showModeratorBoard, setShowModeratorBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [showTrainerBoard, setShowTrainerBoard] = useState(false);
  const [showUserBoard, setShowUserBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState<IUser | undefined>(undefined);
  const { t } = useTranslation("global");

  useEffect(() => {
    const user = AuthService.getCurrentUser();
    if (user) {
      setCurrentUser(user);
      setShowModeratorBoard(user.roles.includes("ROLE_MODERATOR"));
      setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
      setShowTrainerBoard(user.roles.includes("ROLE_TRAINER"));
      setShowUserBoard(user.roles.includes("ROLE_USER"));
    }
    EventBus.on("logout", logOut);
    return () => {
      EventBus.remove("logout", logOut);
    };
  }, []);

  const logOut = () => {
    AuthService.logout();
    setShowModeratorBoard(false);
    setShowAdminBoard(false);
    setShowTrainerBoard(false);
    setShowUserBoard(false);
    setCurrentUser(undefined);
  };

  return (
    <div>
      <nav className="navbar">
        <Link to={"/"} className="navbar-brand">
          zapiszTo
        </Link>
        <ul className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/home"} className="nav-link">
              {t("navigation.home")}
            </Link>
          </li>

          {showModeratorBoard && (
            <li className="nav-item">
              <Link to={"/mod"} className="nav-link">
                Moderator Board
              </Link>
            </li>
          )}

          {showAdminBoard && (
            <li className="nav-item">
              <Link to={"/admin"} className="nav-link">
                Admin Board
              </Link>
            </li>
          )}
          
          {showUserBoard && ( // Conditionally render for user role
            <>
              <li className="nav-item">
                <Link to={"/bodyParams"} className="nav-link">
                  {t("navigation.body_params")}
                </Link>
              </li>
              <li className="nav-item">
                <Link to={"/dictionaries"} className="nav-link">
                  {t("navigation.dictionaries")}
                </Link>
              </li>
              <li className="nav-item">
                <Link to={"/training"} className="nav-link">
                  {t("navigation.training")}
                </Link>
              </li>
            </>
          )}
          
          {showTrainerBoard && ( // Conditionally render for trainer users
            <>
              <li className="nav-item">
                <Link to={"/dictionaries"} className="nav-link">
                  {t("navigation.dictionaries")}
                </Link>
              </li>
              <li className="nav-item">
                <Link to={"/trainer"} className="nav-link">
                  Trainer View
                </Link>
              </li>
              <li className="nav-item">
                <Link to={"/clients"} className="nav-link">
                {t("navigation.clients")}
                </Link>
              </li>
            </>
          )}
        </ul>

        {currentUser ? (
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                {currentUser.username}
              </Link>
            </li>
            <li className="nav-item">
              <a href="/login" className="nav-link" onClick={logOut}>
                {t("navigation.log_out")}
              </a>
            </li>
          </ul>
        ) : (
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                {t("navigation.log_in")}
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                {t("navigation.sign_in")}
              </Link>
            </li>
          </ul>
        )}
        <Menu />
      </nav>

      <div id="content">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/user" element={<BoardUser />} />
          <Route path="/mod" element={<BoardModerator />} />
          <Route path="/admin" element={<BoardAdmin />} />
          <Route path="/bodyParams" element={<BodyParams />} />
          <Route path="/training" element={<Training />} />
          <Route path="/dictionaries" element={<Dictionaries />} />
          <Route path="/trainer" element={<TrainerView />} />  {/* New route for trainer view */}
          <Route path="/clients" element={<Clients />} />  {/* New route for clients */}
        </Routes>
      </div>
    </div>
  );
};

export default App;