package cs3500.pa05.javafx.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa05.model.json.JsonUtils;
import cs3500.pa05.model.json.TimeJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the JsonUtils class
 */
public class JsonUtilsTest {
  private JsonUtils jsonUtils;

  @BeforeEach
  void setup() {
    jsonUtils = new JsonUtils();
  }


  @Test
  void serializeRecord() {
    TimeJson testRecord = new TimeJson(5, 10);

    JsonNode jsonNode = JsonUtils.serializeRecord(testRecord);

    assertTrue(jsonNode.isObject());
    assertEquals(5, jsonNode.get("hour").asInt());
    assertEquals(10, jsonNode.get("minute").asInt());
  }

}


