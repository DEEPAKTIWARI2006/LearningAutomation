package utilities;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ColoredTagWisePdfReport {

    public static void generate(Map<String, List<String>> tagMap, String outputDir) throws IOException {
    	
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);
            content.setFont(PDType1Font.HELVETICA_BOLD, 16);
            content.beginText();
            content.newLineAtOffset(50, 750);
            content.setNonStrokingColor(Color.BLACK);
            content.showText("Cucumber Test Summary (Grouped by Tag)");
            content.setFont(PDType1Font.HELVETICA, 12);

            int yOffset = 720;

            for (Map.Entry<String, List<String>> entry : tagMap.entrySet()) {
                String tag = entry.getKey().replace("@", "");
                List<String> scenarios = entry.getValue();
                long passed = scenarios.stream().filter(s -> s.contains("PASSED")).count();
                long failed = scenarios.stream().filter(s -> s.contains("FAILED")).count();

                // Check for new page
                if (yOffset < 120) {
                    content.endText();
                    content.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    content = new PDPageContentStream(document, page);
                    content.setFont(PDType1Font.HELVETICA, 12);
                    content.beginText();
                    content.newLineAtOffset(50, 750);
                    yOffset = 750;
                }

                // Section Header
                content.setNonStrokingColor(Color.BLUE);
                content.newLineAtOffset(0, -30);
                yOffset -= 30;
                content.setFont(PDType1Font.HELVETICA_BOLD, 13);
                content.showText("Tag: " + tag + " (Total: " + scenarios.size() + ", Passed: " + passed + ", Failed: " + failed + ")");
                content.setFont(PDType1Font.HELVETICA, 11);

                for (String scenario : scenarios) {
                    if (yOffset < 100) {
                        content.endText();
                        content.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        content = new PDPageContentStream(document, page);
                        content.setFont(PDType1Font.HELVETICA, 12);
                        content.beginText();
                        content.newLineAtOffset(50, 750);
                        yOffset = 750;
                    }

                    content.newLineAtOffset(0, -15);
                    yOffset -= 15;

                    if (scenario.contains("PASSED")) {
                        content.setNonStrokingColor(new Color(0, 128, 0)); // Green
                    } else if (scenario.contains("FAILED")) {
                        content.setNonStrokingColor(Color.RED);
                    } else {
                        content.setNonStrokingColor(Color.DARK_GRAY);
                    }

                    content.showText("  • " + scenario);
                }

                content.setNonStrokingColor(Color.BLACK); // Reset color for next tag
            }

            content.endText();
            content.close();
            document.save(outputDir);
            document.close();

            System.out.println("✅ Combined tag-wise report saved to: " + outputDir);
    }
}
