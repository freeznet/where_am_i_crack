package mtrec.wherami.lbs.decrypt;

import android.util.Log;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rui on 15/1/22.
 */
public class PAll extends ArrayList implements ISerializable{


    @Override
    public void deserial(DataInput paramDataInput) throws IOException {
        int i = paramDataInput.readInt();
        for (int j = 0; j < i; j++)
        {
            Float x = paramDataInput.readFloat();
            Float y = paramDataInput.readFloat();
            Log.d("P_ALL", "\tx: " + x + ", y: " + y);
            int loop = paramDataInput.readInt();
            for (int jj = 0; jj < loop; jj++){
                short s = paramDataInput.readShort();
                Float ss = paramDataInput.readFloat();
                Log.d("P_ALL", "\t\ts: " + s + ", ss: " + ss);
            }
        }
    }
}
