package cs3500.pa05.view;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa05.model.json.BujoJson;
import cs3500.pa05.model.json.JsonUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents a Bullet Journal File Writer
 */
public class BujoFileWriter implements Writer {
  BujoJson bujo;

  /**
   * Constructor for a BujoFileWriter
   *
   * @param bujo Bullet JSON
   */
  public BujoFileWriter(BujoJson bujo) {
    this.bujo = bujo;
  }

  /**
   * Write to the file at the given destination
   *
   * @param destination destination to make file at
   * @throws IOException if fails
   */
  @Override
  public void write(String destination) throws IOException {
    if (!destination.equals("")) {
      File dest = new File(destination);
      FileWriter fw = new FileWriter(dest);
      //fw.write(bujo.toString());
      JsonNode serializedBujo = this.serializeBujo(this.bujo);
      String serializedBujoString = serializedBujo.toString();
      fw.write(serializedBujoString);
      fw.close();
    }
  }


  /**
   * helper to serialize a BujoJson
   *
   * @param bujo bujo to serialize
   * @return serialized jsonnode
   */
  private JsonNode serializeBujo(BujoJson bujo) {
    JsonNode serializedBujo = JsonUtils.serializeRecord(bujo);
    return serializedBujo;
  }
}
