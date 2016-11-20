
package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteStatement;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
        import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
        import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by mkalana on 11/20/16.
 */

    public class PersistentTransactionDAO implements TransactionDAO {
            private SQLiteDatabase my_db;
        
                    public PersistentTransactionDAO(SQLiteDatabase db){
                   this.my_db = db;
               }
         @Override
            public void logTransaction(Date Tra_Date, String acc_no, ExpenseType expType, double Amt) {
                    String sql = "INSERT INTO TransactionLog (Account_no,Type,Amount,Tr_Date) VALUES (?,?,?,?)";
                    SQLiteStatement statement = my_db.compileStatement(sql);
                    statement.bindString(1,acc_no);
                    statement.bindLong(2,(expType == ExpenseType.EXPENSE) ? 0 : 1);
                    statement.bindDouble(3,Amt);
                    statement.bindLong(4,Tra_Date.getTime());
                    statement.executeInsert();
               }

                    @Override
            public List<Transaction> getAllTransactionLogs() {
                        Cursor results = my_db.rawQuery("SELECT * FROM TransactionLog",null);
                        List<Transaction> transactions = new ArrayList<Transaction>();

                        if(results.moveToFirst()) {
                                         do{
                                                Transaction tr = new Transaction(new Date(results.getLong(results.getColumnIndex("Tr_Date"))),
                                                                results.getString(results.getColumnIndex("Account_no")),
                                                                (results.getInt(results.getColumnIndex("Type")) == 0) ? ExpenseType.EXPENSE : ExpenseType.INCOME,
                                                                results.getDouble(results.getColumnIndex("Amount")));
                                                transactions.add(tr);
                                            }while (results.moveToNext());
                        }
                    return transactions;
                }

            @Override
            public List<Transaction> getPaginatedTransactionLogs(int limit) {
                    Cursor results = my_db.rawQuery("SELECT * FROM TransactionLog LIMIT " + limit,null);
                    List<Transaction> transactions = new ArrayList<Transaction>();

                           if(results.moveToFirst()) {
                                           do {
                                                   Transaction tr = new Transaction(new Date(results.getLong(results.getColumnIndex("Tr_Date"))),
                                                                   results.getString(results.getColumnIndex("Account_no")),
                                                                   (results.getInt(results.getColumnIndex("Type")) == 0) ? ExpenseType.EXPENSE : ExpenseType.INCOME,
                                                                   results.getDouble(results.getColumnIndex("Amount")));
                                                   transactions.add(tr);
                                               } while (results.moveToNext());
                           }
                    return transactions;
                }
    }