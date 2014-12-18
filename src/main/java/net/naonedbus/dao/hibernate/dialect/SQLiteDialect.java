package net.naonedbus.dao.hibernate.dialect;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.JDBCException;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.AbstractAnsiTrimEmulationFunction;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.spi.SQLExceptionConverter;
import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
import org.hibernate.internal.util.JdbcExceptionHelper;
import org.hibernate.type.StandardBasicTypes;

public class SQLiteDialect extends Dialect {
  public SQLiteDialect() {
    this.registerColumnType(Types.BIT, "boolean");
    this.registerColumnType(Types.TINYINT, "tinyint");
    this.registerColumnType(Types.SMALLINT, "smallint");
    this.registerColumnType(Types.INTEGER, "integer");
    this.registerColumnType(Types.BIGINT, "bigint");
    this.registerColumnType(Types.FLOAT, "float");
    this.registerColumnType(Types.REAL, "real");
    this.registerColumnType(Types.DOUBLE, "double");
    this.registerColumnType(Types.NUMERIC, "numeric($p, $s)");
    this.registerColumnType(Types.DECIMAL, "decimal");
    this.registerColumnType(Types.CHAR, "char");
    this.registerColumnType(Types.VARCHAR, "varchar($l)");
    this.registerColumnType(Types.LONGVARCHAR, "longvarchar");
    this.registerColumnType(Types.DATE, "date");
    this.registerColumnType(Types.TIME, "time");
    this.registerColumnType(Types.TIMESTAMP, "datetime");
    this.registerColumnType(Types.BINARY, "blob");
    this.registerColumnType(Types.VARBINARY, "blob");
    this.registerColumnType(Types.LONGVARBINARY, "blob");
    this.registerColumnType(Types.BLOB, "blob");
    this.registerColumnType(Types.CLOB, "clob");
    this.registerColumnType(Types.BOOLEAN, "boolean");

    this.registerFunction( "concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "", "||", "") );
    this.registerFunction( "mod", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "?1 % ?2" ) );
    this.registerFunction( "quote", new StandardSQLFunction("quote", StandardBasicTypes.STRING) );
    this.registerFunction( "random", new NoArgSQLFunction("random", StandardBasicTypes.INTEGER) );
    this.registerFunction( "round", new StandardSQLFunction("round") );
    this.registerFunction( "substr", new StandardSQLFunction("substr", StandardBasicTypes.STRING) );
    this.registerFunction( "trim", new AbstractAnsiTrimEmulationFunction() {
        @Override
        protected SQLFunction resolveBothSpaceTrimFunction() {
          return new SQLFunctionTemplate(StandardBasicTypes.STRING, "trim(?1)");
        }

        @Override
        protected SQLFunction resolveBothSpaceTrimFromFunction() {
          return new SQLFunctionTemplate(StandardBasicTypes.STRING, "trim(?2)");
        }

        @Override
        protected SQLFunction resolveLeadingSpaceTrimFunction() {
          return new SQLFunctionTemplate(StandardBasicTypes.STRING, "ltrim(?1)");
        }

        @Override
        protected SQLFunction resolveTrailingSpaceTrimFunction() {
          return new SQLFunctionTemplate(StandardBasicTypes.STRING, "rtrim(?1)");
        }

        @Override
        protected SQLFunction resolveBothTrimFunction() {
          return new SQLFunctionTemplate(StandardBasicTypes.STRING, "trim(?1, ?2)");
        }

        @Override
        protected SQLFunction resolveLeadingTrimFunction() {
          return new SQLFunctionTemplate(StandardBasicTypes.STRING, "ltrim(?1, ?2)");
        }

        @Override
        protected SQLFunction resolveTrailingTrimFunction() {
          return new SQLFunctionTemplate(StandardBasicTypes.STRING, "rtrim(?1, ?2)");
        }
    } );
  }

  @Override
  public boolean supportsIdentityColumns() {
    return true;
  }

  /*
  public boolean supportsInsertSelectIdentity() {
    return true; // As specified in NHibernate dialect
  }
  */

  @Override
  public boolean hasDataTypeInIdentityColumn() {
    return false; // As specified in NHibernate dialect
  }

  /*
  public String appendIdentitySelectToInsert(String insertString) {
    return new StringBuffer(insertString.length()+30). // As specified in NHibernate dialect
      append(insertString).
      append("; ").append(getIdentitySelectString()).
      toString();
  }
  */

  @Override
  public String getIdentityColumnString() {
    // return "integer primary key autoincrement";
    return "integer";
  }

  @Override
  public String getIdentitySelectString() {
    return "select last_insert_rowid()";
  }

  @Override
  public boolean supportsLimit() {
    return true;
  }

  @Override
  public boolean bindLimitParametersInReverseOrder() {
    return true;
  }

  @Override
  protected String getLimitString(final String query, final boolean hasOffset) {
    return query + (hasOffset ? " limit ? offset ?" : " limit ?");
  }

  @Override
  public boolean supportsTemporaryTables() {
    return true;
  }

  @Override
  public String getCreateTemporaryTableString() {
    return "create temporary table if not exists";
  }

  @Override
  public Boolean performTemporaryTableDDLInIsolation() {
    return Boolean.FALSE;
  }

  /*
  @Override
  public boolean dropTemporaryTableAfterUse() {
    return true; // temporary tables are only dropped when the connection is closed. If the connection is pooled...
  }
  */

  @Override
  public boolean supportsCurrentTimestampSelection() {
    return true;
  }

  @Override
  public boolean isCurrentTimestampSelectStringCallable() {
    return false;
  }

  @Override
  public String getCurrentTimestampSelectString() {
    return "select current_timestamp";
  }

  private static final int SQLITE_BUSY = 5;
  private static final int SQLITE_LOCKED = 6;
  private static final int SQLITE_IOERR = 10;
  private static final int SQLITE_CORRUPT = 11;
  private static final int SQLITE_NOTFOUND = 12;
  private static final int SQLITE_FULL = 13;
  private static final int SQLITE_CANTOPEN = 14;
  private static final int SQLITE_PROTOCOL = 15;
  private static final int SQLITE_TOOBIG = 18;
  private static final int SQLITE_CONSTRAINT = 19;
  private static final int SQLITE_MISMATCH = 20;
  private static final int SQLITE_NOTADB = 26;
  @Override
  public SQLExceptionConverter buildSQLExceptionConverter() {
    return new SQLExceptionConverter() {
      @Override
      public JDBCException convert(final SQLException sqlException, final String message, final String sql) {
                final int errorCode = JdbcExceptionHelper.extractErrorCode(sqlException);
        if (errorCode == SQLITE_CONSTRAINT) {
          final String constraintName = EXTRACTER.extractConstraintName(sqlException);
          return new ConstraintViolationException(message, sqlException, sql, constraintName);
        } else if (errorCode == SQLITE_TOOBIG || errorCode == SQLITE_MISMATCH) {
          return new DataException(message, sqlException, sql);
        } else if (errorCode == SQLITE_BUSY || errorCode == SQLITE_LOCKED) {
          return new LockAcquisitionException(message, sqlException, sql);
        } else if ((errorCode >= SQLITE_IOERR && errorCode <= SQLITE_PROTOCOL) || errorCode == SQLITE_NOTADB) {
          return new JDBCConnectionException(message, sqlException, sql);
        }
        return new GenericJDBCException(message, sqlException, sql);
      }
    };
  }

  public static final ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter() {
    @Override
    public String extractConstraintName(final SQLException sqle) {
      return this.extractUsingTemplate( "constraint ", " failed", sqle.getMessage() );
    }
  };

  @Override
  public boolean supportsUnionAll() {
    return true;
  }

  @Override
  public boolean hasAlterTable() {
    return false; // As specified in NHibernate dialect
  }

  @Override
  public boolean dropConstraints() {
    return false;
  }

  /*
  public String getAddColumnString() {
    return "add column";
  }
  */

  @Override
  public String getForUpdateString() {
    return "";
  }

  @Override
  public boolean supportsOuterJoinForUpdate() {
    return false;
  }

  @Override
  public String getDropForeignKeyString() {
    throw new UnsupportedOperationException("No drop foreign key syntax supported by SQLiteDialect");
  }

  @Override
  public String getAddForeignKeyConstraintString(final String constraintName,
      final String[] foreignKey, final String referencedTable, final String[] primaryKey,
      final boolean referencesPrimaryKey) {
    throw new UnsupportedOperationException("No add foreign key syntax supported by SQLiteDialect");
  }

  @Override
  public String getAddPrimaryKeyConstraintString(final String constraintName) {
    throw new UnsupportedOperationException("No add primary key syntax supported by SQLiteDialect");
  }

  @Override
  public boolean supportsIfExistsBeforeTableName() {
    return true;
  }

  /*
  public boolean supportsCascadeDelete() {
    return true;
  }
  */

  /* not case insensitive for unicode characters by default (ICU extension needed)
  public boolean supportsCaseInsensitiveLike() {
    return true;
  }
  */

  @Override
  public boolean supportsTupleDistinctCounts() {
    return false;
  }

  @Override
  public String getSelectGUIDString() {
    return "select hex(randomblob(16))";
  }
}