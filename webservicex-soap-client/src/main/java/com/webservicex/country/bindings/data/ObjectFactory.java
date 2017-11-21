
package com.webservicex.country.bindings.data;

import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public NewDataSet createNewDataSet() {
        return new NewDataSet();
    }

    public Table createTable() {
        return new Table();
    }

}
