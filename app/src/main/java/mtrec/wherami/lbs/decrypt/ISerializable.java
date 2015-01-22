package mtrec.wherami.lbs.decrypt;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by rui on 15/1/22.
 */
public interface ISerializable {
    public abstract void deserial(DataInput paramDataInput)
            throws IOException;
}
