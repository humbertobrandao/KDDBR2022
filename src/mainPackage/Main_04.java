package mainPackage;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import utils.Direction;
import utils.Direction_v_04;
import utils.IOFile;
import utils.ImageFunctions;

/**
 *
 * @author humberto
 */
public class Main_04 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String version = "04";

        String[] sets = {"train", "test"};

        for (String set : sets) {
            
            System.out.println("Starting script Main_" + version + " for the " + set + " set...");

            String outputFileName = "aux_" + version + "_" + set + ".csv";

            HashMap<String, ArrayList<Direction_v_04>> results = new HashMap<>();

            int xs[] = {20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
            int ys[] = {20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
            
            int w = 20;
            int h = 20;

            //String folderStr = "/home/humberto/Dropbox/tmp/2022_KDDBR/input/" + set + "/" + set + "/";
            String folderStr = "./input/" + set + "/" + set + "/";

            File folder = new File(folderStr);
            File[] allFiles = folder.listFiles();
            HashSet<File> files = new HashSet<>();
            files.addAll(Arrays.asList(allFiles));

            //for (File file : allFiles) {
            files.parallelStream().forEach(file -> {
                String fileName = file.getName();
                //results for this file...
                ArrayList<Direction_v_04> directions = new ArrayList<>();
                System.out.println("Starting " + fileName + "...");

                String path = folderStr + fileName;

                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File(path));

                    int[][][] originalLeft = ImageFunctions.getOriginalImageFromTheLeft(img);
                    int[][][] originalRight = ImageFunctions.getOriginalImageFromTheRight(img);

                    for (int x : xs) {
                        for (int y : ys) {
                            int[][][] subLeft = ImageFunctions.getSubImage(originalLeft, x, y, w, h);
                            int[][][] subRight = null;

                            int[] vetLeft = ImageFunctions.linearisation(subLeft);
                            int[] vetRight = null;

                            double bestDistance = Double.MAX_VALUE;
                            //System.out.println("Distance: " + bestDistance);

                            int bestDeltaW = -1;
                            int bestDeltaH = -1;

                            for (int i = (x - (w / 2)); i <= (x + (w / 2)); i++) {
                                for (int j = (y - (h / 2)); j <= (y + (h / 2)); j++) {
                                    subRight = ImageFunctions.getSubImage(originalRight, i, j, w, h);
                                    vetRight = ImageFunctions.linearisation(subRight);
                                    double currentDistance = ImageFunctions.distance(vetLeft, vetRight);
                                    if (currentDistance < bestDistance) {
                                        bestDistance = currentDistance;
                                        bestDeltaW = i;
                                        bestDeltaH = j;
//                                    System.out.println("BEST = d(" + (x - bestDeltaW) + "," + (y - bestDeltaH) + ") = " + bestDistance);
                                    }
                                }
                            }

//                        System.out.println();
                            Direction_v_04 vector = new Direction_v_04((x - bestDeltaW), (y - bestDeltaH), bestDistance);
                            directions.add(vector);
//                        System.out.println(x + "," + y);
//                        System.out.println(vector.getDeltaX());
//                        System.out.println(vector.getDeltaY());
//                        System.out.println("----");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                results.put(fileName, directions);

            });
            //}

            StringBuilder output = new StringBuilder();

            //begin - creating head of the output...
            String tmpKey = allFiles[0].getName();
            ArrayList<Direction_v_04> tmp = results.get(tmpKey);
            output.append("\"file\",");
            int size = tmp.size()*3; //x and y and distance (W and H and distance)
            for (int i = 0; i < size; i++) {
                String type = "";
                if( i%3 == 0 ){
                    type = "W";
                } else if( i%3 == 1 ){
                    type = "H";
                } else if( i%3 == 2 ){
                    type = "D";
                }
                if (i < size) {
                    output.append("\"f").append(version).append("_").append(type).append("_").append(i).append("\",");
                } else {
                    output.append("\"f").append(version).append("_").append(type).append("_").append(i).append("\"");
                }
            }
            output.append("\n");
            //end - creating head of the output...

            for (String key : results.keySet()) {
                ArrayList<Direction_v_04> directions = results.get(key);
                System.out.print(key + ",");
                output.append(key).append(",");
                for (int i = 0; i < directions.size(); i++) {
                    Direction_v_04 direction = directions.get(i);
                    System.out.print(direction.getDeltaX() + "," + direction.getDeltaY());
                    output.append(direction.getDeltaX()).append(",")
                            .append(direction.getDeltaY())
                            .append(",").append(direction.getDistance());
                    if (i < (directions.size() - 1)) {
                        System.out.print(",");
                        output.append(",");
                    }
                }
                System.out.println();
                output.append("\n");
            }

            //salving the results in a file...
            try {
                IOFile.getInstance().putString(outputFileName, output.toString());
            } catch (IOException ex) {
                Logger.getLogger(Main_04.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println(tmp.size());
            System.out.println("\n------------------------------------------------------------------");
        }
    }

}
