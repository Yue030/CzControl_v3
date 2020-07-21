package com.yue.czcontrol.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.yue.czcontrol.AlertBox;
import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.connector.SocketConnector;
import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.error.DBConnectFailedError;
import com.yue.czcontrol.exception.UnknownException;
import com.yue.czcontrol.exception.UploadFailedException;
import com.yue.czcontrol.window.LoginController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class PDFGenerater implements SocketSetting, TimeProperty {

    /**
     * Date Formatter.
     */
    private static final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat(DATE_FORMAT);
    /**
     * PrintWriter.
     */
    private final PrintWriter out;

    /**
     * Constructor.
     * @throws IOException IOException
     */
    public PDFGenerater() throws IOException {
        out = new PrintWriter(SocketConnector.getSocket().getOutputStream());
    }

    /**
     * Generate PDF.
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    public static void generate() throws DBCloseFailedError {
         try {
            String select = "SELECT * From PLAYER";
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(select);
            /* Define the SQL query */
            ResultSet rs = psst.executeQuery();
            /* Step-2: Initialize PDF documents - logical objects */
            Document doc = new Document();
            PdfWriter writer =
                    PdfWriter.getInstance(doc,
                            new FileOutputStream(
                                    "./TeamCz\u6210\u54e1\u6aa2\u8996.pdf"));
            TableHeader event = new TableHeader();

            writer.setPageEvent(event); //load the header event
            event.setHeader("TeamCz\u6210\u54e1\u6aa2\u8996");
            doc.open();
            //we have four columns in our table
            final int columns = 6;
            PdfPTable pdf = new PdfPTable(columns);

            BaseFont bfChinese = BaseFont.createFont(
                    "c:\\windows\\fonts\\kaiu.ttf",
                    "Identity-H", BaseFont.NOT_EMBEDDED);
            com.itextpdf.text.Font FontChinese =
                    new com.itextpdf.text.Font(bfChinese, 12);
            //create a cell object
            PdfPCell table_cell;

            List<String> table_list = new ArrayList<>();
            table_list.add("ID");
            table_list.add("Name");
            table_list.add("Rank");
            table_list.add("Active");
            table_list.add("Active_All");
            table_list.add("Handler");
            for (String list : table_list) {
                table_cell = new PdfPCell(new Phrase(list, FontChinese));
                table_cell.setBackgroundColor(BaseColor.YELLOW);
                pdf.addCell(table_cell);
            }
            while (rs.next()) {
                for (String cell : table_list) {
                    String data = rs.getString(cell);
                    table_cell = new PdfPCell(new Phrase(data, FontChinese));
                    pdf.addCell(table_cell);
                }
            }
            /* Attach report table to PDF */
            doc.add(pdf);
            doc.close();

            /* Close all DB related objects */
            rs.close();
            psst.close();
            DBConnector.getConnection().close();

            AlertBox.show("Complete",
                    "pdf\u6a94\u6848\u5df2\u8f38\u51fa\u5b8c\u7562",
                    AlertBox.Type.INFORMATION);

            PDFGenerater pdfG = new PDFGenerater();
            pdfG.message(
                    DATE_FORMATTER.format(
                            new Date()) + "\t"
                            + LoginController.getUserName()
                            + " \u5df2\u751f\u6210\u6210\u54e1"
                            + "PDF\u6a94 ~[console]");
        } catch (DocumentException | SQLException
                 | IOException | ClassNotFoundException e) {
             String message = StackTrace.getStackTrace(e);
             ExceptionBox box = new ExceptionBox(message);
             box.show();
        } catch (DBConnectFailedError e) {
             ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
             box.show();
        } catch (Exception e) {
             throw new UnknownException();
        } finally {
            DBConnector.close();
        }
    }

    /**
     * add Data to DataBase.
     *
     * @param msg msg
     * @throws UploadFailedException upload failed
     */
    @Override
    public void addData(final String msg) throws UploadFailedException {

    }

    /**
     * get data from database.
     */
    @Override
    public void initData() {

    }

    /**
     * send Message to server.
     *
     * @param msg msg
     */
    @Override
    public void message(final String msg) {
        out.println(msg);
        out.flush();
    }

}

class TableHeader extends PdfPageEventHelper {
    /**
     * Header.
     */
    private String header;

    /**
     * Total.
     */
    private PdfTemplate total;

    /**
     * set the PDF Header.
     *
     * @param headerText The PDF Header
     */
    public void setHeader(final String headerText) {
        this.header = headerText;
    }

    /**
     * load page.
     *
     * @param writer   PdfWriter
     * @param document Doc
     */
    public void onEndPage(final PdfWriter writer, final Document document) {
        BaseFont bfChinese = null;
        PdfPCell cell = new PdfPCell();
        try {
            bfChinese = BaseFont.createFont(
                    "c:\\windows\\fonts\\kaiu.ttf",
                    "Identity-H", BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        com.itextpdf.text.Font FontChinese =
                new com.itextpdf.text.Font(bfChinese, 12);
        PdfPTable table = new PdfPTable(3);
        try {
            table.setWidths(new int[]{24, 24, 2});
            table.setTotalWidth(527);
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(20);
            cell.addElement(new Paragraph(header, FontChinese));
            table.addCell(cell);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(String.format("Page %d of", writer.getPageNumber()));
            cell = new PdfPCell(Image.getInstance(total));
            table.addCell(cell);
            table.writeSelectedRows(0, -1, 34, 830, writer.getDirectContent());
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (Exception e) {
            throw new UnknownException();
        }
    }

    /**
     * Set the total count.
     *
     * @param writer   PdfWriter
     * @param document Doc
     */
    public void onOpenDocument(final PdfWriter writer,
                               final Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);
    }

    /**
     * Show text aligned.
     *
     * @param writer   PdfWriter
     * @param document Doc
     */
    public void onCloseDocument(final PdfWriter writer,
                                final Document document) {
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                new Phrase(String.valueOf(writer.getPageNumber())),
                2, 2, 0);
    }
}

