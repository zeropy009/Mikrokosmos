import React, { Component } from "react";

class ProductActionPage extends Component {
  constructor(props){
    super(props);
    this.state={
      id:'',
      txtName:'',
      txtPrice:'',
      chkStatus:'',
    }
  }

  onChange =(e)=>{
    var target=e.target;
    var name = target.name;
    var value = target.value;
    this.setState({
      [name]:value,
    })
  }

  onSave = e=>{
    e.preventDefault();
    console.log(this.state);
  }


  render() {
    var {txtName,txtPrice}= this.state;

    return (
      <div className="col-xs-6 col-sm-6 col-md-6 col-lg-6">
        <form onSubmit={this.onSave}>
          <div className="form-group">
            <label>Product Name: </label>
            <input
              type="text"
              name="txtName"
              className="form-control"
              value={txtName}
              onChange={this.onChange}
            />
          </div>
          <div className="form-group">
            <label>Product Price: </label>
            <input
              type="number"
              name="txtPrice"
              className="form-control"
              value={txtPrice}
              onChange={this.onChange}
            />
          </div>
          <div className="radio">
            <label>
              <input type="radio" name="chkStatus" value="true" />
              Active
            </label>
            <label>
              <input type="radio" name="chkStatus" value="false" />
              Deactive
            </label>
          </div>
          <button type="submit" className="btn btn-primary">
            Submit
          </button>
        </form>
      </div>
    );
  }
}

export default ProductActionPage;
