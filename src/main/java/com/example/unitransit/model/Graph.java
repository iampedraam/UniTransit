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

    public List<Road> bestRoute(int srcId, int dstId) {
        List<Road> path = shortestPath(srcId, dstId, 3);
        if (path.isEmpty()) {
            System.out.println("No path with capacity found!");
        }
        return path;
    }
    //Find Shortest Path by min expense and check capacity
    private List<Road> shortestPath(int src, int dst, int maxStops) {
        record State(int node, int cost, int hops) {}
        Map<Integer, int[]> dist = new HashMap<>();
        Map<String, Road>   prev = new HashMap<>();
        PriorityQueue<State> pq  = new PriorityQueue<>(Comparator.comparingInt(State::cost));

        dist.put(src, new int[]{0, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE});
        pq.add(new State(src, 0, 0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            if (cur.node == dst) break;
            if (cur.hops == maxStops) continue;

            for (Road r : neighbors(cur.node)) {
                if (!r.hasCapacity()) continue;
                int v        = r.getTo();
                int newCost  = cur.cost + r.getPrice();
                int newHops  = cur.hops + 1;

                dist.computeIfAbsent(v, k -> new int[]{Integer.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Integer.MAX_VALUE});
                if (newCost < dist.get(v)[newHops]) {
                    dist.get(v)[newHops] = newCost;
                    prev.put(v + "," + newHops, r);
                    pq.add(new State(v, newCost, newHops));
                }
            }
        }

        int bestHop = -1, bestCost = Integer.MAX_VALUE;
        int[] dstCosts = dist.get(dst);
        if (dstCosts != null) {
            for (int h = 1; h <= maxStops; h++) {
                if (dstCosts[h] < bestCost) {
                    bestCost = dstCosts[h];
                    bestHop  = h;
                }
            }
        }
        if (bestHop == -1) return List.of();

        LinkedList<Road> path = new LinkedList<>();
        int cur   = dst;
        int hops  = bestHop;
        while (cur != src) {
            Road r = prev.get(cur + "," + hops);
            path.addFirst(r);
            cur  = r.getFrom();
            hops--;
        }
        return path;
    }

    public boolean allPairsWithinTwoStops() {
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