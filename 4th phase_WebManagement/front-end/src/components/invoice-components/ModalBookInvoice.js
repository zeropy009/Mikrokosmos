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
import { path_variable, prefix2 } from "../../path_variable";
import {
  NotificationContainer,
  NotificationManager,
} from "react-notifications";
import axios from "axios";
import "react-notifications/lib/notifications.css";
import { PermissionEnum } from "../../PermissionEnum";
import NumberFormat from "react-number-format";
import { typeModalEnum } from "./../../TypeModalEnum";

class ModalBookInvoice extends Component {
  constructor(props) {
    super(props);
    this.state = {
      newBook: {
        bookID: "",
        bookTitle: "",
        bookQuantity: "",
        discount:'',
      },
      typeModal: typeModalEnum.ModalAdd.value,
      listBook: [],
      isOpen: false,
    };
  }

  toggle = (e, book) => {
    console.log(book);
    if (book !== undefined && book.bookID !== undefined) {
      this.setState({
        newBook: book,
        isOpen: !this.state.isOpen,
        typeModal: typeModalEnum.ModalEdit.value,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
        typeModal: typeModalEnum.ModalAdd.value,
      });
    }
  };

  fetchListBookInsert = () => {
    axios({
      method: "GET",
      url: path_variable.Book.GET_LIST_BOOK,
      data: null,
    })
      .then((res) => {
        var temp = [
          {
            bookID: "",
            bookTitle: "",
          },
        ];
        res.data.map((data) => {
          var list = {
            bookID: data.bookID,
            bookTitle: data.bookTitle,
          };
          temp.push(list);
        });
        this.setState({
          listBook: temp,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error(
          "Error message",
          "Show Receipt Detail failed"
        );
      });
  };

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

  fetchListBookInvoice = () => {
    this.props.fetchListBookInvoice(this.props.invoiceID);
    this.props.fetchInvoiceDetail(this.props.invoiceID);
  };

  handleSubmit = (e) => {
    e.preventDefault();
    console.log(this.state.newBook);
    if (this.form.isValid()) {
      this.insertOrUpdateBookInvoice(this.state.newBook);
    }
  };

  insertOrUpdateBookInvoice = (Book) => {
    if(this.state.typeModal === typeModalEnum.ModalEdit.value){
      axios({
        method: "PUT",
        url:
          path_variable.Invoice.UPDATE_BOOK_INVOICE +
          this.props.invoiceID +
          "&userName=" +
          this.props.userName,
        data: Book,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListBookInvoice();
            NotificationManager.success(
              "Success message",
              "Update Book successfully"
            );
          } else {
            NotificationManager.error("Error message", "Update Book failed");
          }
        })
        .catch((err) => {
          NotificationManager.error("Error message", "Update Book failed");
        });
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }else{
      axios({
        method: "POST",
        url:
          path_variable.Invoice.INSERT_BOOK_INVOICE +
          this.props.invoiceID +
          "&userName=" +
          this.props.userName,
        data: Book,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListBookInvoice();
            NotificationManager.success(
              "Success message",
              "Add Book successfully"
            );
          } else {
            NotificationManager.error("Error message", "Add Book failed");
          }
        })
        .catch((err) => {
          NotificationManager.error("Error message", "Add Customer failed");
        });
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }
    
  };

  componentDidMount() {
    this.fetchListBookInsert();
  }

  render() {
    var { isOpen, typeModal, listBook, newBook } = this.state;
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
              <FormControlLabel htmlFor="bookID">
                Book<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <select
                className="form-control"
                name="bookID"
                id="bookID"
                value={newBook.bookID}
                onChange={(e) => this.onHandleChange(e)}
                required
              >
                {listBook.map((data) => {
                  return (
                    <option key={data.bookID} value={data.bookID}>
                      {data.bookTitle}
                    </option>
                  );
                })}
              </select>
              <FieldFeedbacks htmlFor="bookID">
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
              <FormControlLabel htmlFor="quantity">
                Quantity<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <NumberFormat
                name="quantity"
                id="quantity"
                required
                value={!!newBook.quantity ? newBook.quantity : ""}
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
                      ["quantity"]: floatValue,
                    },
                  });
                }}
              />
              <FieldFeedbacks htmlFor="quantity">
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
              <FormControlLabel htmlFor="discount">
                Discount<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <NumberFormat
                name="discount"
                id="discount"
                value={!!newBook.discount ? newBook.discount : 0}
                customInput={FormControlInput}
                disabled
              />
              <FieldFeedbacks htmlFor="discount">
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
      </Modal>
    );
  }
}

export default ModalBookInvoice;
