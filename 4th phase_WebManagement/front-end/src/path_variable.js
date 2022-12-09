export const prefix =window.location.origin +"/mik"
export const prefix2 ="/mikrokosmos"

export const path_variable = {
  USER: {
    GET_DETAIL: `${prefix}/staff/get-detail`,
  },
  Author: {
    GET_LIST_AUTHOR: `${prefix}/author/get1`,
    UPDATE_AUTHOR: `${prefix}/author/update?userName=`,
    ADD_AUTHOR: `${prefix}/author/add?userName=`,
    DELETE_AUTHOR: `${prefix}/author/delPhysic?authorID=`,
  },
  Category: {
    GET_LIST_CATEGORY: `${prefix}/category/get1`,
    UPDATE_CATEGORY: `${prefix}/category/update?userName=`,
    ADD_CATEGORY: `${prefix}/category/add?userName=`,
    DELETE_CATEGORY: `${prefix}/category/delPhysic?categoryID=`,
  },
  Supplier: {
    GET_LIST_SUPPLIER: `${prefix}/supplier/get1`,
    UPDATE_SUPPLIER: `${prefix}/supplier/update?userName=`,
    ADD_SUPPLIER: `${prefix}/supplier/add?userName=`,
    DELETE_SUPPLIER: `${prefix}/supplier/delPhysic?supplierID=`,
  },
  Customer: {
    GET_LIST_CUSTOMER: `${prefix}/customer/get1`,
    GET_CUSTOMER_DETAILS: `${prefix}/customer/get-detail?customerID=`,
    UPDATE_CUSTOMER: `${prefix}/customer/update?userName=`,
    ADD_CUSTOMER: `${prefix}/customer/add?userName=`,
    DELETE_CUSTOMER: `${prefix}/customer/delPhysic?customerID=`,
    GET_HISTORY_POINT_CUSTOMER: `${prefix}/historyPoint/get1?customerID=`,
  },
  Level: {
    GET_LIST_LEVEL: `${prefix}/level/get1`,
    UPDATE_LEVEL: `${prefix}/level/update?userName=`,
    ADD_LEVEL: `${prefix}/level/add?userName=`,
    DELETE_LEVEL: `${prefix}/level/delPhysic?levelID=`,
  },
  Log: {
    GET_LIST_LOG: `${prefix}/log/get1`,
    UPDATE_LOG: `${prefix}/log/update?userName=`,
    ADD_LOG: `${prefix}/log/add?userName=`,
    DELETE_LOG: `${prefix}/log/delPhysic?logID=`,
  },
  Method: {
    GET_LIST_METHOD: `${prefix}/method/get1`,
    UPDATE_METHOD: `${prefix}/method/update?userName=`,
    ADD_METHOD: `${prefix}/method/add?userName=`,
    DELETE_METHOD: `${prefix}/method/delPhysic?methodID=`,
  },
  Image: {
    GET_IMAGE: `${prefix}/image/get-image?refID=`,
    SAVE_IMAGE: `${prefix}/image/save-image?refID=`,
  },
  Receipt: {
    GET_LIST_RECEIPT: `${prefix}/receipt/get1`,
    GET_RECEIPT_DETAI: `${prefix}/receipt/get-receipt?receiptID=`,
    GET_LIST_BOOK_RECEIPT_DETAIL: `${prefix}/receiptdetail/findReceiptID?receiptID=`,
    UPDATE_RECEIPT: `${prefix}/receipt/update?userName=`,
    ADD_RECEIPT: `${prefix}/receipt/add?userName=`,
    DELETE_RECEIPT: `${prefix}/receipt/delPhysic?receiptID=`,
    ADD_RECEIPT_DETAIL: `${prefix}/receiptdetail/add?receiptID=`,
    UPDATE_RECEIPT_DETAIL: `${prefix}/receiptdetail/update?receiptID=`,
    DELETE_RECEIPT_DETAIL: `${prefix}/receiptdetail/delPhysic?receiptID=`,
  },
  Book: {
    GET_LIST_BOOK: `${prefix}/book/get1`,
    GET_BOOK_DETAIL: `${prefix}/book/get-book?bookID=`,
    UPDATE_BOOK: `${prefix}/book/update?userName=`,
    ADD_BOOK: `${prefix}/book/add?userName=`,
    DELETE_BOOK: `${prefix}/book/delPhysic?bookID=`,
    GET_HISTORY_PRICE_BOOK: `${prefix}/historyPrice/findID?bookID=`,
  },
  Invoice: {
    GET_LIST_INVOICE: `${prefix}/invoice/get1`,
    GET_INVOICE_DETAIL: `${prefix}/invoice/get-invoice?invoiceID=`,
    UPDATE_INVOICE: `${prefix}/invoice/update?userName=`,
    ADD_INVOICE: `${prefix}/invoice/add?userName=`,
    DELETE_INVOICE: `${prefix}/invoice/delPhysic?invoiceID=`,
    GET_SHIP_INFOR_INVOICE: `${prefix}/shipInfor/findID?invoiceID=`,
    GET_BOOK_INVOICE: `${prefix}/invoiceDetail/findInvoiceID?invoiceID=`,
    INSERT_BOOK_INVOICE: `${prefix}/invoiceDetail/add?invoiceID=`,
    UPDATE_BOOK_INVOICE: `${prefix}/invoiceDetail/update?invoiceID=`,
    DELETE_BOOK_INVOICE: `${prefix}/invoiceDetail/delPhysic?invoiceID=`,
    INSERT_SHIP_INFO: `${prefix}/shipInfor/add?userName=`,
  },
  Depart: {
    GET_LIST_DEPART: `${prefix}/depart/get1`,
    UPDATE_DEPART: `${prefix}/depart/update?userName=`,
    ADD_DEPART: `${prefix}/depart/add?userName=`,
    DELETE_DEPART: `${prefix}/depart/delPhysic?departID=`,
  },
  Staff: {
    GET_LIST_STAFF: `${prefix}/staff/get1`,
    GET_STAFF_DETAILS: `${prefix}/staff/get-detail-staff?staffID=`,
    UPDATE_STAFF: `${prefix}/staff/update?userName=`,
    ADD_STAFF: `${prefix}/staff/add?userName=`,
    DELETE_STAFF: `${prefix}/staff/delPhysic?staffID=`,
    CHANGE_PASSWORD: `${prefix}/staff/change-password?staffID=`,
    GET_LIST_USERNAME: `${prefix}/staff/get-list-username`,
  },
};