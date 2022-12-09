import axios from 'axios';
import React, { Component } from "react";
import NotificationManager from "react-notifications/lib/NotificationManager";
import Main from './Main';
import { path_variable } from './path_variable';


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: {
        username: "",
        password: "",
        role: "",
        depart_id: "",
      },
    };
  }

  fetchUserDetail = () => {
    axios({
      method: "GET",
      url: path_variable.USER.GET_DETAIL,
      data: null,
    })
      .then((res) => {
        this.setState({
          user: res.data,
        });
      })
      .catch((err) => {
        NotificationManager.error("Error message", "Show list Author failed");
      });
  };

  componentWillMount() {
    this.fetchUserDetail();
  }

  render() {
    return <Main user={this.state.user} />;
  }
}

export default App;
