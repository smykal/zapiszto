import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import Service from "../../services/bodyParams";

const PostBirthDate = () => {
  const [birthdate, setBirthdate] = useState<Date | null>(null);

  const handleDateChange = (date: Date | null) => {
    setBirthdate(date);
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (birthdate !== null) {
      Service.postBirthDate(birthdate);
    } else {
      console.log("Wybierz datę urodzenia.");
    }
  };

  const { t } = useTranslation("global");

  return (
    <div className="container">
      <form onSubmit={handleSubmit}>
        <label>
          {t("buttons.select_birthdate")}:
          <DatePicker
            selected={birthdate}
            onChange={handleDateChange}
            dateFormat="yyyy-MM-dd"
          />
        </label>
        <br />
        <button type="submit">{t("buttons.add")}</button>
      </form>
    </div>
  );
};

export default PostBirthDate;
