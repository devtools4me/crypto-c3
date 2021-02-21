package me.devtools4.csv;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.Writer;
import java.util.List;

public class CsvOps {

  public static <T> void csv(List<T> list, Class<T> type, String[] columns, Writer writer) {
    var mappingStrategy = new ColumnPositionMappingStrategy<>();
    mappingStrategy.setType(type);
    mappingStrategy.setColumnMapping(columns);
    var beanToCsv = new StatefulBeanToCsvBuilder<>(writer)
        .withMappingStrategy(mappingStrategy)
        .build();
    try {
      beanToCsv.write(list);
    } catch (CsvException e) {
      throw new IllegalArgumentException(e);
    }
  }
}