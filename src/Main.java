import java.util.*;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Graph {
    private final int graphID;
    private final MyMap<Integer, Vertex> vertices;
    private double totalCostOfMerging = 0;
    private double minimumHeight;
    private double mostLeftLength;

    public Graph(Integer graphID) {
        this.graphID = graphID;
        vertices = new MyMap<>();
    }

    public void removeVertex(Integer vertexID) {
        vertices.remove(vertexID);
    }

    public int getGraphID() {
        return graphID;
    }

    public MyMap<Integer, Vertex> getVertices() {
        return vertices;
    }

    public double getTotalCostOfMerging() {
        return totalCostOfMerging;
    }

    public void setTotalCostOfMerging(double totalCostOfMerging) {
        this.totalCostOfMerging = totalCostOfMerging + this.totalCostOfMerging;
    }

    public double getMinimumHeight() {
        return minimumHeight;
    }

    public void setMinimumHeight(double minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    public void setMostLeftLength(double mostLeftLength) {
        this.mostLeftLength = mostLeftLength;
    }

    public double getMostLeftLength() {
        return mostLeftLength;
    }
}

class MyMap<K, V> extends TreeMap<K, V> {
    public MyMap() {
        super();
    }
}

class Vertex {
    private final MyMap<Integer, Double> edges;
    private final Integer ID;
    private final Double weight;
    private final Double x;
    private final Double y;

    public Vertex(Integer ID, Double x, Double y, Double weight) {
        edges = new MyMap<>();
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public MyMap<Integer, Double> getEdges() {
        return edges;
    }

    public Integer getID() {
        return ID;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfLines = 0;
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.startsWith("READ_TEXT")) {
                numberOfLines = Integer.parseInt(scanner.nextLine());
                break;
            } else {
                processCommand(command);
            }
        }
        String[] mainArray = new String[numberOfLines];
        for (int i = 0; i < numberOfLines; i++) {
            mainArray[i] = scanner.nextLine();
        }
        scanner.close();
        for (String s : mainArray) {
            processReadText(s);
        }
        findConnectedComponents();
        for (Graph graph : connectedComponentsOfPassage) {
            double height = Double.MAX_VALUE;
            for (Integer vertexID : graph.getVertices().keySet()) {
                if (graph.getVertices().get(vertexID).getY() < height) {
                    height = graph.getVertices().get(vertexID).getY();
                }
            }
            graph.setMinimumHeight(-height);
        }
        for (Graph graph : connectedComponentsOfPassage) {
            double length = Double.MAX_VALUE;
            for (Integer vertexID : graph.getVertices().keySet()) {
                if (graph.getVertices().get(vertexID).getX() < length) {
                    length = graph.getVertices().get(vertexID).getX();
                }
            }
            graph.setMostLeftLength(length);
        }
        Comparator<Graph> graphComparator = Comparator.comparingDouble(Graph::getMinimumHeight).thenComparingDouble(Graph::getMostLeftLength);
        TreeMap<Graph, Graph> letterMap = new TreeMap<>(graphComparator);
        double min = Double.MAX_VALUE;
        double difference;
        for (Graph firstGraph : connectedComponentsOfPassage) {
            for (Graph secondGraph : alphabet) {
                if (secondGraph != null) {
                    difference = graphDistance(firstGraph, secondGraph);
                    if (difference < min) {
                        min = difference;
                        letterMap.put(firstGraph, secondGraph);
                    }
                    minimum = Double.MAX_VALUE;
                    minimizedDifference = Double.MAX_VALUE;
                }
            }
            min = Double.MAX_VALUE;
        }
        Graph graph = new Graph(27);
        for (Graph secondGraph : alphabet) {
            if (secondGraph != null) {
                graph = secondGraph;
                break;
            }
        }
        if (letterMap.isEmpty()) {
            for (Graph firstGraph : connectedComponentsOfPassage) {
                letterMap.put(firstGraph, graph);
            }
        }
        LinkedList<Graph> helper = new LinkedList<>(letterMap.keySet());
        while (!helper.isEmpty()) {
            Graph first = helper.poll();
            if (!helper.isEmpty()) {
                Graph second = helper.getFirst();
                if (first.getMinimumHeight() == second.getMinimumHeight()) {
                    System.out.print((char) (letterMap.get(first).getGraphID() + 'a' - 1));
                } else {
                    System.out.println((char) (letterMap.get(first).getGraphID() + 'a' - 1));
                }
            } else {
                System.out.print((char) (letterMap.get(first).getGraphID() + 'a' - 1));
            }
        }
    }

    private static final Pattern NEW_GRAPH_PATTERN = Pattern.compile("^NEW_GRAPH\\s+([a-z])$");
    private static final Pattern ADD_VERTEX_PATTERN = Pattern.compile("^\\s*ADD_VERTEX\\s+(-?\\d+)\\s+(-?\\d+)\\s+(-?\\d+)\\s*$");
    private static final Pattern ADD_EDGE_PATTERN = Pattern.compile("^ADD_EDGE\\s+(-?\\d+)\\s+(-?\\d+)$");
    private static final Graph[] alphabet = new Graph[26];
    private static final Graph passage = new Graph(0);
    private static final List<Graph> connectedComponentsOfPassage = new ArrayList<>();
    private static int activeLetter;
    private static double minimizedDifference = Double.MAX_VALUE;
    private static double minimum = Double.MAX_VALUE;

    public static void processCommand(String command) {
        if (command.startsWith("NEW_GRAPH")) {
            Matcher matcher = NEW_GRAPH_PATTERN.matcher(command);
            if (matcher.matches()) {
                newGraph(matcher);
            } else {
                System.out.println("INVALID COMMAND");
            }
        } else if (command.startsWith("ADD_VERTEX")) {
            Matcher matcher = ADD_VERTEX_PATTERN.matcher(command);
            if (matcher.matches()) {
                addVertex(matcher);
            } else {
                System.out.println("INVALID COMMAND");
            }
        } else if (command.startsWith("ADD_EDGE")) {
            Matcher matcher = ADD_EDGE_PATTERN.matcher(command);
            if (matcher.matches()) {
                addEdge(matcher);
            } else {
                System.out.println("INVALID COMMAND");
            }
        }
    }

    private static void processReadText(String command) {
        if (command.startsWith("ADD_VERTEX")) {
            Matcher matcher = ADD_VERTEX_PATTERN.matcher(command);
            if (matcher.matches()) {
                Integer vertexID = Integer.parseInt(matcher.group(1));
                Double x = Double.parseDouble(matcher.group(2));
                Double y = Double.parseDouble(matcher.group(3));
                Vertex vertex = new Vertex(vertexID, x, y, 1000.0);
                passage.getVertices().put(vertexID, vertex);
            } else {
                System.out.println("INVALID COMMAND");
            }
        } else if (command.startsWith("ADD_EDGE")) {
            Matcher matcher = ADD_EDGE_PATTERN.matcher(command);
            if (matcher.matches()) {
                Integer startVertexID = Integer.parseInt(matcher.group(1));
                Integer endVertexID = Integer.parseInt(matcher.group(2));
                Double distance = calculateDistance(passage.getVertices().get(startVertexID).getX(), passage.getVertices().get(startVertexID).getY(), passage.getVertices().get(endVertexID).getX(), passage.getVertices().get(endVertexID).getY());
                passage.getVertices().get(startVertexID).getEdges().put(endVertexID, distance);
                passage.getVertices().get(endVertexID).getEdges().put(startVertexID, distance);
            } else {
                System.out.println("INVALID COMMAND");
            }
        }
    }

    private static void newGraph(Matcher matcher) {
        String letterName = matcher.group(1);
        activeLetter = letterName.charAt(0) - 'a';
        alphabet[activeLetter] = new Graph(activeLetter + 1);
    }

    private static void addVertex(Matcher matcher) {
        Integer vertexID = Integer.parseInt(matcher.group(1));
        Double x = Double.parseDouble(matcher.group(2));
        Double y = Double.parseDouble(matcher.group(3));
        Vertex vertex = new Vertex(vertexID, x, y, 1000.0);
        alphabet[activeLetter].getVertices().put(vertexID, vertex);
    }

    private static void addEdge(Matcher matcher) {
        Integer startVertexID = Integer.parseInt(matcher.group(1));
        Integer endVertexID = Integer.parseInt(matcher.group(2));
        Double distance = calculateDistance(alphabet[activeLetter].getVertices().get(startVertexID).getX(), alphabet[activeLetter].getVertices().get(startVertexID).getY(), alphabet[activeLetter].getVertices().get(endVertexID).getX(), alphabet[activeLetter].getVertices().get(endVertexID).getY());
        alphabet[activeLetter].getVertices().get(startVertexID).getEdges().put(endVertexID, distance);
        alphabet[activeLetter].getVertices().get(endVertexID).getEdges().put(startVertexID, distance);
    }

    private static Double calculateDistance(Double x1, Double y1, Double x2, Double y2) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    private static double graphDistance(Graph firstGraph, Graph secondGraph) {
        int numberOfConnectedComponentsSecondGraph = countConnectedComponents(secondGraph);
        if (1 == numberOfConnectedComponentsSecondGraph) {
            Graph testGraph1 = makeCopy(firstGraph);
            Graph testGraph2 = makeCopy(secondGraph);
            List<Graph> firstGraphSituations = new ArrayList<>();
            List<Graph> secondGraphSituations = new ArrayList<>();
            recursiveContraction(testGraph1, firstGraphSituations);
            recursiveContraction(testGraph2, secondGraphSituations);
            uniformityManager(firstGraphSituations, secondGraphSituations);
            return minimum;
        }
        return Double.MAX_VALUE;
    }

    private static void recursiveContraction(Graph graph, List<Graph> graphSituations) {
        graphSituations.add(graph);
        for (Vertex vertex : graph.getVertices().values()) {
            if (graph.getVertices().size() != 1 && !vertex.getEdges().isEmpty()) {
                Graph helper = makeCopy(graph);
                vertexMerging(helper, vertex);
                recursiveContraction(helper, graphSituations);
            }
        }

        for (Integer startVertexID : graph.getVertices().keySet()) {
            for (Integer endVertexID : graph.getVertices().get(startVertexID).getEdges().keySet()) {
                if (startVertexID < endVertexID) {
                    Graph helper = makeCopy(graph);
                    edgeMerging(helper, helper.getVertices().get(startVertexID), helper.getVertices().get(endVertexID));
                    recursiveContraction(helper, graphSituations);
                }
            }
        }

    }

    private static void uniformityManager(List<Graph> firstGraphSituations, List<Graph> secondGraphSituations) {
        for (Graph fisrtGraph : firstGraphSituations) {
            for (Graph secondGraph : secondGraphSituations) {
                if (areGraphsUniformed(fisrtGraph, secondGraph)) {
                    minimum = Math.min(minimum, minimizedDifference);
                }
            }
        }
    }

    private static Graph makeCopy(Graph graph) {
        Graph newGraph = new Graph(graph.getGraphID());
        for (Integer vertexID : graph.getVertices().keySet()) {
            Vertex newVertex = new Vertex(vertexID, graph.getVertices().get(vertexID).getX(), graph.getVertices().get(vertexID).getY(), graph.getVertices().get(vertexID).getWeight());
            newGraph.getVertices().put(newVertex.getID(), newVertex);
        }
        for (Integer vertexID : graph.getVertices().keySet()) {
            for (Integer edgeID : graph.getVertices().get(vertexID).getEdges().keySet()) {
                newGraph.getVertices().get(vertexID).getEdges().put(edgeID, graph.getVertices().get(vertexID).getEdges().get(edgeID));
            }
        }
        newGraph.setTotalCostOfMerging(graph.getTotalCostOfMerging());
        return newGraph;
    }

    private static boolean areGraphsUniformed(Graph firstGraph, Graph secondGraph) {
        if (firstGraph.getVertices().size() != secondGraph.getVertices().size()) {
            return false;
        }
        if (numberOfEdges(firstGraph) != numberOfEdges(secondGraph)) {
            return false;
        }
        List<Integer> firstDegreeSequence = getDegreeSequence(firstGraph);
        List<Integer> secondDegreeSequence = getDegreeSequence(secondGraph);
        if (!firstDegreeSequence.equals(secondDegreeSequence)) {
            return false;
        }
        if (isBipartite(firstGraph) != isBipartite(secondGraph)) {
            return false;
        }
        return mapping(new HashMap<>(), firstGraph, secondGraph, new HashSet<>());
    }


    private static int numberOfEdges(Graph graph) {
        int number = 0;
        for (Integer vertexID : graph.getVertices().keySet()) {
            number = number + graph.getVertices().get(vertexID).getEdges().size();
        }
        return (number / 2);
    }

    private static List<Integer> getDegreeSequence(Graph graph) {
        List<Integer> degreeSequence = new ArrayList<>();
        for (Integer vertexID : graph.getVertices().keySet()) {
            degreeSequence.add(graph.getVertices().get(vertexID).getEdges().size());
        }
        Collections.sort(degreeSequence);
        return degreeSequence;
    }

    private static boolean mapping(Map<Integer, Integer> mapping, Graph firstGraph, Graph secondGraph, Set<Integer> usedVertices) {
        if (mapping.size() == firstGraph.getVertices().size() && edgeMappingValidation(mapping, firstGraph, secondGraph)) {
            minimizedDifference = Math.min(minimizedDifference, (calculateWeightDifference(mapping, firstGraph, secondGraph) + firstGraph.getTotalCostOfMerging() + secondGraph.getTotalCostOfMerging()));
            return true;
        }
        boolean check = false;
        for (int v1 : firstGraph.getVertices().keySet()) {
            if (!mapping.containsKey(v1)) {
                for (int v2 : secondGraph.getVertices().keySet()) {
                    if (!usedVertices.contains(v2)) {
                        mapping.put(v1, v2);
                        usedVertices.add(v2);
                        if (mapping(mapping, firstGraph, secondGraph, usedVertices)) {
                            check = true;
                        }
                        mapping.remove(v1);
                        usedVertices.remove(v2);
                    }
                }
                return check;
            }
        }// Be Careful
        return false;
    }

    private static boolean edgeMappingValidation(Map<Integer, Integer> mapping, Graph graph1, Graph graph2) {
        for (Integer firstVertexID : graph1.getVertices().keySet()) {
            int mappedV1 = mapping.get(firstVertexID);
            for (Integer secondGraphID : graph1.getVertices().get(firstVertexID).getEdges().keySet()) {
                int mappedNeighbor = mapping.get(secondGraphID);
                //Be Careful
                if (!graph2.getVertices().get(mappedV1).getEdges().containsKey(mappedNeighbor)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static double calculateWeightDifference(Map<Integer, Integer> mapping, Graph firstGraph, Graph secondGraph) {
        double totalDifference = 0;
        for (Map.Entry<Integer, Integer> entry : mapping.entrySet()) {
            double weightOfV1 = firstGraph.getVertices().get(entry.getKey()).getWeight();
            double weightOfV2 = secondGraph.getVertices().get(entry.getValue()).getWeight();
            totalDifference = totalDifference + Math.abs(weightOfV1 - weightOfV2);
        }
        for (Integer startVertexID : firstGraph.getVertices().keySet()) {
            for (Integer endVertexID : firstGraph.getVertices().get(startVertexID).getEdges().keySet()) {
                if (startVertexID < endVertexID) {
                    totalDifference = totalDifference + Math.abs(firstGraph.getVertices().get(startVertexID).getEdges().get(endVertexID) - secondGraph.getVertices().get(mapping.get(startVertexID)).getEdges().get(mapping.get(endVertexID)));
                }
            }
        }
        //Be Careful
        return totalDifference;
    }

    private static void dfs(int node, Set<Integer> visited, List<Integer> component, Graph graph) {
        visited.add(node);
        component.add(node);
        for (int neighbor : graph.getVertices().get(node).getEdges().keySet()) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited, component, graph);
            }
        }
    }

    public static void findConnectedComponents() {
        Set<Integer> visited = new HashSet<>();
        int i = 0;
        for (int vertex : passage.getVertices().keySet()) {
            if (!visited.contains(vertex)) {
                List<Integer> componentNodes = new ArrayList<>();
                dfs(vertex, visited, componentNodes, passage);
                Graph componentGraph = new Graph(i);
                for (int node : componentNodes) {
                    Vertex newVertex = new Vertex(node, passage.getVertices().get(node).getX(), passage.getVertices().get(node).getY(), passage.getVertices().get(node).getWeight());
                    componentGraph.getVertices().put(node, newVertex);
                }
                for (int node : componentNodes) {
                    for (int neighbor : passage.getVertices().get(node).getEdges().keySet()) {
                        if (node < neighbor) {
                            Double distance = calculateDistance(passage.getVertices().get(node).getX(), passage.getVertices().get(node).getY(), passage.getVertices().get(neighbor).getX(), passage.getVertices().get(neighbor).getY());
                            componentGraph.getVertices().get(node).getEdges().put(neighbor, distance);
                            componentGraph.getVertices().get(neighbor).getEdges().put(node, distance);
                        }
                    }
                }
                connectedComponentsOfPassage.add(componentGraph);
                i++;
            }
        }
    }

    private static int countConnectedComponents(Graph graph) {
        Map<Integer, Boolean> visited = new HashMap<>();
        int count = 0;

        for (Integer vertexID : graph.getVertices().keySet()) {
            visited.put(vertexID, false);
        }

        for (Integer vertexID : graph.getVertices().keySet()) {
            if (!visited.get(vertexID)) {
                DFS(vertexID, graph, visited);
                count++;
            }
        }
        return count;
    }

    private static void DFS(Integer vertexID, Graph graph, Map<Integer, Boolean> visited) {
        visited.put(vertexID, true);
        for (Integer neighbor : graph.getVertices().get(vertexID).getEdges().keySet()) {
            if (!visited.get(neighbor)) {
                DFS(neighbor, graph, visited);
            }
        }
    }


    private static void vertexMerging(Graph graph, Vertex vertex) {
        Set<Integer> edge = new HashSet<>();
        for (Integer neighborID : graph.getVertices().get(vertex.getID()).getEdges().keySet()) {
            edge.addAll(graph.getVertices().get(neighborID).getEdges().keySet());
        }
        edge.remove(vertex.getID());
        for (Integer neighborID : graph.getVertices().get(vertex.getID()).getEdges().keySet()) {
            edge.remove(neighborID);
        }
        Vertex newVertex = new Vertex(vertex.getID(), vertex.getX(), vertex.getY(), vertexMergingCost(graph, vertex));
        for (Integer vertexID : edge) {
            Vertex currentVertex = graph.getVertices().get(vertexID);
            double combinedWeight = 0;
            for (Integer neighborID : graph.getVertices().get(vertex.getID()).getEdges().keySet()) {
                if (currentVertex.getEdges().containsKey(neighborID)) {
                    combinedWeight += currentVertex.getEdges().get(neighborID);
                    currentVertex.getEdges().remove(neighborID);
                }
            }
            currentVertex.getEdges().put(newVertex.getID(), combinedWeight);
            newVertex.getEdges().put(vertexID, combinedWeight);
        }
        for (Integer neighborID : graph.getVertices().get(vertex.getID()).getEdges().keySet()) {
            graph.removeVertex(neighborID);
        }
        graph.removeVertex(vertex.getID());
        graph.getVertices().put(newVertex.getID(), newVertex);
        graph.setTotalCostOfMerging(newVertex.getWeight());
    }

    private static void edgeMerging(Graph graph, Vertex startVertex, Vertex endVertex) {
        Vertex newVertex = new Vertex(startVertex.getID(), startVertex.getX(), startVertex.getY(), edgeMergingCost(graph, startVertex, endVertex));
        Set<Integer> edge = new HashSet<>();
        edge.addAll(graph.getVertices().get(startVertex.getID()).getEdges().keySet());
        edge.addAll(graph.getVertices().get(endVertex.getID()).getEdges().keySet());
        edge.remove(startVertex.getID());
        edge.remove(endVertex.getID());
        for (Integer vertexID : edge) {
            Vertex currentVertex = graph.getVertices().get(vertexID);
            double combinedWeight = 0;
            if (currentVertex.getEdges().containsKey(startVertex.getID())) {
                combinedWeight += currentVertex.getEdges().get(startVertex.getID());
                currentVertex.getEdges().remove(startVertex.getID());
            }
            if (currentVertex.getEdges().containsKey(endVertex.getID())) {
                combinedWeight += currentVertex.getEdges().get(endVertex.getID());
                currentVertex.getEdges().remove(endVertex.getID());
            }
            currentVertex.getEdges().put(newVertex.getID(), combinedWeight);
            newVertex.getEdges().put(vertexID, combinedWeight);
        }
        graph.getVertices().remove(startVertex.getID());
        graph.getVertices().remove(endVertex.getID());
        graph.getVertices().put(newVertex.getID(), newVertex);
        graph.setTotalCostOfMerging(newVertex.getWeight());
    }

    private static double edgeMergingCost(Graph graph, Vertex startVertex, Vertex endVertex) {
        return startVertex.getWeight() + endVertex.getWeight() + graph.getVertices().get(startVertex.getID()).getEdges().get(endVertex.getID());
    }

    private static double vertexMergingCost(Graph graph, Vertex vertex) {
        double newWeight = vertex.getWeight();
        for (Integer neighborID : graph.getVertices().get(vertex.getID()).getEdges().keySet()) {
            newWeight = newWeight + graph.getVertices().get(neighborID).getWeight() + graph.getVertices().get(vertex.getID()).getEdges().get(neighborID);
            for (Integer neighborOfNeighbor : graph.getVertices().get(vertex.getID()).getEdges().keySet()) {
                if (neighborID < neighborOfNeighbor) {
                    if (graph.getVertices().get(neighborID).getEdges().containsKey(neighborOfNeighbor)) {
                        newWeight += graph.getVertices().get(neighborID).getEdges().get(neighborOfNeighbor);
                    }
                }
            }
        }
        return newWeight;
    }

    private static List<Integer> findCycleLengths(Graph graph) {
        Map<Integer, Boolean> visited = new HashMap<>();
        List<Integer> cycleLengths = new ArrayList<>();

        for (Integer vertexID : graph.getVertices().keySet()) {
            visited.put(vertexID, false);
        }

        for (Integer vertexID : graph.getVertices().keySet()) {
            if (!visited.get(vertexID)) {
                dfs(graph, vertexID, -1, visited, new LinkedHashSet<>(), cycleLengths);
            }
        }
        return cycleLengths;
    }

    private static void dfs(Graph graph, int current, int parent, Map<Integer, Boolean> visited, Set<Integer> path, List<Integer> cycleLengths) {
        visited.put(current, true);
        path.add(current);

        for (int neighbor : graph.getVertices().get(current).getEdges().keySet()) {
            if (!visited.get(neighbor)) {
                dfs(graph, neighbor, current, visited, path, cycleLengths);
            } else if (neighbor != parent && path.contains(neighbor)) {
                cycleLengths.add(calculateCycleLength(path, neighbor));
            }
        }

        path.remove(current);
    }

    private static int calculateCycleLength(Set<Integer> path, int cycleStart) {
        List<Integer> pathList = new ArrayList<>(path);
        int index = pathList.indexOf(cycleStart);
        return pathList.size() - index;
    }

    private static boolean isBipartite(Graph graph) {
        Map<Integer, Integer> color = new HashMap<>();
        for (Integer vertex : graph.getVertices().keySet()) {
            if (!color.containsKey(vertex)) {
                if (!bfsCheckBipartite(graph, vertex, color)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean bfsCheckBipartite(Graph graph, int start, Map<Integer, Integer> color) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        color.put(start, 0);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            int currentColor = color.get(current);

            for (int neighbor : graph.getVertices().get(current).getEdges().keySet()) {
                if (!color.containsKey(neighbor)) {
                    color.put(neighbor, 1 - currentColor);
                    queue.add(neighbor);
                } else if (color.get(neighbor) == currentColor) {
                    return false;
                }
            }
        }
        return true;
    }
}
