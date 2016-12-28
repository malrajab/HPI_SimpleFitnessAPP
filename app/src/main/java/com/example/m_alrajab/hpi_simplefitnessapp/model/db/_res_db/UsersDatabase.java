package com.example.m_alrajab.hpi_simplefitnessapp.model.db._res_db;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by m_alrajab on 12/27/16.
 *
 * @author Moaath Alrajab
 *
 * This is to create the users table. Table name sets to be users.
 * the annoutation below sets the version to 1
 *
 */

@Database(version = UsersDatabase.VERSION)
public class UsersDatabase {
  // prevent accidental creation
  private UsersDatabase(){}
  public static final int VERSION = 1;
  @Table(UserColumns.class) public static final String USERS = "users";//this is the name of the users table
}
