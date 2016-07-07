package org.example;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonDateSerializer extends JsonSerializer<Date> {
	private static final DateFormat DATE_FROMAT = new SimpleDateFormat("dd.MM.yyyy");

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		DateFormat dateFormat = (DateFormat) DATE_FROMAT.clone();
		String formattedDate = dateFormat.format(date);
		jsonGenerator.writeString(formattedDate);
	}
}