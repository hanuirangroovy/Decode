import { Statistic } from "semantic-ui-react";
import axios from "axios";
import { useEffect, useState } from "react";
import style from "../../../styles/main/Data.module.css"
import allAxios from "../../lib/allAxios";
import userAxios from "../../lib/userAxios";

export default function Data() {
  const [themeCnt, setThemeCnt] = useState(0);
  const [storeCnt, setStoreCnt] = useState(0);

  useEffect(() => {
    allAxios
      .get(`/home`).then(({ data }) => {
      setThemeCnt(data.count[0]);
      setStoreCnt(data.count[1]);
    });
  }, []);

  const [userCnt, setUserCnt] = useState(0)
  useEffect(() => {
    userAxios
      .get(`/user/userCount`).then(({ data }) => {
      setUserCnt(data.userCount)
    });
  }, []);
  return (
    <>
      <Statistic.Group>
        <Statistic color="yellow" className={style.font}>
          <Statistic.Value>{themeCnt}</Statistic.Value>
          <Statistic.Label>테마 데이터</Statistic.Label>
        </Statistic>
        <Statistic color="orange" className={style.font}>
          <Statistic.Value>{storeCnt}</Statistic.Value>
          <Statistic.Label>지점 데이터</Statistic.Label>
        </Statistic>
        <Statistic color="olive" className={style.font}>
          <Statistic.Value>{userCnt}</Statistic.Value>
          <Statistic.Label>유저 데이터</Statistic.Label>
        </Statistic>
      </Statistic.Group>
    </>
  );
}
