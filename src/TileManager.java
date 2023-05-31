import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class TileManager {
    GamePanel panel;
    Tile[] tile;
    int mapTileNum[][];
    public TileManager(GamePanel panel){
        this.panel=panel;
        tile=new Tile[10];
        mapTileNum=new int[panel.maxWorldCol][panel.maxWorldRow];
        getTileImage();
        loadMap();
    }
    public void getTileImage() {
            tile[0]=new Tile();
            tile[0].image= new ImageIcon("imgs/grass.png").getImage();
            tile[1]=new Tile();
            tile[1].image= new ImageIcon("imgs/brickWall.jpg").getImage();
            tile[1].collide=true;
            tile[2]=new Tile();
            tile[2].image= new ImageIcon("imgs/water.jpg").getImage();
            tile[2].collide=true;
            tile[3]=new Tile();
            tile[3].image= new ImageIcon("imgs/lava.jpg").getImage();
            tile[3].collide=true;
            tile[4]=new Tile();
            tile[4].image= new ImageIcon("imgs/tree.png").getImage();
            tile[4].collide=true;
            tile[5]=new Tile();
            tile[5].image= new ImageIcon("imgs/sand.jpg").getImage();

    }
    public void loadMap(){
        try {
            File fe = new File("mapLevelOneLarge.txt");
            FileInputStream fis = new FileInputStream(fe);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            int col=0, row=0;
            while (col<panel.maxWorldCol && row<panel.maxWorldRow) {
                String line = reader.readLine();
                while (col<panel.maxWorldCol){
                    String[] textures = line.split(" ");
                    int texture = Integer.parseInt(textures[col]);
                    mapTileNum[col][row]=texture;
                    col++;
                }
                if (col == panel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }reader.close();

        }catch (Exception e){

        }
    }
    public void draw(Graphics2D g2){
        int colWorld = 0, rowWorld = 0;
        while (colWorld<panel.maxWorldCol && rowWorld<panel.maxWorldRow) {
            int tileNum=mapTileNum[colWorld][rowWorld];
            int worldX = colWorld*panel.tankSize;
            int worldY = rowWorld*panel.tankSize;
            int screenX = worldX-panel.playerX+panel.screenX;
            int screenY = worldY-panel.playerY+panel.screenY;

            if(worldX+panel.tankSize>panel.playerX-panel.screenX &&
                    worldX-panel.tankSize<panel.playerX+panel.screenX &&
                    worldY+panel.tankSize>panel.playerY-panel.screenY &&
                    worldY-panel.tankSize<panel.playerY+panel.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, panel.tankSize, panel.tankSize, null);
            }
            colWorld++;
            if (colWorld == panel.maxWorldCol) {
                colWorld = 0;
                rowWorld++;
            }
        }
    }

}
