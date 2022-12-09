import React, { Component } from "react";

export default class PageHeader extends Component {
  render() {
    return (
      <div className="page-header">
        <ol className="breadcrumb">
          <li className="breadcrumb-item">{this.props.menu}</li>
          <li className="breadcrumb-item active">{this.props.subMenu}</li>
        </ol>
      </div>
    );
  }
}
