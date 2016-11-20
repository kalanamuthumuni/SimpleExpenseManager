import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;

public class MainActivity extends AppCompatActivity {
    private ExpenseManager expManager;

    expManager = new PersistentExpenseManager(getApplicationContext());
}
  