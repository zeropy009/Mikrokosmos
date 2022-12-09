const path = require("path");
const webpack = require("webpack");
const HtmlWebpackPlugin = require("html-webpack-plugin");

const devServer = {
  port: 4000,
  open: true,
};
module.exports = {
  entry: {
    bundle: "./src/index.js",
  },
  output: {
    filename: "[name].js",
    path: path.resolve(__dirname, "build"),
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: "babel-loader",
      },
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader"],
      },
      {
        loader: "file-loader",
        test: /\.jpe?g$|\.gif$|\.png$|\.svg$|\.ico$|\.jpg$|\.eot$|\.woff$|\.ttf$/,
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: "public/index.html",
    })
  ],
  devServer,
};
