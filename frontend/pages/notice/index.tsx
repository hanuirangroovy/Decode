import {
    Pagination,
    Grid,
    Icon,
  } from "semantic-ui-react";
import React from 'react'
import { useEffect, useState } from "react";
import allAxios from "../../src/lib/allAxios";
import IsLogin from "../../src/lib/customLogin";
import Router from "next/router";
import styles from "../../styles/notice/notice.module.css";
import userAxios from "../../src/lib/userAxios";

export default function Notice() {
    const [qna, setQna]:any = useState([])
    const [pages, setPages] = useState(0)  //바뀌는 page
    const [totalPages, setTotalPages] = useState(10) // 최대 page
    const [title, setTitle] = useState('')
    const [id, setId] = useState(0)
    const [createdAt, setCreatedAt] = useState([])
    const [nickName, setNickName] = useState('')
    const [userInfo, setUserInfo]: any = useState([])
    const [notice, setNotice] = useState([])
    const [noticeTitle, setNoticeTitle] = useState('')
    const [noticeId, setNoticeId] = useState(0)
    const [noticeNickName, setNoticeNickName] = useState('')
    const [noticeCreatedAt, setNoticeCreatedAt] = useState([])
    const [isSecret, setIsSecret] = useState<boolean>(false)

    // 유저
    useEffect(() => {
        loadUser()
    }, [])

    const loadUser = async() => {
        if (IsLogin()){
            userAxios.get(`/auth/users`)
            .then(({ data }) => {
                setUserInfo(data.body.user)
            })
            .catch((e) => {
            console.log(e);
            alert('로그인 시간이 만료되었습니다.')
            });
          }
        }


    // 게시글 정보 (qna)
    useEffect(() => {
        loadqnaboard(pages)
    }, [pages, id, title, createdAt, nickName, isSecret])


    const loadqnaboard = async (pages: Number) => {
        await allAxios
            .get(`/qna`, {
                params: {
                    title: title,
                    id: id,
                    nickName: nickName,
                    createdAt: createdAt,
                    isSecret: isSecret,
                    page: pages,
                }
            })
            .then(({ data }) => {
                setTotalPages(data.qnaList.totalPages)
                setQna(data.qnaList.content)
            })
            .catch((e) => {
                console.log(e)
            })
    }
    // 공지사항
    useEffect(() => {
        loadNotice(id)
    }, [id, title, createdAt, nickName])

    const loadNotice = async (id: Number) => {
        await allAxios
            .get(`/notice`, {
                params: {
                    title: noticeTitle,
                    id: noticeId,
                    nickName: noticeNickName,
                    createdAt: noticeCreatedAt,
                }
            })
            .then(({ data }) => {
                setNotice(data.noticeList.content)
            })
            .catch((e) => {
                console.log(e)
            })
    }


    // 페이지
    function movePage(e: any) {
        console.log(e)
        if (e.target.type == "nextItem") {
            if (pages >= totalPages - 1) {
                return
            }
            setPages(pages + 1)
        } else if (e.target.type === "prevItem") {
            if (pages < 1) {
                return
            }
            setPages(Number(pages - 1))
        } else if (e.target.type === 'pageItem') {
            setPages(e.target.textContent - 1)
        }
    }

    // 글 작성 버튼 연결
    const goqnaWrite = async () => {
        if (IsLogin() && userInfo.id == 90489394) {
            Router.push(`/notice/noticecreate`)
        } else if(IsLogin()) {
            Router.push(`/notice/create`)
        }else {
            alert('게시글 작성은 로그인 후 이용가능합니다.')
            return
        }
    }

    // 비밀글 연결
    const moveSecretTitle = async () => {
            alert("비밀글입니다.")
        }





return (
    <>
    <Grid>
    <Grid.Column width={2}></Grid.Column>
    <Grid.Column width={12}>
    <div className={styles.board_wrap}>    
     
        <div className={styles.noticetop}>
            <Grid>
                <Grid.Column width={13}>
                <div className={styles.board_title}>
                    <strong>공지게시판</strong>
               </div> 
                <div>문의사항이 있으실 경우 질문을 남겨주세요</div>
                </Grid.Column>
                <Grid.Column width={3}>
                <div className={styles.bt_wrap}>
                    <div className={styles.on} onClick={goqnaWrite}>글 작성</div>
                </div>
                </Grid.Column>
            </Grid>
        </div>
        <div className={styles.board_list_wrap}>
            <div className={styles.board_list}>
                <div className={styles.top}>
                    <div className={styles.type}>유형</div>
                    <div className={styles.num}>번호</div>
                    <div className={styles.title}>제목</div>
                    <div className={styles.writer}>글쓴이</div>
                    <div className={styles.date}>작성일</div>
                </div>


                {notice? notice.map((board:any) => {
                    return(
                        <div className={styles.top_notice} key={board.id}>
                        <div className={styles.type}>공지</div>
                        <div className={styles.num}>{board.id}</div>
                        <div className={styles.title} onClick={() => Router.push(`/notice/notice/${board.id}`)}>{board.title}</div>
                        <div className={styles.writer}>{board.nickName}</div>
                        <div className={styles.date}>{board.createdAt[0]}.{board.createdAt[1]}.{board.createdAt[2]}</div>
                        </div>
                    );
                }) : ''}

                {qna ? qna.map((board:any) => {
                return (
                <div className={styles.qna_notice} key={board.id}>
                    <div className={styles.type}>Q&A</div>
                    <div className={styles.num}>{board.id}</div>
                    {board.isSecret == true && (userInfo.id ==  90489394 || userInfo.id ==board.userId)? 
                        <div className={styles.title} onClick={() => Router.push(`/notice/qna/${board.id}`)}>{board.title}
                        <Icon name='lock' />
                        </div>
                    :''}
                    {board.isSecret == true && (userInfo.id !=  90489394 && userInfo.id != board.userId)? 
                        <div className={styles.title} onClick={moveSecretTitle}>{board.title}
                        <Icon name='lock' />
                        </div>
                    :''}
                        {board.isSecret != true? 
                        <div className={styles.title} onClick={() => Router.push(`/notice/qna/${board.id}`)}>{board.title}
                        </div>
                    :''}
                    

                    <div className={styles.writer}>{board.nickName}</div>
                    <div className={styles.date}>{board.createdAt[0]}.{board.createdAt[1]}.{board.createdAt[2]}</div>
                </div>
                );
            }) : '' }
            </div>
            <div className={styles.board_page}>
            <Pagination
            boundaryRange={0}
            defaultActivePage={1}
            ellipsisItem={null}
            firstItem={null}
            lastItem={null}
            siblingRange={2}
            totalPages={totalPages}
            onClick={movePage}
             />

            </div>
        </div>
    </div>
    </Grid.Column>
    <Grid.Column width={2}></Grid.Column>
    </Grid>
    
    </>
);
}