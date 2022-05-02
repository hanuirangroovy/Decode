import { Grid } from 'semantic-ui-react'
import Infoboard from '../../src/component/board/infoboard';

export default function Info() {
    return (
        <>
            <Grid stackable centered>
                <Grid.Row>
                    <Grid.Column width={2} />
                    <Grid.Column width={12}> 
                    </Grid.Column>
                    <Grid.Column width={2} />
                </Grid.Row>
                <Grid.Row>
                    <Grid.Column width={2} />
                    <Grid.Column width={12}> 
                        <Infoboard />  
                    </Grid.Column>
                    <Grid.Column width={2} />
                </Grid.Row>
            </Grid>
        </>
    );
}