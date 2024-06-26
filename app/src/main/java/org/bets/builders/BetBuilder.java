package org.bets.builders;

import org.bets.exceptions.*;
import org.bets.types.Bet;
import org.bets.types.BetResult;

public class BetBuilder {
    private Integer betNumber;
    private String bettorName;
    private Integer betAmount;
    private BetResult result;

    public BetBuilder betNumber(int betNumber) {
        this.betNumber = betNumber;
        return this;
    }

    public BetBuilder bettorName(String bettorName) throws BettorNameTooLongException {
        if (bettorName.length() > 15) {
            throw new BettorNameTooLongException("Bettor name was longer than 15! Got: %s with length %d"
                    .formatted(bettorName, bettorName.length()));
        }

        this.bettorName = bettorName;
        return this;
    }

    public BetBuilder betAmount(int betAmount) throws BetNotInRangeException {
        if (betAmount < 10 || betAmount > 100) {
            throw new BetNotInRangeException(
                    "Bet amount was supposed to be in range [10, 100], got %d".formatted(betAmount));
        }

        this.betAmount = betAmount;
        return this;
    }

    public BetBuilder betResult(char result) throws ResultFormatException {
        if (result == '1') {
            this.result = BetResult.ONE;
        } else if (result == '2') {
            this.result = BetResult.TWO;
        } else if (result == 'X' || result == 'x') {
            this.result = BetResult.EVEN;
        } else {
            throw new ResultFormatException("Result must be one of 1 | 2 | X, got %c!".formatted(result));
        }

        return this;
    }

    public BetBuilder betResult(BetResult result) {
        this.result = result;
        return this;
    }

    public Bet build() throws MissingBuilderFieldException {
        if (bettorName == null || betAmount == null || result == null) {
            var message = """
                        At least one of the fields were null! Status:
                        - bettorName: %s
                        - betAmount: %s
                        - result: %s
                    """.formatted(bettorName, betAmount.toString(), result.toString());
            throw new MissingBuilderFieldException(message);
        }

        return new Bet(betNumber, bettorName, betAmount, result);
    }
}
