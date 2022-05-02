import { useState, useEffect } from "react";
import IsLogin from "../../lib/customLogin";
import UserInfoModal from "./userInfoModal";
import userAxios from "../../lib/userAxios";
import { Icon } from "semantic-ui-react";

export default function UserInfo() {
  const [userInfo, setUserInfo]: any = useState(0);

  const getUserInfo = async () => {
    userAxios
      .get(`/auth/users`)
      .then((data) => {
        setUserInfo(data.data.body.user);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const genreArr = [
    "19금",
    "모험/액션",
    "코미디",
    "드라마/감성",
    "공포",
    "추리",
    "SF/판타지",
    "스릴러",
    "범죄",
    "로맨스",
  ];
  const [favoriteGenre, setFavoriteGenre] = useState("");
  useEffect(() => {
    if (IsLogin()) {
      getUserInfo();
    }
  }, []);

  useEffect(() => {
    if (userInfo !== 0) {
      userAxios
        .get(`/user/allProfile/${userInfo.id}`)
        .then(({ data }) => {
          setFavoriteGenre(
            genreArr[
              [
                data.adult,
                data.adventure,
                data.comedy,
                data.drama,
                data.horror,
                data.reasoning,
                data.sf_fantasy,
                data.thrill,
                data.crime,
                data.romance,
              ].indexOf(
                Number(
                  Math.max.apply(null, [
                    data.adult,
                    data.adventure,
                    data.comedy,
                    data.drama,
                    data.horror,
                    data.reasoning,
                    data.sf_fantasy,
                    data.thrill,
                    data.crime,
                    data.romance,
                  ])
                )
              )
            ]
          );
        });
    }
  }, [userInfo])

  return (
    <>
      <span>
      <h3>
        {String(JSON.stringify(userInfo.nick_name)).substr(
          1,
          String(userInfo.nick_name).length
        )}&nbsp;님
      </h3>
      <h3>연령대:&nbsp; {JSON.stringify(userInfo.age)}대</h3>
      <h3>성별:&nbsp; {String(JSON.stringify(userInfo.gender)).substr(1, 1)=='남'?<>남<Icon color={'blue'} name='man'/></>:<>여<Icon color={'red'} name='woman' /></>}</h3>
      <h3>선호장르:&nbsp; {favoriteGenre}</h3>
      <h3>
        활동지역:&nbsp;
        {String(JSON.stringify(userInfo.small_region)).substr(
          1,
          String(userInfo.small_region).length
        )}
      </h3>
      </span><br />
      <UserInfoModal></UserInfoModal>
    </>
  );
}
