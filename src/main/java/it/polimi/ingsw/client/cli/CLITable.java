package it.polimi.ingsw.client.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class CLITable is used to represent some game elements throguh a table
 */
public class CLITable {
    private static final String HORIZONTAL_SEP = "-";
    private String verticalSep;
    private String joinSep;
    private String[] headers;
    private List<String[]> rows = new ArrayList<>();
    private boolean rightAlign;

    /**
     * Constructor generating a new instance of the class
     */
    public CLITable() {
        setShowVerticalLines(false);
    }

    /**
     * Method showVerticalLines chooses to show vertical lines in the table based on the boolean input
     * @param showVerticalLines boolean choice
     */
    public void setShowVerticalLines(boolean showVerticalLines) {
        verticalSep = showVerticalLines ? "|" : "";
        joinSep = showVerticalLines ? "+" : " ";
    }

    /**
     * Method setHeaders set the headers of the table
     * @param headers string of headers
     */
    public void setHeaders(String... headers) {
        this.headers = headers;
    }

    /**
     * Method addRow adds rows to the table
     * @param cells string of cells
     */
    public void addRow(String... cells) {
        rows.add(cells);
    }

    /**
     * Method print prints the entire table once it is set up
     */
    public void print() {
        int[] maxWidths = headers != null ?
                Arrays.stream(headers).mapToInt(String::length).toArray() : null;

        for (String[] cells : rows) {
            if (maxWidths == null) {
                maxWidths = new int[cells.length];
            }
            if (cells.length != maxWidths.length) {
                throw new IllegalArgumentException("Number of row-cells and headers should be consistent");
            }
            for (int i = 0; i < cells.length; i++) {
                maxWidths[i] = Math.max(maxWidths[i], cells[i].length());
            }
        }

        if (headers != null) {
            printLine(maxWidths);
            printRow(headers, maxWidths);
            printLine(maxWidths);
        }
        for (String[] cells : rows) {
            printRow(cells, maxWidths);
        }
        if (headers != null && maxWidths != null) {
            printLine(maxWidths);
        }
    }

    /**
     * Method printLine prints a line of the table
     * @param columnWidths column length
     */
    private void printLine(int[] columnWidths) {
        for (int i = 0; i < columnWidths.length; i++) {
            String line = String.join("", Collections.nCopies(columnWidths[i] +
                    verticalSep.length() + 1, HORIZONTAL_SEP));
            System.out.print(joinSep + line + (i == columnWidths.length - 1 ? joinSep : ""));
        }
        System.out.println();
    }

    /**
     * Method printRow prints the rows of the table
     * @param cells cells
     * @param maxWidths max widths
     */
    private void printRow(String[] cells, int[] maxWidths) {
        for (int i = 0; i < cells.length; i++) {
            String s = cells[i];
            String verStrTemp = i == cells.length - 1 ? verticalSep : "";
            if (rightAlign) {
                System.out.printf("%s %" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            } else {
                System.out.printf("%s %-" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            }
        }
        System.out.println();
    }
}