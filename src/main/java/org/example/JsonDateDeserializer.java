package org.example;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonDateDeserializer extends JsonDeserializer<Date> {

	private static final DateFormat DATE_FROMAT = new SimpleDateFormat("dd.MM.yyyy");

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
		DateFormat dateFormat = (DateFormat) DATE_FROMAT.clone();		
		try {
			return dateFormat.parse(jsonParser.getText());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;			
		}
	}
		
}