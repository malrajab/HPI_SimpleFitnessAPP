package com.example.m_alrajab.hpi_simplefitnessapp.model.db._res_db;

import android.net.Uri;
import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by m_alrajab on 12/27/16.
 *
 * Content provider is implemented to enable future extensions
 *
 */

@ContentProvider(authority = UsersProvider.AUTHORITY, database = UsersDatabase.class)
public class UsersProvider {
  public static final String AUTHORITY = "com.example.m_alrajab.hpi_simplefitnessapp.model.db._res_db.UsersProvider";

  static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  interface Path{
    String USERS = "users";

  }

  private static Uri buildUri(String... paths){
    Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
    for (String path:paths){
      builder.appendPath(path);
    }
    return builder.build();
  }

  @TableEndpoint(table = UsersDatabase.USERS) public static class Users{
    @ContentUri(
        path = Path.USERS,
        type = "vnd.android.cursor.dir/users"
    )
    public static final Uri CONTENT_URI = buildUri(Path.USERS);

    @InexactContentUri(
        name = "USER_ID",
        path = Path.USERS + "/*",
        type = "vnd.android.cursor.item/user",
        whereColumn = UserColumns.USERNAME,
        pathSegment = 1
    )
    public static Uri withSymbol(String symbol){
      return buildUri(Path.USERS, symbol);
    }
  }


}
