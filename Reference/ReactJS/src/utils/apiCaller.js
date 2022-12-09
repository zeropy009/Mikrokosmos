import axios from "axios";
import * as Configs from "../constants/Config";

export default function callApi(endpoint, method = "GET", body) {
  const proxyurl = "https://cors-anywhere.herokuapp.com/";
  return axios({
    method: "GET",
    url: `http://localhost:8888/author/get1`,
    data: body,
  }).catch((err) => {
    console.log(err);
  });
}
