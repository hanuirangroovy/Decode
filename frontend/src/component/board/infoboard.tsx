import { useEffect, useState } from 'react';
import { Grid, Header, Pagination, Rating, Select, Radio } from 'semantic-ui-react'
import allAxios from '../../lib/allAxios';
import Detail from '../modal/detail';
import styles from '../../../styles/info/infoboard.module.css'

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

const genreOptions = [
    { key: '전체', value: '전체', text: '전체' },
    { key: '스릴러', value: '스릴러', text: '스릴러' },
    { key: '로맨스', value: '로맨스', text: '로맨스' },
    { key: '추리', value: '추리', text: '추리' },
    { key: 'SF/판타지', value: 'SF/판타지', text: 'SF/판타지' },
    { key: '모험/액션', value: '모험/액션', text: '모험/액션' },
    { key: '코미디', value: '코미디', text: '코미디' },
    { key: '범죄', value: '범죄', text: '범죄' },
    { key: '공포', value: '공포', text: '공포' },
    { key: '19금', value: '19금', text: '19금' },
    { key: '감성/드라마', value: '감성/드라마', text: '감성/드라마' },
  ]

export default function Infoboard() {

    const [themeInfo, setThemeInfo] = useState([])
    const [pages, setPages] = useState(0)
    const [totalPages, setTotalPages] = useState(10)
    const [region, setRegion] = useState(null)
    const [smallRegion, setSmallRegion] = useState(null)
    const [smallRegionOptions, setSmallRegionOptions] = useState([{ key: '전체', value: '전체', text: '전체' }])
    const [genres, setGenres] = useState(null)
    const [person, setPerson] = useState(5)
    const [difficulty, setDifficulty] = useState(3)
    const [minute, setMinute] = useState(60)
    const [onePerson, setOnePerson] = useState(0)
    

    useEffect(() => {
        loadInfomation(pages)
    }, [person, difficulty, minute, pages, genres, region, smallRegion, onePerson])

    useEffect(() => {
        loadSmallRegion(null)
    }, [])

    function selectedRegion(e: any){
        setPages(0)
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
        setPages(0)
        setSmallRegion(e.target.textContent)
    }

    function selectedGenre(e: any) {
        setPages(0)
        if (e.target.textContent === '전체'){
            setGenres(null)
            return
        }
        setGenres(e.target.textContent)
    }

    function changePerson(e: any){
        setPages(0)
        setPerson(e.target.value)
    }

    function changeDifficulty(e: any){
        setPages(0)
        setDifficulty(e.target.value)
    }

    function changeMinute(e: any){
        setPages(0)
        if (e.target.value>70){
            setMinute(360)
            return
        }
        setMinute(e.target.value)
    }

    function checkOnePerson(){
        setPages(0)
        if (onePerson){
            setOnePerson(0)
            return
        }
        setOnePerson(1)
    }

    const loadInfomation = async (pages: Number) => {
        await allAxios
            .get("/information", {
                params: {
                    genre: genres,
                    isSingleplay: onePerson,
                    largeRegion: region,
                    maxLevel: difficulty,
                    maxNumber: person,
                    maxTime: minute,
                    page: pages,  
                    smallRegion: smallRegion,
                }
            })
            .then(({ data }) => {
                setTotalPages(data.informationList.totalPages)
                setThemeInfo(data.informationList.content)
            })
            .catch((e) => {
                alert('잠시후 다시 시도해주세요')
            })
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
                alert('잠시후 다시 시도해주세요')
            })
    }

    function movePage(e: any) {
        if(e.target.type == "nextItem"){
            if (pages >= totalPages-1){
                return
            }
            setPages(pages+1)
        } else if (e.target.type === "prevItem"){
            if (pages < 1){
                return
            }
            setPages(Number(pages-1))
        } else if (e.target.type === 'pageItem'){
            setPages(e.target.textContent-1)
        }
    }

    return(
        <>
            <Grid stackable>
                <Grid.Row>
                    <Grid.Column width={5}>
                        <Header as='h5'>지역</Header>
                        <Select placeholder='지역' options={regionOptions} onChange={selectedRegion} />
                    </Grid.Column>
                    <Grid.Column width={5}>
                        <Header as='h5'>세부지역</Header>
                        <Select placeholder='세부지역' options={smallRegionOptions} onChange={selectedSmallRegion} />
                    </Grid.Column>
                    <Grid.Column width={4}>
                        <Header as='h5'>장르</Header>
                        <Select placeholder='장르' options={genreOptions} onChange={selectedGenre}/>
                    </Grid.Column>
                </Grid.Row>
                <Grid.Column width={4}>
                    <Header as='h5'>인원수</Header>
                    <input
                        type='range'
                        min={2}
                        max={8}
                        value={person}
                        onChange={changePerson}
                        />
                    {person}명 이하<br />
                    <Rating rating={person} maxRating={8}/>
                </Grid.Column>
                <Grid.Column width={4}>
                    <Header as='h5'>난이도</Header>
                    <input
                        type='range'
                        min={1}
                        max={5}
                        value={difficulty}
                        onChange={changeDifficulty}
                        />
                    {difficulty} 이하<br />
                    <Rating rating={difficulty} maxRating={5}/>
                </Grid.Column>
                <Grid.Column width={5}>
                    <Header as='h5'>탈출 시간</Header>
                    <input
                        type='range'
                        min={40}
                        max={80}
                        step={10}
                        value={minute}
                        onChange={changeMinute}
                        />
                    {minute>70?"전체시간":minute+"분 이하"}<br />
                    <Rating rating={minute/10} maxRating={8}/>
                </Grid.Column>
                <Grid.Column width={3}>
                    <Header as='h5'>1인 가능 여부</Header>
                    <Radio className={onePerson?styles.color:''} label={onePerson?'1인 가능':''} onClick={checkOnePerson} checked={onePerson?true:false}/>
                </Grid.Column>
            </Grid>
            <Grid celled centered stackable>
                <Grid.Row>
                <Grid.Column width={2}>
                    <Header as='h4' textAlign='center'>번호</Header>
                </Grid.Column>
                <Grid.Column width={2}>
                    <Header as='h4' textAlign='center'>지역</Header>
                </Grid.Column>
                <Grid.Column width={2}>
                    <Header as='h4' textAlign='center'>장르</Header>
                </Grid.Column>
                <Grid.Column width={4}>
                    <Header as='h4' textAlign='center'>테마명</Header>
                </Grid.Column>
                <Grid.Column width={2}>
                    <Header as='h4' textAlign='center'>인원수</Header>
                </Grid.Column>
                <Grid.Column width={2}>
                    <Header as='h4' textAlign='center'>난이도</Header>
                </Grid.Column>
                <Grid.Column width={2}>
                    <Header as='h4' textAlign='center'>시간</Header>
                </Grid.Column>
                </Grid.Row>
                {themeInfo?themeInfo.map((theme: any, index) => {
                    return (
                        <Grid.Row key={theme.themeId}>
                            <Grid.Column width={2}>
                                <Header as='h4' textAlign='center'>{index+pages*10+1}</Header>
                            </Grid.Column>
                            <Grid.Column width={2}>
                                <Header as='h4' textAlign='center'>{theme.largeRegion}/{theme.smallRegion}</Header>
                            </Grid.Column>
                            <Grid.Column width={2}>
                                <Header as='h4' textAlign='center'>{theme.genre}</Header>
                            </Grid.Column>
                            <Grid.Column width={4}>
                                <Detail themeId={theme.themeId} isImage={false} w={150} h={200}/>
                            </Grid.Column>
                            <Grid.Column width={2}>
                                <Header as='h4' textAlign='center'>{theme.maxNumber}명</Header>
                            </Grid.Column>
                            <Grid.Column width={2}>
                                <Header as='h4' textAlign='center'>{theme.level>5
                                ?<Rating icon='star' rating={1} maxRating={1} size={'huge'} disabled/>
                                :<Rating rating={theme.level} maxRating={theme.level} size={'mini'} disabled/>}</Header>
                            </Grid.Column>
                            <Grid.Column width={2}>
                                <Header as='h4' textAlign='center'>{theme.time}분</Header>
                            </Grid.Column>
                        </Grid.Row>
                    );
                }):''} 
            </Grid>
            <Grid centered>
                <Pagination
                        boundaryRange={0}
                        ellipsisItem={null}
                        firstItem={null}
                        lastItem={null}
                        siblingRange={2}
                        totalPages={totalPages}
                        onClick={movePage}
                        activePage={pages+1}
                    />
            </Grid>
        </>
    );
}