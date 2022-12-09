/* eslint-disable jsx-a11y/anchor-is-valid */
import React, { Component } from "react";
import { Link } from "react-router-dom";
import { prefix2 } from "./../../path_variable";
import Permission from "./../../Permission";
import { PermissionEnum } from "./../../PermissionEnum";

class TabHeader extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const permissionProduct = [
      PermissionEnum.IT.value,
      PermissionEnum.KHO.value,
      PermissionEnum.KINHDOANH.value,
    ];
    const permissionStorage = [
      PermissionEnum.KHO.value,
      PermissionEnum.IT.value,
    ];
    const permissionSale = [
      PermissionEnum.KINHDOANH.value,
      PermissionEnum.IT.value,
    ];
    const permissionPersonel = [
      PermissionEnum.NHANSU.value,
      PermissionEnum.IT.value,
    ];
    const permissionConvention = [PermissionEnum.IT.value];
    const permissionAdministration = [PermissionEnum.IT.value];
    var { id,user } = this.props;
    return (
      <nav className="navbar navbar-expand-lg custom-navbar">
        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#bluemoonNavbar"
          aria-controls="bluemoonNavbar"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon">
            <i></i>
            <i></i>
            <i></i>
          </span>
        </button>
        <div className="collapse navbar-collapse" id="bluemoonNavbar">
          <ul className="navbar-nav">
            <li className="nav-item dropdown">
              <Link
                className={id === 1 ? "nav-link active-page" : "nav-link"}
                to={`${prefix2}/dashboard`}
              >
                <i className="icon-devices_other nav-icon"></i>
                Dashboards
              </Link>
            </li>
            <Permission user={user} hasPermission={permissionProduct}>
              <li className="nav-item dropdown">
                <a
                  className={id === 2 ? "nav-link active-page" : "nav-link"}
                  id="appsDropdown"
                  role="button"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  <i className="icon-package nav-icon"></i>
                  Product
                </a>
                <ul className="dropdown-menu" aria-labelledby="appsDropdown">
                  <li>
                    <Link
                      className="dropdown-item"
                      to={`${prefix2}/author/show`}
                    >
                      Authors
                    </Link>
                  </li>
                  <li>
                    <Link
                      className="dropdown-item"
                      to={`${prefix2}/category/show`}
                    >
                      Categories
                    </Link>
                  </li>
                  <li>
                    <Link className="dropdown-item" to={`${prefix2}/book/show`}>
                      Books
                    </Link>
                  </li>
                </ul>
              </li>
            </Permission>
            <Permission user={user} hasPermission={permissionStorage}>
              <li className="nav-item dropdown">
                <a
                  className={id === 3 ? "nav-link active-page" : "nav-link"}
                  id="pagesDropdown"
                  role="button"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  <i className="icon-book-open nav-icon"></i>
                  Storage
                </a>
                <ul className="dropdown-menu" aria-labelledby="pagesDropdown">
                  <li>
                    <Link
                      className="dropdown-item"
                      to={`${prefix2}/supplier/show`}
                    >
                      Suppliers
                    </Link>
                  </li>
                  <li>
                    <Link
                      className="dropdown-item"
                      to={`${prefix2}/receipt/show`}
                    >
                      Receipts
                    </Link>
                  </li>
                </ul>
              </li>
            </Permission>
            <Permission user={this.props.user} hasPermission={permissionSale}>
              <li className="nav-item dropdown">
                <a
                  className={id === 4 ? "nav-link active-page" : "nav-link"}
                  id="formsDropdown"
                  role="button"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  <i className="icon-edit1 nav-icon"></i>
                  Sale
                </a>
                <ul className="dropdown-menu" aria-labelledby="formsDropdown">
                  <li>
                    <Link
                      className="dropdown-item"
                      to={`${prefix2}/customer/show`}
                    >
                      Customers
                    </Link>
                  </li>
                  <li>
                    <Link
                      className="dropdown-item"
                      to={`${prefix2}/invoice/show`}
                    >
                      Invoices
                    </Link>
                  </li>
                </ul>
              </li>
            </Permission>
            <Permission user={user} hasPermission={permissionPersonel}>
              <li className="nav-item dropdown">
                <a
                  className={id === 5 ? "nav-link active-page" : "nav-link"}
                  id="formsDropdown"
                  role="button"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  <i className="icon-edit1 nav-icon"></i>
                  Personel
                </a>
                <ul className="dropdown-menu" aria-labelledby="formsDropdown">
                  <li>
                    <Link
                      className="dropdown-item"
                      to={`${prefix2}/depart/show`}
                    >
                      Departs
                    </Link>
                  </li>
                  <li>
                    <Link
                      className="dropdown-item"
                      to={`${prefix2}/staff/show`}
                    >
                      Staffs
                    </Link>
                  </li>
                </ul>
              </li>
            </Permission>
            <Permission user={user} hasPermission={permissionConvention}>
              <li className="nav-item dropdown">
                <a
                  className={id === 6 ? "nav-link active-page" : "nav-link"}
                  id="uiElementsDropdown"
                  role="button"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  <i className="icon-image nav-icon"></i>
                  Convention
                </a>
                <ul
                  className="dropdown-menu"
                  aria-labelledby="uiElementsDropdown"
                >
                  <li>
                    <Link
                      className="dropdown-item"
                      to={`${prefix2}/level/show`}
                    >
                      Levels
                    </Link>
                  </li>
                  <li>
                    <Link
                      className="dropdown-item"
                      to={`${prefix2}/method/show`}
                    >
                      Methods
                    </Link>
                  </li>
                </ul>
              </li>
            </Permission>
            <Permission user={user} hasPermission={permissionAdministration}>
              <li className="nav-item dropdown">
                <a
                  className={id === 7 ? "nav-link active-page" : "nav-link"}
                  id="tablesDropdown"
                  role="button"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  <i className="icon-border_all nav-icon"></i>
                  Administration
                </a>
                <ul className="dropdown-menu" aria-labelledby="tablesDropdown">
                  <li>
                    <Link className="dropdown-item" to={`${prefix2}/log/show`}>
                      Log
                    </Link>
                  </li>
                  <li>
                    <Link
                      className="dropdown-item"
                      to={`${prefix2}/recyclebin/show`}
                    >
                      Recycle Bin
                    </Link>
                  </li>
                </ul>
              </li>
            </Permission>
          </ul>
        </div>
      </nav>
    );
  }
}

export default TabHeader;
