import {
    Pagination,
    Grid,
  } from "semantic-ui-react";
import React, {Component} from 'react'
  
import styles from "../../styles/notice/detail.module.css";

export default function notice_detail() {

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
                        글 제목
                    </div>
                    <div className={styles.info}>
                        <dl>
                            <dt>번호</dt>
                            <dd>1</dd>
                        </dl>
                        <dl>
                            <dt>글쓴이</dt>
                            <dd>하루</dd>
                        </dl>
                        <dl>
                            <dt>작성일</dt>
                            <dd>2021.03.14</dd>
                        </dl>
                        <dl>
                            <dt>조회</dt>
                            <dd>127</dd>
                        </dl>
                    </div>
                    <div className={styles.cont}>
                        글 내용이 들어갑니다.
                    </div>
                    
                </div>
                <div className={styles.bt_wrap}>
                    {/* <a href="/notice" className={styles.on}>목록</a> */}
                    <a href="/create">수정</a>
                </div>
            </div>
    </div>
    </Grid.Column>
    <Grid.Column width={2}></Grid.Column>
    </Grid>
    
    </>
);
}
