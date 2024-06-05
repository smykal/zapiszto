import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";

import Service from "../../services/bodyParams";
import PostAge from "./postAge";

const Age = () => {
    const [age, setAge] = useState<number | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const { t } = useTranslation("global");

    useEffect(() => {
        getAge();
    }, []);

    const getAge = () => {
        Service.getAge()
            .then(response => {
                if (response.status === 200) {
                    const age = response.data.age;
                    setAge(age);
                    setLoading(false);
                } else if (response.status === 204) {
                    setAge(null);
                    setLoading(false);
                } else {
                    console.log("Unexpected status code:", response.status);
                    setLoading(false);
                }
            })
            .catch(error => {
                console.log("Error fetching age:", error);
                setLoading(false);
            });
    };

    if (loading) {
        return <p>{t("loading")}</p>;
    }

    if (age !== null) {
        return (
            <div>
                <p>{t("body_params.age")}: {age}</p>
            </div>
        );
    }

    // If age is null, render PostAge component
    return <PostAge />;
};

export default Age;
