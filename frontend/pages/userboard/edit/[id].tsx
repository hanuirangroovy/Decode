import {
    Grid,
    Select,
  } from "semantic-ui-react";
import React from 'react'
import styles from "../../../styles/userboard/create.module.css";
import allAxios from "../../../src/lib/allAxios";
import { useEffect, useState } from 'react';
import IsLogin from "../../../src/lib/customLogin";
import userAxios from "../../../src/lib/userAxios";
import Router, { useRouter } from "next/router";



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

export default function Userboard_edit() {
    const [userInfo, setUserInfo]: any = useState({
        id: 0,
        nick_name: ''
    });
    const [userboardDetail,setUserboardDetail]:any = useState({
        title: '',
        content: '',
        userId: 0,
        nickName: '',
    });
    const router = useRouter()
    const id = Number(router.query.id)

    // 지역 선택    
    const [region, setRegion] = useState(null)
    const [smallRegion, setSmallRegion] = useState(null)
    const [smallRegionOptions, setSmallRegionOptions] = useState([{ key: '전체', value: '전체', text: '전체' }])

    useEffect(() => {
        loadSmallRegion(null)
    }, [])

    useEffect(() => {
        setRegion(userboardDetail.largeRegion)
        setSmallRegion(userboardDetail.smallRegion)
    }, [userboardDetail])

    function selectedRegion(e: any){
        setSmallRegion(null)
        if (e.target.textContent === '전체'){
            loadSmallRegion(null)
            setRegion(null)
            return
        }
        setRegion(e.target.textContent) 
        loadSmallRegion(e.target.textContent)
    }

    function selectedSmallRegion(e: any){
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
                    return {key: regions, value:regions, text: regions}
                })
                setSmallRegionOptions(tempRegion)
            })
            .catch((e: any) => {
                console.log(e)
            })
    }

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

    // 게시글 정보
    useEffect(() => {
        if(id){
        loaduserboardDetail(id)
        }
    }, [id])

    const loaduserboardDetail = async(id:Number) => {
        await allAxios
            .get(`/article/${id}`)
            .then(({ data }) => {
                setUserboardDetail(data.article)
            })
            .catch((e) => {
                console.log(e)
            })
    }


    // 글 수정
    const userboardSubmit = async() => {
        if (userboardDetail.title.length == 0){
            alert('제목을 작성해주세요')
            return
        }
        if (userboardDetail.content.length == 0){
            alert('내용을 작성해주세요')
            return
        }
        if (!smallRegion || !region){
            alert('지역을 선택해주세요')
        }
        if (!IsLogin()){
            return
        }
        const body = {
            "title": userboardDetail.title,
            "content": userboardDetail.content,
            "smallRegion": smallRegion,
            "largeRegion": region,
            "userId": userInfo.id,
        }

        await allAxios.put(`/article/${id}`, body)

        .then(() => {
            alert("게시글이 수정되었습니다.")
            Router.push(`/userboard/${id}`)
        })
        .catch((e)=>{
            alert("잠시 후 시도해주세요")
        })    
        }



    const handleOnChange = (e:any) => {
        const {value, name} = e.target;

        setUserboardDetail({
            ...userboardDetail,
            [name]:value,
        });


    }
 

    useEffect(() => {
        if (userInfo.id && (userInfo.id != userboardDetail.userId)){
            Router.push("/userboard");
            alert("게시글 수정은 작성한 본인만 이용가능합니다.")                
        }
    })


return (
    <>
    <Grid stackable>
    <Grid.Column width={2}></Grid.Column>
    <Grid.Column width={12}>
    <div className={styles.board_wrap}>    
        <div className={styles.noticetext}>
            <Grid>
                <Grid.Column width={16}>
                <div className={styles.board_title}>
                    <strong>유저게시판</strong>
                </div>
                <div>유저게시판 게시글 수정페이지입니다.</div>
                </Grid.Column>
            </Grid>
        </div>
            <div className={styles.board_write_wrap}>
                <div className={styles.board_write}>
                    <div className={styles.title}>
                        <dl>
                            <dt>제목</dt>
                            <dd><input name="title" value={userboardDetail.title} type="text" placeholder="제목 입력" onChange={handleOnChange} /></dd>
                        </dl>
                    </div>
                    <div className={styles.info}>
                        <dl>
                            <dt>글쓴이</dt>
                            <dd>{userboardDetail.nickName}</dd>
                        </dl>
                        <dl>
                            <dt>지역선택</dt>
                            <dd>
                                <div className={styles.board_select}>
                                    <Select placeholder={userboardDetail.largeRegion} options={regionOptions} onChange={selectedRegion}/>
                                </div>
                                <div className={styles.board_select}>
                                    <Select placeholder={userboardDetail.smallRegion} options={smallRegionOptions} onChange={selectedSmallRegion} />
                                </div>
                            </dd>
                        </dl>
                    </div>
                    <div className={styles.cont}>
                        <textarea name="content" value={userboardDetail.content} placeholder="내용 입력" onChange={handleOnChange}></textarea>
                    </div>
                    
                </div> 


                <div className={styles.bt_wrap}>
                    <div className={styles.on} onClick={userboardSubmit}>등록</div>
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
