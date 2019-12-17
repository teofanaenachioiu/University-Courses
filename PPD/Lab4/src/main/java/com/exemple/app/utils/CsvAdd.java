package com.exemple.app.utils;

import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CsvAdd {
  public static void addData(String filename, List<String[]> rowsContent){
    File file = new File(filename);

    try {
      FileWriter outputfile = new FileWriter(file);

      CSVWriter writer = new CSVWriter(outputfile);

      writer.writeAll(rowsContent);

      writer.close();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}