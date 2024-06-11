import React from 'react';
import { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";

import Service from "../../services/bodyParams";
import PostGender from "./postGender";

const GetGender = () => {
  const [gender, setGender] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const { t } = useTranslation("global");

  useEffect(() => {
    getGender();
  }, []);

  const getGender = () => {
    Service.getGender()
      .then(response => {
        if (response.status === 200) {
          const gender = response.data.gender;
          setGender(gender);
          setLoading(false);
        } else if (response.status === 204) {
          setGender(null);
          setLoading(false);
        } else {
          console.log("Unexpected status code:", response.status);
          setLoading(false);
        }
      })
      .catch(error => {
        console.log("Error fetching gender:", error);
        setLoading(false);
      });
  };

  if (loading) {
    return <p>{t("loading")}</p>;
  }

  if (gender !== null) {
    return (
      <div>
        <p>{t("body_params.gender")}: {gender}</p>
      </div>
    );
  } else {
    return <PostGender />;
  }
};

export default GetGender;
