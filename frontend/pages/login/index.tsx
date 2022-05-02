import { useEffect, useState } from "react";
import { Form, Input, Grid, Button } from "semantic-ui-react";
import userAxios from "../../src/lib/userAxios";
import allAxios from "../../src/lib/allAxios";
import recoAxios from "../../src/lib/recoAxios";
// components
import { useRouter } from "next/router";
// css
import style from "../../styles/login/login.module.css";
export default function Index() {
  // 유저 정보 불러오기
  const [userInfo, setUserInfo]: any = useState([]);

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

  // 닉네임
  const [nick, setNick] = useState("");

  const handleChangeNick = (e: any) => {
    setNick(e.target.value);
  };

  // 성별 선택
  const [gender, setGender] = useState("남");

  const handleChangeGender = (e: any) => {
    setGender(e.target.value);
  };

  // 연령대 선택
  const ages = [
    { key: 1, text: "10대 이하", value: 10 },
    { key: 2, text: "20대", value: 20 },
    { key: 3, text: "30대", value: 30 },
    { key: 4, text: "40대 이상", value: 40 },
  ];

  const [age, setAge] = useState(10);

  const handleChangeAges = (e: any) => {
    setAge(e.target.value);
  };

  // 지역 선택
  const bigPlace = [
    { key: 1, text: "서울", value: "서울" },
    { key: 2, text: "경기/인천", value: "경기/인천" },
    { key: 3, text: "충청", value: "충청" },
    { key: 4, text: "강원", value: "강원" },
    { key: 5, text: "경상", value: "경상" },
    { key: 6, text: "전라", value: "전라" },
    { key: 7, text: "제주", value: "제주" },
  ];
  const [selectedBigPlace, setSelectedBigPlace] = useState("서울");
  const [smallPlace, setSmallPlace] = useState([]);
  const [selectedSmallPlace, setselectedSmallPlace] = useState("");

  useEffect(() => {
    getUserInfo();
    allAxios
      .get(`/information/region?largeRegion=서울`)
      .then(({ data }) => {
        setSmallPlace(data.smallRegions);
        return data.smallRegions;
      })
      .then((data) => {
        setselectedSmallPlace(data[0]);
      });
  }, []);
  const handleChangeBig = (e: any) => {
    allAxios
      .get(`/information/region?largeRegion=${e.target.value}`)
      .then(({ data }) => {
        setSelectedBigPlace(e.target.value);
        setSmallPlace(data.smallRegions);
      });
  };

  const handleChangeSmall = (e: any) => {
    setselectedSmallPlace(e.target.value);
  };

  // 장르 선택
  const genre = [
    { key: "thrill", text: "스릴러", value: 0 },
    { key: "romance", text: "로맨스", value: 0 },
    { key: "reasoning", text: "추리", value: 0 },
    { key: "sffantasy", text: "SF판타지", value: 0 },
    { key: "adventure", text: "모험액션", value: 0 },
    { key: "comedy", text: "코미디", value: 0 },
    { key: "crime", text: "범죄", value: 0 },
    { key: "horror", text: "공포", value: 0 },
    { key: "adult", text: "19금", value: 0 },
    { key: "drama", text: "감성드라마", value: 0 },
  ];

  const score = [
    { key: 1, text: "1점", value: 1 },
    { key: 2, text: "2점", value: 2 },
    { key: 3, text: "3점", value: 3 },
    { key: 4, text: "4점", value: 4 },
    { key: 5, text: "5점", value: 5 },
  ];
  const [scoreThrill, setThrill] = useState(1);
  const [scoreRomance, setRomance] = useState(1);
  const [scoreReasoning, setReasoning] = useState(1);
  const [scoreSffantasy, setSffantasy] = useState(1);
  const [scoreAdventure, setAdventure] = useState(1);
  const [scoreComedy, setComedy] = useState(1);
  const [scoreCrime, setCrime] = useState(1);
  const [scoreHorror, setHorror] = useState(1);
  const [scoreAdult, setAdult] = useState(1);
  const [scoreDrama, setDrama] = useState(1);

  const handleChangeThrill = (e: any) => {
    setThrill(Number(e.target.value));
  };
  const handleChangeRomance = (e: any) => {
    setRomance(Number(e.target.value));
  };
  const handleChangeReasoning = (e: any) => {
    setReasoning(Number(e.target.value));
  };
  const handleChangeSffantasy = (e: any) => {
    setSffantasy(Number(e.target.value));
  };
  const handleChangeAdventure = (e: any) => {
    setAdventure(Number(e.target.value));
  };
  const handleChangeComedy = (e: any) => {
    setComedy(Number(e.target.value));
  };
  const handleChangeCrime = (e: any) => {
    setCrime(Number(e.target.value));
  };
  const handleChangeHorror = (e: any) => {
    setHorror(Number(e.target.value));
  };
  const handleChangeAdult = (e: any) => {
    setAdult(Number(e.target.value));
  };
  const handleChangeDrama = (e: any) => {
    setDrama(Number(e.target.value));
  };

  const [scorearr, setScorearr] = useState([
    scoreThrill,
    scoreRomance,
    scoreReasoning,
    scoreSffantasy,
    scoreAdventure,
    scoreComedy,
    scoreCrime,
    scoreHorror,
    scoreAdult,
    scoreDrama,
  ]);
  useEffect(() => {
    setScorearr([
      scoreThrill,
      scoreRomance,
      scoreReasoning,
      scoreSffantasy,
      scoreAdventure,
      scoreComedy,
      scoreCrime,
      scoreHorror,
      scoreAdult,
      scoreDrama,
    ]);
  }, [
    scoreThrill,
    scoreRomance,
    scoreReasoning,
    scoreSffantasy,
    scoreAdventure,
    scoreComedy,
    scoreCrime,
    scoreHorror,
    scoreAdult,
    scoreDrama,
  ]);

  const [genreIdx, setGenreIdx] = useState(1);

  useEffect(() => {
    const maxScore = Math.max.apply(null, scorearr);
    setGenreIdx(scorearr.indexOf(Number(maxScore)));
  }, [scorearr]);

  const cb = async () => {
    await recoAxios
      .get(`/cb/${userInfo.id}/${genre[genreIdx].text}/`)
      .then((data) => {
        console.log("success");
        console.log(data);
      })
      .catch((e) => {
        console.log("fail");
        console.log(e);
      });
  };

  const cf1 = async () => {
    await recoAxios
      .get(`/cf/${userInfo.id}/${genre[genreIdx].text}/`)
      .then((data) => {
        console.log("success");
        console.log(data);
      })
      .catch((e) => {
        console.log("fail");
        console.log(e);
      });
  };

  const cf2 = async () => {
    console.log(genre[genreIdx].text);
    await recoAxios
      .get(`/cf/${userInfo.id}/${genre[genreIdx].text}/${gender}/${age}/`)
      .then((data) => {
        console.log("success");
        console.log(data);
      })
      .catch((e) => {
        console.log("fail");
        console.log(e);
      });
  };

  // 정보수정
  const router = useRouter();
  const edit = (e: any) => {
    if (nick === "") {
      alert("닉네임을 입력해주세요");
    }
    if (gender === null) {
      alert("성별을 선택해주세요");
    }
    if (selectedBigPlace === null) {
      alert("큰 지역을 선택해주세요");
    }
    if (selectedSmallPlace === null) {
      alert("작은 지역을 선택해주세요");
    }
    if (scoreThrill === null) {
      alert("스릴러 장르 선호도를 선택해주세요");
    }
    if (scoreAdult === null) {
      alert("성인 장르 선호도를 선택해주세요");
    }
    if (scoreAdventure === null) {
      alert("모험/액션 장르 선호도를 선택해주세요");
    }
    if (scoreComedy === null) {
      alert("코미디 장르 선호도를 선택해주세요");
    }
    if (scoreCrime === null) {
      alert("범죄 장르 선호도를 선택해주세요");
    }
    if (scoreDrama === null) {
      alert("감성/드라마 장르 선호도를 선택해주세요");
    }
    if (scoreHorror === null) {
      alert("공포 장르 선호도를 선택해주세요");
    }
    if (scoreReasoning === null) {
      alert("추리 장르 선호도를 선택해주세요");
    }
    if (scoreRomance === null) {
      alert("로맨스 장르 선호도를 선택해주세요");
    }
    if (scoreSffantasy === null) {
      alert("SF/판타지 장르 선호도를 선택해주세요");
    } else {
      e.preventDefault();

      const body = {
        age: Number(age),
        gender: gender,
        id: userInfo.id,
        large_region: selectedBigPlace,
        nick_name: nick,
        user_preference: {
          adult: scoreAdult,
          adventure: scoreAdventure,
          comedy: scoreComedy,
          crime: scoreCrime,
          drama: scoreDrama,
          horror: scoreHorror,
          reasoning: scoreReasoning,
          romance: scoreRomance,
          sf_fantasy: scoreSffantasy,
          thrill: scoreThrill,
        },
        small_region: selectedSmallPlace,
      };

      console.log(body);
      userAxios
        .post(`/user/recommend`, body)
        .then(({ data }) => {
          getUserInfo();
        })
        .then(() => {
          cb();
          cf2();
          cf1();
        })
        .then(() => {
          alert('추천 알고리즘을 분석중입니다. 3초후 이동합니다.')
          setTimeout(() => location.href = "https://j6c203.p.ssafy.io/", 4000)
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <>
      <Grid>
        <Grid.Row>
          <Grid.Column width={6}></Grid.Column>
          <Grid.Column width={4}>
            <Form>
              <br></br>
              <div className={style.middleText}>
                <h1>추가 정보를 입력해주세요!</h1>
                <h2>필수사항입니다!</h2>
              </div>
              <br></br>
              <br></br>
              <Form.Group inline>
                <Form.Field>
                  <span className={style.formLabel}>닉네임</span>
                  <Input
                    placeholder={userInfo.name}
                    onChange={(e) => {
                      handleChangeNick(e);
                    }}
                  />
                </Form.Field>
              </Form.Group>
              <br></br>

              <div>
                <label className={`${style.formLabel} ${style.gender_Margin}`}>
                  성별
                </label>
                <span> 남 </span>
                <input
                  id={"a"}
                  value={"남"}
                  name="platform"
                  type="radio"
                  checked={gender === "남"}
                  onChange={handleChangeGender}
                />
                <span> 여 </span>
                <input
                  id={"a"}
                  value={"여"}
                  name="platform"
                  type="radio"
                  checked={gender === "여"}
                  onChange={handleChangeGender}
                />
              </div>

              <br></br>
              <br></br>

              <Grid>
                <Grid.Row>
                  <Grid.Column width={3}>
                    <p className={style.formLabel}>연령대</p>
                  </Grid.Column>
                  <Grid.Column width={5}>
                    <select
                      onChange={(e) => {
                        handleChangeAges(e);
                      }}
                    >
                      {ages.map((a, i) => {
                        return (
                          <option value={a.value} key={i}>
                            {a.text}
                          </option>
                        );
                      })}
                    </select>
                  </Grid.Column>
                  <Grid.Column width={8}></Grid.Column>
                </Grid.Row>
              </Grid>
              <Grid>
                <Grid.Row>
                  <Grid.Column width={8}>
                    <h3>대분류 지역을 선택하세요</h3>
                  </Grid.Column>
                  <Grid.Column width={8}>
                    <h3>소분류 지역을 선택하세요</h3>
                  </Grid.Column>
                </Grid.Row>
                <Grid.Row>
                  <Grid.Column width={8}>
                    <div className={style.formWidth}>
                      <select onChange={(e) => handleChangeBig(e)}>
                        {bigPlace.map((p, i) => {
                          return (
                            <option value={p.value} key={p.key}>
                              {p.text}
                            </option>
                          );
                        })}
                      </select>
                    </div>
                  </Grid.Column>
                  <Grid.Column width={8}>
                    <div className={style.formWidth}>
                      <select onChange={(e) => handleChangeSmall(e)}>
                        {smallPlace.map((p, i) => {
                          return (
                            <option value={p} key={i}>
                              {p}
                            </option>
                          );
                        })}
                      </select>
                    </div>
                  </Grid.Column>
                </Grid.Row>
              </Grid>
              <br></br>
              <br></br>
              <div>
                <h2>장르 선호도를 선택해주세요</h2>
                <br></br>
                <Grid>
                  <Grid.Row>
                    <Grid.Column width={3}>
                      <p className={style.formLabel}>스릴러 </p>
                    </Grid.Column>
                    <Grid.Column width={4}>
                      <div className={style.formWidth}>
                        <select onChange={(e) => handleChangeThrill(e)}>
                          {score.map((s, i) => {
                            return (
                              <option value={s.value} key={i}>
                                {s.text}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                    </Grid.Column>
                    <Grid.Column width={1}></Grid.Column>
                    <Grid.Column width={3}>
                      <p className={style.formLabel}>로맨스</p>
                    </Grid.Column>
                    <Grid.Column width={4}>
                      <div className={style.formWidth}>
                        <select onChange={(e) => handleChangeRomance(e)}>
                          {score.map((s, i) => {
                            return (
                              <option value={s.value} key={i}>
                                {s.text}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                    </Grid.Column>
                    <Grid.Column width={1}></Grid.Column>
                  </Grid.Row>

                  <Grid.Row>
                    <Grid.Column width={3}>
                      <p className={style.formLabel}>추리</p>
                    </Grid.Column>
                    <Grid.Column width={4}>
                      <div className={style.formWidth}>
                        <select onChange={(e) => handleChangeReasoning(e)}>
                          {score.map((s, i) => {
                            return (
                              <option value={s.value} key={i}>
                                {s.text}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                    </Grid.Column>
                    <Grid.Column width={1}></Grid.Column>
                    <Grid.Column width={3}>
                      <p className={style.formLabel}>SF/판타지</p>
                    </Grid.Column>
                    <Grid.Column width={4}>
                      <div className={style.formWidth}>
                        <select onChange={(e) => handleChangeSffantasy(e)}>
                          {score.map((s, i) => {
                            return (
                              <option value={s.value} key={i}>
                                {s.text}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                    </Grid.Column>
                    <Grid.Column width={1}></Grid.Column>
                  </Grid.Row>
                  <Grid.Row>
                    <Grid.Column width={3}>
                      <p className={style.formLabel}>모험/액션</p>
                    </Grid.Column>
                    <Grid.Column width={4}>
                      <div className={style.formWidth}>
                        <select onChange={(e) => handleChangeAdventure(e)}>
                          {score.map((s, i) => {
                            return (
                              <option value={s.value} key={i}>
                                {s.text}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                    </Grid.Column>
                    <Grid.Column width={1}></Grid.Column>
                    <Grid.Column width={3}>
                      <p className={style.formLabel}>코미디</p>
                    </Grid.Column>
                    <Grid.Column width={4}>
                      <div className={style.formWidth}>
                        <select onChange={(e) => handleChangeComedy(e)}>
                          {score.map((s, i) => {
                            return (
                              <option value={s.value} key={i}>
                                {s.text}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                    </Grid.Column>
                    <Grid.Column width={1}></Grid.Column>
                  </Grid.Row>
                  <Grid.Row>
                    <Grid.Column width={3}>
                      <p className={style.formLabel}>범죄</p>
                    </Grid.Column>
                    <Grid.Column width={4}>
                      <div className={style.formWidth}>
                        <select onChange={(e) => handleChangeCrime(e)}>
                          {score.map((s, i) => {
                            return (
                              <option value={s.value} key={i}>
                                {s.text}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                    </Grid.Column>
                    <Grid.Column width={1}></Grid.Column>
                    <Grid.Column width={3}>
                      <p className={style.formLabel}>공포</p>
                    </Grid.Column>
                    <Grid.Column width={4}>
                      <div className={style.formWidth}>
                        <select onChange={(e) => handleChangeHorror(e)}>
                          {score.map((s, i) => {
                            return (
                              <option value={s.value} key={i}>
                                {s.text}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                    </Grid.Column>
                    <Grid.Column width={1}></Grid.Column>
                  </Grid.Row>
                  <Grid.Row>
                    <Grid.Column width={3}>
                      <p className={style.formLabel}>19금</p>
                    </Grid.Column>
                    <Grid.Column width={4}>
                      <div className={style.formWidth}>
                        <select onChange={(e) => handleChangeAdult(e)}>
                          {score.map((s, i) => {
                            return (
                              <option value={s.value} key={i}>
                                {s.text}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                    </Grid.Column>
                    <Grid.Column width={1}></Grid.Column>
                    <Grid.Column width={3}>
                      <p className={style.formLabel}>감성/드라마</p>
                    </Grid.Column>
                    <Grid.Column width={4}>
                      <div className={style.formWidth}>
                        <select onChange={(e) => handleChangeDrama(e)}>
                          {score.map((s, i) => {
                            return (
                              <option value={s.value} key={i}>
                                {s.text}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                    </Grid.Column>
                    <Grid.Column width={1}></Grid.Column>
                  </Grid.Row>
                </Grid>
              </div>

              <br></br>
              <Button primary onClick={edit}>
                작성
              </Button>
            </Form>
          </Grid.Column>
          <Grid.Column width={6}></Grid.Column>
        </Grid.Row>
      </Grid>
    </>
  );
}
