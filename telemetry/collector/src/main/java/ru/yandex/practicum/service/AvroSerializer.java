package ru.yandex.practicum.service;

import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class AvroSerializer<T extends SpecificRecord> {
    public byte[] serialize(T avroRecord) throws IOException {
        DatumWriter<T> writer = new SpecificDatumWriter<>(avroRecord.getSchema());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
        writer.write(avroRecord, encoder);
        encoder.flush();
        outputStream.close();
        return outputStream.toByteArray();
    }
}