package util.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class TextFileLineReader {


    public static List<String> readLines(String sourcefilePath, String regex, List<String> lst) throws IOException {
          FileInputStream  in = new FileInputStream(sourcefilePath);

          try {
              BufferedReader br = new BufferedReader(new InputStreamReader(in));
              try {
                  String line = null;
                  if (lst == null) {
                      lst = new ArrayList<String>(200);
                  }
                  Pattern p = Pattern.compile(regex);
                  while(( line = br.readLine()) != null) {
                      if (p.matcher(line).matches()) {
                          lst.add(line);
                      }
                  }
              } finally {
                  br.close();
              }
          } finally {
              in.close();
          }

          return lst;
    }
}
