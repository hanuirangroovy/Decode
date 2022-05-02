import Axios from "axios";

const LOCAL_API_URL = "http://localhost:8080";
const SERVER_API_URL = "https://j6c203.p.ssafy.io/api/escape-server";

const allAxios = Axios.create({
  baseURL: `${SERVER_API_URL}`,
});

export default allAxios;
