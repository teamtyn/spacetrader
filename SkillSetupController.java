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
 *
 * @author Clayton
 * @version 1.0
 */
public class SkillSetupController implements Initializable, ControlledScreen {

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

    private final Map<String, Integer> plusButtonMap = new HashMap<>(5);
    private final Map<String, Integer> minusButtonMap = new HashMap<>(5);

    public static Player player;
    private Label[] labelArray;
    private Label[] pointLabelArray;
    private Button[] minusButtonArray;
    private Button[] plusButtonArray;
    private ProgressBar[] barsArray;
    private Integer[] skillPointArray;
    private final int avgValue;
    private final int barMax;
    private final int maxPts;
    private int totalPts;
    private int len;
    private ScreensController parentController;
    private List<Skill> skillList;

    /**
     * Sets up SkillSetupController by creating the player and establishing the default values and limitations.
     */
    public SkillSetupController() {
        player = new Player();
        avgValue = 10;
        barMax = 30;
        maxPts = 75;
        totalPts = 50;
    }

    /**
     * Initializes the controller and creates the button arrays.
     * 
     * @param url the url for this controller.
     * @param rb the resource bundle for this controller.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        plusButtonArray = new Button[]{plus0, plus1, plus2, plus3, plus4};
        minusButtonArray = new Button[]{minus0, minus1, minus2, minus3, minus4};
        pointLabelArray = new Label[]{points0, points1, points2, points3, points4};
        barsArray = new ProgressBar[]{bar0, bar1, bar2, bar3, bar4};
        labelArray = new Label[]{skill0, skill1, skill2, skill3, skill4};
        // DO NOT MOVE LEN DECLARATION
        len = labelArray.length;
        skillList = createSkillList();
        setUp();
    }

    /**
     * Initializes the controller.
     */
    @Override
    public void lazyInitialize() {
    }

    /**
     * Synchronizes Player's skills with the GUI for skill selection
     */ 
    private void updatePlayerSkills() {
        for (int i = 0; i < len; i++) {
            player.getSkills().get(skillList.get(i).getType()).setValue(skillPointArray[i]);
        }
    }

    /**
     * Sets the parent controller for this screen.
     * 
     * @param parentController the parent controller for this screen.
     */
    @Override
    public void setScreenParent(ScreensController parentController) {
        this.parentController = parentController;
    }

    /**
     * Initializes the players' skills and the GUI arrays.
     */
    private void setUp() {
        setUpControls();
    }

    /**
     * Functions when any plus button is pressed.
     * 
     * @param index the index of the skill being added to.
     */
    private void addToSkill(int index) {
        int skillPoints = skillPointArray[index];
        if ((skillPoints < barMax) && (totalPts < maxPts)) {
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
    private void subtractFromSkill(int index) {
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
        totalBar.setProgress((float) (maxPts - totalPts) / maxPts);
        totalLabel.setText(Integer.toString(maxPts - totalPts));
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
     * Initializes labelArray, skillPointArray, plusButtonMap, minusButtonMap, barsArray, totalBar,
     * totalLabel, and pointLabelArray
     */
    private void setUpControls() {
        for (int i = 0; i < len; i++) {
            labelArray[i].setText(skillList.get(i).getType());
        }
        skillPointArray = new Integer[len];
        for (int i = 0; i < len; i++) {
            skillPointArray[i] = avgValue;
        }
        for (int i = 0; i < len; i++) {
            plusButtonMap.put(plusButtonArray[i].getId(), i);
            minusButtonMap.put(minusButtonArray[i].getId(), i);
            updateProgressBar(i, avgValue);
            updatePointLabel(i, avgValue);
        }
    }

    /**
     *  Helper method which updates a selected point label.
     * 
     * @param index the index of the pointLabel being updated.
     * @param points the points for that skill label.
     */
    private void updatePointLabel(int index, int points) {
        pointLabelArray[index].setText(Integer.toString(points));
    }

    /**
     * Helper method which updates a selected progress.
     * 
     * @param index the progress bar to be changed.
     * @param points the points in that bar.
     */
    private void updateProgressBar(int index, int points) {
        barsArray[index].setProgress((float) points / barMax);
        updateTotalDisplays();
    }

    // All button handlers from here on out
    // No idea how to javadoc these, because FXML - DAVID
    @FXML
    private void cancelButtonAction(ActionEvent event) {
        parentController.setScreen("Menu");
    }

    @FXML
    private void resetButtonAction(ActionEvent event) {
        nameField.setText("");
        setUpControls();
    }

    @FXML
    private void doneButtonAction(ActionEvent event) {
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
        System.out.println(closingMessage.toString());
        GameModel.setPlayer(player);
        GameModel.generateSystems();

        parentController.setScreen("UniverseMap");
    }

    @FXML
    private void plusButtonAction(ActionEvent event) {
        Node n = (Node) event.getSource();
        addToSkill(plusButtonMap.get(n.getId()));
    }

    @FXML
    private void minusButtonAction(ActionEvent event) {
        Node n = (Node) event.getSource();
        subtractFromSkill(minusButtonMap.get(n.getId()));
    }

}
