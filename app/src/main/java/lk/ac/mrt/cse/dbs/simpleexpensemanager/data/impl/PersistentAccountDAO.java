package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;



import android.my_db.Cursor;
import android.my_db.sqlite.SQLiteDatabase;
import android.my_db.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class PersistentAccountDAO implements AccountDAO {
        private SQLiteDatabase my_db;

        public PersistentAccountDAO(SQLiteDatabase db){
                this.my_db = db;
            }
    @Override
    public List<String> getAccountNumbersList() {
                Cursor results = my_db.rawQuery("SELECT Account_no FROM Account",null);
                List<String> accounts = new ArrayList<String>();

        if(resultSet.moveToFirst()) {
            do {
                accounts.add(results.getString(results.getColumnIndex("Account_no")));

            } while (results.moveToNext());
        }

                        return accounts;
    }

    @Override
    public List<Account> getAccList() {
                Cursor results = my_db.rawQuery("SELECT * FROM Account",null);
                List<Account> accounts = new ArrayList<Account>();

                if(resultSet.moveToFirst()) {
                        do {
                                Account account = new Account(results.getString(results.getColumnIndex("Account_no")),
                                                resultSet.getString(resultSet.getColumnIndex("Branch")),
                                                resultSet.getString(resultSet.getColumnIndex("Name")),
                                                resultSet.getDouble(resultSet.getColumnIndex("Initial_deposit")));
                                                accounts.add(account);
                            } while (results.moveToNext());
                    }

                        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

                Cursor results = my_db.rawQuery("SELECT * FROM Account WHERE Account_no = " + accountNo,null);
                Account account = null;
        if(results.moveToFirst()) {
                        do {
                                account = new Account(results.getString(resultSet.getColumnIndex("Account_no")),
                                        results.getString(resultSet.getColumnIndex("Branch")),
                                        results.getString(resultSet.getColumnIndex("Name")),
                                        results.getDouble(resultSet.getColumnIndex("Initial_deposit")));
                           } while (resultSet.moveToNext());
        }

                        return account;
    }

    @Override
    public void addAccount(Account account) {
                String sql = "INSERT INTO Account (Account_no,Branch,Name,Initial_deposit) VALUES (?,?,?,?)";
                SQLiteStatement statement = my_db.compileStatement(sql);

                statement.bindString(1, account.getAccountNo());
                statement.bindString(2, account.getBrachName());
                statement.bindString(3, account.getAccountHolderName());
                statement.bindDouble(4, account.getBalance());

                statement.executeInsert();


    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
                String sql = "DELETE FROM Account WHERE Account_no = ?";
                SQLiteStatement statement = my_db.compileStatement(sql);

                statement.bindString(1,accountNo);

                statement.executeUpdateDelete();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
                String sql = "UPDATE Account SET Initial_deposit = Initial_deposit + ?";
                SQLiteStatement statement = my_db.compileStatement(sql);
                if(expenseType == ExpenseType.EXPENSE){
                        statement.bindDouble(1,-amount);
                    }else{
                        statement.bindDouble(1,amount);
                    }

                statement.executeUpdateDelete();
    }
}