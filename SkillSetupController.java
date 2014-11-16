package spacetrader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import spacetrader.player.Player;
import spacetrader.player.Skill;

/**
 * FXML Controller class that allows the player to set their starting skills.
 * @author Team TYN
 * @version 1.0
 */
public class SkillSetupController implements Initializable, ControlledScreen {
    /**
     * Acts as the initial value of the progress bars among other things.
     */
    private static final int AVG_VALUE = 10;
    /**
     * The maximum that a progress bar can fill (in other words, it is the
     *     maximum that can be allotted to one skill.
     */
    private static final int BAR_MAX = 30;
    /**
     * The maximum number of total points the player can allot.
     */
    private static final int MAX_POINTS = 75;
    /**
     * The current total points the player has alloted.
     */
    private static final int TOTAL_POINTS = 50;

    @FXML
    private Button minus0;
    @FXML
    private Button minus1;
    @FXML
    private Button minus2;
    @FXML
    private Button minus3;
    @FXML
    private Button minus4;
    @FXML
    private Button plus0;
    @FXML
    private Button plus1;
    @FXML
    private Button plus2;
    @FXML
    private Button plus3;
    @FXML
    private Button plus4;
    @FXML
    private Label points0;
    @FXML
    private Label points1;
    @FXML
    private Label points2;
    @FXML
    private Label points3;
    @FXML
    private Label points4;
    @FXML
    private ProgressBar bar0;
    @FXML
    private ProgressBar bar1;
    @FXML
    private ProgressBar bar2;
    @FXML
    private ProgressBar bar3;
    @FXML
    private ProgressBar bar4;
    @FXML
    private Label skill0;
    @FXML
    private Label skill1;
    @FXML
    private Label skill2;
    @FXML
    private Label skill3;
    @FXML
    private Label skill4;
    @FXML
    private Label totalLabel;
    @FXML
    private ProgressBar totalBar;
    @FXML
    private TextField nameField;

    /**
     * Holds all of the plus buttons.
     */
    private final Map<String, Integer> plusButtonMap = new HashMap<>(5);
    /**
     * Holds all of the minus buttons.
     */
    private final Map<String, Integer> minusButtonMap = new HashMap<>(5);

    /**
     * The player who is acquiring skills.
     */
    private static Player player;
    /**
     * Holds all of the skill labels.
     */
    private Label[] labelArray;
    /**
     * Holds all of the skill points labels.
     */
    private Label[] pointLabelArray;
    /**
     * Holds all of the actual minus buttons.
     */
    private Button[] minusButtonArray;
    /**
     * Holds all of the actual plus buttons.
     */
    private Button[] plusButtonArray;
    /**
     * Holds all of the skill progress bars.
     */
    private ProgressBar[] barsArray;
    /**
     * Holds all of the skill points.
     */
    private Integer[] skillPointArray;
    /**
     * The total skill points of all the player's skills currently.
     */
    private int totalPts;
    /**
     * The number of skills, used as a convenience and shorthand.
     */
    private int len;
    /**
     * The parent controller of this controller.
     */
    private ScreensController parentController;
    /**
     * The list of the player's skills.
     */
    private List<Skill> skillList;

    /**
     * Sets up SkillSetupController by creating the player and establishing
     *     the default values and limitations.
     */
    public SkillSetupController() {
        player = new Player();
        totalPts = TOTAL_POINTS;
    }

    @Override
    public final void initialize(final URL url, final ResourceBundle rBundle) {
        plusButtonArray = new Button[]{plus0, plus1, plus2, plus3, plus4};
        minusButtonArray = new Button[]{minus0, minus1, minus2, minus3, minus4};
        pointLabelArray = new Label[]{points0, points1, points2,
            points3, points4};
        barsArray = new ProgressBar[]{bar0, bar1, bar2, bar3, bar4};
        labelArray = new Label[]{skill0, skill1, skill2, skill3, skill4};
        // DO NOT MOVE LEN DECLARATION
        len = labelArray.length;
        skillList = createSkillList();
        setUp();
    }

    @Override
    public void lazyInitialize() {
        // TODO
    }

    /**
     * Synchronizes Player's skills with the GUI for skill selection.
     */
    private void updatePlayerSkills() {
        for (int i = 0; i < len; i++) {
            player.getSkills().get(skillList.get(i).getType()).setValue(
                    skillPointArray[i]);
        }
    }

    @Override
    public final void setScreenParent(
            final ScreensController aParentController) {
        parentController = aParentController;
    }

    /**
     * Initializes the player's skills and the GUI arrays.
     */
    private void setUp() {
        setUpControls();
    }

    /**
     * Functions when any plus button is pressed.
     *
     * @param index the index of the skill being added to.
     */
    private void addToSkill(final int index) {
        int skillPoints = skillPointArray[index];
        if ((skillPoints < BAR_MAX) && (totalPts < MAX_POINTS)) {
            skillPoints++;
            totalPts++;
        }
        skillPointArray[index] = skillPoints;
        updateProgressBar(index, skillPoints);
        updatePointLabel(index, skillPoints);
    }

    /**
     * Functions when any minus button is pressed.
     *
     * @param index the index of the skill being subtracted from.
     */
    private void subtractFromSkill(final int index) {
        int skillPoints = skillPointArray[index];
        if (skillPoints > 0) {
            skillPoints--;
            totalPts--;
        }
        skillPointArray[index] = skillPoints;
        updateProgressBar(index, skillPoints);
        updatePointLabel(index, skillPoints);
    }

    /**
     * Keeps totalBar and totalLabel up to date.
     */
    private void updateTotalDisplays() {
        totalBar.setProgress((float) (MAX_POINTS - totalPts) / MAX_POINTS);
        totalLabel.setText(Integer.toString(MAX_POINTS - totalPts));
    }

    /**
     * Initializes the skills as an ArrayList.
     *
     * @return the newly created List<Skill>.
     */
    private List<Skill> createSkillList() {

        List<Skill> skillsForNow = new ArrayList(player.getSkills().values());
        return skillsForNow;
    }

    /**
     * Initializes labelArray, skillPointArray, plusButtonMap, minusButtonMap,
     *     barsArray, totalBar, totalLabel, and pointLabelArray.
     */
    private void setUpControls() {
        for (int i = 0; i < len; i++) {
            labelArray[i].setText(skillList.get(i).getType());
        }
        skillPointArray = new Integer[len];
        for (int i = 0; i < len; i++) {
            skillPointArray[i] = AVG_VALUE;
        }
        for (int i = 0; i < len; i++) {
            plusButtonMap.put(plusButtonArray[i].getId(), i);
            minusButtonMap.put(minusButtonArray[i].getId(), i);
            updateProgressBar(i, AVG_VALUE);
            updatePointLabel(i, AVG_VALUE);
        }
    }

    /**
     *  Helper method which updates a selected point label.
     *
     * @param index the index of the pointLabel being updated.
     * @param points the points for that skill label.
     */
    private void updatePointLabel(final int index, final int points) {
        pointLabelArray[index].setText(Integer.toString(points));
    }

    /**
     * Helper method which updates a selected progress.
     *
     * @param index the progress bar to be changed.
     * @param points the points in that bar.
     */
    private void updateProgressBar(final int index, final int points) {
        barsArray[index].setProgress((float) points / BAR_MAX);
        updateTotalDisplays();
    }

    /**
     * Defines the response to the cancel button.
     * @param event The cancel button being pressed
     */
    @FXML
    private void cancelButtonAction(final ActionEvent event) {
        parentController.setScreen("Menu");
    }

    /**
     * Defines the response to the reset button.
     * @param event The reset button being pressed
     */
    @FXML
    private void resetButtonAction(final ActionEvent event) {
        nameField.setText("");
        setUpControls();
    }

    /**
     * Defines the response to the done button.
     * @param event The done button being pressed
     */
    @FXML
    private void doneButtonAction(final ActionEvent event) {
        String name = nameField.getText();
        if (name != null && !name.trim().equals("")) {
            player.setName(nameField.getText());
        }
        updatePlayerSkills();
        StringBuilder closingMessage = new StringBuilder();
        closingMessage.append("~~~PLAYER INFORMATION~~~\nNAME: ")
                .append(player.getName()).append("\nSKILLS: \n");

        Map<String, Skill> skillMap = player.getSkills();
        Set<String> names = skillMap.keySet();
        for (String skillName : names) {
            Skill skill = skillMap.get(skillName);
            closingMessage.append(skill.getType()).append(" - ")
                    .append(skill.getValue()).append("\n");
        }
        //System.out.println(closingMessage.toString());
        GameModel.setPlayer(player);
        GameModel.generateSystems();

        parentController.setScreen("Encounter");
    }

    /**
     * Defines the response to a plus button.
     * @param event A plus button being pressed
     */
    @FXML
    private void plusButtonAction(final ActionEvent event) {
        Node n = (Node) event.getSource();
        addToSkill(plusButtonMap.get(n.getId()));
    }

    /**
     * Defines the response a minus button.
     * @param event A minus button being pressed
     */
    @FXML
    private void minusButtonAction(final ActionEvent event) {
        Node n = (Node) event.getSource();
        subtractFromSkill(minusButtonMap.get(n.getId()));
    }
}
