package com.jr.component.castor;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.*;
import org.xml.sax.InputSource;

import java.io.*;

public class DefaultCastorComponent implements CastorComponent{

  @Override
  public <T> T read(InputStream mapper, InputStream xml, Class<T> clazz) throws MappingException, MarshalException, ValidationException {
    // Load Mapping
    Mapping mapping = new Mapping();
    mapping.loadMapping(new InputSource(mapper));

    // initialize and configure XMLContext
    XMLContext context = new XMLContext();
    context.addMapping(mapping);

    // Create a new Unmarshaller
    Unmarshaller unmarshaller = context.createUnmarshaller();
    unmarshaller.setClass(clazz);

    // Unmarshal the person object
    @SuppressWarnings("unchecked")
    T object = (T)unmarshaller.unmarshal(new InputSource(xml));
    
    return object;
  }

  @Override
  public void write(InputStream mapper, OutputStream xml, Object object) throws MappingException, MarshalException, ValidationException, IOException {
    // Load Mapping
    Mapping mapping = new Mapping();
    mapping.loadMapping(new InputSource(mapper));

    // initialize and configure XMLContext
    XMLContext context = new XMLContext();
    context.setProperty("org.exolab.castor.indent", true);
    context.addMapping(mapping);

    Writer writer = new OutputStreamWriter(xml, "UTF-8");
    // create a new Marshaller
    Marshaller marshaller = context.createMarshaller();
    marshaller.setWriter(writer);

    // marshal the person object
    marshaller.marshal(object);
  }


}
