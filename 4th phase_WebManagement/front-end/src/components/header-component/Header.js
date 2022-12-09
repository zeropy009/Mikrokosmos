import React, { Component } from "react";
import "./Header.css";

class Header extends Component {
  render() {
    return (
      <header className="header">
        <div className="logo-wrapper">
          <a href="/mikrokosmos/" className="logo">
            <span className="title1">MIKRO</span>
            <span className="title2">KOSMOS</span>
          </a>
        </div>
        <div className="header-items">
          <ul className="header-actions">
            <li className="dropdown">
              <a
                href="/mikrokosmos/"
                id="notifications"
                data-toggle="dropdown"
                aria-haspopup="true"
              >
                <i className="icon-bell"></i>
                <span className="count-label">8</span>
              </a>
              <div
                className="dropdown-menu dropdown-menu-right lrg"
                aria-labelledby="notifications"
              >
                <div className="dropdown-menu-header">Notifications (40)</div>
                <ul className="header-notifications">
                  <li>
                    <a href="/mikrokosmos/">
                      <div className="user-img away">
                        <img src="../img/3.jpg" alt="User" />
                      </div>
                      <div className="details">
                        <div className="user-title">Abbott</div>
                        <div className="noti-details">
                          Membership has been ended.
                        </div>
                        <div className="noti-date">Oct 20, 07:30 pm</div>
                      </div>
                    </a>
                  </li>
                  <li>
                    <a href="/mikrokosmos/">
                      <div className="user-img busy">
                        <img src="../img/2.jpg" alt="User" />
                      </div>
                      <div className="details">
                        <div className="user-title">Braxten</div>
                        <div className="noti-details">Approved new design.</div>
                        <div className="noti-date">Oct 10, 12:00 am</div>
                      </div>
                    </a>
                  </li>
                  <li>
                    <a href="/mikrokosmos/">
                      <div className="user-img online">
                        <img src="../img/3.jpg" alt="User" />
                      </div>
                      <div className="details">
                        <div className="user-title">Larkyn</div>
                        <div className="noti-details">
                          Check out every table in detail.
                        </div>
                        <div className="noti-date">Oct 15, 04:00 pm</div>
                      </div>
                    </a>
                  </li>
                </ul>
              </div>
            </li>
            <li className="dropdown">
              <a
                href="/mikrokosmos/"
                id="userSettings"
                className="user-settings"
                data-toggle="dropdown"
                aria-haspopup="true"
              >
                <span className="user-name">{this.props.userName}</span>
                <span className="avatar">
                  ZF<span className="status busy"></span>
                </span>
              </a>
              <div
                className="dropdown-menu dropdown-menu-right"
                aria-labelledby="userSettings"
              >
                <div className="header-profile-actions">
                  <div className="header-user-profile">
                    <div className="header-user">
                      <img src={`/download/${this.props.userName}`} alt="Staff" />
                    </div>
                    <h5>{this.props.userName}</h5>
                    <p>{this.props.user.role}</p>
                  </div>
                  <a href="user-profile.html">
                    <i className="icon-user1"></i> My Profile
                  </a>
                  <a href="account-settings.html">
                    <i className="icon-settings1"></i> Account Settings
                  </a>
                  <a>
                    <form action="/logout" method="post">
                      <button type="submit" className="button">
                        <i className="icon-log-out1"></i> Sign Out
                      </button>
                    </form>
                  </a>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </header>
    );
  }
}

export default Header;
