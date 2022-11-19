package bridge;

public class GameController {

    private final InputView inputView;
    private final OutputView outputView;
    private BridgeGame bridgeGame;

    public GameController() {
        inputView = new InputView();
        outputView = new OutputView();
    }

    public void executeGame() {
        outputView.printOpening();
        bridgeGame = new BridgeGame(makeBridge());
        crossToOtherSide();
    }

    private Bridge makeBridge() {
        outputView.printBrideSizeOpening();
        int bridgeSize = 0;
        try {
            bridgeSize = inputView.readBridgeSize();
        } catch (IllegalArgumentException exception) {
            outputView.printErrorMessage(exception.getMessage());
            makeBridge();
        }
        outputView.printEmptyLine();
        return new Bridge(bridgeSize);
    }

    private void crossToOtherSide() {
        while (!bridgeGame.playerHasCrossed()) {
            outputView.printUserChoiceOpening();
            try {
                String choice = inputView.readMoving();
                bridgeGame.move(choice);
                outputView.printMap(bridgeGame.matchResults(), bridgeGame.getPlayersMove());
                continueOrQuitIfFailed(bridgeGame.lastMoveMatches());
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private void continueOrQuitIfFailed(boolean success) {
        if (!success) {
            outputView.printGameContinueOpening();
            try {
                String cmd = inputView.readGameCommand();
                // decideNextStep(cmd);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
                continueOrQuitIfFailed(success);
            }


        }


    }
}
