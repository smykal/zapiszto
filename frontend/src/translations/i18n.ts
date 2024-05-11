import i18n from "i18next";
import i18next from "i18next";
import global_en from '../translations/en/global_en.json'
import global_es from '../translations/es/global_es.json'
import global_it from '../translations/it/global_it.json'
import global_pl from '../translations/pl/global_pl.json'
import global_ru from '../translations/ru/global_ru.json'
import global_ua from '../translations/ua/global_ua.json'

i18next.init({
    interpolation: {escapeValue: false},
    lng: "en",
    resources: {
        en: {
            global: global_en
        },
        es: {
            global: global_es
        },
        it: {
            global: global_it
        },
        pl: {
            global: global_pl
        },
        ru: {
            global: global_ru
        },
        ua: {
            global: global_ua
        }
    }
})

export default i18n;