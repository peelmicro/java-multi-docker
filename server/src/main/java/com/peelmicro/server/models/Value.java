package com.peelmicro.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "values")
public class Value {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private int id; 
  
  private int number;

  public Value() { }

  public Value(int number) {
		this.number = number; 
	}

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }
}