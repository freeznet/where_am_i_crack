package mtrec.wherami.lbs.decrypt;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by rui on 15/1/22.
 */
public final class Deserializer {
    public static <T extends ISerializable> T deserializer(byte[] bytes, T clz) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        clz.deserial(dataInputStream);
        dataInputStream.close();
        inputStream.close();
        return clz;
    }
}
