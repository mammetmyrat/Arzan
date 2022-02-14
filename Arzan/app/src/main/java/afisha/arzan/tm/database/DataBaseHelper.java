package afisha.arzan.tm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import afisha.arzan.tm.R;
import afisha.arzan.tm.fragment.FirstFragment;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Credit_cards.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_cards";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name_surname";
    private static final String COLUMN_CARD_NUMBER = "card_number";
    private static final String COLUMN_END_TIME = "card_end_time";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_CARD_NUMBER + " INTEGER, " +
                        COLUMN_END_TIME + " STRING);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addCard(String name, Integer cardNumber, String endTime, FragmentActivity activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_CARD_NUMBER,cardNumber);
        cv.put(COLUMN_END_TIME,endTime);

        long result = db.insert(TABLE_NAME,null,cv);
        if (result== -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            activity.getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in_left,R.anim.out_right)
                    .replace(R.id.frame,new FirstFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
