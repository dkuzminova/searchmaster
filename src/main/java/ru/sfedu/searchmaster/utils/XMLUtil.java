package ru.sfedu.searchmaster.utils;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "list")
public class XMLUtil<T> {
    @ElementList(inline = true, required = false)
    private List<T> list;

    public XMLUtil() {
    }

    public XMLUtil(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

