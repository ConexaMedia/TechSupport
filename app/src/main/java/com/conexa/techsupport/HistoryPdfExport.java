package com.conexa.techsupport;

import android.content.Context;
import android.widget.Toast;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;;

public class HistoryPdfExport {
    private final Context context;

    public HistoryPdfExport(Context context) {
        this.context = context;
    }

    public void exportFullReport(Map<String, Object> aktivasiData, Map<String, Object> maintenanceData) {

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "Report_TechSupport_" + timeStamp + ".pdf";
            File path = new File(context.getExternalFilesDir(null), fileName);

            try{

                PdfWriter writer = new PdfWriter(path);
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);

                document.add(new Paragraph("Laporan Pekerjaan Teknisi Conexa")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold()
                        .setFontSize(18));

                document.add(new Paragraph("\n"));
                document.add(new Paragraph("Dicetak Pada : " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()))
                        .setTextAlignment(TextAlignment.CENTER));

                document.add(new Paragraph("\nAKTIVASI INTERNET")
                        .setBold()
                        .setFontSize(16));

                Table aktivasiTable = new Table(4);
                aktivasiTable.addHeaderCell("Nama Pelanggan");
                aktivasiTable.addHeaderCell("Paket");
                aktivasiTable.addHeaderCell("Alamat");
                aktivasiTable.addHeaderCell("Tanggal");

                for(Map.Entry<String, Object> entry : aktivasiData.entrySet()){
                    Map<String, Object> task = (Map<String, Object>) entry.getValue();
                    aktivasiTable.addCell(task.get("namaPelanggan").toString());
                    aktivasiTable.addCell(task.get("paket").toString());
                    aktivasiTable.addCell(task.get("alamat").toString());
                    aktivasiTable.addCell(task.get("tanggal").toString());
                }
                document.add(aktivasiTable);

                document.add(new Paragraph("\n\nMAINTENANCE JARINGAN")
                        .setBold()
                        .setFontSize(16));

                Table maintenanceTable = new Table(5);
                maintenanceTable.addHeaderCell("Nama Pelanggan");
                maintenanceTable.addHeaderCell("Masalah");
                maintenanceTable.addHeaderCell("Alamat");
                maintenanceTable.addHeaderCell("Teknisi");
                maintenanceTable.addHeaderCell("Tanggal Selesai");

                for (Map.Entry<String, Object> entry : maintenanceData.entrySet()) {
                    Map<String, Object> task = (Map<String, Object>) entry.getValue();
                    maintenanceTable.addCell(task.get("namaPelanggan").toString());
                    maintenanceTable.addCell(task.get("issue").toString());
                    maintenanceTable.addCell(task.get("alamat").toString());
                    maintenanceTable.addCell(task.get("teknisi").toString());
                    maintenanceTable.addCell(task.get("tanggal").toString());
                }
                document.add(maintenanceTable);

                document.close();
                Toast.makeText(context, "PDF terimpan di : "+path.getAbsolutePath(), Toast.LENGTH_LONG).show();
                } catch (Exception e){
                Toast.makeText(context, "Gagal menyimpan PDF " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

