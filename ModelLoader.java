/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spacetrader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import javafx.scene.shape.TriangleMesh;

/**
 *
 * @author Administrator
 */
public class ModelLoader {
    InputStream data; 
    TriangleMesh mesh;
    
    public ModelLoader() {
        mesh = new TriangleMesh();
    }
    
    public ModelLoader(String fileName) {
        data = getClass().getResourceAsStream(fileName);
        mesh = new TriangleMesh();
    }
    
//    public ModelLoader(String url) {
//        try {
//            file = new File(new URI(url));
//        } catch (URISyntaxException e) {
//            System.out.println(e.getMessage());
//        }
//
//        mesh = new TriangleMesh();
//    }
//    
//    public void setFile(String url) {
//        try {
//            file = new File(new URI(url));
//        } catch (URISyntaxException e) {
//            System.out.println(e.getMessage());
//        }
//    }
    
    public boolean load(boolean smooth) {
        if (data == null) {
            System.out.println("File has not been set.");
            return false;
        }
        
        boolean texIncluded = false;
        boolean normsIncluded = false;
        HashMap<Integer, int[]> smoothingGroups = new HashMap<>();
        Scanner scanner;
        try {
            scanner = new Scanner(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        scanner.useDelimiter("/|\\s+");
        int line = 0;
        while (scanner.hasNext()) {
            line++;
            String token = scanner.next();
            if (token.equals("v")) {
                float[] point = new float[3];
                for (int i = 0; i < point.length; i++) {
                    if (scanner.hasNextDouble()) {
                        point[i] = (float) scanner.nextDouble();
                    } else {
                        System.out.println("Error while parsing at line: " + line);
                        return false;
                    }
                }
                mesh.getPoints().addAll(point, 0, point.length);
                scanner.nextLine();
            } else if (token.equals("vt")) {
                if (!texIncluded) {
                    texIncluded = true;
                }
                float[] point = new float[2];
                for (int i = 0; i < point.length; i++) {
                    if (scanner.hasNextDouble()) {
                        point[i] = (float) scanner.nextDouble();
                    } else {
                        System.out.println("Error while parsing at line: " + line);
                        return false;
                    }
                }
                mesh.getTexCoords().addAll(point, 0, point.length);
                scanner.nextLine();
            } else if (token.equals("vn")) {
                if (!normsIncluded) {
                    normsIncluded = true;
                }
                scanner.nextLine();
            } else if (token.equals("f")) {
                int[] face = new int[6];
                for (int i = 0; i < face.length; i += 2) {
                    if (scanner.hasNextInt()) {
                        face[i] = scanner.nextInt() - 1;
                    } else {
                        System.out.println("Error while parsing at line: " + line);
                        return false;
                    }
                    
                    if (texIncluded) {
                        if (scanner.hasNextInt()) {
                            face[i + 1] = scanner.nextInt() - 1;
                        } else {
                            System.out.println("Error while parsing at line: " + line);
                            return false;
                        }
                    } else {
                        face[i + 1] = 0;
                    }

                    if (normsIncluded) {
                        scanner.next();
                    }
                }
                
                if (!smooth) {
                    boolean groupAssigned = false;
                    for (int i = 0; i < 32 && !groupAssigned; i++) {
                        if (smoothingGroups.get(i) != null) {
                            int[] group = smoothingGroups.get(i);
                            boolean failed = false;
                            for (int j = 0; j < face.length && !failed; j += 2) {
                                for (int k = 0; k < group.length && !failed; k++) {
                                    if (group[k] == face[j]) {
                                        failed = true;
                                    }
                                }
                            }
                            if (!failed) {
                                mesh.getFaceSmoothingGroups().addAll(i);
                                groupAssigned = true;
                            }
                        } else {
                            mesh.getFaceSmoothingGroups().addAll(i);
                            groupAssigned = true;
                        }
                    }
                    if (!groupAssigned) {
                        mesh.getFaceSmoothingGroups().addAll(0);
                    }
                }
                
                mesh.getFaces().addAll(face, 0, face.length);
                scanner.nextLine();
            } else {
                scanner.nextLine();
            }
        }
        if (!texIncluded) {
            mesh.getTexCoords().addAll(0f, 0f);
        }
        scanner.close();
        return true;
    }
    
    public TriangleMesh getMesh() {
        return mesh;
    }
}
