import React, { Component } from "react";

export default class Permission extends Component {
  // eslint-disable-next-line no-useless-constructor
  constructor(props) {
    super(props);
  }

  checkPermission = () => {
    var { user } = this.props;
    if (!this.props.hasPermission && !this.props.allPermission) return false;
    const hasPermission =
      this.props.hasPermission === undefined
        ? []
        : Array.isArray(this.props.hasPermission)
        ? this.props.hasPermission
        : [this.props.hasPermission];
    const listModule = user ? [user.role, user.depart_id] : [];
    const array = listModule.filter((e) => hasPermission.includes(e));
    if (array.length > 0) {
      return true;
    } else {
      return false;
    }
  };

  render() {
    //LocalStore.checkSessionTimeOut()
    return this.checkPermission() ? this.props.children : null;
  }
}
