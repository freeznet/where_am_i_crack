package mtrec.wherami.lbs.decrypt;

import android.util.Log;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by rui on 15/1/22.
 * Hashmap <Short, c>
 */
public class Bin implements ISerializable {
    @Override
    public void deserial(DataInput paramDataInput) throws IOException {
        int i = paramDataInput.readInt();
        for (int j = 0; j < i; j++) {
            short s = paramDataInput.readShort();
            int loop = paramDataInput.readInt();
            for (int jj = 0; jj < loop; jj++){
                short a = paramDataInput.readShort();
                short b = paramDataInput.readShort();
                Log.d("Bin", "s: " + s + "\t\ta: " + a + ", b: " + b);
            }
        }
    }
}
