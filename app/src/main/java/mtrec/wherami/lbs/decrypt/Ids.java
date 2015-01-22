package mtrec.wherami.lbs.decrypt;

import android.util.Log;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by rui on 15/1/22.
 */
public final class Ids extends HashMap<Long, Short> implements ISerializable {

    private static Ids instance = null;
    private static short length = Short.MIN_VALUE;

    public static Ids getInstance(){
        if (instance == null){
            instance = new Ids();
        }
        return instance;
    }


    @Override
    public void deserial(DataInput dataInput) throws IOException {
        length = dataInput.readShort();
        clear();
        int i = dataInput.readInt();

        Log.d("Ids", "length: " + length + ", loop: " + i);

        for(int j=0;j<i;j++){
            Long key = dataInput.readLong();
            Short val = dataInput.readShort();

            Log.d("Ids", "key: " + key + "(" + Long.toHexString(key) + "), val: " + val);
            put(key, val);
        }
    }
}
