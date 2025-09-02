package src.javaproject.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.classes.SerialWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public interface SerializationUtilities {
    Logger logger = LoggerFactory.getLogger(SerializationUtilities.class);
    File log = new File("logs/dbLog.ser");

    static void serialize(SerialWriter<String, SerializeMarker> sw) {
        if (!log.exists() || log.length() == 0) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(log))) {
                oos.writeObject(sw);
            } catch (IOException _) {
                logger.error("Problem with writing serialized file");
            }
        } else {
            try (FileOutputStream fos = new FileOutputStream(log, true);
            ObjectOutputStream oos = new ObjectOutputStream(fos) {
                @Override
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            }) {
                oos.writeObject(sw);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Problem with writing serialized data");
            }
        }
    }

    static List<SerialWriter<String, SerializeMarker>> deserialize() {
        List<SerialWriter<String, SerializeMarker>> out = new ArrayList<>();

        if (!log.exists() || log.length() == 0) {
            return out;
        }

        try (FileInputStream fis = new FileInputStream(log);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            boolean hasMoreData = true;

            while (hasMoreData) {
                try {
                    Object temp = ois.readObject();
                    if (temp instanceof SerialWriter<?,?>) {
                        SerialWriter<String, SerializeMarker> written = (SerialWriter<String, SerializeMarker>) temp;
                        out.add(written);
                    }
                } catch (EOFException _) {
                    hasMoreData = false;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("Problem reading serialized data");
        }

        return out;
    }
}
