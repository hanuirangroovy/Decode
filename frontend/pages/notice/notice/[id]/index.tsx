import { Grid } from "semantic-ui-react";
import React from 'react'
import allAxios from "../../../../src/lib/allAxios";
import { useEffect, useState } from 'react';
import IsLogin from "../../../../src/lib/customLogin";
import userAxios from "../../../../src/lib/userAxios";
import { useRouter } from "next/router";
import styles from "../../../../styles/notice/detail.module.css";

export default function Notice_detail() {

    const [userInfo, setUserInfo]: any = useState([])
    const [noticeDetail,setNoticeDetail]:any = useState([])
    const router = useRouter()
    const id = Number(router.query.id)

    // 유저
    useEffect(() => {
        loadUser()
    }, [])

    useEffect(() => {
        if (id){
        loadnoticeDetail(id)
        }
    }, [id])

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

    const loadnoticeDetail = async(id:Number) => {
        await allAxios
            .get(`/notice/${id}`)
            .then(({ data }) => {
                setNoticeDetail(data.notice)
            })
            .catch((e) => {
                console.log(e)
            })      
    }

    const deleteNotice = () => {
        allAxios
        .delete(`/notice/${id}`)
        .then(()=>{
            alert("게시글이 삭제되었습니다.")
            router.push("/notice");
        })
        .catch(() => {
        alert("잠시 후 다시 시도해주세요.")
        });
    };


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
                    <strong>공지게시판</strong>
                </div>
                <div>문의사항이 있으실 경우 질문을 남겨주세요</div>
                </Grid.Column>
            </Grid>
        </div>
            <div className={styles.board_view_wrap}>
                <div className={styles.board_view}>
                    <div className={styles.title}>
                        {noticeDetail.title}
                    </div>
                    <div className={styles.info}>
                        <dl>
                            <dt>번호</dt>
                            <dd>{String(id)}</dd>
                        </dl>
                        <dl>
                            <dt>글쓴이</dt>
                            <dd>{noticeDetail.nickName}</dd>
                        </dl>
                        <dl>
                            <dt>작성일</dt>
                            <dd>{noticeDetail.createdAt?noticeDetail.createdAt[0]+'.'+noticeDetail.createdAt[1]+'.'+noticeDetail.createdAt[2]:''}</dd>
                        </dl>
                    </div>
                    <pre className={styles.cont}>
                        {noticeDetail.content}
                    </pre>
                    
                </div>
                <div className={styles.bt_wrap}>
                    <div className={styles.on} onClick={() => router.push('/notice')}>목록</div>
                    {userInfo.id == noticeDetail.userId &&(
                    <>
                        <div className={styles.editbutton} onClick={() => router.push(`/notice/noticeedit/${id}`)}> 수정</div>
                        <div className={styles.deletebutton} onClick={deleteNotice}> 삭제</div>
                    </>
                    )}
                </div>
                </div>
            </div>
    
    </Grid.Column>
    <Grid.Column width={2}></Grid.Column>
    </Grid>
    
    </>
);
}
