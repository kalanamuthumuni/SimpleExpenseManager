package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;


/**
 * Created by mkalana on 11/20/16.
 */




public class PersistentExpenseManager extends ExpenseManager {
        private Context ctx;
        public PersistentExpenseManager(Context ctx){
                this.ctx = ctx;
                setup();
            }

    @Override
    public void setup(){
               SQLiteDatabase mydatabase = ctx.openOrCreateDatabase("140583B", ctx.MODE_PRIVATE, null);




            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Account("Account_no VARCHAR primary key, Name VARCHAR,Branch VARCHAR,Initial_deposit Real);");
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Trans_Log("Transaction_ID INTEGER primary key, Amount Real, Tr_Date DATE,Type VARCHAR,Account_no VARCHAR,FOREIGN KEY(Account_no) REFERENCES Account(Account_no));");

            AccountDAO accountDAO = new PersistentAccountDAO(mydatabase);
            setAccountsDAO(accountDAO);
            setTransactionsDAO(new PersistentTransactionDAO(mydatabase));


    }

    }
