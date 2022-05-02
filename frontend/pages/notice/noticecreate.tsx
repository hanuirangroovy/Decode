import { Grid } from "semantic-ui-react";
import React from 'react'
import styles from "../../styles/notice/create.module.css";
import allAxios from "../../src/lib/allAxios";
import { useEffect, useState } from 'react';
import IsLogin from "../../src/lib/customLogin";
import userAxios from "../../src/lib/userAxios";
import Router from "next/router";


export default function Noticecreate() {

    const [title, setTitle] = useState('')
    const [content, setContent] = useState([])
    const [userInfo, setUserInfo]: any = useState([])
    const [isNotice, setIsNotice] = useState(false)
    const [isSecret, setIsSecret] = useState<boolean>(false)
    const [id, setId] = useState(0)

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

    // 글 작성
    const noticeSubmit = async() => {
        if (title.length == 0){
            alert('제목을 작성해주세요')
            return
        }
        if (content.length == 0){
            alert('내용을 작성해주세요')
            return
        }
        if (IsLogin()){
            const body = {
                title: title,
                content: content,
                userId: userInfo.id,
                id: id,
                isNotice: isNotice,
                isSecret: isSecret,
                nickName: userInfo.nick_name,
            }
    await allAxios.post('/notice', body)

    .then(() => {
        alert("공지사항이 작성되었습니다.")
        Router.push("/notice")
    })
    .catch((e)=>{
        console.log(e)
    })    
    }
    }

    function noticeTitleWrite(e: any){
        setTitle(e.target.value)
    }
    function noticeContentWrite(e: any){
        setContent(e.target.value)
    }

    // 우회 막기
    useEffect(() => {
        if (userInfo.id && (userInfo.id != 90489394)){
            Router.push("/notice");
            alert("관리자 작성페이지입니다.")                
        }
    })



return (
    <>
    <Grid>
    <Grid.Column width={2}></Grid.Column>
    <Grid.Column width={12}>
    <div className={styles.board_wrap}>    
        <div className={styles.noticetext}>
            <Grid>
                <Grid.Column width={16}>
                <div className={styles.board_title}>
                    <strong>공지사항</strong>
                </div>
                <div>공지사항을 작성해주세요.</div>
                </Grid.Column>
            </Grid>
        </div>
            <div className={styles.board_write_wrap}>
                <div className={styles.board_write}>
                    <div className={styles.title}>
                        <dl>
                            <dt>제목</dt>
                            <dd><input value={title} type="text" placeholder="제목 입력" onChange={noticeTitleWrite}/></dd>
                        </dl>
                    </div>
                    <div className={styles.info}>
                        <dl>
                            <dt>글쓴이</dt>
                            <dd>{userInfo.nick_name}</dd>
                        </dl>
                    </div>
                    <div className={styles.cont}>
                        <textarea value={content} placeholder="내용 입력" onChange={noticeContentWrite}></textarea>
                    </div>

                </div>


                <div className={styles.bt_wrap}>
                    <div className={styles.on} onClick={noticeSubmit}>등록</div>
                    <div onClick={() => Router.back()}>취소</div>
                </div>
            </div>
    </div>
    </Grid.Column>
    <Grid.Column width={2}></Grid.Column>
    </Grid>
    </>
    );
    }