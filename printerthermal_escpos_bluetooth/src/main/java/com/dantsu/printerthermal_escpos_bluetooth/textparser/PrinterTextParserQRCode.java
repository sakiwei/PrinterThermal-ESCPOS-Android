package com.dantsu.printerthermal_escpos_bluetooth.textparser;

import com.dantsu.printerthermal_escpos_bluetooth.Printer;
import com.dantsu.printerthermal_escpos_bluetooth.PrinterCommands;
import com.dantsu.printerthermal_escpos_bluetooth.bluetooth.BluetoothPrinterSocketConnexion;

import java.util.Hashtable;

public class PrinterTextParserQRCode implements PrinterTextParserElement {
    private int length;
    private int pixelSize;
    private byte[] align;
    private String data;
    private int qrCodeType;


    public PrinterTextParserQRCode(PrinterTextParserColumn printerTextParserColumn, String textAlign, Hashtable<String, String> qrCodeAttributes, String data) {
        Printer printer = printerTextParserColumn.getLine().getTextParser().getPrinter();
        data = data.trim();

        this.align = PrinterCommands.TEXT_ALIGN_LEFT;
        switch (textAlign) {
            case PrinterTextParser.TAGS_ALIGN_CENTER:
                this.align = PrinterCommands.TEXT_ALIGN_CENTER;
                break;
            case PrinterTextParser.TAGS_ALIGN_RIGHT:
                this.align = PrinterCommands.TEXT_ALIGN_RIGHT;
                break;
        }

        this.qrCodeType = PrinterCommands.QRCODE_2;
        switch (qrCodeAttributes.get(PrinterTextParser.ATTR_QRCODE_TYPE)) {
            case PrinterTextParser.ATTR_QRCODE_TYPE_1:
                this.qrCodeType = PrinterCommands.QRCODE_1;
                break;
        }

        this.length = printer.getNbrCharactersPerLine();


        this.pixelSize = printer.mmToPx(10f);
        try {
            if (qrCodeAttributes.containsKey(PrinterTextParser.ATTR_QRCODE_SIZE)) {
                this.pixelSize = printer.mmToPx(Integer.parseInt(qrCodeAttributes.get(PrinterTextParser.ATTR_QRCODE_SIZE)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.data = data;
    }

    /**
     * Get the barcode width in char length.
     *
     * @return int
     */
    @Override
    public int length() {
        return this.length;
    }

    /**
     * Print barcode
     *
     * @param printerSocket Bluetooth printer socket connexion
     * @return Fluent interface
     */
    @Override
    public PrinterTextParserQRCode print(BluetoothPrinterSocketConnexion printerSocket) {
        printerSocket
                .setAlign(this.align)
                .printQRCode(this.qrCodeType, this.data, this.pixelSize);
        return this;
    }

}
