package src.domain.account;

import java.security.SecureRandom;
import java.util.stream.Collectors;

public class PIN {
    private final String pin;
    public PIN() {
        this.pin = generatePIN();
    }

    // function to generate PIN: 4 digits number
    public String generatePIN() {
        SecureRandom secureRandom = new SecureRandom();
        String pin = secureRandom.ints(4, 0, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
        return pin;
    }

    @Override
    public String toString() {
        return this.pin;
    }
}
