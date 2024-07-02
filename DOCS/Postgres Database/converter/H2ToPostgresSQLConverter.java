import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.StringEscapeUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class H2ToPostgresSQLConverter {
	

    public static void syntaxConverter() {
        // Input and output file paths
        String inputFile = "process.sql";
        String outputFile = "temp.sql";

        try {
			File file = new File(outputFile);
            // Create reader and writer objects
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));

            // Read input file line by line
            String line;
            while ((line = reader.readLine()) != null) {
                // Convert H2 syntax to PoarGresSQL syntax
                String convertedLine = convertToPostgres(line);
                // Write the converted line to the output file
                writer.write(convertedLine);
                writer.newLine();
            }

            // Close reader and writer
            reader.close();
            writer.close();

            //System.out.println("Conversion completed. Output written to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to convert H2 syntax to MySQL syntax
    private static String convertToPostgres(String line) {
		//0. FORMAT INSERT STATEMENTS
		//line = line.replace("INSERT INTO \"PUBLIC\".\"", "INSERT INTO ");
		//line = line.replace("\" VALUES", " VALUES");

		
		//1. convert STRINGDECODE('')
		
		 Pattern pattern = Pattern.compile("STRINGDECODE\\('([^']*)'\\)");

        Matcher matcher = pattern.matcher(line);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String originalString = matcher.group(1);
            originalString = originalString.replace("\\", "\\\\");
			//originalString = originalString.replace("\\u", "\\\\u");
            //String decodedString = StringEscapeUtils.unescapeJava(originalString);
            matcher.appendReplacement(sb, "E'" + originalString + "'");
        }
        matcher.appendTail(sb);
		
		line=sb.toString();
		
		//2. convert attackment X()
		 pattern = Pattern.compile("X'([0-9a-fA-F]+)'");
        matcher = pattern.matcher(line);
        sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "'" + matcher.group(1) + "'");
        }
        matcher.appendTail(sb);
        line = sb.toString();
		
		
		//4. Rank is reserved use TBL_RANK
		line = line.replaceAll("INSERT INTO RANK VALUES", "INSERT INTO TBL_RANK VALUES"); 
		//5. User is reserved use TBL_USER
		line = line.replaceAll("INSERT INTO \"public\".\"user\" VALUES", "INSERT INTO \"public\".\"tbl_user\" VALUES"); 
		
        // Example: line = line.replace("H2-specific", "MySQL-specific");
		// test function

        return line;
    }

	
	public static void tablenameToLowercase() {
        String inputFile = "temp.sql";
        String outputFile = "output.sql";

        try {
			File file = new File(outputFile);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line contains an insert statement
                if (line.trim().toLowerCase().startsWith("insert into")) {
                    // Extract table name
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 3) {
                        String tableName = parts[2].replaceAll("[(),]", "").toLowerCase();
                        // Replace the table name in the line with lowercase version
                        line = line.replaceFirst(parts[2], tableName);
                    }
                }
                // Write the modified or unmodified line to the output file
                writer.write(line + "\n");
            }

            // Close the reader and writer
            reader.close();
            writer.close();

            System.out.println("Table names converted to lowercase successfully.");
        } catch (IOException e) {
            System.err.println("Error processing the SQL file: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
		System.out.println("H2 to POSTGRESQL converter");
		H2ToPostgresSQLConverter.syntaxConverter();
		H2ToPostgresSQLConverter.tablenameToLowercase();
        System.out.println("converted to postgres successfully");
		
	}

	
}
