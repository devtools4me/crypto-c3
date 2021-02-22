package me.devtools4.csv;

import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.avro.specific.SpecificRecord;
import org.apache.commons.lang3.tuple.Pair;
import org.json.CDL;
import org.json.JSONArray;

public class CsvOps {

  public static void csv(List list, Writer writer) {
    var beanToCsv = new StatefulBeanToCsvBuilder<>(writer)
        .build();
    try {
      beanToCsv.write(list);
    } catch (CsvException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static String csv(List<? extends SpecificRecord> list) {
    return list.stream()
        .findFirst()
        .map(e -> e.getSchema().getFields()
            .stream()
            .map(x -> Pair.of(x.pos(), x.name()))
            .collect(Collectors.toList()))
        .map(fields -> csv(list, fields))
        .orElse("");
  }

  public static String csv(List<? extends SpecificRecord> list, List<Pair<Integer, String>> fields) {
    var json = new JSONArray();
    list.stream()
        .map(x -> fields
            .stream()
            .map(p -> Pair.of(p.getValue(), String.valueOf(x.get(p.getKey()))))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue)))
        .forEach(json::put);
    return CDL.toString(json);
  }
}