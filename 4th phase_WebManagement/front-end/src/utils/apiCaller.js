import axios from "axios";

export default function callApi(path, method = "GET", body) {
  return axios({
    method: method,
    url: path,
    data: body,
  }).catch((err) => {
    console.log(err);
  });
}
