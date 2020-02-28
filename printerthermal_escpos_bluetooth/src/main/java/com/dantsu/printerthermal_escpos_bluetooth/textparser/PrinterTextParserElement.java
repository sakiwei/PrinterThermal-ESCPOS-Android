package com.dantsu.printerthermal_escpos_bluetooth.textparser;

import com.dantsu.printerthermal_escpos_bluetooth.bluetooth.BluetoothPrinterSocketConnexion;

public interface PrinterTextParserElement {
    public int length();
    public PrinterTextParserElement print(BluetoothPrinterSocketConnexion printerSocket);
}
