import React, { Component } from "react";
import ProductList from "./../../components/ProductList/ProductList";
import ProductItem from "./../../components/ProductItem/ProductItem";
import { connect } from "react-redux";
import callApi from './../../utils/apiCaller';
import {Link} from 'react-router-dom';

class ProductListPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      products: [],
    };
  }

  componentDidMount() {
    callApi("products",'GET',null).then(res=>{
      console.log(res);
      this.setState({
        products:res.data,
      })
    })
  }

  showProducts = (products) => {
    var result = null;
    if (products.length > 0) {
      result = products.map((product, index) => {
        return <ProductItem key={index} product={product} index={index + 1} />;
      });
    }
    return result;
  };

  render() {
    // var {products}=this.props;
    var { products } = this.state;

    return (
      <div className="container">
        <Link to="/product/add" className="btn btn-info mb-10">
          Add
        </Link>
        <ProductList>
          {this.showProducts(products)}
          </ProductList>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    products: state.products,
  };
};
export default connect(mapStateToProps, null)(ProductListPage);
