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


public class H2ToMySQLConverter {
	public static void trimInsert() {
        String inputFile = "temp.sql";
        String outputFile = "process.sql";

        // Tables for which we want to exclude INSERT queries
        String[] excludedTables = {"TBL_COUNTRY", "TBL_MAST_SHIPCOMPANY", "TBL_MOU_COUNTRY", "TBL_SCHEDULE", "TBL_SEAPORT", "TBL_TENANTCODE", "TBL_TENANTCONFIG", "TBL_TENANTREFERENCE"};

        try {
			File file = new File(outputFile);
           
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));

            String line;
            boolean ignoreInserts = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("INSERT INTO")) {
                    String tableName = getTableName(line);
                    if (isExcludedTable(tableName, excludedTables)) {
                        ignoreInserts = true;
                    } else {
                        ignoreInserts = false;
                    }
                }

                // Skip lines if ignoreInserts is true
                if (!ignoreInserts) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    // Handle multi-line INSERTs
                    if (line.trim().endsWith(";")) {
                        ignoreInserts = false;
                    }
                }
            }

            reader.close();
            writer.close();

            System.out.println("Insert queries removed successfully from " + inputFile + " and saved to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getTableName(String insertQuery) {
        String[] parts = insertQuery.split("\\s+");
        return parts[2]; // Assuming the table name is the third part after "INSERT INTO"
    }

    private static boolean isExcludedTable(String tableName, String[] excludedTables) {
        for (String table : excludedTables) {
            if (tableName.equalsIgnoreCase(table)) {
                return true;
            }
        }
        return false;
    }

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
                // Convert H2 syntax to MySQL syntax
                String convertedLine = convertToMySQL(line);
                // Write the converted line to the output file
                writer.write(convertedLine);
                writer.newLine();
            }

            // Close reader and writer
            reader.close();
            writer.close();

            System.out.println("Conversion completed. Output written to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to convert H2 syntax to MySQL syntax
    private static String convertToMySQL(String line) {
		//0. FORMAT INSERT STATEMENTS
		line = line.replace("INSERT INTO \"PUBLIC\".\"", "INSERT INTO ");
		line = line.replace("\" VALUES", " VALUES");

		
		//1. convert STRINGDECODE('P\u00c4RNU') to charecters
		
		 Pattern pattern = Pattern.compile("STRINGDECODE\\('([^']*)'\\)");

        Matcher matcher = pattern.matcher(line);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String originalString = matcher.group(1);
            String decodedString = StringEscapeUtils.unescapeJava(originalString);
            matcher.appendReplacement(sb, "'" + decodedString + "'");
        }
        matcher.appendTail(sb);
		
		line=sb.toString();
		
		//2. convert attackment X() to UNHEX
		 pattern = Pattern.compile("X'([0-9a-fA-F]+)'");
        matcher = pattern.matcher(line);
        sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "UNHEX('" + matcher.group(1) + "')");
        }
        matcher.appendTail(sb);
        line = sb.toString();

        //3. TBL_TENANTCONFIG escape backslashes
		if(line.contains("DATA_INBOX") || line.contains("DATA_OUTBOX") || line.contains("REPORT_LOCATION") )       
			line = line.replaceAll("\\\\", "\\\\\\\\");
		
		
		//4. Rank is reserved use TBL_RANK
		line = line.replaceAll("INSERT INTO RANK VALUES", "INSERT INTO TBL_RANK VALUES"); 
		
        // Example: line = line.replace("H2-specific", "MySQL-specific");
		// test function

        return line;
    }

	
	public static void tablenameToLowercase() {
        String inputFile = "process.sql";
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
		System.out.println("H2 to MYSQL converter");
		H2ToMySQLConverter.syntaxConverter();
		H2ToMySQLConverter.trimInsert();
		H2ToMySQLConverter.tablenameToLowercase();
		
	}

	
}
