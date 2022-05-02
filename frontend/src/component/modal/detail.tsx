import { useEffect, useState } from 'react';
import { Button, Header, Modal, Rating } from 'semantic-ui-react'
import allAxios from '../../lib/allAxios';
import Reviewboard from '../board/reviewboard';
import Kakaomap from '../kakaomap/kmap';

export default function Detail({themeId, isImage, w, h}: any){

    const [open, setOpen] = useState(false)
    const [themeDetail, setThemeDetail]: any = useState([])

    useEffect(() => {
        loadDetailInfomation()
    }, [themeId])

    const loadDetailInfomation = async () => {
        if (themeId){
            await allAxios
                .get(`/information/detail/${themeId}`, {
                    params: {
                        themeId: themeId
                    }
                })
                .then(({ data }) => {
                    setThemeDetail(data.storeandtheme)
                })
                .catch((e) => {
                    alert('잠시후 다시 시도해주세요')
                })
        }
    }

    return (
        <>
            <Modal
                onClose={() => setOpen(false)}
                onOpen={() => setOpen(true)}
                open={open}
                trigger={isImage?
                    <button style={{ backgroundColor: "white", border: "white", cursor: "pointer"}}><img src={themeDetail.posterUrl} alt="url" width={w} height={h} /></button>
                :
                    <Button style={{  margin: 'auto', display: 'block' }}>{themeDetail.themeName}</Button>
                }
                >
                <Modal.Content image style={{ backgroundImage: `linear-gradient(rgba(255, 255, 255, 0.7), rgba(255, 255, 255, 0.7)), url(${themeDetail.posterUrl})`, backgroundSize:"cover"}}>
                    <img src={themeDetail.posterUrl} alt="이미지가 없습니다" width="250px" height="350px"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <Modal.Description>
                        <Header as='h2'>{themeDetail.themeName?themeDetail.themeName.slice(0, 12):''}<br/>{themeDetail.themeName?themeDetail.themeName.slice(12, 24):''}</Header>
                        <Header as='h3'>난이도 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <Rating icon='heart' defaultRating={themeDetail.level} maxRating={themeDetail.level} disabled/></Header>
                        <Header as='h3'>장르 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {themeDetail.genre}</Header>
                        <Header as='h3'>추천 인원 &nbsp;&nbsp;&nbsp;&nbsp; {themeDetail.numberIsTwo?'2 ':''}&nbsp;{themeDetail.numberIsThree?'3 ':''}&nbsp;{themeDetail.numberIsFour?'4 ':''}&nbsp;{themeDetail.numberIsFive?'5':''}명</Header>
                        <Header as='h3'>최대 인원 &nbsp;&nbsp;&nbsp;&nbsp; {themeDetail.maxNumber}명</Header>
                        <Header as='h3'>시간 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {themeDetail.time}분</Header>
                    </Modal.Description>
                    <Modal.Description />
                    <Modal.Description>
                        <br />
                        <h3>{themeDetail.storeName}</h3>
                        <Kakaomap storename={themeDetail.storeName} latitude={33.450701} longitude={126.570667}/>
                        <br />
                        <Button inverted color='teal'>
                            <a href={themeDetail.reserveUrl}>예약하기</a>
                        </Button>
                    </Modal.Description>
                </Modal.Content>
                <Modal.Actions>
                    <br />
                    <Reviewboard themeIds={themeId}/>
                    <br />
                    <Button 
                        content='close'
                        color='red'
                        onClick={() => setOpen(false)}>
                    </Button>
                </Modal.Actions>
            </Modal>
        </>
    );
}