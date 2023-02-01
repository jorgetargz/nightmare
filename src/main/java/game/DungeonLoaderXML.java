package game;

import game.demiurge.Demiurge;
import game.demiurge.DungeonConfiguration;
import game.object.ItemCreationErrorException;
import game.objectContainer.exceptions.ContainerFullException;
import game.objectContainer.exceptions.ContainerUnacceptedItemException;
import game.spell.SpellUnknowableException;
import game.util.ValueOverMaxException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;

public interface DungeonLoaderXML {

    void load(Demiurge demiurge, DungeonConfiguration dungeonConfiguration, File file) throws ParserConfigurationException, IOException, SAXException, ContainerUnacceptedItemException, XPathExpressionException, ValueOverMaxException, ContainerFullException, ItemCreationErrorException, SpellUnknowableException;

    void save(Demiurge demiurge, DungeonConfiguration dungeonConfiguration, File file) throws IOException;

}
