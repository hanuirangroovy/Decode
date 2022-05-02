import { Grid } from "semantic-ui-react";
import IsLogin from "../src/lib/customLogin";
import { useState, useEffect } from "react";
import getrecoAxios from "../src/lib/getrecoAxios";
// Component
import Data from "../src/component/main/data";
import Explain from "../src/component/main/explain";
import Detail from "../src/component/modal/detail";
import userAxios from "../src/lib/userAxios";
import allAxios from "../src/lib/allAxios";

export default function Home() {
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

  useEffect(() => {
    loadMyRecommend();
  }, [userInfo.id]);

  const [myRecommend, setMyRecommend]: any = useState([]);

  const loadMyRecommend = async () => {
    if (userInfo.id) {
      getrecoAxios
        .get(`/recommend/like/${userInfo.id}`)
        .then(({ data }) => {
          setMyRecommend(data);
        })
        .catch((e) => {
          console.log(e);
        });
    }
  };

  const [notLoginReco, setNotLoginReco] = useState([])
  useEffect(() => {
    allAxios.get('/home/nonmember')
    .then (({data}) => {
      const arr = data.themes
      arr.sort(function(){return Math.random() - Math.random();})
      setNotLoginReco(arr)
    })
  },[])

  if (IsLogin()) {
    return (
      <>
        <Grid stackable>
          <Grid.Row>
            <Grid.Column width={2}></Grid.Column>
            <Grid.Column width={12}>
              <h1>안녕하세요 {userInfo.nick_name}님!</h1>
              <h1>좋아하실만한 테마를 추천해드릴게요!</h1>
              <Grid centered columns={4}>
                <Grid.Column>
                  <Detail
                    themeId={myRecommend.like_one}
                    isImage={true}
                    w={250}
                    h={350}
                  />
                </Grid.Column>
                <Grid.Column>
                  <Detail
                    themeId={myRecommend.like_two}
                    isImage={true}
                    w={250}
                    h={350}
                  />
                </Grid.Column>
                <Grid.Column>
                  <Detail
                    themeId={myRecommend.like_three}
                    isImage={true}
                    w={250}
                    h={350}
                  />
                </Grid.Column>
                <Grid.Column>
                  <Detail
                    themeId={myRecommend.like_four}
                    isImage={true}
                    w={250}
                    h={350}
                  />
                </Grid.Column>
              </Grid>
              <Grid centered columns={5}>
                <Grid.Row />
                <Data />
              </Grid>
              <Explain />
            </Grid.Column>
            <Grid.Column width={2}></Grid.Column>
          </Grid.Row>
        </Grid>
      </>
    );
  } else {
    return (
      <>
        <Grid stackable>
          <Grid.Row>
            <Grid.Column width={2}></Grid.Column>
            <Grid.Column width={12}>
              <h1>안녕하세요 게스트님!</h1>
              <h1>로그인을 하고 맞춤 테마를 추천받아보세요!</h1>
              <Grid centered columns={4}>
                <Grid.Column>
                  <Detail
                    themeId={notLoginReco[0]}
                    isImage={true}
                    w={250}
                    h={350}
                  />
                </Grid.Column>
                <Grid.Column>
                  <Detail
                    themeId={notLoginReco[1]}
                    isImage={true}
                    w={250}
                    h={350}
                  />
                </Grid.Column>
                <Grid.Column>
                  <Detail
                    themeId={notLoginReco[2]}
                    isImage={true}
                    w={250}
                    h={350}
                  />
                </Grid.Column>
                <Grid.Column>
                  <Detail
                    themeId={notLoginReco[3]}
                    isImage={true}
                    w={250}
                    h={350}
                  />
                </Grid.Column>
              </Grid>
              <br></br>
              <br></br>
              <br></br>
              <Grid centered columns={5}>
                <Data />
              </Grid>
              <Explain />
            </Grid.Column>
            <Grid.Column width={2}></Grid.Column>
          </Grid.Row>
        </Grid>
      </>
    );
  }
}
