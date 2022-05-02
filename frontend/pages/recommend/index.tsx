import { useEffect, useState } from "react";
import { Grid, Header, Icon } from "semantic-ui-react";
import Detail from "../../src/component/modal/detail";
import IsLogin from "../../src/lib/customLogin";
import getrecoAxios from "../../src/lib/getrecoAxios";
import userAxios from "../../src/lib/userAxios";
import styles from "../../styles/recommend/recommend.module.css";


export default function Recommend(){

    const [userInfo, setUserInfo]: any = useState([])
    const [myRecommend, setMyRecommend]: any = useState([])
    const [ageRecommend, setAgeRecommend]: any = useState([])
    const [genreRecommend, setGenreRecommend]: any = useState([])

    useEffect(() => {
        loadUser()
    }, [])

    useEffect(() => {
        loadMyRecommend()
        loadAgeRecommend()
        loadgenreRecommend()
    }, [userInfo.id])

    const loadUser = async () => {
    if (IsLogin()){
            userAxios.get(`/auth/users`)
            .then(({ data }) => {
                setUserInfo(data.body.user)
            })
            .catch((e) => {
                alert('로그인 시간이 만료되었습니다.')
            })
        }
    } 

    const loadMyRecommend = async () => {
        if(userInfo.id){
            getrecoAxios.get(`/recommend/like/${userInfo.id}`)
            .then(({ data }) => {
                setMyRecommend(data)
            })
            .catch((e) => {
                console.log(e)
            })
        }
    }


    const loadAgeRecommend = async () => {
        if(userInfo.id){
            getrecoAxios.get(`/recommend/genderAge/${userInfo.id}`)
            .then(({ data }) => {
                setAgeRecommend(data)
            })
            .catch((e) => {
                console.log(e)
            })
        }
    }

    const loadgenreRecommend = async () => {
        if(userInfo.id){
            getrecoAxios.get(`/recommend/genre/${userInfo.id}`)
            .then(({ data }) => {
                setGenreRecommend(data)
            })
            .catch((e) => {
                console.log(e)
            })
        }
    }

    return (
        <>
            <Grid stackable>
                {userInfo.id?
                <>
                <Grid.Row>
                    <Grid.Column width={2}/>
                    <Grid.Column width={3}>
                        <Header as='h3'>방탈출 추천 서비스입니다. <Icon color="yellow" name='key'/></Header> 
                        <br />
                    </Grid.Column>
                    <Grid.Column width={4}>
                        <Header as='h3'>마음에 드는 테마를 찾아보세요! <Icon color="red" name='heart'/></Header> 
                        <br />
                    </Grid.Column>
                    <Grid.Column width={2}/>
                </Grid.Row>
                <Grid.Row>
                    <Grid.Column width={2}/>
                    <Grid.Column width={12}>
                        <Header as='h3'><span className={styles.color}>{userInfo.nick_name}</span> 님이 좋아하실만한 <span className={styles.color}>테마</span>를 준비해봤어요!</Header>
                        <Detail themeId={myRecommend.like_one} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <Detail themeId={myRecommend.like_two} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <Detail themeId={myRecommend.like_three} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <Detail themeId={myRecommend.like_four} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </Grid.Column>
                    <Grid.Column width={2}/>
                </Grid.Row>
                <Grid.Row>
                    <Grid.Column width={2}/>
                    <Grid.Column width={12}>
                        <Header as='h3'><span className={styles.color}>{userInfo.nick_name}</span> 님과 같은 <span className={styles.color}>{userInfo.age}대 {userInfo.gender==='남'?"남자":"여자"}</span>들이 좋아하는 방에 도전해보세요!</Header>
                        <Detail themeId={ageRecommend.gender_age_one} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <Detail themeId={ageRecommend.gender_age_two} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <Detail themeId={ageRecommend.gender_age_three} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <Detail themeId={ageRecommend.gender_age_four} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </Grid.Column>
                    <Grid.Column width={2}/>
                </Grid.Row>
                <Grid.Row>
                    <Grid.Column width={2}/>
                    <Grid.Column width={12}>
                        <Header as='h3'><span className={styles.color}>{userInfo.nick_name}</span> 님이 좋아하는 <span className={styles.color}>장르</span>를 모아봤어요!</Header>
                        <Detail themeId={genreRecommend.genre_one} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <Detail themeId={genreRecommend.genre_two} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <Detail themeId={genreRecommend.genre_three} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <Detail themeId={genreRecommend.genre_four} isImage={true} w={150} h={200}/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </Grid.Column>
                    <Grid.Column width={2}/>
                </Grid.Row>
                </>:
                <Grid.Row>
                    <Grid.Column width={2} />
                    <Grid.Column width={12}><Header as='h3'>추천 정보를 보시려면 로그인해주세요! <Icon color="yellow" name='id card'/></Header></Grid.Column>
                    <Grid.Column width={2} />
                </Grid.Row>
                }
            </Grid>
        </>
    );
}