import { Grid } from "semantic-ui-react";
import React from 'react'
import styles from "../../../styles/notice/create.module.css";
import allAxios from "../../../src/lib/allAxios";
import { useEffect, useState } from 'react';
import IsLogin from "../../../src/lib/customLogin";
import userAxios from "../../../src/lib/userAxios";
import Router, { useRouter } from "next/router";


export default function QnaEdit() {
    const [userInfo, setUserInfo]: any = useState({
        id: 0,
        nick_name: ''
    });

    const [qnaDetail,setQnaDetail]:any = useState({
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
        loadqnaDetail(id)
    }, [id])

    const loadqnaDetail = async(id:Number) => {
        await allAxios
            .get(`/qna/${id}`)
            .then(({ data }) => {
                setQnaDetail(data.qna)
            })
            .catch((e) => {
                console.log(e)
            })
    }


    // 글 수정
    const qnaSubmit = async() => {
        if (qnaDetail.title.length == 0){
            alert('제목을 작성해주세요')
            return
        }
        if (qnaDetail.content.length == 0){
            alert('내용을 작성해주세요')
            return
        }

        if (!IsLogin()){
            return
        }

        const body = {
            "title": qnaDetail.title,
            "content": qnaDetail.content,
            "userId": userInfo.id,
            "isSecret": qnaDetail.isSecret,
            "isNotice": qnaDetail.isNotice,
            "id": qnaDetail.id,
        }

        await allAxios.put(`/qna/${id}`, body)
        
        .then(() => {
            alert("Q&A가 수정되었습니다.")
            Router.push(`/notice/qna/${id}`)
        })
        .catch((e)=>{
            console.log(e)
            alert("잠시 후 시도해주세요.")
        })    
        }

    const handleOnChange = (e:any) => {
        const {value, name} = e.target;

        setQnaDetail({
            ...qnaDetail,
            [name]:value,
        });


    }

    useEffect(() => {
        if (userInfo.id && (userInfo.id != qnaDetail.userId)){
            Router.push(`/notice/edit/${id}`);
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
                    <strong>Q&A</strong>
                </div>
                <div>문의사항이 있으실 경우 질문을 남겨주세요</div>
                </Grid.Column>
            </Grid>
        </div>
            <div className={styles.board_write_wrap}>
                <div className={styles.board_write}>
                    <div className={styles.title}>
                        <dl>
                            <dt>제목</dt>
                            <dd><input name="title" value={qnaDetail.title} type="text" placeholder="제목 입력" onChange={handleOnChange}/></dd>
                        </dl>
                    </div>
                    <div className={styles.info}>
                        <dl>
                            <dt>글쓴이</dt>
                            <dd>{qnaDetail.nickName}</dd>
                        </dl>
                        <dl>
                            <dt>
                            비밀글
                            </dt>
                            <dd>
                                <input type="checkbox" checked={qnaDetail.isSecret} />
                            </dd>
                        </dl>
                    </div>
                    <div className={styles.cont}>
                        <textarea name="content" value={qnaDetail.content} placeholder="내용 입력" onChange={handleOnChange}></textarea>
                    </div>

                </div>


                <div className={styles.bt_wrap}>
                    <div className={styles.on} onClick={qnaSubmit}>등록</div>
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