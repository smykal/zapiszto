import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import Service from "../../services/bodyParams";

const PostGender = () => {
  const [gender, setGender] = useState<string>("");
  const { t } = useTranslation("global");

  const handleGenderChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setGender(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (gender) {
      Service.postSex(gender);
    } else {
      console.log("Nie wybrano płci.");
    }
  };

  return (
    <div className="container">
      <form onSubmit={handleSubmit}>
        <label>
          {t("buttons.select_gender")}:
          <select value={gender} onChange={handleGenderChange}>
            <option value="">{t("buttons.select")}</option>
            <option value="Male">{t("buttons.male")}</option>
            <option value="Female">{t("buttons.female")}</option>
          </select>
        </label>
        <button type="submit">{t("buttons.add")}</button>
      </form>
    </div>
  );
};

export default PostGender;
