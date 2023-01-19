package game;

import game.demiurge.Demiurge;
import game.demiurge.DungeonConfiguration;

import java.io.File;

public interface DungeonLoaderXML {

    public void load(Demiurge demiurge, DungeonConfiguration dungeonConfiguration, File file);

}
