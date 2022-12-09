import React, { Component } from "react";

class NotFoundPage extends Component {
  render() {
    return (
      <div className="container">
        <h1>
          <div className="alert alert-warning">
              <button type="button" className="close" data-dismiss="alert" aria-hidden="true">&times;</button>
              <strong>404 Not Found</strong> 
          </div>
        </h1>
      </div>
    );
  }
}

export default NotFoundPage;
