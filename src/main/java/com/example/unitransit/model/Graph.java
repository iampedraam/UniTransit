package com.example.unitransit.model;

import java.util.*;

public class Graph {
    private final Map<Integer, University> nodes = new HashMap<>();
    private final Map<Integer, List<Road>> adj   = new HashMap<>();

    public Graph(Collection<University> universities, Collection<Road> roads) {
        universities.forEach(u -> {
            nodes.put(u.getUniversityId(), u);
            adj.put(u.getUniversityId(), new ArrayList<>());
        });
        roads.forEach(this::addEdgeInternal);
    }
    private void addEdgeInternal(Road r) {
        adj.get(r.getFrom()).add(r);
    }
    private List<Road> neighbors(int id) {
        return adj.getOrDefault(id, List.of());
    }

    public List<Road> computeMST() {
        if (nodes.isEmpty()) return List.of();

        Set<Integer> visited = new HashSet<>();
        List<Road>   mst     = new ArrayList<>();
        PriorityQueue<Road> pq = new PriorityQueue<>(Comparator.comparingInt(Road::getPrice));

        int start = nodes.keySet().iterator().next();
        visited.add(start);
        pq.addAll(adj.get(start));

        while (!pq.isEmpty() && visited.size() < nodes.size()) {
            Road r = pq.poll();
            if (visited.contains(r.getTo())) continue;
            visited.add(r.getTo());
            mst.add(r);
            pq.addAll(adj.get(r.getTo()));
        }
        return mst;
    }

    public List<Road> suggestBestRoute(int srcId, int dstId) {
        List<Road> path = shortestPath(srcId, dstId);
        if (path.isEmpty()) {
            System.out.println("No path with capacity found!!!!");
            return List.of();
        }
        return path;
    }
    private List<Road> shortestPath(int src, int dst) {
        record State(int node, int cost) {}
        Map<Integer,Integer> dist = new HashMap<>();
        Map<Integer,Road>    prev = new HashMap<>();
        PriorityQueue<State> pq   = new PriorityQueue<>(Comparator.comparingInt(State::cost));

        dist.put(src, 0);
        pq.add(new State(src, 0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            if (cur.node == dst) break;  //got to destination
            if (cur.cost != dist.get(cur.node)) continue;

            for (Road r : neighbors(cur.node)) {
                if (!r.hasCapacity()) continue;
                int v = r.getTo();
                int newCost = cur.cost + r.getPrice();
                if (newCost < dist.getOrDefault(v, Integer.MAX_VALUE)) {
                    dist.put(v, newCost);
                    prev.put(v, r);
                    pq.add(new State(v, newCost));
                }
            }
        }

        if (!prev.containsKey(dst)) return List.of(); //if theres no route
        LinkedList<Road> path = new LinkedList<>();
        for (int cur = dst; cur != src; ) {
            Road r = prev.get(cur);
            path.addFirst(r);
            cur = r.getFrom();
        }
        return path;
    }
    private void addUniversity(University u) {
        nodes.putIfAbsent(u.getUniversityId(), u);
        adj.putIfAbsent(u.getUniversityId(), new ArrayList<>());
    }
    private void addRoad(Road r) {
        addEdgeInternal(r);
    }
    private boolean allPairsWithinTwoStops() {
        for (int a : nodes.keySet()) {
            if (!bfsTwoHop(a)) return false;
        }
        return true;
    }
    private boolean bfsTwoHop(int src) {
        Queue<Integer> q = new ArrayDeque<>();
        Map<Integer,Integer> dist = new HashMap<>();
        dist.put(src, 0);  q.add(src);

        while (!q.isEmpty()) {
            int u = q.poll();
            int d = dist.get(u);
            if (d == 3) continue;
            for (Road r : neighbors(u)) {
                int v = r.getTo();
                if (!dist.containsKey(v)) {
                    dist.put(v, d + 1);
                    q.add(v);
                }
            }
        }
        return dist.size() == nodes.size();
    }

}