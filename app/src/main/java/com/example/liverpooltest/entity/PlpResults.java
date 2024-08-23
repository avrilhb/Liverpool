package com.example.liverpooltest.entity;

import java.util.ArrayList;

public class PlpResults {
    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public ArrayList<Record> records;
}
