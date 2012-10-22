package PhaseA2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 18/09/12
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
public class MapEditor {

    JFrame frame;
    MapPanel mapPanel;

    public MapEditor() {

        mapPanel = new MapPanel(800, 600);
        mapPanel.addKeyListener(new MapPanelKeyAdapter(mapPanel));

        frame = new JFrame("Map Editor");

        // Add the stuff
        frame.add(mapPanel);

        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MapEditor();
    }
}

class MapPanel extends JComponent {
    // height and width of the view
    int width, height;
    // location of the view
    int x = 0, y = 0;

    Map map;
    MapLayerData mapLayerData;

    public MapPanel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < width; i += Constants.CELL_SIZE) {
            g.drawLine(i, 0, i, height);
        }
        for (int j = 0; j < height; j += Constants.CELL_SIZE) {
            g.drawLine(0, j, width, j);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(width, height));
    }

    public void shiftUp() {
        y--;
    }

    public void shiftDown() {
        y++;
    }

    public void shiftLeft() {
        x--;
    }

    public void shiftRight() {
        x++;
    }
}

// Contains the layered data for the map
class Map {
    // the int refers to the layer
    int[][] data;

    /**
     * Constructor
     *
     * @param width  width in cells
     * @param height height in cells
     */
    public Map(int width, int height) {
        data = new int[width][height];
    }

    /**
     * Generates a buffered image from the combination of a Map and a MapLayerData
     *
     * @param map
     * @param mapLayerData
     * @return
     */
    public static BufferedImage GenerateMap(Map map, MapLayerData mapLayerData) {
        return null;
    }
}

class MapLayerData {
    // Indexed layers for the map
    HashMap<Integer, Image> layers;

    public void addLayer(int value, Image image) {
        assert (value <= 255 * 255 * 255) : "Value too high";
        layers.put(value, image);
    }

    public static MapLayerData GenerateMapLayerData(String fileName) {
        MapLayerData data = new MapLayerData();

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(fileName));

            document.getDocumentElement().normalize();

            NodeList listOfTiles = document.getElementsByTagName("Tile");
            for (int tileIndex = 0; tileIndex < listOfTiles.getLength(); tileIndex++) {
                Node tile = listOfTiles.item(tileIndex);

                // Process tile
                if (tile.getNodeType() == Node.ELEMENT_NODE) {
                    Element tileElement = (Element) tile;

                    int red = Integer.parseInt(tileElement.getAttribute("red"));
                    int green = Integer.parseInt(tileElement.getAttribute("green"));
                    int blue = Integer.parseInt(tileElement.getAttribute("blue"));
                    String source = tileElement.getAttribute("source");

                    // Load the buffered image
                    ImageIcon imgIcon = new ImageIcon("/images/" + source);
                    Image image = imgIcon.getImage();

                    data.addLayer(((red * 255 * 255) + (green * 255) + (blue)),image);
                }
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("Couldn't find file: " + fileName);
        } catch (SAXException se) {
            System.out.println("SAXException");
        } catch (IOException ioe) {
            System.out.println("IOException");
        }

        return data;
    }
}

class MapPanelKeyAdapter extends KeyAdapter {

    MapPanel map;

    MapPanelKeyAdapter(MapPanel map) {
        this.map = map;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case KeyEvent.VK_LEFT:
                map.shiftLeft();
                break;
            case KeyEvent.VK_UP:
                map.shiftUp();
                break;
            case KeyEvent.VK_RIGHT:
                map.shiftRight();
                break;
            case KeyEvent.VK_DOWN:
                break;
        }
    }
}




