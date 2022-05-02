import '../styles/globals.css'
import type { AppProps } from 'next/app'
import 'semantic-ui-css/semantic.min.css'
import Navbar from '../src/component/navbar/navbar'

function MyApp({ Component, pageProps }: AppProps) {
  return <>
  <Navbar/>
  <br></br>
  <Component {...pageProps} />
  </>
}

export default MyApp
