package com.example.unitransit.model;

import java.util.*;

public class Graph {
    private List<University> nodes;
    private List<Road> edges;
    private Map<Integer, University> idToUniversity;
    private Map<Integer, List<Road>> adjacencyList;

    public Graph(List<University> universities, List<Road> roads) {
        this.nodes = universities;
        this.edges = roads;
        this.idToUniversity = new HashMap<>();
        this.adjacencyList = new HashMap<>();

        for (University u : universities) {
            idToUniversity.put(u.getUniversityId(), u);
            adjacencyList.put(u.getUniversityId(), new ArrayList<>());
        }

        for (Road r : roads) {
            adjacencyList.get(r.getFrom()).add(r);
        }
    }

    public List<Road> getNeighbors(int universityId) {
        return adjacencyList.getOrDefault(universityId, new ArrayList<>());
    }

    public University getUniversityById(int id) {
        return idToUniversity.get(id);
    }

    // Prim MST
    public List<Road> getMST() {
        if (nodes.isEmpty()) return new ArrayList<>();

        List<Road> mst = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Road> minHeap = new PriorityQueue<>(Comparator.comparingInt(Road::getPrice));

        int startNode = nodes.get(0).getUniversityId();
        visited.add(startNode);

        minHeap.addAll(adjacencyList.get(startNode));
        while (!minHeap.isEmpty() && visited.size() < nodes.size()) {
            Road minEdge = minHeap.poll();
            int nextNode = minEdge.getTo();

            if (!visited.contains(nextNode)) {
                visited.add(nextNode);
                mst.add(minEdge);

                for (Road edge : adjacencyList.get(nextNode)) {
                    if (!visited.contains(edge.getTo())) {
                        minHeap.add(edge);
                    }
                }
            }
        }

        return mst;
    }

    public boolean isMSTReachableWith2Stops() {
        List<Road> mstEdges = getMST();
        Graph mstGraph = buildMSTGraph(mstEdges);
        return mstGraph.isReachableWith2Stops();
    }

    // Build a Graph from MST edges
    private Graph buildMSTGraph(List<Road> mstEdges) {
        List<Road> bidirectionalEdges = new ArrayList<>();
        for (Road edge : mstEdges) {
            bidirectionalEdges.add(edge);
            // Add reverse edge for undirected MST
            bidirectionalEdges.add(new Road(edge.getTo(), edge.getFrom(), edge.getCapacity(), edge.getPrice(), edge.getOpen(), edge.getClose()));
        }
        return new Graph(new ArrayList<>(nodes), bidirectionalEdges);
    }

    // Check if all universities are reachable within 2 stops (3 edges) in the current graph
    public boolean isReachableWith2Stops() {
        for (University u1 : nodes) {
            for (University u2 : nodes) {
                if (u1.getUniversityId() != u2.getUniversityId()) {
                    int distance = getShortestDistance(u1.getUniversityId(), u2.getUniversityId());
                    if (distance > 3) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // BFS
    public int getShortestDistance(int startId, int endId) {
        if (startId == endId) return 0;

        Map<Integer, Integer> distances = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        distances.put(startId, 0);
        queue.add(startId);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (Road r : getNeighbors(current)) {
                int neighbor = r.getTo();
                if (!distances.containsKey(neighbor)) {
                    distances.put(neighbor, distances.get(current) + 1);
                    if (neighbor == endId) {
                        return distances.get(neighbor);
                    }
                    queue.add(neighbor);
                }
            }
        }

        return Integer.MAX_VALUE;
    }
}