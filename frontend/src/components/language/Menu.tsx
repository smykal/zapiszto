// components/Menu.tsx
import React from "react";
import { LANGUAGES } from "../../translations/Languages";
import { useTranslation } from "react-i18next";
import ReactCountryFlag from "react-country-flag";

export const Menu = () => {
  const { i18n } = useTranslation();

  const onChangeLang = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const lang_code = e.target.value;
    i18n.changeLanguage(lang_code);
  };

  return (
    <nav>
      <select defaultValue={i18n.language} onChange={onChangeLang}>
        {LANGUAGES.map(({ code, label }) => (
          <option key={code} value={code}>
            <span style={{ display: "flex", alignItems: "center" }}>
              <ReactCountryFlag 
                countryCode={code === "en" ? "GB" : code.toUpperCase()} 
                svg 
                style={{ width: "20px", height: "15px", marginRight: "8px" }} 
                title={label} 
              />
              {label}
            </span>
          </option>
        ))}
      </select>
    </nav>
  );
};
