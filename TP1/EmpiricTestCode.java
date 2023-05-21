public class EmpiricTestCode {
    public static void main(String[] args) {
        empiricTest(0);
    }

    public static void empiricTest(int inputsNumber) {
        LinkedList<Long> datas = new LinkedList<>();
        try {
            LinkedList<String> inputs = new LinkedList<>();
            for (int i = 100; i <= inputsNumber*50; i += 50) {
                String a = "" + i;
                inputs.add(a);
            }

            for (int i = 0; i < inputsNumber-1; ++i) {
            String inputsPaths = ".\\exemplaires_dev1\\";
            inputsPaths += inputs.get(i) + ".txt";
            LinkedList<Integer> boxsData = boxsData(inputsPaths);
            LinkedList<Double> buldi = buildings(inputsPaths);
            LinkedList<Double> boxs = numberOfBoxsPerbuilding(inputsPaths);
            LinkedList<Double> truckpos = truckPosition(boxs, buldi);
            LinkedList<Double> dist = distances(buldi, truckpos);
            LinkedList<LinkedList<Double>> ordoredLists = ordoredLists(dist, boxs, buldi);

            long startTime = System.currentTimeMillis();
            updateOrdoredLists(ordoredLists, boxsData);
            long endTime = System.currentTimeMillis();

            datas.add(endTime - startTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter("EmpiricTestData.txt"))) {
            for (long data : datas) {
                bWriter.append("" + data);
                bWriter.newLine();
            }
            bWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
