package com.joshua.entities;

import com.joshua.annotations.Column;
import com.joshua.annotations.Entity;
import com.joshua.annotations.Id;
import com.joshua.annotations.Table;

@Entity
@Table(name = "Database_Table")
public class DatabaseTable {
    @Id
    @Column(name = "entity_id")
    private Integer id;

    @Column(name = "entity_description")
    private String description;

    private String unnamed;
}
