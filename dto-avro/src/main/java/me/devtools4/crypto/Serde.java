package me.devtools4.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class Serde {
  public static <T> byte[] serialize(T event, Schema schema) {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
      DatumWriter<T> dataFileWriter = new SpecificDatumWriter<>(schema);
      dataFileWriter.write(event, encoder);
      encoder.flush();
      return out.toByteArray();
    } catch (IOException ex) {
      throw new IllegalArgumentException(ex);
    }
  }

  public static <T> T deserialize(byte[] bytes, Schema schema) {
    try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
      BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(in, null);
      DatumReader<T> reader = new SpecificDatumReader<>(schema);
      return reader.read(null, decoder);
    } catch (IOException ex) {
      throw new IllegalArgumentException(ex);
    }
  }

}