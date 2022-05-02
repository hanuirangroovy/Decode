import { useState } from 'react';
import { Select } from 'semantic-ui-react'

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

const seoul = [
    { key: '강남', value: '강남', text: '강남' },
    { key: '건대', value: '건대', text: '건대' },
    { key: '성수', value: '성수', text: '성수' },
    { key: '잠실', value: '잠실', text: '잠실' },
    { key: '신촌', value: '신촌', text: '신촌' },
    { key: '홍대', value: '홍대', text: '홍대' },
    { key: '연신내', value: '연신내', text: '연신내' },
    { key: '대학로', value: '대학로', text: '대학로' },
    { key: '성신여대앞', value: '성신여대앞', text: '성신여대앞' },
    { key: '명동', value: '명동', text: '명동' },
    { key: '노원', value: '노원', text: '노원' },
    { key: '수유', value: '수유', text: '수유' },
    { key: '종로', value: '종로', text: '종로' },
    { key: '용산', value: '용산', text: '용산' },
    { key: '신림', value: '신림', text: '신림' },
    { key: '서울대 입구', value: '서울대 입구', text: '서울대 입구' },
    { key: '구로', value: '구로', text: '구로' },
    { key: '영등포', value: '영등포', text: '영등포' },
    { key: '천호', value: '천호', text: '천호' },
    { key: '이수', value: '이수', text: '이수' },
    { key: '왕십리', value: '왕십리', text: '왕십리' },
    { key: '회기', value: '회기', text: '회기' },
    { key: '노량진', value: '노량진', text: '노량진' },
    { key: '목동', value: '목동', text: '목동' },
]

const incheon = [
    { key: '안양', value: '안양', text: '안양' },
    { key: '안산', value: '안산', text: '안산' },
    { key: '군포', value: '군포', text: '군포' },
    { key: '수원', value: '수원', text: '수원' },
    { key: '화성', value: '화성', text: '화성' },
    { key: '평택', value: '평택', text: '평택' },
    { key: '용인', value: '용인', text: '용인' },
    { key: '성남', value: '성남', text: '성남' },
    { key: '이천', value: '이천', text: '이천' },
    { key: '구리', value: '구리', text: '구리' },
    { key: '동두천', value: '동두천', text: '동두천' },
    { key: '의정부', value: '의정부', text: '의정부' },
    { key: '일산', value: '일산', text: '일산' },
    { key: '김포', value: '김포', text: '김포' },
    { key: '부천', value: '부천', text: '부천' },
    { key: '인천', value: '인천', text: '인천' },
]

const chungcheong = [
    { key: '당진', value: '당진', text: '당진' },
    { key: '천안', value: '천안', text: '천안' },
    { key: '대전', value: '대전', text: '대전' },
    { key: '청주', value: '청주', text: '청주' },
]

const gangwon = [
    { key: '춘천', value: '춘천', text: '춘천' },
    { key: '원주', value: '원주', text: '원주' },
    { key: '강릉', value: '강릉', text: '강릉' },
]

const gyeongsang = [
    { key: '대구', value: '대구', text: '대구' },
    { key: '구미', value: '구미', text: '구미' },
    { key: '안동', value: '안동', text: '안동' },
    { key: '영주', value: '영주', text: '영주' },
    { key: '포항', value: '포항', text: '포항' },
    { key: '경주', value: '경주', text: '경주' },
    { key: '창원', value: '창원', text: '창원' },
    { key: '부산', value: '부산', text: '부산' },
    { key: '울산', value: '울산', text: '울산' },
    { key: '야산', value: '야산', text: '야산' },
    { key: '진주', value: '진주', text: '진주' },
]

const jeolla = [
    { key: '익산', value: '익산', text: '익산' },
    { key: '군산', value: '군산', text: '군산' },
    { key: '전주', value: '전주', text: '전주' },
    { key: '광주', value: '광주', text: '광주' },
    { key: '여수', value: '여수', text: '여수' },
    { key: '순천', value: '순천', text: '순천' },
    { key: '목포', value: '목포', text: '목포' },
]

const jeju = [
    { key: '제주', value: '제주', text: '제주' },
]

const smallRegionOptions = [
    { key: '강남', value: '강남', text: '강남' },
    { key: '건대', value: '건대', text: '건대' },
    { key: '성수', value: '성수', text: '성수' },
    { key: '잠실', value: '잠실', text: '잠실' },
    { key: '신촌', value: '신촌', text: '신촌' },
    { key: '홍대', value: '홍대', text: '홍대' },
    { key: '연신내', value: '연신내', text: '연신내' },
    { key: '대학로', value: '대학로', text: '대학로' },
    { key: '성신여대앞', value: '성신여대앞', text: '성신여대앞' },
    { key: '명동', value: '명동', text: '명동' },
    { key: '노원', value: '노원', text: '노원' },
    { key: '수유', value: '수유', text: '수유' },
    { key: '종로', value: '종로', text: '종로' },
    { key: '용산', value: '용산', text: '용산' },
    { key: '신림', value: '신림', text: '신림' },
    { key: '서울대 입구', value: '서울대 입구', text: '서울대 입구' },
    { key: '구로', value: '구로', text: '구로' },
    { key: '영등포', value: '영등포', text: '영등포' },
    { key: '천호', value: '천호', text: '천호' },
    { key: '이수', value: '이수', text: '이수' },
    { key: '왕십리', value: '왕십리', text: '왕십리' },
    { key: '회기', value: '회기', text: '회기' },
    { key: '노량진', value: '노량진', text: '노량진' },
    { key: '목동', value: '목동', text: '목동' },
    { key: '안양', value: '안양', text: '안양' },
    { key: '안산', value: '안산', text: '안산' },
    { key: '군포', value: '군포', text: '군포' },
    { key: '수원', value: '수원', text: '수원' },
    { key: '화성', value: '화성', text: '화성' },
    { key: '평택', value: '평택', text: '평택' },
    { key: '용인', value: '용인', text: '용인' },
    { key: '성남', value: '성남', text: '성남' },
    { key: '이천', value: '이천', text: '이천' },
    { key: '구리', value: '구리', text: '구리' },
    { key: '동두천', value: '동두천', text: '동두천' },
    { key: '의정부', value: '의정부', text: '의정부' },
    { key: '일산', value: '일산', text: '일산' },
    { key: '김포', value: '김포', text: '김포' },
    { key: '부천', value: '부천', text: '부천' },
    { key: '인천', value: '인천', text: '인천' },
    { key: '당진', value: '당진', text: '당진' },
    { key: '천안', value: '천안', text: '천안' },
    { key: '대전', value: '대전', text: '대전' },
    { key: '청주', value: '청주', text: '청주' },
    { key: '춘천', value: '춘천', text: '춘천' },
    { key: '원주', value: '원주', text: '원주' },
    { key: '강릉', value: '강릉', text: '강릉' },
    { key: '대구', value: '대구', text: '대구' },
    { key: '구미', value: '구미', text: '구미' },
    { key: '안동', value: '안동', text: '안동' },
    { key: '영주', value: '영주', text: '영주' },
    { key: '포항', value: '포항', text: '포항' },
    { key: '경주', value: '경주', text: '경주' },
    { key: '창원', value: '창원', text: '창원' },
    { key: '부산', value: '부산', text: '부산' },
    { key: '울산', value: '울산', text: '울산' },
    { key: '야산', value: '야산', text: '야산' },
    { key: '진주', value: '진주', text: '진주' },
    { key: '익산', value: '익산', text: '익산' },
    { key: '군산', value: '군산', text: '군산' },
    { key: '전주', value: '전주', text: '전주' },
    { key: '광주', value: '광주', text: '광주' },
    { key: '여수', value: '여수', text: '여수' },
    { key: '순천', value: '순천', text: '순천' },
    { key: '목포', value: '목포', text: '목포' },
    { key: '제주', value: '제주', text: '제주' },
]

// const smallRegionOptions = [
//     { key: '전체', value: '전체', text: '전체' },
//     { key: '강남', value: '서울', text: '강남' },
//     { key: '건대', value: '서울', text: '건대' },
//     { key: '성수', value: '서울', text: '성수' },
//     { key: '잠실', value: '서울', text: '잠실' },
//     { key: '신촌', value: '서울', text: '신촌' },
//     { key: '홍대', value: '서울', text: '홍대' },
//     { key: '연신내', value: '서울', text: '연신내' },
//     { key: '대학로', value: '서울', text: '대학로' },
//     { key: '성신여대앞', value: '서울', text: '성신여대앞' },
//     { key: '명동', value: '서울', text: '명동' },
//     { key: '노원', value: '서울', text: '노원' },
//     { key: '수유', value: '서울', text: '수유' },
//     { key: '종로', value: '서울', text: '종로' },
//     { key: '용산', value: '서울', text: '용산' },
//     { key: '신림', value: '서울', text: '신림' },
//     { key: '서울대 입구', value: '서울', text: '서울대 입구' },
//     { key: '구로', value: '서울', text: '구로' },
//     { key: '영등포', value: '서울', text: '영등포' },
//     { key: '천호', value: '서울', text: '천호' },
//     { key: '이수', value: '서울', text: '이수' },
//     { key: '왕십리', value: '서울', text: '왕십리' },
//     { key: '회기', value: '서울', text: '회기' },
//     { key: '노량진', value: '서울', text: '노량진' },
//     { key: '목동', value: '서울', text: '목동' },
//     { key: '안양', value: '경기/인천', text: '안양' },
//     { key: '안산', value: '경기/인천', text: '안산' },
//     { key: '군포', value: '경기/인천', text: '군포' },
//     { key: '수원', value: '경기/인천', text: '수원' },
//     { key: '화성', value: '경기/인천', text: '화성' },
//     { key: '평택', value: '경기/인천', text: '평택' },
//     { key: '용인', value: '경기/인천', text: '용인' },
//     { key: '성남', value: '경기/인천', text: '성남' },
//     { key: '이천', value: '경기/인천', text: '이천' },
//     { key: '구리', value: '경기/인천', text: '구리' },
//     { key: '동두천', value: '경기/인천', text: '동두천' },
//     { key: '의정부', value: '경기/인천', text: '의정부' },
//     { key: '일산', value: '경기/인천', text: '일산' },
//     { key: '김포', value: '경기/인천', text: '김포' },
//     { key: '부천', value: '경기/인천', text: '부천' },
//     { key: '인천', value: '경기/인천', text: '인천' },
//     { key: '당진', value: '충청', text: '당진' },
//     { key: '천안', value: '충청', text: '천안' },
//     { key: '대전', value: '충청', text: '대전' },
//     { key: '청주', value: '충청', text: '청주' },
//     { key: '춘천', value: '강원', text: '춘천' },
//     { key: '원주', value: '강원', text: '원주' },
//     { key: '강릉', value: '강원', text: '강릉' },
//     { key: '대구', value: '경상', text: '대구' },
//     { key: '구미', value: '경상', text: '구미' },
//     { key: '안동', value: '경상', text: '안동' },
//     { key: '영주', value: '경상', text: '영주' },
//     { key: '포항', value: '경상', text: '포항' },
//     { key: '경주', value: '경상', text: '경주' },
//     { key: '창원', value: '경상', text: '창원' },
//     { key: '부산', value: '경상', text: '부산' },
//     { key: '울산', value: '경상', text: '울산' },
//     { key: '야산', value: '경상', text: '야산' },
//     { key: '진주', value: '경상', text: '진주' },
//     { key: '익산', value: '전라', text: '익산' },
//     { key: '군산', value: '전라', text: '군산' },
//     { key: '전주', value: '전라', text: '전주' },
//     { key: '광주', value: '전라', text: '광주' },
//     { key: '여수', value: '전라', text: '여수' },
//     { key: '순천', value: '전라', text: '순천' },
//     { key: '목포', value: '전라', text: '목포' },
//     { key: '제주', value: '제주', text: '제주' },
// ]

export default function Region() {

    const [region, setRegion] = useState('전체')
    const [smallRegion, setSmallRegion] = useState('전체')
    const [filteredRegion, setFilteredRegion] = useState(smallRegionOptions)

    function selectedRegion(e: any){
        setRegion(e.target.textContent)
        setSmallRegion('전체')
        regionFiltering(e.target.textContent)
        
    }

    function selectedSmallRegion(e: any){
        setSmallRegion(e.target.textContent)
    }
    
    function regionFiltering(region: String){
        if (region === '전체'){
            setFilteredRegion(smallRegionOptions)
        } else if (region === '서울') {
            setFilteredRegion(seoul)
        }else if (region === '경기/인천') {
            setFilteredRegion(incheon)
        }else if (region === '충청') {
            setFilteredRegion(chungcheong)
        }else if (region === '강원') {
            setFilteredRegion(gangwon)
        }else if (region === '경상') {
            setFilteredRegion(gyeongsang)
        }else if (region === '전라') {
            setFilteredRegion(jeolla)
        }else if (region === '제주') {
            setFilteredRegion(jeju)
        }
    }
    
    return(
        <>
            <Select placeholder='지역' options={regionOptions} onChange={selectedRegion} />
            <Select placeholder='세부지역' options={filteredRegion} onChange={selectedSmallRegion} />
        </>
    );
}