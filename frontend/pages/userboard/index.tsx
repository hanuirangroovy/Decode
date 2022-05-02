import {
    Pagination,
    Grid,
    Header,
    Select,
} from "semantic-ui-react";
import { useEffect, useState } from "react";
import React from 'react'
import styles from "../../styles/userboard/userboard.module.css";
import allAxios from "../../src/lib/allAxios";
import IsLogin from "../../src/lib/customLogin";
import Router from "next/router";


const options = [
    { key: '제목', value: '제목', text: '제목' },
    { key: '내용', value: '내용', text: '내용' },
    { key: '글쓴이', value: '글쓴이', text: '글쓴이' }
]

const regionOptions = [
    { key: '전체', value: '전체', text: '전체' },
    { key: '서울', value: '서울', text: '서울' },
    { key: '경기/인천', value: '경기/인천', text: '경기/인천' },
    { key: '충청', value: '충청', text: '충청' },
    { key: '강원', value: '강원', text: '강원' },
    { key: '경상', value: '경상', text: '경상' },
    { key: '전라', value: '전라', text: '전라' },
    { key: '제주', value: '제주', text: '제주' },
]

export default function Userboard() {

    // 지역선택
    const [region, setRegion] = useState(null)
    const [smallRegionOptions, setSmallRegionOptions] = useState([{ key: '전체', value: '전체', text: '전체' }])

    // 게시글 정보
    const [userboard, setUserboard] = useState([])
    const [pages, setPages] = useState(0)  //바뀌는 page
    const [totalPages, setTotalPages] = useState(10) // 최대 page
    const [title, setTitle] = useState('')
    const [id, setId] = useState(0)
    const [createdAt, setCreatedAt] = useState([])
    const [nickName, setNickName] = useState('')
    const [smallRegion, setSmallRegion] = useState(null)

    //지역 선택
    useEffect(() => {
        loadSmallRegion(null)
    }, [])

    function selectedRegion(e: any) {
        setPages(0)
        setSmallRegion(null)
        if (e.target.textContent === '전체') {
            loadSmallRegion(null)
            setRegion(null)
            return
        }
        setRegion(e.target.textContent)
        loadSmallRegion(e.target.textContent)
    }

    function selectedSmallRegion(e: any) {
        setPages(0)
        setSmallRegion(e.target.textContent)
    }

    const loadSmallRegion = async (region: any) => {
        await allAxios
            .get("/information/region", {
                params: {
                    largeRegion: region
                }
            })
            .then(({ data }) => {
                const tempRegion = data.smallRegions.map((regions: String) => {
                    return { key: regions, value: regions, text: regions }
                })
                setSmallRegionOptions(tempRegion)
            })
            .catch((e: any) => {
                console.log(e)
            })
    }

    // 게시글 정보 
    useEffect(() => {
        loadUserboard(pages)
    }, [pages, id, title, createdAt, nickName, smallRegion, region])


    const loadUserboard = async (pages: Number) => {
        await allAxios
            .get(`/article/board`, {
                params: {
                    title: title,
                    id: id,
                    largeRegion: region,
                    smallRegion: smallRegion,
                    nickName: nickName,
                    createdAt: createdAt,
                    page: pages,
                }
            })
            .then(({ data }) => {
                setTotalPages(data.articleList.totalPages)
                setUserboard(data.articleList.content)
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
    const goUserWrite = async () => {
        if (IsLogin()) {
            Router.push(`/userboard/create`)
        } else {
            alert('게시글 작성은 로그인 후 이용가능합니다.')
            return
        }
    }


    return (
        <>
            <Grid stackable>
                <Grid.Column width={2}></Grid.Column>
                <Grid.Column width={12}>
                    <div className={styles.board_wrap}>

                        <div className={styles.userboardtop}>
                            <Grid >
                                <Grid.Column width={13}>
                                    <div className={styles.board_title}>
                                        <strong>유저게시판</strong>
                                        <div>
                                            <div className={styles.board_select}>
                                                <div className={styles.select_title}>
                                                    <Header as='h5'>지역</Header>
                                                </div>
                                                <Select placeholder='지역' options={regionOptions} onChange={selectedRegion} />
                                            </div>
                                            <div className={styles.board_select}>
                                                <div className={styles.select_title}>
                                                    <Header as='h5'>세부지역</Header>
                                                </div>
                                                <Select placeholder='세부지역' options={smallRegionOptions} onChange={selectedSmallRegion} />
                                            </div>
                                        </div>
                                    </div>

                                </Grid.Column>
                                <Grid.Column width={3}>
                                    <div className={styles.bt_wrap}>
                                        <div className={styles.on} onClick={goUserWrite}>글 작성</div>
                                    </div>
                                </Grid.Column>
                            </Grid>
                        </div>
                        <div className={styles.board_list_wrap}>
                            <div className={styles.board_list}>
                                <div className={styles.top}>
                                    <div className={styles.num}>번호</div>
                                    <div className={styles.type}>지역</div>
                                    <div className={styles.title}>제목</div>
                                    <div className={styles.writer}>글쓴이</div>
                                    <div className={styles.date}>작성일</div>
                                </div>
                                {userboard ? userboard.map((board: any) => {
                                    return (
                                        <div className={styles.boardListContent} key={board.id}>
                                            <div className={styles.num}>{board.id}</div>
                                            <div className={styles.type}>{board.largeRegion}/{board.smallRegion}</div>
                                            <div className={styles.title} onClick={() => Router.push(`/userboard/${board.id}`)}>{board.title}</div>
                                            <div className={styles.writer}>{board.nickName}</div>
                                            <div className={styles.date}>{board.createdAt[0]}.{board.createdAt[1]}.{board.createdAt[2]}</div>
                                        </div>
                                    );
                                }) : ''}

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