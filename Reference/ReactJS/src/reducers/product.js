import * as types from "./../constants/ActionTypes";

var initialState = [
  {
    id: 1,
    name: "Iphone XS",
    price: 400,
    status: true,
  },
  {
    id: 2,
    name: "SamSung XS",
    price: 300,
    status: false,
  },
  {
    id: 1,
    name: "Iphone XS Max",
    price: 200,
    status: true,
  },
];

const products = (state = initialState, action) => {
  switch (action.types) {
    default:
      return [...state];
  }
};

export default products;
