import { Grid } from "semantic-ui-react";
import React from 'react'
import styles from "../../../styles/notice/create.module.css";
import allAxios from "../../../src/lib/allAxios";
import { useEffect, useState } from 'react';
import IsLogin from "../../../src/lib/customLogin";
import userAxios from "../../../src/lib/userAxios";
import Router, { useRouter } from "next/router";


export default function NoticeEdit() {
    const [userInfo, setUserInfo]: any = useState({
        id: 0,
        nick_name: ''
    });

    const [noticeDetail,setNoticeDetail]:any = useState({
        title: '',
        content: '',
        isNotice: false,
        isSecret: false,
    })
    const router = useRouter()
    const id = Number(router.query.id)

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

    // 글 정보
    useEffect(() => {
        if (id) {
        loadNoticeDetail(id)
        }
    }, [id])

    const loadNoticeDetail = async(id:Number) => {
        await allAxios
            .get(`/notice/${id}`)
            .then(({ data }) => {
                setNoticeDetail(data.notice)
            })
            .catch((e) => {
                console.log(e)
            })
    }


    // 글 수정
    const noticeSubmit = async() => {
        if (noticeDetail.title.length == 0){
            alert('제목을 작성해주세요')
            return
        }
        if (noticeDetail.content.length == 0){
            alert('내용을 작성해주세요')
            return
        }

        if (!IsLogin()){
            return
        }

        const body = {
            "title": noticeDetail.title,
            "content": noticeDetail.content,
            "userId": userInfo.id,
            "isSecret": noticeDetail.isSecret,
            "isNotice": noticeDetail.isNotice,
            "id": noticeDetail.id,
        }

        await allAxios.put(`/notice/${id}`, body)
        
        .then(() => {
            alert("Q&A가 수정되었습니다.")
            Router.push(`/notice/notice/${id}`)
        })
        .catch((e)=>{
            console.log(e)
            alert("잠시 후 시도해주세요.")
        })    
        }

    const handleOnChange = (e:any) => {
        const {value, name} = e.target;

        setNoticeDetail({
            ...noticeDetail,
            [name]:value,
        });


    }

      useEffect(() => {
        if (userInfo.id && (userInfo.id != noticeDetail.userId)){
            Router.push(`/notice/notice/${id}`);
            alert("게시글 수정은 작성한 본인만 이용가능합니다.")                
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
                <div>공지사항 수정 페이지입니다.</div>
                </Grid.Column>
            </Grid>
        </div>
            <div className={styles.board_write_wrap}>
                <div className={styles.board_write}>
                    <div className={styles.title}>
                        <dl>
                            <dt>제목</dt>
                            <dd><input name="title" value={noticeDetail.title} type="text" placeholder="제목 입력" onChange={handleOnChange}/></dd>
                        </dl>
                    </div>
                    <div className={styles.info}>
                        <dl>
                            <dt>글쓴이</dt>
                            <dd>{noticeDetail.nickName}</dd>
                        </dl>
                    </div>
                    <div className={styles.cont}>
                        <textarea name="content" value={noticeDetail.content} placeholder="내용 입력" onChange={handleOnChange}></textarea>
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