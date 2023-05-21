import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;

public class Tp1 {
    public static void main(String[] args) {
        LinkedList<Integer> boxsData = boxsData(args[0]);
        LinkedList<Double> buldi = buildings(args[0]);
        LinkedList<Double> boxs = numberOfBoxsPerbuilding(args[0]);
        LinkedList<Double> truckpos = truckPosition(boxs, buldi);
        LinkedList<Double> dist = distances(buldi, truckpos);
        LinkedList<LinkedList<Double>> ordoredLists = ordoredLists(dist, boxs, buldi);
        LinkedList<LinkedList<Double>> updateOrdoredLists = updateOrdoredLists(ordoredLists, boxsData);
        writer(args[1], updateOrdoredLists, truckpos);
    }

    // Return a LinkedList with the number of box to transport and the truck max capacity.
    public static LinkedList<Integer> boxsData (String argument1){
        LinkedList<Integer> boxsDatas = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(argument1))){

            String line = reader.readLine();

            String[] element = line.split(" ", 2);
            int nbOfBoxToTransport = Integer.parseInt(element[0]);
            int truckCapacity = Integer.parseInt(element[1]);
            reader.close();
            boxsDatas.add(nbOfBoxToTransport);
            boxsDatas.add(truckCapacity);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return boxsDatas;
    }

    // Return a LinkedList with the nummber of boxes in each buildings.
    public static LinkedList<Double> numberOfBoxsPerbuilding(String argument1){
        LinkedList<Double> nBboiteDansBatiment = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(argument1))) {
            String line;
            int startLine = 2;
            int lineNumber = 1;
            while ((line = bufferedReader.readLine()) != null) {
                if (lineNumber >= startLine){
                    String[] elements = line.split("\\([^\\)]*\\)");
                    for (String element : elements) {
                        element = element.trim();
                        if (!element.isEmpty()) {
                            nBboiteDansBatiment.add(Double.parseDouble(element));
                        }
                    }
                }
                lineNumber++;
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return nBboiteDansBatiment;
    }

    // Return a linkedList that contains the buildings positions.
    public static LinkedList<Double> buildings(String argument1) {
        LinkedList<Double> buildingsCoordinates = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(argument1))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int start = 0;
                int end;
                /* This part of the code removes coordinates couple by couple. It starts to copy the datas from the first "(" symbol and stop at the
                    first ")" symbol. Then, it starts again after the the last ")" and look for the next "(" symbol to repeat the same process. */ 
                while ((start = line.indexOf("(", start)) != -1) {
                    end = line.indexOf(")", start);
                    String values = line.substring(start + 1, end);
                    String[] splitValues = values.split(",");
                    double latitude = Double.parseDouble(splitValues[0].trim());
                    double longitude = Double.parseDouble(splitValues[1].trim());
                    buildingsCoordinates.add(latitude);
                    buildingsCoordinates.add(longitude);
                    start = end + 1;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buildingsCoordinates;
    }

    // This method take the number of boxs in each building and the buildings positions and return the position of the truck.
    public static LinkedList<Double> truckPosition(LinkedList<Double> boxs, LinkedList<Double> buildings) {
        LinkedList<Double> truckPositions = new LinkedList<>();

        // This list contains the latitudes of the buildings that contains the most boxes.
        LinkedList<Double> latitudes = new LinkedList<>();

        // This list contains the longitudes of the buildings that contains the most boxes.
        LinkedList<Double> longitudes = new LinkedList<>();

        // Find the indexs that contains the most boxes from the boxes list.
        Double max = Collections.max(boxs); 
        LinkedList<Integer> indexs = new LinkedList<>();
        for (int i = 0; i < boxs.size(); ++i) {
            if (boxs.get(i) == max ) {
                indexs.add(i);
            }
        }

        // Updtate latitude and lngitude list.
        for (int i = 0; i < indexs.size(); ++i ) {
            int index1 = 2*indexs.get(i);
            int index2 = index1 + 1;
            latitudes.add(buildings.get(index1));
            longitudes.add(buildings.get(index2));
        }

        // Update the truckPosition list.
        if (latitudes.size() == 1) {
            truckPositions.add(latitudes.getFirst());
            truckPositions.add(longitudes.getFirst());
        } else {
            double min = Collections.min(latitudes);
            int minIndex = latitudes.indexOf(min);

            truckPositions.add(min);
            truckPositions.add(longitudes.get(minIndex));
        }
        return truckPositions;
    }

    // This method calculates the haversine distance betweem two points.
    public static double haversine(double phi1, double phi2, double lambda1, double lambda2){
        double ray = 6371000;
        double sin1 = Math.sin(Math.toRadians((phi2 - phi1)/2));
        double sin2 = Math.sin(Math.toRadians((lambda2 - lambda1)/2));
        double cos1 = Math.cos(Math.toRadians(phi1));
        double cos2 = Math.cos(Math.toRadians(phi2));
        return 2*ray*Math.asin(Math.sqrt(sin1*sin1 + cos1*cos2*sin2*sin2));
    }

    // This method calculates the distance between the truck and the buildings and return a list that contains those distances.
    public static LinkedList<Double> distances(LinkedList<Double> buildings, LinkedList<Double> truckPositions) {
        LinkedList<Double> distances = new LinkedList<>();
        double phi1 = truckPositions.get(0);
        double lambda1 = truckPositions.get(1);
        int j = 1;
        for (int i = 0; i < buildings.size(); i+=2) {
            distances.add(haversine(phi1, buildings.get(i), lambda1, buildings.get(j)));
            j+=2;
        }
        return distances;
    }

    // This method takes the distances list, boxes list and buildings list, order them in ascending order and return the ordered lists.
    public static LinkedList<LinkedList<Double>> ordoredLists(LinkedList<Double> distances, LinkedList<Double> boxs, LinkedList<Double> buildings) {
        LinkedList<LinkedList<Double>> ordoredLists = new LinkedList<>();
        LinkedList<Double> orderedBuildings = new LinkedList<>();
        LinkedList<Double> ordoredBoxs = new LinkedList<>();
        LinkedList<Double> ordoredDistances = new LinkedList<>();

        // Find the smallest distance and its index and update the lists 
        for (int i = 0; i < distances.size(); ++i) {
            double smallestDistance = Collections.min(distances);
            ordoredDistances.add(smallestDistance);

            int smallesIndex = distances.indexOf(smallestDistance);
            ordoredBoxs.add(boxs.get(smallesIndex));


            int index = 2*smallesIndex;
            orderedBuildings.add(buildings.get(index));
            orderedBuildings.add(buildings.get(index + 1));

            distances.remove(smallesIndex);
        }

        ordoredLists.add(ordoredDistances);
        ordoredLists.add(ordoredBoxs);
        ordoredLists.add(orderedBuildings);
        return ordoredLists;

    }

    /* This method is the algorithm used to update the boxes in each buildings visited by the goods-lift(the goods-lift go to building with
        with the smallest distance.)*/ 
    public static LinkedList<LinkedList<Double>> updateOrdoredLists(LinkedList<LinkedList<Double>> ordoredLists, LinkedList<Integer> boxsData) {
        // Informations needed to keep track the progress.
        int boxTransported = 0;
        int boxToTransport = boxsData.get(0);
        int boxRemaining = boxToTransport;
        int truckMaxCapacity = boxsData.get(1);
        int truckSpaceRemaining = truckMaxCapacity;
        int i = 0;
        LinkedList<Double> orderedBoxs = ordoredLists.get(1);

        while (boxRemaining > 0) {
            // Verify if we reached the end of the list.
            if (i != orderedBoxs.size()) {
                double element = orderedBoxs.get(i);
                // Verify if the number of boxs in this building can fit in the truck.
                if (element < truckSpaceRemaining) {
                    double element2 = element + boxTransported;
                    if (boxTransported < truckMaxCapacity && element2 < boxToTransport) {
                        // update the informations.
                        boxTransported += element;
                        boxRemaining -= element;
                        truckSpaceRemaining -= element;
    
                        // Replace the element in the list.
                        orderedBoxs.remove(i);
                        orderedBoxs.add(i, 0.0);
                        i++;
                    } else if (element2 >= boxToTransport) {
                        // Update informations and end the algorithm.
                        element2 -= boxToTransport;
                        orderedBoxs.remove(i);
                        orderedBoxs.add(i, element2);
                        boxTransported = boxToTransport;
                        boxRemaining = 0;
                    } else {
                        // End the algorithm.
                        boxRemaining = 0;
                    }
                } else if (element < truckMaxCapacity) {
                    // Update informations and end the algorithm.
                    boxTransported += boxRemaining;
                    element -= boxRemaining;
                    orderedBoxs.remove(i);
                    orderedBoxs.add(i, element);
                    truckSpaceRemaining = 0;
                    boxRemaining = 0;
    
                } else {
                    // Update informations and end the algorithm.
                    element -= boxToTransport;
                    orderedBoxs.remove(i);
                    orderedBoxs.add(i, element);
                    boxTransported = boxToTransport;
                    boxRemaining = 0;
                }
            } else {
                // End the algorithm.
                boxRemaining = 0;
            }

        }

        return ordoredLists;

    }

    // Convert a list of Integer to a list of Double.
    public static LinkedList<Integer> listConverter(LinkedList<Double> list) {
        LinkedList<Integer> result = new LinkedList<>();
        for (int i = 0; i < list.size(); ++i) {
            result.add((int)Math.round(list.get(i))); 
        }
        return result;
    }

    // Round a double number passed as parameter to the number of digits passed as parameter as well.
    public static double round(double value, int nDigits) { 
        String pattern = "#.";
        for (int i = 0; i < nDigits; ++i) {
            pattern += "#";
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String roundedNum = decimalFormat.format(value);
        double result = Double.parseDouble(roundedNum);

        return result;
    } 

    // Round a list of Double with a certain number of digits to another number of digits.
    public static LinkedList<Double> roundList(LinkedList<Double> distances) {
        LinkedList<Double> result = new LinkedList<>();

        for (double distance : distances) {
            result.add(round(distance, 1));
        }

        return result;

    }

    // This method write the final result in the second document passed as parameter in the command line.
    public static void writer(String argument2, LinkedList<LinkedList<Double>> updateOrdoredLists, LinkedList<Double> truckPosition) {

        LinkedList<Double> distances = roundList(updateOrdoredLists.get(0));
        LinkedList<Integer> boxes = listConverter(updateOrdoredLists.get(1));
        LinkedList<Double> buildings = updateOrdoredLists.get(2);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(argument2))){
            String truckPositionString  = "Truck position:" + "\t" + "(" + truckPosition.getFirst() + "," + truckPosition.getLast() + ")";
            writer.append(truckPositionString);

            writer.newLine();

            int j = 1;
            int k = 0;
            for (int i = 0; i < distances.size(); i+=2) {
                String line = "";
                double dist = distances.get(k);
                if (dist == 0){
                    line += "Distance:0" + "\t\t\t\t" + "Number of boxes:" + boxes.get(k) + "\t\t\t" + "Postion:" + "(" + buildings.get(i) + "," + buildings.get(j) + ")";
                } else {
                    line += "Distance:" + dist + "\t\t\t" + "Number of boxes:" + boxes.get(k) + "\t\t\t" + "Postion:" + "(" + buildings.get(i) + "," + buildings.get(j) + ")";
                }
                writer.append(line);
                writer.newLine();
                j += 2;
                k++;
            }

            writer.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}