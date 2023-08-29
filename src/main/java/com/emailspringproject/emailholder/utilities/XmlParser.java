package com.emailspringproject.emailholder.utilities;

import org.springframework.stereotype.Component;

import javax.xml.bind.*;
import java.io.*;

@Component
public class XmlParser {

    public <T> T fromFile(File file, Class<T> object) throws JAXBException, FileNotFoundException {
        final JAXBContext context = JAXBContext.newInstance(object);
        final Unmarshaller unmarshaller = context.createUnmarshaller();

        final FileReader fileReader = new FileReader(file);

        return (T) unmarshaller.unmarshal(fileReader);
    }


}
