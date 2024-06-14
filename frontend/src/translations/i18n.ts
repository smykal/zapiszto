import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import global_en from '../translations/en/global_en.json';
import global_es from '../translations/es/global_es.json';
import global_it from '../translations/it/global_it.json';
import global_pl from '../translations/pl/global_pl.json';
import global_ru from '../translations/ru/global_ru.json';
import global_ua from '../translations/ua/global_ua.json';

import terms_en from '../translations/en/terms_en.json';
import terms_es from '../translations/es/terms_es.json';
import terms_it from '../translations/it/terms_it.json';
import terms_pl from '../translations/pl/terms_pl.json';
import terms_ru from '../translations/ru/terms_ru.json';
import terms_ua from '../translations/ua/terms_ua.json';

import privacy_en from '../translations/en/privacy_en.json';
import privacy_es from '../translations/es/privacy_es.json';
import privacy_it from '../translations/it/privacy_it.json';
import privacy_pl from '../translations/pl/privacy_pl.json';
import privacy_ru from '../translations/ru/privacy_ru.json';
import privacy_ua from '../translations/ua/privacy_ua.json';


i18n
  .use(initReactI18next) 
  .init({
    interpolation: { escapeValue: false },
    lng: "en", 
    fallbackLng: "en",
    resources: {
      en: {
        global: global_en,
        terms: terms_en,
        privacy: privacy_en
      },
      es: {
        global: global_es,
        terms: terms_es,
        privacy: privacy_es
      },
      it: {
        global: global_it,
        terms: terms_it,
        privacy: privacy_it
      },
      pl: {
        global: global_pl,
        terms: terms_pl,
        privacy: privacy_pl
      },
      ru: {
        global: global_ru,
        terms: terms_ru,
        privacy: privacy_ru
      },
      ua: {
        global: global_ua,
        terms: terms_ua,
        privacy: privacy_ua
      }
    }
  });

export default i18n;
