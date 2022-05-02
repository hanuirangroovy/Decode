import Image from "next/image";
import { Grid } from "semantic-ui-react";
export default function explain() {
  return (
    <>
    <br></br>
    <br></br>
    <br></br>
    <br></br>
    <br></br>
    <h1>디코드 사용 안내!</h1>
    <Grid>
      <Grid.Row>
        <Grid.Column width={2}></Grid.Column>
        <Grid.Column width={4}>
          <br></br>
          <h2>Profile Page</h2>
          <h3>자신의 정보를 확인해보세요!</h3>
          <h3>자신의 남긴 글들을 확인하고,</h3>
          <h3>클리어한 테마를 볼 수 있어요!</h3>
          
        </Grid.Column>
        <Grid.Column width={10}>
        <Image src="/images/111.PNG" width="600px" height="400px" className="item" data-aos="fade-up"></Image>
        </Grid.Column>
      </Grid.Row>
      

      <Grid.Row>
        <Grid.Column width={2}></Grid.Column>
        <Grid.Column width={8}>
        <Image src="/images/222.PNG" width="600px" height="400px" className="item" data-aos="fade-up"></Image>
        </Grid.Column>
        <Grid.Column width={6}>
          <br></br>
          <h2>Info Page</h2>
          <h3>약 1700개의 테마데이터가 있어요!</h3>
          <h3>지역 필터링을 통해 원하는 지역의</h3>
          <h3>방탈출 카페와 테마를 찾아보세요!</h3>
        </Grid.Column>
      </Grid.Row>

      <Grid.Row>
      <Grid.Column width={2}></Grid.Column>
        <Grid.Column width={5}>
          <br></br>
          <h2>Recommend Page</h2>
          <h3>방탈출 추천 서비스를 경험해보세요!</h3>
          <h3>취향을 저격하는 장르 추천 테마!</h3>
          <h3>자신의 지역에서 제일 인기있는 테마!</h3>
          <h3>나와 성향이 비슷한 유저들의 인기 테마까지!</h3>
          <h3>모두 확인해보세요!</h3>
        </Grid.Column>
        <Grid.Column width={9}>
        <Image src="/images/333.PNG" width="400px" height="400px" className="item" data-aos="fade-up"></Image>
        </Grid.Column>
      </Grid.Row>

      <Grid.Row>
      <Grid.Column width={2}></Grid.Column>
        <Grid.Column width={7}>
        <Image src="/images/444.PNG" width="600px" height="400px" className="item" data-aos="fade-up"></Image>
        </Grid.Column>
        <Grid.Column width={7}>
          <br></br>
          <h2>Notice Page</h2>
          <h3>공지사항 및 QnA게시판입니다.</h3>
          <h3>관리자의 공지사항을 확인해보세요!</h3>
          <h3>문의사항이 있다면 QnA글을 등록해보세요!</h3>
        </Grid.Column>
      </Grid.Row>

      <Grid.Row>
      <Grid.Column width={2}></Grid.Column>
        <Grid.Column width={5}>
          <br></br>
          <h2>UserBoard Page</h2>
          <h3>유저게시판입니다.</h3>
          <h3>여러가지 글을 자유롭게 작성해보세요!</h3>
          <h3>자신의 지역에서 방탈출에 같이</h3>
          <h3>도전할 사람들을 직접 찾아보세요!</h3>
        </Grid.Column>
        <Grid.Column width={9}>
        <Image src="/images/555.PNG" width="600px" height="400px" className="item" data-aos="fade-up"></Image>
        </Grid.Column>
      </Grid.Row>
    </Grid>

      <style jsx>
        {`
          * {
            box-sizing: border-box;
          }
          h1 {
            text-align: center;
            font-size: 70px;
            color: #5C4E4E;
          }
          h2 {
            font-size:50px;
            color: #AC9090;
          }
          h3 {
            color: #9C6A6A;
          }
          .item {
            width: 200px;
            height: 200px;
            margin: 50px auto;
            padding-top: 75px;
            background: #ccc;
            text-align: center;
            color: #fff;
            font-size: 3em;
          }
        `}
      </style>
    </>
  );
}
