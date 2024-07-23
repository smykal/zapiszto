import React, { useEffect, useState } from "react";
import { Chart } from "react-google-charts";
import DiagramService from "../../../../../../services/diagrams/DiagramService";
import { ExerciseStats } from "../../../../../../types/types";

interface DiagramProps {
  mesocycleId: string;
}

const Diagram: React.FC<DiagramProps> = ({ mesocycleId }) => {
  const [data, setData] = useState<{ repetitions: (string | number)[][]; sets: (string | number)[][] }>({ repetitions: [], sets: [] });
  const [categories, setCategories] = useState<string[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await DiagramService.getExerciseStats(mesocycleId);
        const fetchedData: ExerciseStats[] = response.data;

        // Zbierz unikalne kategorie
        const uniqueCategoriesArray = fetchedData.map(item => item.category);
        const uniqueCategories = uniqueCategoriesArray.filter((item, index) => uniqueCategoriesArray.indexOf(item) === index);
        setCategories(uniqueCategories);

        // Nagłówki dla danych
        const repetitionsHeaders = ["Order ID", ...uniqueCategories.map(category => `${category} Repetitions`)];
        const setsHeaders = ["Order ID", ...uniqueCategories.map(category => `${category} Sets`)];

        // Przygotuj struktury do przechowywania danych
        const repetitionsData: (string | number)[][] = [repetitionsHeaders];
        const setsData: (string | number)[][] = [setsHeaders];

        // Znajdź maksymalną wartość orderId aby utworzyć odpowiednią ilość wierszy
        const maxOrderId = Math.max(...fetchedData.map(item => item.orderId));

        // Inicjalizuj wiersze dla każdej wartości orderId
        for (let i = 1; i <= maxOrderId; i++) {
          const repRow: (string | number)[] = [i];
          const setRow: (string | number)[] = [i];

          uniqueCategories.forEach(category => {
            const item = fetchedData.find(data => data.orderId === i && data.category === category);
            if (item) {
              repRow.push(item.repetitions);
              setRow.push(item.sets);
            } else {
              repRow.push("");
              setRow.push("");
            }
          });

          repetitionsData.push(repRow);
          setsData.push(setRow);
        }

        setData({ repetitions: repetitionsData, sets: setsData });
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [mesocycleId]);

  const repetitionsOptions = {
    title: "Repetitions per Category",
    hAxis: { title: "Order ID" },
    vAxis: { title: "Repetitions" },
    series: categories.map((category, index) => ({
      curveType: "function",
      targetAxisIndex: index,
    })),
  };

  const setsOptions = {
    title: "Sets per Category",
    hAxis: { title: "Order ID" },
    vAxis: { title: "Sets" },
    series: categories.map((category, index) => ({
      curveType: "function",
      targetAxisIndex: index,
    })),
  };

  return (
    <div>
      <Chart
        chartType="LineChart"
        width="50%"
        height="200px"
        data={data.repetitions}
        options={repetitionsOptions}
      />
      <Chart
        chartType="LineChart"
        width="50%"
        height="200px"
        data={data.sets}
        options={setsOptions}
      />
    </div>
  );
};

export default Diagram;
