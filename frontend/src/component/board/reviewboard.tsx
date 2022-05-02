import { useEffect, useState } from "react";
import { Button, Grid, Header, Icon, Pagination, Rating } from "semantic-ui-react";
import allAxios from "../../lib/allAxios";
import IsLogin from "../../lib/customLogin";
import userAxios from "../../lib/userAxios";

export default function Reviewboard({ themeIds }: any){

    const [myRate, setMyRate] = useState(3)
    const [myTime, setMytime] = useState(0)
    const [myReview, setMyReview] = useState('')
    const [reviewInfo, setReviewInfo] = useState([])
    const [pages, setPages] = useState(0)
    const [totalPages, setTotalPages] = useState(10)
    const [userInfo, setUserInfo]: any = useState([])

    useEffect(() => {
        loadReview(pages)
    }, [pages])

    useEffect(() => {
        loadUser()
    }, [])

    const loadReview = async (pages: Number) => {
        await allAxios.get(`/review/${themeIds}`, {
            params: {
                themeId: themeIds,
                page: pages
            }
        })
        .then(({ data }) => {
            setTotalPages(data.review.totalPages)
            setReviewInfo(data.review.content)
        })
        .catch((e) => {
            console.log(e)
        })
    }
        
    const loadUser = async () => {{}
        if (IsLogin()){
            userAxios.get(`/auth/users`)
            .then(({ data }) => {
                setUserInfo(data.body.user)
            })
            .catch((e) => {
                console.log(e)
                alert('로그인 시간이 만료되었습니다.')
            })
        }
    } 

    const reviewWrite = async() => {
        if (!myTime && (myTime < 10 || myTime > 500)){
            alert('클리어 시간은 10 ~ 500 사이 숫자로만 작성이 가능합니다.')
            return
        }

        if (myReview.length > 200 || myReview.length < 1){
            alert('리뷰는 1자 이상 200자 이하로 작성해주세요')
            return
        }

        if (IsLogin()){
            const body = {
                clearTime: Number(myTime), 
                myScore: Number(myRate),
                reviewContent: myReview,
                themeId: themeIds,
                userId: userInfo.id,
            }

            await allAxios.post(`/review`, body)
            .then(( ) => {
                setMytime(0)
                setMyReview('')
                var timeinput: any = document.getElementsByClassName('timeinput')[0]
                timeinput['value'] = 0
                var reviewinput: any = document.getElementsByClassName('reviewinput')[0]
                reviewinput['value'] = ''
                loadReview(0)
                alert('리뷰가 저장되었습니다.')
            })
            .catch((e) => {
                console.log(e)
            })
        }
    }

    const reviewDelete = async (reviewId: Number) => {
        await allAxios.delete(`/review/${reviewId}`, {
            params: {
                themeReviewId: reviewId
            }
        })
        .then(() => {
            loadReview(0)
            alert('리뷰가 삭제되었습니다.')
        })
        .catch((e: any) => {
            console.log(e)
        })
    }

    function movePage(e: any){
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

    function changeTime(e: any){
        setMytime(e.target.value)
    }

    function changeRate(e: any){
        setMyRate(e.target.ariaPosInSet)
    }

    function changeReview(e: any){
        setMyReview(e.target.value)
    }

    return(
        <>
            <Grid centered stackable>
                {totalPages?
                <Grid.Row>
                    <Grid.Column width={2}>
                        <Header as='h5' textAlign="center">번호</Header>
                    </Grid.Column>
                    <Grid.Column width={2}>
                        <Header as='h5'>닉네임</Header>
                    </Grid.Column>
                    <Grid.Column width={2}>
                        <Header as='h5'>별점</Header>
                    </Grid.Column>
                    <Grid.Column width={2}>
                        <Header as='h5'>작성날짜</Header>
                    </Grid.Column>
                    <Grid.Column width={2}>
                        <Header as='h5'>클리어시간</Header>
                    </Grid.Column>
                    <Grid.Column width={6}>
                        <Header as='h5'>후기</Header>
                    </Grid.Column>
                </Grid.Row>
                :<Header as='h4'>첫번째 리뷰를 남겨주세요!</Header>}
                {reviewInfo?reviewInfo.map((review: any, index) => {
                    return(
                        <Grid.Row key={review.themeReviewId}>
                            <Grid.Column width={2}>
                                <Header as='h5' textAlign="center">{index+pages*5+1}</Header>
                            </Grid.Column>
                            <Grid.Column width={2}>
                                <Header as='h5'>{review.userNickName}</Header>
                            </Grid.Column>
                            <Grid.Column width={2}>
                                <Header as='h5'><Rating icon='star' defaultRating={review.myScore} maxRating={review.myScore} disabled/></Header>
                            </Grid.Column>
                            <Grid.Column width={2}>
                                <Header as='h5'>{review.createdAt[0]}.{review.createdAt[1]}.{review.createdAt[2]}.</Header>
                            </Grid.Column>
                            <Grid.Column width={2}>
                                <Header as='h5'>{review.clearTime}분</Header>
                            </Grid.Column>
                            <Grid.Column width={5}>
                                <Header as='h5'>{review.reviewContent}</Header>
                            </Grid.Column>
                            <Grid.Column width={1}>
                                {userInfo.id === review.userId?
                                <button onClick={function(){reviewDelete(review.themeReviewId)}}><Icon key={review.themeReviewId} name="trash" color="red"/></button>
                                :''}
                            </Grid.Column>
                        </Grid.Row>
                    );
                }):''}
                <Grid.Row />
                {userInfo.nick_name?
                <Grid.Row>
                    <Grid.Column width={2}>
                        <Header as='h5' textAlign="center">닉네임</Header>
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{userInfo.nick_name}</p>
                    </Grid.Column>
                    <Grid.Column width={2}>
                        <Header as='h5'>별점</Header>
                        <Rating className='scoreinput' icon='star' defaultRating={myRate} maxRating={5} onClick={changeRate}/>
                    </Grid.Column>
                    <Grid.Column width={2}>
                        <Header as='h5'>클리어 시간</Header>
                        <input className='timeinput' type="number" min={30} max={90} onChange={changeTime} />분
                    </Grid.Column>
                    <Grid.Column width={7}>
                        <Header as='h5'>후기</Header>
                        <textarea className='reviewinput' cols={50} rows={3} maxLength={200} onChange={changeReview}></textarea>
                    </Grid.Column>
                    <Grid.Column width={3}>
                        <br /><br />
                        <Button 
                            content='리뷰작성'
                            color='blue'
                            onClick={reviewWrite}
                        />
                    </Grid.Column>
                </Grid.Row>
                :''}
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
            </Grid>
        </>
    );
}