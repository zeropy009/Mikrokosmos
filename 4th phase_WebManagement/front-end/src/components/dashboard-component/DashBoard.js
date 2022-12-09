import React, { Component } from "react";
import TabHeader from "./../tab-component/TabHeader";

class DashBoard extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  componentDidMount() {
    this.setState({
      user: this.props.user,
    });
  }

  render() {
    var { user } = this.state;
    return (
      <div>
        <TabHeader id={1} user={user} />
        <div className="main-container">
          <div className="page-header">
            <ol className="breadcrumb">
              <li className="breadcrumb-item">Home</li>
            </ol>
          </div>

          <div className="content-wrapper">
            <div className="row gutters">
              <div className="col-xl-3 col-lg-3 col-md-6 col-sm-6 col-12">
                <div className="plain-widget blue">
                  <div className="growth">
                    15<i className="icon-arrow-up-right"></i>
                  </div>
                  <h2>9000</h2>
                  <p>Sales</p>
                  <div className="progress">
                    <div
                      className="progress-bar"
                      role="progressbar"
                      style={{ width: "37%" }}
                      aria-valuenow="45"
                      aria-valuemin="0"
                      aria-valuemax="100"
                    ></div>
                  </div>
                </div>
              </div>
              <div className="col-xl-3 col-lg-3 col-md-6 col-sm-6 col-12">
                <div className="plain-widget blue">
                  <div className="growth">
                    19<i className="icon-arrow-up-right"></i>
                  </div>
                  <h2>5000</h2>
                  <p>Expenses</p>
                  <div className="progress">
                    <div
                      className="progress-bar"
                      role="progressbar"
                      style={{ width: "48%" }}
                      aria-valuenow="57"
                      aria-valuemin="0"
                      aria-valuemax="100"
                    ></div>
                  </div>
                </div>
              </div>
              <div className="col-xl-3 col-lg-3 col-md-6 col-sm-6 col-12">
                <div className="plain-widget blue">
                  <div className="growth">
                    24<i className="icon-arrow-up-right"></i>
                  </div>
                  <h2>7000</h2>
                  <p>Visitors</p>
                  <div className="progress">
                    <div
                      className="progress-bar"
                      role="progressbar"
                      style={{ width: "48%" }}
                      aria-valuenow="57"
                      aria-valuemin="0"
                      aria-valuemax="100"
                    ></div>
                  </div>
                </div>
              </div>
              <div className="col-xl-3 col-lg-3 col-md-6 col-sm-6 col-12">
                <div className="plain-widget red">
                  <div className="growth">
                    17<i className="icon-arrow-down-right"></i>
                  </div>
                  <h2>4000</h2>
                  <p>Orders</p>
                  <div className="progress">
                    <div
                      className="progress-bar"
                      role="progressbar"
                      style={{ width: "48%" }}
                      aria-valuenow="68"
                      aria-valuemin="0"
                      aria-valuemax="100"
                    ></div>
                  </div>
                </div>
              </div>
            </div>
            <div className="row gutters">
              <div className="col-xl-3 col-lg-6 col-md-6 col-sm-6 col-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Emails</div>
                  </div>
                  <div className="card-body">
                    <div id="donut-emails"></div>
                    <div className="row gutters">
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col">
                        <div className="info-stats">
                          <span className="info-label"></span>
                          <p className="info-title">Sent</p>
                          <h3 className="info-total">4750</h3>
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col">
                        <div className="info-stats">
                          <span className="info-label red"></span>
                          <p className="info-title">Opened</p>
                          <h3 className="info-total">3590</h3>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-xl-3 col-lg-6 col-md-6 col-sm-6 col-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Orders</div>
                  </div>
                  <div className="card-body">
                    <div id="area-orders"></div>
                    <div className="row gutters">
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col">
                        <div className="info-stats">
                          <span className="info-label"></span>
                          <p className="info-title">Online</p>
                          <h3 className="info-total">3550</h3>
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col">
                        <div className="info-stats">
                          <span className="info-label red"></span>
                          <p className="info-title">Direct</p>
                          <h3 className="info-total">2980</h3>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-xl-3 col-lg-6 col-md-6 col-sm-6 col-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Deals</div>
                  </div>
                  <div className="card-body">
                    <div id="column-deals"></div>
                    <div className="row gutters">
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col">
                        <div className="info-stats">
                          <span className="info-label"></span>
                          <p className="info-title">Claimed</p>
                          <h3 className="info-total">2800</h3>
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col">
                        <div className="info-stats">
                          <span className="info-label red"></span>
                          <p className="info-title">Expired</p>
                          <h3 className="info-total">2200</h3>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-xl-3 col-lg-6 col-md-6 col-sm-6 col-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Visitors</div>
                  </div>
                  <div className="card-body">
                    <div id="scatter-visitors"></div>
                    <div className="row gutters">
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col">
                        <div className="info-stats">
                          <span className="info-label"></span>
                          <p className="info-title">Male</p>
                          <h3 className="info-total">2650</h3>
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col">
                        <div className="info-stats">
                          <span className="info-label red"></span>
                          <p className="info-title">Female</p>
                          <h3 className="info-total">2400</h3>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="row gutters">
              <div className="col-xl-6 col-lg-6 col-md-12 col-sm-12 col-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Visitors</div>
                  </div>
                  <div className="card-body">
                    <div className="row gutters">
                      <div className="col-xl-12">
                        <div
                          id="world-map-markers"
                          className="chart-height-lg"
                        ></div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-xl-6 col-lg-6 col-md-12 col-sm-12 col-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title"></div>
                  </div>
                  <div className="card-body">
                    <div id="bar-sales"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default DashBoard;
