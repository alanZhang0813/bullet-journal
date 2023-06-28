package cs3500.pa05.view;

import java.io.IOException;

/**
 * Interface allowing for content to be written to a specific destination,
 * as a file or directory path.
 */
public interface Writer {
  /**
   * Writes to the given destination
   *
   * @param destination destination of the desired file
   * @throws IOException if unable to access file path
   */
  void write(String destination) throws IOException;
}
