package dyehard.GameScreens;

import Engine.BaseCode;

// TODO: Auto-generated Javadoc
/**
 * The Class CreditScreen.
 */
public class CreditScreen {
    
    /** The credit front. */
    private final CreditFront creditFront;
    
    /** The credit. */
    private final Credit credit;
    
    /** The credit back. */
    private final CreditBack creditBack;

    /**
     * Instantiates a new credit screen.
     */
    public CreditScreen() {
    	
    	creditFront = new CreditFront();
    	credit = new Credit();
    	creditBack = new CreditBack();
    }

    /**
     * Show screen.
     *
     * @param show the showscreen
     */
    public void showScreen(boolean show) {
        if (show) {
            credit.setCurFrame(0);
            BaseCode.resources.moveToFrontOfDrawSet(creditBack);
            BaseCode.resources.moveToFrontOfDrawSet(credit);
            BaseCode.resources.moveToFrontOfDrawSet(creditFront);
        }
        creditFront.visible = show;
        credit.visible = show;
        creditBack.visible = show;
    }

    /**
     * Checks if is shown.
     *
     * @return true, if is shown
     */
    public boolean isShown() {
        return credit.visible;
    }
}
