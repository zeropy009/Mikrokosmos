import React, { Component } from "react";
import {
  Button,
  Modal,
  ModalHeader,
  ModalFooter,
  ModalBody,
  Input,
} from "reactstrap";
import {
  FormWithConstraints,
  FieldFeedback,
} from "react-form-with-constraints";
import {
  FormGroup,
  FieldFeedbacks,
  FormControlInput,
  FormControlLabel,
} from "react-form-with-constraints-bootstrap4";
import { path_variable } from "../../path_variable";
import {
  NotificationContainer,
  NotificationManager,
} from "react-notifications";
import axios from "axios";
import "react-notifications/lib/notifications.css";
import NumberFormat from "react-number-format";
import { typeModalEnum } from "./../../TypeModalEnum";
class ModalForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      newBook: {
        bookID: "",
        bookTitle: "",
        authorID: "",
        categoryID: "",
        description: "",
        amount: 0,
        price: 0,
      },
      typeModal: typeModalEnum.ModalAdd.value,
      listCategory: [],
      listAuthor: [],
      isOpen: false,
    };
  }

  toggle = (e, book) => {
    if (book !== undefined) {
      this.setState({
        newBook: book,
        isOpen: !this.state.isOpen,
        typeModal: typeModalEnum.ModalEdit.value,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen
      });
    }
  };

  fetchListBook = ()=>{
    this.props.fetchListBook();
  }

  onHandleChange = (e) => {
    var target = e.target;
    var name = target.name;
    var value = target.value;
    this.setState({
      newBook: {
        ...this.state.newBook,
        [name]: value,
      },
    });
  };

  fetchBookDetail = () => {
    this.props.fetchBookDetail(this.props.param);
    this.props.fetchListPrice(this.props.param);
  };

  handleSubmit = (e) => {
    e.preventDefault();
    console.log(this.state.newBook);
    if (this.form.isValid()) {
      this.insertOrUpdateBook(this.state.newBook);
    }
  };

  insertOrUpdateBook = (Book) => {
    if (this.state.typeModal === typeModalEnum.ModalEdit.value) {
      axios({
        method: "PUT",
        url: path_variable.Book.UPDATE_BOOK + this.props.userName,
        data: Book,
      })
        .then((res) => {
          if (res.data) {
            this.fetchBookDetail();
            NotificationManager.success(
              "Success message",
              "Update Book successfully"
            );
          } else {
            NotificationManager.error("Error message", "Update Book failed");
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Update Book failed");
        });
    } else {
      axios({
        method: "POST",
        url: path_variable.Book.ADD_BOOK + this.props.userName,
        data: Book,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListBook();
            NotificationManager.success(
              "Success message",
              "Add Book successfully"
            );
          } else {
            NotificationManager.error("Error message", "Add Book failed");
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Add Book failed");
        });
    }

    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    var { isOpen, newBook, typeModal } = this.state;
    var { listCategory, listAuthor } = this.props;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>
          {typeModal === typeModalEnum.ModalAdd.value
            ? "Add Book"
            : "Edit Book"}
        </ModalHeader>
        <FormWithConstraints
          ref={(formWithConstraints) => (this.form = formWithConstraints)}
          onSubmit={(e) => this.handleSubmit(e)}
        >
          <ModalBody>
            <FormGroup>
              <FormControlLabel htmlFor="bookID">Book ID</FormControlLabel>
              <FormControlInput
                name="bookID"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="This will be automatically created"
                value={!!newBook.bookID ? newBook.bookID : ""}
                disabled={true}
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="bookTitle">
                Name<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <FormControlInput
                name="bookTitle"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Input Book Title"
                value={!!newBook.bookTitle ? newBook.bookTitle : ""}
                required
              />
              <FieldFeedbacks htmlFor="bookTitle">
                <FieldFeedback
                  when={(value) => {
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup>
            {typeModal === typeModalEnum.ModalEdit.value ? (
              <FormGroup>
                <FormControlLabel htmlFor="amount">
                  Amount<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <NumberFormat
                  name="amount"
                  id="amount"
                  value={!!newBook.amount ? newBook.amount : ""}
                  customInput={FormControlInput}
                  placeholder="Input Book Amount"
                  thousandSeparator={true}
                  decimalScale={2}
                  allowNegative={false}
                  onValueChange={(values) => {
                    const { floatValue } = values;
                    this.setState({
                      newBook: {
                        ...this.state.newBook,
                        ["amount"]: floatValue,
                      },
                    });
                  }}
                />
                <FieldFeedbacks htmlFor="amount">
                  <FieldFeedback
                    when={(value) => {
                      return value === "";
                    }}
                  >
                    <strong style={{ color: "red" }}> Required Field</strong>
                  </FieldFeedback>
                </FieldFeedbacks>
              </FormGroup>
            ) : (
              ""
            )}
            {/* <FormGroup>
              <FormControlLabel htmlFor="amount">
                Amount<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <NumberFormat
                name="amount"
                id="amount"
                value={!!newBook.amount ? newBook.amount : ""}
                customInput={FormControlInput}
                placeholder="Input Book Amount"
                thousandSeparator={true}
                decimalScale={2}
                allowNegative={false}
                onValueChange={(values) => {
                  const { floatValue } = values;
                  this.setState({
                    newBook: {
                      ...this.state.newBook,
                      ["amount"]: floatValue,
                    },
                  });
                }}
              />
              <FieldFeedbacks htmlFor="amount">
                <FieldFeedback
                  when={(value) => {
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup> */}
            <FormGroup>
              <FormControlLabel htmlFor="price">
                Price<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <NumberFormat
                name="price"
                id="price"
                value={!!newBook.price ? newBook.price : ""}
                customInput={FormControlInput}
                placeholder="Input Book Price"
                thousandSeparator={true}
                decimalScale={2}
                allowNegative={false}
                onValueChange={(values) => {
                  const { floatValue } = values;
                  this.setState({
                    newBook: {
                      ...this.state.newBook,
                      ["price"]: floatValue,
                    },
                  });
                }}
                required
              />
              <FieldFeedbacks htmlFor="price">
                <FieldFeedback
                  when={(value) => {
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="categoryID">
                Category<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <select
                className="form-control"
                name="categoryID"
                id="categoryID"
                value={newBook.categoryID}
                onChange={(e) => this.onHandleChange(e)}
                required
              >
                {listCategory.map((data) => {
                  return (
                    <option key={data.categoryID} value={data.categoryID}>
                      {data.categoryName}
                    </option>
                  );
                })}
              </select>
              <FieldFeedbacks htmlFor="categoryID">
                <FieldFeedback
                  when={(value) => {
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="authorID">
                Author<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <select
                className="form-control"
                name="authorID"
                id="authorID"
                value={newBook.authorID}
                onChange={(e) => this.onHandleChange(e)}
                required
              >
                {listAuthor.map((data) => {
                  return (
                    <option key={data.authorID} value={data.authorID}>
                      {data.authorName}
                    </option>
                  );
                })}
              </select>
              <FieldFeedbacks htmlFor="authorID">
                <FieldFeedback
                  when={(value) => {
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="description">
                Description<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <Input
                id="description"
                type="textarea"
                name="description"
                placeholder="Input Book Description"
                rows="5"
                maxLength="250"
                className="form-control form-control-success"
                onChange={(e) => this.onHandleChange(e)}
                value={newBook.description}
              />
              <FieldFeedbacks htmlFor="description">
                <FieldFeedback
                  when={(value) => {
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup>
          </ModalBody>
          <ModalFooter>
            <Button
              type="submit"
              color="success"
              onClick={(e) => {
                if (!!this.form) {
                  this.form.validateFields();
                  // eslint-disable-next-line no-undef
                  customValidateFields(this.form);
                }
              }}
            >
              {typeModal === typeModalEnum.ModalAdd.value
                ? "Add"
                : "Save changes"}
            </Button>{" "}
            <Button color="secondary" onClick={this.toggle}>
              Cancel
            </Button>
          </ModalFooter>
        </FormWithConstraints>
        <NotificationContainer />
      </Modal>
    );
  }
}

export default ModalForm;
