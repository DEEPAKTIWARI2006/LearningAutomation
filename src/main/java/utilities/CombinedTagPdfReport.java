package utilities;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CombinedTagPdfReport {

    public static void generate(Map<String, List<String>> tagMap, String pdfPath) throws IOException, InterruptedException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream content = new PDPageContentStream(document, page);
        content.setFont(PDType1Font.HELVETICA_BOLD, 16);
        content.beginText();
        content.setNonStrokingColor(Color.BLACK);
        content.newLineAtOffset(50, 750);
        content.showText("Cucumber Test Summary (Tag-wise Table)");
        content.endText();

        // Table headers
        int startY = 720;
        int rowHeight = 20;
        int tableStartX = 50;
        int tableWidth = 500;

        String[] headers = {"Tag", "Total Scenarios", "Passed Scenarios", "Failed Scenarios"};
        int[] colWidths = {150, 100, 120, 120};

        int currentY = startY;

        // Draw header row
        drawRow(content, currentY, tableStartX, colWidths, headers, true);
        currentY -= rowHeight;

        // Draw data rows
        for (Map.Entry<String, List<String>> entry : tagMap.entrySet()) {
            String tag = entry.getKey();
            List<String> scenarios = entry.getValue();
            long passed = scenarios.stream().filter(s -> s.contains("PASSED")).count();
            long failed = scenarios.stream().filter(s -> s.contains("FAILED")).count();

            String[] data = {
                tag,
                String.valueOf(scenarios.size()),
                String.valueOf(passed),
                String.valueOf(failed)
            };

            // Add new page if needed
            if (currentY < 100) {
                content.close();
                page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                content = new PDPageContentStream(document, page);
                currentY = 750;
            }

            drawRow(content, currentY, tableStartX, colWidths, data, false);
            currentY -= rowHeight;
        }

        content.close();
        document.save(pdfPath);
        Thread.sleep(2000);
        document.close();

        System.out.println("✅ PDF table report generated: " + pdfPath);
    }

    private static void drawRow(PDPageContentStream content, int y, int startX, int[] colWidths, String[] data, boolean isHeader) throws IOException {
        int x = startX;
        content.setNonStrokingColor(isHeader ? Color.LIGHT_GRAY : Color.WHITE);
        content.addRect(x, y - 15, colWidths[0] + colWidths[1] + colWidths[2] + colWidths[3], 20);
        content.fill();

        content.setNonStrokingColor(Color.BLACK);
        content.setLineWidth(1f);
        content.addRect(x, y - 15, colWidths[0] + colWidths[1] + colWidths[2] + colWidths[3], 20);
        content.stroke();

        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, isHeader ? 12 : 11);
        content.setNonStrokingColor(Color.BLACK);
        content.newLineAtOffset(x + 5, y - 10);

        for (int i = 0; i < data.length; i++) {
            content.showText(data[i]);
            if (i < data.length - 1) {
                content.newLineAtOffset(colWidths[i], 0);
            }
        }

        content.endText();
    }
}
