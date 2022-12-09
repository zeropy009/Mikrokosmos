import axios from "axios";
import FilePondPluginFileValidateSize from "filepond-plugin-file-validate-size";
import FilePondPluginFileValidateType from "filepond-plugin-file-validate-type";
import FilePondPluginImageExifOrientation from "filepond-plugin-image-exif-orientation";
import FilePondPluginImagePreview from "filepond-plugin-image-preview";
import "filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css";
import "filepond/dist/filepond.min.css";
import React, { Component } from "react";
import { FilePond, registerPlugin } from "react-filepond";
import { path_variable } from "../../path_variable";
import ImageItem from "./ImageItem";

registerPlugin(
  FilePondPluginImageExifOrientation,
  FilePondPluginImagePreview,
  FilePondPluginFileValidateSize,
  FilePondPluginFileValidateType
);
export default class Image extends Component {
  constructor() {
    super();
    this.state = {
      files: {},
      prCode: "",
      maximumFileSize: 2000000,
      fileTypeAllow: "jpg,gif,png,jpeg,webm,pneg".split(","),
      maximumFile: 1,
      isDeleted: false,
    };
  }

  handleFetchData = (refID) => {
    axios({
      method: "GET",
      url: path_variable.Image.GET_IMAGE+refID,
      data: null,
    })
      .then((response) => {
        console.log(response.data);
        this.setState({
          files: response.data,
        });
      })
      .catch((error) => {
        console.log(error);
      });
  };

  uploadAllFileSameTime = (refNo, fnCallback) => {
    const formData = new FormData();
    let self = this;
    if (self.refs.pond.getFiles().length > 0) {
      self.refs.pond.getFiles().map(item => {
        formData.append("file", item.file);
      })
      return axios({
        method: "POST",
        url: path_variable.Image.SAVE_IMAGE+refNo,
        data: formData,
      })
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(function () {
          !!fnCallback && fnCallback.call(self);
        });
      }
    }

  render() {
    let { files } = this.state;
    let { maximumFileSize } = this.state;
    let { fileTypeAllow } = this.state;
    let stringFileTypeAllow = fileTypeAllow
      .map((item) => " ." + item)
      .toString();
    let { maximumFile } = this.state;
    let totalFileUploaded = files.length;
    let maxFileCanUpload = 1; //maximumFile - totalFileUploaded;
    //const isPDClerk = LocalStore.checkPermission(EligibleEnum.IS_PD_CLERK, this.props.purchaseCode);
    let lableUpload =
      '<i class="fa fa-cloud-upload" aria-hidden="true"></i> Drag & Drop your files or <span class="filepond--label-action"> Browse </span> at here.';
    // if (this.props.disable) {
    //   maxFileCanUpload = 0;
    //   lableUpload = this.props.alertDenyAttach
    // } else
    //   if (maxFileCanUpload <= 0) {
    //     lableUpload = this.props.alertMaxFile
    //   }
    //const permission = true//this.props.attachmentType === AttachmentTypeEnum.PO_ATTACHMENT.value ? PermissionEnum.UPLOAD_FILE_PO.value : this.props.attachmentType === AttachmentTypeEnum.PR_ATTACHMENT.value ? PermissionEnum.UPLOAD_FILE_PR.value : false;
    const hasPermission = true; //permission ? Permission.hasPermission(permission) : true;
    if (maximumFile != 0)
      return (
        <div>
          {hasPermission ? (
            <div className="container" style={{ width: "100%" }}>
              <div className="block-body">
                <div className="row">
                  <div className="col-md-12" style={{ marginBottom: "0px" }}>
                    <FilePond
                      ref="pond"
                      labelIdle={lableUpload}
                      allowBrowse={true}
                      allowMultiple={true}
                      instantUpload={false}
                      allowRevert={false}
                      allowCancel={false}
                      allowPaste={false}
                      allowFileTypeValidation={true}
                      acceptedFileTypes={["*/*"]}
                      onwarning={(res) => {
                        // if (res && res.body == "Max files") MyNotification.notifyI18n(
                        //   I18N.ATTACHMENT.MAX_FILE,
                        //   { maxFile: maxFileCanUpload },
                        //   ALERT.DANGER)
                      }}
                      fileValidateTypeDetectType={(source, type) =>
                        new Promise((resolve, reject) => {
                          let fName = source.name
                            .substr(source.name.lastIndexOf(".") + 1)
                            .toLowerCase();
                          if (fileTypeAllow.indexOf(fName) >= 0) {
                            type = "*/*";
                          }
                          resolve(type);
                        })
                      }
                      labelFileTypeNotAllowed="File is invalid."
                      fileValidateTypeLabelExpectedTypes={
                        "Accepts " + stringFileTypeAllow
                      }
                      maxFiles={maxFileCanUpload}
                      maxFileSize={maximumFileSize}
                      // labelMaxFileSize={
                      //   //i18n.t(I18N.PR.MESSAGE.FILE_SIZE) + this.formatBytes(maximumFileSize, 3) + "."
                      // }
                      // onaddfilestart={(e) => this.hiddenButtonUploadWhenNewMode(e)}
                      // server='http://localhost:8080/download/'
                      onupdatefiles={(fileItems) => {
                        if (fileItems.length > 0) {
                          let listItems = fileItems;
                          listItems.reduce((prev, current) => {
                            if (
                              prev.find((item) => {
                                return (
                                  item.file.name === current.file.name &&
                                  item.file.lastModified ===
                                    current.file.lastModified &&
                                  item.file.size === current.file.size &&
                                  item.file.type === current.file.type
                                );
                              })
                            ) {
                              this.refs.pond.removeFile(current.id);
                              // MyNotification.notifyI18n(
                              //   I18N.ATTACHMENT.UPLOAD_EXISTED,
                              //   "",
                              //   ALERT.DANGER);
                              // return prev;
                            } else {
                              return [...prev, current];
                            }
                          }, []);
                        }
                      }}
                    />
                    <span className="mb-3">
                      {this.props.validationMessage
                        ? this.props.validationMessage
                        : ""}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          ) : null}
          <ImageItem
            handleFetchData={this.handleFetchData}
            handleDelete={this.handleDelete}
            listAttachment={files}
          />
        </div>
      );
    else return null;
  }
}
