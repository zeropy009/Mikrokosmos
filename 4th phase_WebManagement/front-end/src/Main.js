import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Author from "./components/author-component/Author";
import Book from "./components/book-component/Book";
import BookDetail from "./components/book-component/BookDetail";
import Category from "./components/category-component/Category";
import Customer from "./components/customer-component/Customer";
import CustomerDetail from "./components/customer-component/CustomerDetail";
import DashBoard from "./components/dashboard-component/DashBoard";
import Depart from './components/depart-component/Depart';
import Header from "./components/header-component/Header";
import Invoice from "./components/invoice-components/Invoice";
import InvoiceDetail from "./components/invoice-components/InvoiceDetail";
import Level from "./components/level-component/Level";
import Log from "./components/log-component/Log";
import Method from "./components/method-component/Method";
import Receipt from "./components/receipt-component/Receipt";
import ReceiptDetail from "./components/receipt-component/ReceiptDetail";
import Staff from './components/staff-component/Staff';
import StaffDetail from './components/staff-component/StaffDetail';
import Supplier from "./components/supplier-component/Supplier";
import { prefix2 } from "./path_variable";

class Main extends Component {
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

  render() {
    var { user } = this.props;
    return (
      <Router>
        <div id="loading-wrapper">
          <div className="spinner-border" role="status">
            <span className="sr-only">Loading...</span>
          </div>
        </div>
        <Header {...this.props} userName={user.username} />

        <div className="container-fluid">
          <Switch>
            {/* <Author /> */}
            <Route
              path={`${prefix2}/author/show`}
              component={() => (
                <Author userName={user.username} {...this.props} />
              )}
            />
            {/* Category */}
            <Route
              path={`${prefix2}/category/show`}
              component={() => (
                <Category userName={user.username} {...this.props} />
              )}
            />
            {/* Supplier */}
            <Route
              path={`${prefix2}/supplier/show`}
              component={() => (
                <Supplier userName={user.username} {...this.props} />
              )}
            />
            {/* Customer */}
            <Route
              path={`${prefix2}/customer/show/:customerid`}
              component={(match) => (
                <CustomerDetail
                  userName={user.username}
                  {...this.props}
                  match={match}
                />
              )}
            />
            <Route
              path={`${prefix2}/customer/show`}
              component={() => (
                <Customer userName={user.username} {...this.props} />
              )}
            />
            {/* <Invoice /> */}
            <Route
              path={`${prefix2}/invoice/show/:invoiceid`}
              component={(match) => (
                <InvoiceDetail
                  userName={user.username}
                  {...this.props}
                  match={match}
                />
              )}
            />
            <Route
              path={`${prefix2}/invoice/show`}
              component={() => (
                <Invoice userName={user.username} {...this.props} />
              )}
            />
            {/* <Receipt /> */}
            <Route
              path={`${prefix2}/receipt/show/:receiptid`}
              component={(match) => (
                <ReceiptDetail
                  userName={user.username}
                  {...this.props}
                  match={match}
                />
              )}
            />
            <Route
              path={`${prefix2}/receipt/show`}
              component={() => (
                <Receipt userName={user.username} {...this.props} />
              )}
            />
            {/* Depart */}
            <Route
              path={`${prefix2}/depart/show`}
              component={() => (
                <Depart userName={user.username} {...this.props} />
              )}
            />
            {/* <Staff /> */}
            <Route
              path={`${prefix2}/staff/show/:staffid`}
              component={(match) => (
                <StaffDetail
                  userName={user.username}
                  {...this.props}
                  match={match}
                />
              )}
            />
            <Route
              path={`${prefix2}/staff/show`}
              component={() => (
                <Staff userName={user.username} {...this.props} />
              )}
            />
            {/* Log */}
            <Route
              path={`${prefix2}/log/show`}
              component={() => <Log userName={user.username} {...this.props} />}
            />
            {/* <Level /> */}
            <Route
              path={`${prefix2}/level/show`}
              component={() => (
                <Level userName={user.username} {...this.props} />
              )}
            />
            {/* <Method /> */}
            <Route
              path={`${prefix2}/method/show`}
              component={() => (
                <Method userName={user.username} {...this.props} />
              )}
            />
            {/* <Book /> */}
            <Route
              path={`${prefix2}/book/show/:bookid`}
              component={(match) => (
                <BookDetail
                  userName={user.username}
                  {...this.props}
                  match={match}
                />
              )}
            />
            <Route
              path={`${prefix2}/book/show`}
              component={() => (
                <Book userName={user.username} {...this.props} />
              )}
            />
            {/* <DashBoard /> */}
            <Route
              path={`${prefix2}/`}
              component={() => (
                <DashBoard userName={user.username} {...this.props} />
              )}
            />
          </Switch>
        </div>
        <footer className="main-footer">© Mikrokosmos 2018 - 2020</footer>
      </Router>
    );
  }
}

export default Main;
