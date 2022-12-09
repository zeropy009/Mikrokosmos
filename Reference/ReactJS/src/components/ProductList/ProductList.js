import React, { Component } from "react";

class ProductList extends Component {
  render() {
    return (
      <div className="panel panel-primary">
        <div className="panel-heading">
          <h3 className="panel-title">Product List</h3>
        </div>
        <div className="panel-body">
          <table className="table table-hover table-bordered">
            <thead>
              <tr>
                <th>STT</th>
                <th>ProductID</th>
                <th>Product Name</th>
                <th>Price</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>{this.props.children}</tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default ProductList;
