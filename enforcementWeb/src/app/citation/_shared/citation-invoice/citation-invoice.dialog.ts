import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { NgxQrcodeElementTypes, NgxQrcodeErrorCorrectionLevels } from '@techiediaries/ngx-qrcode';

@Component({
  selector: 'app-citation-invoice-dialog',
  templateUrl: './citation-invoice.dialog.html',
  styleUrls: ['./citation-invoice.dialog.scss']
})
export class CitationInvoiceDialog {
  elementType = NgxQrcodeElementTypes.URL;
  correctionLevel = NgxQrcodeErrorCorrectionLevels.HIGH;
  value: string;

  constructor(
    public dialogRef: MatDialogRef<CitationInvoiceDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    // console.log(' dialog data', data);
    this.value = data.qrData;
    console.log("dialog data invoice this.value ", this.value);
  }
  ngOnInit(): void {
  }

  close(): void {
    this.dialogRef.close();
  }

  onConfirm(): void {

    // {"success":true,"error":null,"code":200,"payload":{"invoiceNumber":10064,"invoiceDate":"2022-02-03T12:46:03.090594Z","totalAmount":360.0,"totalVATAmount":54.000,"vat":0.15,"totalAmountWithVAT":414.000,"paymentMethodId":"PortalWFSettlement","comment":"","source":"Enforcement Portal","items":[{"itemName":"penalty two new","amount":120.0,"totalAmount":360.0,"quantity":3}]},"serviceTime":null}

    const prtContent = document.getElementById("printcontent");
    console.log("prtContent", prtContent);
    const WinPrint = window.open('', '', 'left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0');
    WinPrint.document.write('<html><head>');
    //WinPrint.document.write('<link rel="stylesheet" href="style.css">');
    WinPrint.document.write(`<style>
        .footer {
          padding: 15px;
        }

        .btn-confirm {
          font-weight: 600;
          font-size: 20px;
          border-radius: 8px;
          background: #39B2E5;
          color: white;
        }
        .btn-cancel {
          font-weight: 600;
          font-size: 20px;
          border-radius: 8px;
          background: #ACB5BE;
          color: black;
        }
        .text-center .btn {
          margin: 0px 15px;
          width: 30%;
        }

        .header {
          display: flex;
          justify-content: space-between;
          padding: 15px;
        }

        .header-right {
          text-align: left;
        }

        .dialog-content {
          margin: 30px;;
        }
      /************************/

      * {
        font-size: 12px;
        font-family: 'Times New Roman';
      }

      td,
      th {
            text-align: left;
      }
      tr,
      table {
        border-top: 0px solid #E9ECEF;
        border-collapse: collapse;
        width: 700px;
        height: 30px;
      }

      td.description,
      th.description {
        width: 80px;
        max-width: 80px;
      }

      td.quantity,
      th.quantity {
        width: 20px;
        max-width: 20px;
        word-break: break-all;
      }

      td.price,
      th.price {
        width: 60px;
        max-width: 60px;
        word-break: break-all;
      }

      .centered {
        text-align: center;
        align-content: center;
      }

      .img-print {
        max-width: inherit;
        width: inherit;
        margin-bottom: 7px;
      }

      @media print {
        .hidden-print,
        .hidden-print * {
            display: none !important;
        }
      }
      .print-content {
        margin: 0 10px;
      }
      .total {
        text-transform: uppercase;
        font-weight: bold;
        font-size: x-large;
      }

      .sub-total {
        text-transform: uppercase;
        font-size: xx-small;
      }
      .tr-border {
        border-top: 1px solid #E9ECEF;
      }
    </style>`);
    WinPrint.document.write('</head><body onload="print();close();">');
    WinPrint.document.write(prtContent.innerHTML);
    WinPrint.document.write('</body></html>');
    console.log("prtContent.innerHTML ", prtContent.innerHTML)
    WinPrint.document.close();
    WinPrint.focus();

  }

}
