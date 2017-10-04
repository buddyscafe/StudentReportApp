package com.studentreport.data;

import java.util.List;

public interface PersistenceManager<T> {

    List<T> getRecords();

    void createRecord(T t);

    void removeAll();

}
