package com.fpoly.mlbshop.Utils;

import com.fpoly.mlbshop.entity.Order;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class OrderExcelExporter {
    private List<Order> listOrder;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public OrderExcelExporter(List<Order> listOrdes) {
        this.listOrder = listOrdes;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Orders");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Order ID", style);
        createCell(row, 1, "Customer ID", style);
        createCell(row, 2, "Full Name", style);
        createCell(row, 3, "Address", style);
        createCell(row, 4, "Phone Number", style);
        createCell(row, 5, "Total", style);
        createCell(row, 6, "Order Date", style);
        createCell(row, 7, "Status", style);
        createCell(row, 8, "Payment Type", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        switch (value) {
            case Integer i -> cell.setCellValue(i);
            case Boolean b -> cell.setCellValue(b);
            case Double v -> cell.setCellValue(v);
            case Date d -> cell.setCellValue((Date) d);
            case null, default -> cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() throws FileNotFoundException {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Order order : listOrder) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            double totalPrice = order.getTotalPrice();
            Locale locale = new Locale("vi", "VN");
            DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
            String totalPriceString = decimalFormat.format(totalPrice);

            String orderDate = new SimpleDateFormat("dd/MM/yyyy").format(order.getOrderDate());

            createCell(row, columnCount++, order.getIdOrder(), style);
            createCell(row, columnCount++, order.getUser().getIdUser(), style);
            createCell(row, columnCount++, order.getUser().getFullname(), style);
            createCell(row, columnCount++, order.getUser().getAddress(), style);
            createCell(row, columnCount++, order.getUser().getPhoneNumber(), style);
            createCell(row, columnCount++, totalPriceString, style);
            createCell(row, columnCount++, orderDate, style);
            createCell(row, columnCount++, order.getPaymentStatus(), style);
            createCell(row, columnCount++, order.getPaymentTypes(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}

