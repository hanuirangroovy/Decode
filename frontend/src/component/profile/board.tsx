import { Tab } from "semantic-ui-react";
import { useState, useEffect } from "react";
import IsLogin from "../../lib/customLogin";
import { useRouter } from "next/router";
import { Pagination } from "semantic-ui-react";
import { Grid } from "semantic-ui-react";
import style from "../../../styles/profile/Board.module.css";
import userAxios from "../../lib/userAxios";
import allAxios from "../../lib/allAxios";

export default function Board() {
  const router = useRouter();
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
          console.log(e);
        });
    }
  }, []);

  const [totalPageUserBoard, setTotalPageUserBoard] = useState(1);
  const [currentPagesUserBoard, setCurrentPagesUserBoard] = useState(1);
  // userBoard

  const [totalPageComment, setTotalPageComment] = useState(1);
  const [currentPagesComment, setCurrentPagesComment] = useState(1);
  // comment

  const [totalPageQa, setTotalPageQa] = useState(1);
  const [currentPagesQa, setCurrentPagesQa] = useState(1);
  // qa

  // 유저게시판 불러오기
  const [userBoard, setUserBoard] = useState([]);
  const tmpUserBoard: any = [];
  useEffect(() => {
    if (userInfo !== 0) {
      allAxios
        .get(
          `/article/profile/${userInfo.id}?page=${
            currentPagesUserBoard - 1
          }`
        )
        .then(({ data }) => {
          setTotalPageUserBoard(data.myArticleList.totalPages);
          data.myArticleList.content.map((d: any, i: number) => {
            tmpUserBoard.push(d);
          });
        })
        .then(() => {
          setUserBoard(tmpUserBoard);
        });
    }
  }, [currentPagesUserBoard, userInfo]);

  // 댓글 불러오기
  const [comment, setComment] = useState([]);
  useEffect(() => {
    if (userInfo !== 0) {
      allAxios
        .get(
          `/comment/profile/${userInfo.id}?page=${
            currentPagesComment - 1
          }`
        )
        .then(({ data }) => {
          setTotalPageComment(data.myArticleCommentList.totalPages);
          setComment(data.myArticleCommentList.content)
        })
    }
  }, [currentPagesComment, userInfo]);

  // Q&A 불러오기
  const [qa, setQA] = useState([]);
  const tmpQA: any = [];
  useEffect(() => {
    if (userInfo !== 0) {
      allAxios
        .get(
          `/qna/profile/${userInfo.id}?page=${
            currentPagesQa - 1
          }`
        )
        .then(({ data }) => {
          setTotalPageQa(data.myQnaList.totalPages);
          data.myQnaList.content.map((d: any, i: number) => {
            tmpQA.push(d);
          });
        })
        .then(() => {
          setQA(tmpQA);
        });
    }
  }, [currentPagesQa, userInfo]);

  function movePageUserBoard(e: any) {
    if (e.target.type === "nextItem") {
      if (currentPagesUserBoard === totalPageUserBoard) {
        return;
      } else {
        setCurrentPagesUserBoard(Number(currentPagesUserBoard + 1));
      }
    } else if (e.target.type === "prevItem") {
      if (currentPagesUserBoard === 1) {
        return;
      } else {
        setCurrentPagesUserBoard(Number(currentPagesUserBoard - 1));
      }
    } else if (e.target.type === "pageItem") {
      setCurrentPagesUserBoard(Number(e.target.textContent));
    }
  }

  function movePageComment(e: any) {
    if (e.target.type == "nextItem") {
      if (currentPagesComment === totalPageComment) {
        return;
      } else {
        setCurrentPagesComment(Number(currentPagesComment + 1));
      }
    } else if (e.target.type === "prevItem") {
      if (currentPagesComment === 1) {
        return;
      } else {
        setCurrentPagesComment(Number(currentPagesComment - 1));
      }
    } else if (e.target.type === "pageItem") {
      setCurrentPagesComment(Number(e.target.textContent));
    }
  }

  function movePageQa(e: any) {
    if (e.target.type == "nextItem") {
      if (currentPagesQa === totalPageQa) {
        return;
      } else {
        setCurrentPagesQa(Number(currentPagesQa + 1));
      }
    } else if (e.target.type === "prevItem") {
      if (currentPagesQa === 1) {
        return;
      } else {
        setCurrentPagesQa(Number(currentPagesQa - 1));
      }
    } else if (e.target.type === "pageItem") {
      setCurrentPagesQa(Number(e.target.textContent));
    }
  }

  const UserBoardRender = (t: any) => {
    if (t === 0) {
      return (
        <>
          <Tab.Pane attached={false}>
            <h3>첫 게시글을 작성해보세요!</h3>
          </Tab.Pane>
        </>
      );
    } else {
      return (
        <>
          <Tab.Pane attached={false}>
            <Grid divided>
              {userBoard.map((d: any, i: number) => {
                return (
                  <>
                    <Grid.Row
                      key={i}
                      onClick={() => {
                        router.push(`/userboard/${d.id}`);
                      }}
                      style={{ cursor: "pointer" }}
                    >
                      <Grid.Column width={3}>
                        번호: {i + 1 + (currentPagesUserBoard - 1) * 3}{" "}
                      </Grid.Column>
                      <Grid.Column width={5}>제목: {d.title}</Grid.Column>
                      <Grid.Column width={8}>내용: {d.content}</Grid.Column>
                    </Grid.Row>
                    <hr className={style.hr_line}></hr>
                  </>
                );
              })}
            </Grid>
          </Tab.Pane>
          <Pagination
            boundaryRange={0}
            ellipsisItem={null}
            firstItem={null}
            lastItem={null}
            siblingRange={2}
            totalPages={totalPageUserBoard}
            onClick={movePageUserBoard}
            activePage={currentPagesUserBoard}
          />
        </>
      );
    }
  };

  const CommentRender = (t: any) => {
    if (t === 0) {
      return (
        <>
          <Tab.Pane attached={false}>
            <h3>첫 댓글을 작성해보세요!</h3>
          </Tab.Pane>
        </>
      );
    } else {
      return (
        <>
          <Tab.Pane attached={false}>
            <Grid divided>
              {comment.map((d: any, i: number) => {
                return (
                  <>
                    <Grid.Row
                      key={i}
                      onClick={() => {
                        router.push(`/userboard/${d.articleId}`);
                      }}
                      style={{ cursor: "pointer" }}
                    >
                      <Grid.Column width={3}>
                        번호: {i + 1 + (currentPagesComment - 1) * 3}{" "}
                      </Grid.Column>
                      <Grid.Column width={5}>제목: {d.articleTitle}</Grid.Column>
                      <Grid.Column width={8}>내용: {d.content}</Grid.Column>
                    </Grid.Row>
                    <hr className={style.hr_line}></hr>
                  </>
                );
              })}
            </Grid>
          </Tab.Pane>
          <Pagination
            boundaryRange={0}
            ellipsisItem={null}
            firstItem={null}
            lastItem={null}
            siblingRange={2}
            totalPages={totalPageComment}
            onClick={movePageComment}
            activePage={currentPagesComment}
          />
        </>
      );
    }
  };

  const QaRender = (t: any) => {
    if (t === 0) {
      return (
        <>
          <Tab.Pane attached={false}>
            <h3>첫 문의글을 작성해보세요!</h3>
          </Tab.Pane>
        </>
      );
    } else {
      return (
        <>
          <Tab.Pane attached={false}>
            <Grid divided>
              {qa.map((d: any, i: number) => {
                return (
                  <>
                    <Grid.Row
                      key={i}
                      onClick={() => {
                        router.push(`/notice/qna/${d.id}`);
                      }}
                      style={{ cursor: "pointer" }}
                    >
                      <Grid.Column width={3}>
                        번호: {i + 1 + (currentPagesQa - 1) * 3}{" "}
                      </Grid.Column>
                      <Grid.Column width={5}>제목: {d.title}</Grid.Column>
                      <Grid.Column width={8}>내용: {d.content}</Grid.Column>
                    </Grid.Row>
                    <hr className={style.hr_line}></hr>
                  </>
                );
              })}
            </Grid>
          </Tab.Pane>

          <Pagination
            boundaryRange={0}
            ellipsisItem={null}
            firstItem={null}
            lastItem={null}
            siblingRange={2}
            totalPages={totalPageQa}
            onClick={movePageQa}
            activePage={currentPagesQa}
          />
        </>
      );
    }
  };

  const panes = [
    {
      menuItem: "유저 게시판",
      render: () => UserBoardRender(totalPageUserBoard),
    },
    {
      menuItem: "댓글",
      render: () => CommentRender(totalPageComment),
    },
    {
      menuItem: "Q&A",
      render: () => QaRender(totalPageQa),
    },
  ];

  return <Tab menu={{ secondary: true, pointing: true }} panes={panes} />;
}
