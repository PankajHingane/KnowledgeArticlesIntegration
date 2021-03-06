package com.translations.globallink.connect.sf.model.vendor.utility.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public class GloballinkContentXMLUtil {

	private static final Logger logger = Logger.getLogger(GloballinkContentXMLUtil.class);

	public InputStream getXMLInputStream(Content content) throws Exception {

		if (content == null)
			return null;

		byte[] bytes = getContentBytesForStream(content);

		String contentString = new String(bytes, "UTF-8");
		logger.trace(contentString);

		return new ByteArrayInputStream(bytes);
	}

	public Content getContentFromInputStream(InputStream inputStream) throws Exception {

		if (inputStream == null)
			return null;

		JAXBContext context = JAXBContext.newInstance(Content.class);

		Unmarshaller unMarshaller = context.createUnmarshaller();

		Content content = (Content) unMarshaller.unmarshal(inputStream);

		return content;
	}

	public byte[] getContentBytesForStream(Content content) throws Exception {
		
		JAXBContext context = JAXBContext.newInstance(Content.class);

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty("jaxb.encoding", "UTF-8");
		m.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
			@Override
			public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
				writer.write(ac, i, j); 
			}
		});

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		m.marshal(content, out);

		byte[] bytes = out.toByteArray();

		return bytes;
	}
	
}
