package mainPackage;

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
import utils.Direction_v_05;
import utils.IOFile;
import utils.ImageFunctions;

/**
 *
 * @author humberto
 */
public class Main_05 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String version = "05";

        String[] sets = {"train", "test"};

        for (String set : sets) {
            
            System.out.println("Starting script Main_" + version + " for the " + set + " set...");

            String outputFileName = "aux_" + version + "_" + set + ".csv";

            HashMap<String, ArrayList<Direction_v_05>> results = new HashMap<>();

// original
            int xs[] = {50,60,70};
            int ys[] = {50,60,70};
            
            int w = 50;
            int h = 50;

            String folderStr  = "./input/" + set + "/" + set + "/";
            
            File folder = new File(folderStr);
            File[] allFiles = folder.listFiles();
            HashSet<File> files = new HashSet<>();
            files.addAll(Arrays.asList(allFiles));

            //for (File file : allFiles) {
            files.parallelStream().forEach(file -> {
                String fileName = file.getName();
                //results for this file...
                ArrayList<Direction_v_05> directions = new ArrayList<>();
                System.out.println("Starting " + fileName + "...");

                String path = folderStr + fileName;
                
                int[][][] originalLeft = null;
                int[][][] originalRight = null;

                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File(path));

                    originalLeft = ImageFunctions.getOriginalImageFromTheLeft(img, originalLeft);
                    originalRight = ImageFunctions.getOriginalImageFromTheRight(img, originalRight);

                    int[] vetLeft = null;
                    int[] vetRight = null;
                    
                    int[][][] subLeft = null;
                    int[][][] subRight = null;
                    
                    for (int x : xs) {
                        for (int y : ys) {
                            subLeft = ImageFunctions.getSubImage(originalLeft, x, y, w, h, subLeft);
                            
                            vetLeft = ImageFunctions.linearisation(subLeft, vetLeft);
                            
                            double bestDistance = Double.MAX_VALUE;
                            //System.out.println("Distance: " + bestDistance);

                            int bestDeltaW = -1;
                            int bestDeltaH = -1;

                            for (int i = (x - (w / 2)); i <= (x + (w / 2)); i++) {
                                for (int j = (y - (h / 2)); j <= (y + (h / 2)); j++) {
                                    subRight = ImageFunctions.getSubImage(originalRight, i, j, w, h, subRight);
                                    vetRight = ImageFunctions.linearisation(subRight, vetRight);
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
                            Direction_v_05 vector = new Direction_v_05((x - bestDeltaW), (y - bestDeltaH), bestDistance);
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
            ArrayList<Direction_v_05> tmp = results.get(tmpKey);
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
                if (i < (size-1)) {
                    output.append("\"f").append(version).append("_").append(type).append("_").append(i).append("\",");
                } else {
                    output.append("\"f").append(version).append("_").append(type).append("_").append(i).append("\"");
                }
            }
            output.append("\n");
            //end - creating head of the output...

            for (String key : results.keySet()) {
                ArrayList<Direction_v_05> directions = results.get(key);
                System.out.print(key + ",");
                output.append(key).append(",");
                for (int i = 0; i < directions.size(); i++) {
                    Direction_v_05 direction = directions.get(i);
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
                Logger.getLogger(Main_05.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println(tmp.size());
            System.out.println("\n------------------------------------------------------------------");
        }
    }

}