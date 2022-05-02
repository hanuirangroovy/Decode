import { useState, useEffect } from "react";
import IsLogin from "../../lib/customLogin";
import { Pie } from "react-chartjs-2";
import "chart.js/auto";
import userAxios from "../../lib/userAxios";
import allAxios from "../../lib/allAxios";

export default function Graph() {
  const [userInfo, setUserInfo]: any = useState(0);

  useEffect(() => {
    if (IsLogin()) {
      var Token: any = null;
      if (typeof window !== "undefined") Token = localStorage.getItem("token");

      userAxios
        .get("/auth/users", {
          headers: { Authorization: `Bearer ${Token}` },
        })
        .then(({ data }) => {
          setUserInfo(data.body.user);
        })
        .catch((e: any) => {
          console.log("에러");
          console.log(e);
        });
    }
  }, []);

  var tmparr: any = [];
  const [genreCnt, SetGenreCnt] = useState([]);

  const [sumGenre, setSumGenre] = useState(0)
  useEffect(() => {
    if (userInfo !== 0) {
      let tmpSum = 0
      allAxios
        .get(`/review/mygenre/${userInfo.id}`)
        .then((data) => {
          data.data.genre.map((d: any, i: number) => {
            tmpSum += d
            tmparr.push(d);
          });
          setSumGenre(tmpSum)
          SetGenreCnt(tmparr);
        })
        .catch((e: any) => {});
    }
  }, [userInfo]);

  // 그래프 챠트
  const MyChart = () => {
    const options = {
      responsive: true,
      maintainAspectRatio: false,
      legend: {
        display: false,
      },
    };

    const pieData = {
      labels: [
        "스릴러",
        "로맨스",
        "추리",
        "SF/판타지",
        "모험/액션",
        "코미디",
        "범죄",
        "공포",
        "19금",
        "감성/드라마",
      ],
      datasets: [
        {
          data: genreCnt,
          backgroundColor: [
            "#571EF5",
            "#FF007F",
            "#A0A0A0",
            "#006666",
            "#994C00",
            "#FFFF33",
            "#000000",
            "#000066",
            "#FF0000",
            "#FFCCE5",
          ],
        },
      ],
    };

    return (
      <div>
        <div>
          <Pie data={pieData} options={options} style={{ height: "250px" }} />
        </div>
      </div>
    );
  };
  if (sumGenre === 0) {
    return (
      <>
        <h3>방탈출 클리어 기록을 남기고</h3>
        <h3>자신의 그래프를 확인해보세요!</h3>
      </>
    )
  } else {
    return (
      <div className="graph">
        <MyChart />
      </div>
    );
  }
  
}
