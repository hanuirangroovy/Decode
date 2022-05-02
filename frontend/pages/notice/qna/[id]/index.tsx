import {
    Comment,
    Button,
    Grid,
    Icon,
  } from "semantic-ui-react";
import React from 'react'
import allAxios from "../../../../src/lib/allAxios";
import { useEffect, useState } from 'react';
import IsLogin from "../../../../src/lib/customLogin";
import userAxios from "../../../../src/lib/userAxios";
import Router, { useRouter } from "next/router";
import styles from "../../../../styles/notice/detail.module.css";


export default function Qna_detail() {
    const [userInfo, setUserInfo]: any = useState([])
    const [qnaDetail,setQnaDetail]:any = useState([])
    const [comments, setComments]:any = useState('')
    const [commentInfo, setCommentInfo]:any = useState([])
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
    
    // qna
    useEffect(() => {
        if (id){
            loadqnaDetail(id)
        }
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

    const deleteqna = () => {
        allAxios
        .delete(`/qna/${id}`)
        .then(()=>{
            alert("게시글이 삭제되었습니다.")
            router.push("/notice");
        })
        .catch(() => {
        alert("잠시 후 다시 시도해주세요.")
        });
    };

    // 댓글
    useEffect(() => {
        if (id){
        loadcomment(id)
        }
    }, [id])

    const loadcomment = async(id:Number) => {
        await allAxios
        .get(`/qnaComment/${id}`)
        .then(({ data }) => {
            setCommentInfo(data.commentList)
        })
        .catch((e:any) => {
            console.log(e)
        })      
    }  


    const submitComment = async() => {
        if (comments.length == 0 || comments.length > 100){
            alert('댓글은 1자 이상 100자 이하로 작성해주세요')
            return
        }
        if (IsLogin()){
            const body = {
                content: comments,
                id: qnaDetail.id,
                qnaId: qnaDetail.id,
                userId: userInfo.id,
            }
        await allAxios.post(`/qnaComment`, body)
        .then(() => {
            loadcomment(id)
            setComments(null)
            const commentsinput: any = document.getElementsByClassName('commentInput')[0]
            commentsinput['value'] = null
            commentsinput['textContent'] = null
            alert('리뷰가 작성되었습니다.')
        })
        .catch((e) => {
            console.log(e)
            alert('잠시 후 다시 시도해주세요')
        })
        }
    }

    function writeComment(e: any){
        setComments(e.target.value)
    }

    const commentDelete = async (commentId: Number) => {
        await allAxios.delete(`/qnaComment/${commentId}`, {
            params: {
                id: commentId
            }
        })
        .then(() => {
            loadcomment(0)
            alert('리뷰가 삭제되었습니다.')
        })
        .catch((e) => {
            console.log(e)
        })
    }

    // 우회 막기
    useEffect(() => {
        if (qnaDetail.isSecret == true){
            if (userInfo.id != qnaDetail.userId){}
            else if (userInfo.id != 46441431){}
            else{
                router.push("/notice");
                alert("비밀글입니다")
            }
        }
        else {
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
                    <strong>공지게시판</strong>
                </div>
                <div>문의사항이 있으실 경우 질문을 남겨주세요</div>
                </Grid.Column>
            </Grid>
        </div>
            <div className={styles.board_view_wrap}>
                <div className={styles.board_view}>
                    <div className={styles.title}>
                        {qnaDetail.title}
                    </div>
                    <div className={styles.info}>
                        <dl>
                            <dt>번호</dt>
                            <dd>{String(id)}</dd>
                        </dl>
                        <dl>
                            <dt>글쓴이</dt>
                            <dd>{qnaDetail.nickName}</dd>
                        </dl>
                        <dl>
                            <dt>작성일</dt>
                            <dd>{qnaDetail.createdAt?qnaDetail.createdAt[0]+'.'+qnaDetail.createdAt[1]+'.'+qnaDetail.createdAt[2]:''}</dd>
                        </dl>
                    </div>
                    <div className={styles.cont}>
                        {qnaDetail.content}
                    </div>
                    
                </div>
                <div className={styles.bt_wrap}>
                    <div className={styles.on} onClick={() => Router.back()}>목록</div>
                    {userInfo.id == qnaDetail.userId &&(
                    <>
                        <div className={styles.editbutton} onClick={() => router.push(`/notice/edit/${id}`)}> 수정</div>
                        <div className={styles.deletebutton} onClick={deleteqna}> 삭제</div>
                    </>
                    )}
                </div>
                <div className={styles.comment_cont}>
                    <div className={styles.comment_title}>
                        댓글
                    </div>
                        {userInfo.id?
                            <Grid verticalAlign='middle' centered stackable>
                            <Grid.Column width={1}>
                                <Comment.Group size='massive'>
                                    <Comment>
                                        <Comment. Avatar src={userInfo.image} />
                                    </Comment>
                                </Comment.Group>
                            </Grid.Column>
                            <Grid.Column width={1}>
                                <div className={styles.comment_createname}>
                                    {userInfo.nick_name}
                                </div>
                            </Grid.Column>
                            <Grid.Column width={12}>
                            <div>
                                <textarea className="commentInput" value={comments} placeholder="댓글을 작성해주세요" onChange={writeComment}></textarea>
                            </div>
                            </Grid.Column>
                            <Grid.Column width={2}>
                                <div className={styles.comment_button}>
                                <Button color='black' onClick={submitComment} animated='fade'>
                                    <Button.Content visible>
                                        <Icon name='write' />
                                    </Button.Content>
                                    <Button.Content hidden>
                                        작성
                                    </Button.Content>
                                </Button>
                                </div>
                            </Grid.Column>
                        </Grid>
                        :''}
                        <div className={styles.commentslocation}>
                        {commentInfo? commentInfo.map((comment:any) => {
                            return(
                                <Comment.Group key={comment.id}>
                                <Grid>
                                    <Grid.Column width={15}>
                                        <Comment>
                                        <Comment. Avatar src={comment.userImage} />
                                        <Comment.Content>
                                            <Comment.Author as='a'>{comment.nickName}</Comment.Author>
                                            <Comment.Metadata>
                                            <div>{comment.createdAt[0]}.{comment.createdAt[1]}.{comment.createdAt[2]}</div>
                                            </Comment.Metadata>
                                            <Comment.Text>
                                            <p>{comment.content}</p>
                                            </Comment.Text>
                                        </Comment.Content>
                                        </Comment>
                                    </Grid.Column>
                                    <Grid.Column width={1}>
                                    {userInfo.id == comment.userId? 
                                        <Button color='red' inverted animated='fade' onClick={function(){commentDelete(comment.id)}}>
                                        <Button.Content visible>
                                            <Icon name='trash alternate outline' key={comment.id} />
                                        </Button.Content>
                                        <Button.Content hidden>
                                            삭제
                                        </Button.Content>
                                        </Button>
                                    :''}
                                    </Grid.Column>
                                </Grid>

                                </Comment.Group>

                            );
                        }) : ''}
                        </div>
                        </div>
                    </div>
            </div>
    
    </Grid.Column>
    <Grid.Column width={2}></Grid.Column>
    </Grid>
    
    </>
);
}
