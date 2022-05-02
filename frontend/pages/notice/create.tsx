import { Grid } from "semantic-ui-react";
import React from 'react'
import styles from "../../styles/notice/create.module.css";
import allAxios from "../../src/lib/allAxios";
import { useEffect, useState } from 'react';
import IsLogin from "../../src/lib/customLogin";
import userAxios from "../../src/lib/userAxios";
import Router from "next/router";


export default function Qnacreate() {

    const [title, setTitle] = useState('')
    const [content, setContent] = useState('')
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
    const qnaSubmit = async() => {
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
    await allAxios.post('/qna', body)

    .then(() => {
        alert("Q&A가 작성되었습니다.")
        Router.push("/notice")
    })
    .catch((e)=>{
        console.log(e)
    })    
    }
    }

    function qnaTitleWrite(e: any){
        setTitle(e.target.value)
    }
    function qnaContentWrite(e: any){
        setContent(e.target.value)
    }

    // 비밀글
    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setIsSecret(e.target.checked);
      };

    useEffect(() => {
        if (!IsLogin()){
            Router.push("/notice");
            alert("게시글 작성은 로그인 후 이용가능합니다.")                
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
                            <dd><input value={title} type="text" placeholder="제목 입력" onChange={qnaTitleWrite}/></dd>
                        </dl>
                    </div>
                    <div className={styles.info}>
                        <dl>
                            <dt>글쓴이</dt>
                            <dd>{userInfo.nick_name}</dd>
                        </dl>
                        <dl>
                            <dt>
                            비밀글
                            </dt>
                            <dd>
                                <input type="checkbox" onChange={onChange} checked={isSecret} />
                            </dd>
                        </dl>
                    </div>
                    <div className={styles.cont}>
                        <textarea value={content} placeholder="내용 입력" onChange={qnaContentWrite}></textarea>
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