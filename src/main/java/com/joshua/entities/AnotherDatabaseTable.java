package com.joshua.entities;


import com.joshua.annotations.Column;
import com.joshua.annotations.Entity;
import com.joshua.annotations.Id;
import com.joshua.annotations.Table;

@Entity
@Table(name = "Another_Database_Table")
public class AnotherDatabaseTable {
    @Id
    private int id;

    private String description;

    private String anotherField;
}
