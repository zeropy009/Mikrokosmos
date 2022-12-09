import React, { Component } from "react";

class ProductItem extends Component {
  render() {
    var { product, index } = this.props;
    var statusName = product.status === true ? "Active" : "Deactive";
    var statusClass = product.status === true ? "danger" : "success";
    return (
      <tr>
        <td>{index}</td>
        <td>{product.id}</td>
        <td>{product.name}</td>
        <td>{product.price}</td>
        <td>
          <span className={`label label-${statusClass}`}>{statusName}</span>
        </td>
        <td>
          <button type="button" className="btn btn-success mr-10">
            Update
          </button>
          <button type="button" className="btn btn-danger">
            Delete
          </button>
        </td>
      </tr>
    );
  }
}

export default ProductItem;
