import { useState } from 'react';
import { Grid, GridColumn, Header, Rating } from 'semantic-ui-react'

export default function Stick(){

    const [startPoint, setStartPoint] = useState(1)
    const [endPoint, setEndPoint] = useState(1)
    const [person, setPerson] = useState(1)
    const [difficulty, setDifficulty] = useState(1)
    const [minute, setMinute] = useState(30)

    function changePerson(e: any){
        setPerson(e.target.value)
    }

    function changeDifficulty(e: any){
        setDifficulty(e.target.value)
    }

    function changeMinute(e: any){
        setMinute(e.target.value)
    }

    function changeStartPoint(e: any){
        setStartPoint(e.target.value)
    }

    function changeEndPoint(e: any){
        setEndPoint(e.target.value)
    }

    return(
        <>
            <Grid stackable>
                <Grid.Column width={3}>
                    <Header as='h5'>인원수</Header>
                    <input
                        type='range'
                        min={1}
                        max={5}
                        value={person}
                        onChange={changePerson}
                        />
                    {person}명 이하
                    <Rating rating={person} maxRating={5}/>
                </Grid.Column>
                <Grid.Column width={3}>
                    <Header as='h5'>난이도</Header>
                    <input
                        type='range'
                        min={1}
                        max={5}
                        value={difficulty}
                        onChange={changeDifficulty}
                        />
                    {difficulty} 이하
                    <Rating rating={difficulty} maxRating={5}/>
                </Grid.Column>
                <Grid.Column width={3}>
                    <Header as='h5'>탈출 시간</Header>
                    <input
                        type='range'
                        min={30}
                        max={90}
                        value={minute}
                        onChange={changeMinute}
                        />
                    {minute}분 이하
                    <Rating rating={minute/10} maxRating={9}/>
                </Grid.Column>
            </Grid>
        </>
    );
}