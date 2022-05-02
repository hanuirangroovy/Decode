import { Grid, Image } from "semantic-ui-react";
import { useEffect, useState } from "react";
// Components
import IsLogin from "../../src/lib/customLogin";
import Graph from "../../src/component/profile/graph";
import ClearList from "../../src/component/profile/clearList";
import UserInfo from "../../src/component/profile/userInfo";
import Board from "../../src/component/profile/board";
import userAxios from "../../src/lib/userAxios";

// style
import style from "../../styles/profile/Profile.module.css"

export default function Index() {
  const [userInfo, setUserInfo]: any = useState([]);

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

  const ImageSpace = () => {
    if (userInfo.image === null || userInfo.image === undefined) {
      return (
        <>
          <Image className={style.img} src="/images/noImage.png" alt="profileImage"></Image>
        </>
      )
    } else {
      return (
      <Image className={style.img} src={userInfo.image} alt="profileImage"></Image>
      )
    }
  }

  if (IsLogin()) {
    return (
      <>
        <Grid stackable>
          <Grid.Row>
            {/* 여백 */}
            <Grid.Column width={2}></Grid.Column>
            {/* 왼쪽 프로필사진, 정보, 그래프 */}
            <Grid.Column width={6}>
              <Grid centered>
                <Grid.Column width={10}>
                <div className={style.minHeight}>
                  <h2>My Profile</h2>
                  <ImageSpace></ImageSpace>
                  </div>
                </Grid.Column>
                <Grid.Column width={6}>
                  <br></br>
                  <br></br>
                  <br></br>
                  <UserInfo></UserInfo>
                </Grid.Column>
              </Grid>
              <h2>My Theme Graph</h2>
              <Graph></Graph>
            </Grid.Column>

            {/* 오른쪽 클리어리스트, 게시글 */}
            <Grid.Column width={6}>
              <div className={style.minHeight}>
                <h2>My Board</h2>
                <Board></Board>
              </div>
              <h2>My Clear List</h2>
              <ClearList></ClearList>
            </Grid.Column>

            {/* 여백 */}
            <Grid.Column width={2}></Grid.Column>
          </Grid.Row>
        </Grid>
      </>
    );

  } else {
    return <></>;
  }
}
