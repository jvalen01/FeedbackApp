package dat250.feedApp.model;
import dat250.feedApp.model.Vote;
public class Payload {
    public String username;
    public Vote vote;

    public String getUsername() {
        return username;
    }
    public Vote getVote() {
        return vote;
    }
}
