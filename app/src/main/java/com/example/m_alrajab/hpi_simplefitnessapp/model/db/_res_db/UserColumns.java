package com.example.m_alrajab.hpi_simplefitnessapp.model.db._res_db;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;


/**
 * Created by m_alrajab on 12/27/16.
 *
 * @author Moaath Alrajab
 *
 * This is a contract class for the database. It implements the baseColums class
 * better compatibility with Android OS
 *
 */

public class UserColumns {
  @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement
  public static final String _ID = "_id";

  @DataType(DataType.Type.TEXT) @NotNull
  public static final String FName = "fisrt_name";
  @DataType(DataType.Type.TEXT) @NotNull
  public static final String SNAME = "family_name";

  @DataType(DataType.Type.TEXT) @NotNull
  public static final String USERNAME = "username";
  @DataType(DataType.Type.TEXT) @NotNull
  public static final String PASSWORD = "password";


  @DataType(DataType.Type.TEXT) @NotNull
  public static final String EMAIL = "email";
  @DataType(DataType.Type.INTEGER) @NotNull
  public static final String PHONE = "phone";


  @DataType(DataType.Type.TEXT) @NotNull
  /*
   * Assumptions: The user address is stored as a string sequence not as a table
   * -* this needs a redesign so that the address is pulled from an autocomplete
   * db
   */
  public static final String ADDRESS = "address";



}
