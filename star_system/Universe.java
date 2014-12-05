package spacetrader.star_system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import spacetrader.GameModel;
import static spacetrader.GameModel.SYSTEM_COUNT;
import static spacetrader.GameModel.SYSTEM_SPACING;
import static spacetrader.GameModel.SYSTEM_SPACING_FUZZINESS;
import static spacetrader.GameModel.UNIVERSE_HEIGHT;
import static spacetrader.GameModel.UNIVERSE_WIDTH;
import spacetrader.ui.Point;

/**
 * Container for all of the star systems the trader can travel to.
 */
public final class Universe implements Iterable<StarSystem>, Serializable {

    /**
     * All the systems that make up the universe.
     */
    private final List<StarSystem> systems;

    /**
     * @param newSystems the systems to include in the universe.
     */
    public Universe(final List<StarSystem> newSystems) {
        systems = newSystems;
    }

    @Override
    public Iterator<StarSystem> iterator() {
        return systems.iterator();
    }

    /**
     * Generate the systems of the Universe. The positions of each star system
     * is decided by each one choosing a random point on an x, y plane where
     * each point is a certain distance away from each other and the border of
     * the Universe
     *
     * @return A randomly generated Universe.
     */
    public static Universe create() {
        List<Point> positions = new ArrayList<>();
        for (int x = 0; x <= UNIVERSE_WIDTH; x += SYSTEM_SPACING) {
            for (int y = 0; y <= UNIVERSE_HEIGHT; y += SYSTEM_SPACING) {
                positions.add(new Point(
                        x + calculateFuzziness(), y + calculateFuzziness()));
            }
        }
        Collections.shuffle(positions, GameModel.getRandom());

        List<StarSystem> generatedSystems = new ArrayList<>();
        for (int i = 0; i < SYSTEM_COUNT; i++) {
            generatedSystems.add(new StarSystem(
                    StarSystemNames.getName(), positions.remove(0)));
        }

        return new Universe(generatedSystems);
    }

    /**
     * @return A random fuzzy number by which to vary spacing.
     */
    private static int calculateFuzziness() {
        return GameModel.getRandom().nextInt(SYSTEM_SPACING_FUZZINESS)
                - SYSTEM_SPACING_FUZZINESS / 2;
    }
}
