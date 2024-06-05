import { LANGUAGES } from "../../translations/Languages";
import { useTranslation } from "react-i18next";
import LanguageService from "../../services/languages"; // Import the LanguageService
import authHeader from "../../services/auth-header"; // Import the authHeader function
import { UserDetailsLanguage } from "../../types/types"; // Import the type

export const Menu = () => {
  const { i18n, t } = useTranslation();

  const onChangeLang = async (e: React.ChangeEvent<HTMLSelectElement>) => {
    const lang_code = e.target.value;
    i18n.changeLanguage(lang_code);

    // Check if the user is logged in
    if (authHeader()) {
      const requestBody: UserDetailsLanguage = { languageCode: lang_code };
      try {
        await LanguageService.postLanguage(requestBody);
      } catch (error) {
        console.error("Błąd podczas zmiany języka:", error);
      }
    }
  };

  return (
    <nav>
      <select defaultValue={i18n.language} onChange={onChangeLang}>
      <option value="" disabled selected>Select language</option>
        {LANGUAGES.map(({ code, label }) => (
          <option key={code} value={code}>
            {label}
          </option>
        ))}
      </select>
    </nav>
  );
};
