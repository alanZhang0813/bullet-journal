package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record for a BujoJson object
 *
 * @param week Week of the current bullet journal
 * @param quotes Quotes/Notes contained in the bullet journal
 */
public record BujoJson(
    @JsonProperty("week") WeekAdapter week,
    @JsonProperty("quotes") String quotes,
    @JsonProperty("password") String password) {
}
