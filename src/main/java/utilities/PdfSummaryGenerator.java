package utilities;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PdfSummaryGenerator {

    public static void generateTestSummary(List<String> scenarioSummaries, String pdfPath) throws IOException {
        int total = scenarioSummaries.size();
        long passed = scenarioSummaries.stream().filter(s -> s.contains("PASSED")).count();
        long failed = scenarioSummaries.stream().filter(s -> s.contains("FAILED")).count();

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText("Cucumber Test Summary");

        // Move down
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(0, -30);
        contentStream.showText("Summary:");

        // Table-style output
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Total Scenarios: " + total);
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Passed: " + passed);
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Failed: " + failed);

        // Move down to list scenarios
        contentStream.newLineAtOffset(0, -30);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.showText("Detailed Scenario Results:");
        contentStream.setFont(PDType1Font.HELVETICA, 11);

        for (String summary : scenarioSummaries) {
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(summary);
        }

        contentStream.endText();
        contentStream.close();

        document.save(pdfPath);
        document.close();

        System.out.println("PDF Test Summary saved to: " + pdfPath);
    }
}
