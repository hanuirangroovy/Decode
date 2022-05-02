import { useState } from 'react';
import { Select } from 'semantic-ui-react'

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

export default function Genre() {

    const [genre, setGenre] = useState('전체')

    function selectedGenre(e: any) {
        setGenre(e.target.textContent)
    }
    return(
        <>
            <Select placeholder='장르' options={genreOptions} onChange={selectedGenre}/>
        </>
    );
}