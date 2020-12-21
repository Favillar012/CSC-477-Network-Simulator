package GUI;

import java.util.ArrayList;

/**
 * This class implements a network map with the topology of the network depicted in
 * network_image_3.png.
 * @author Fernando Villarreal
 * @date 11/13/2020
 */
public class NetworkMap {

    //============== CLASS VARIABLES ==============

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private ArrayList<Integer> edgeDistances;

    //============== CONSTRUCTOR ==============

    public NetworkMap() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.edgeDistances = new ArrayList<>();
        // Create the network map from network_image_3
        this.createNetworkImage3Map();
    }

    //============== METHODS ==============


    public ArrayList<Integer> getEdgeDistances() {
        return this.edgeDistances;
    }

    private void createNetworkImage3Map() {
        // Create and add the nodes
        Node sender = new Node(Node.sender);
        Node receiver = new Node(Node.receiver);
        Node router1 = new Node(Node.router1);
        Node router2 = new Node(Node.router2);
        Node router3 = new Node(Node.router3);
        Node router4 = new Node(Node.router4);
        Node router5 = new Node(Node.router5);
        this.nodes.add(sender);
        this.nodes.add(receiver);
        this.nodes.add(router1);
        this.nodes.add(router2);
        this.nodes.add(router3);
        this.nodes.add(router4);
        this.nodes.add(router5);
        // Create and add the edges
        this.edges.add(new Edge(sender, router1, this.randomNum(30)));    // 0: H1 - R1
        this.edges.add(new Edge(router1, router2, this.randomNum(30)));   // 1: R1 - R2
        this.edges.add(new Edge(router1, router5, this.randomNum(30)));   // 2: R1 - R5
        this.edges.add(new Edge(router1, router4, this.randomNum(50)));   // 3: R1 - R4
        this.edges.add(new Edge(router2, router5, this.randomNum(20)));   // 4: R2 - R5
        this.edges.add(new Edge(router2, router3, this.randomNum(20)));   // 5: R2 - R3
        this.edges.add(new Edge(router3, router5, this.randomNum(20)));   // 6: R3 - R5
        this.edges.add(new Edge(router3, router4, this.randomNum(30)));   // 7: R3 - R4
        this.edges.add(new Edge(router4, router5, this.randomNum(30)));   // 8: R4 - R5
        this.edges.add(new Edge(router4, receiver, this.randomNum(30)));  // 9: R4 - H2
    }

    /**
     * Creates a random number between 1 and maxNum for the distance of an edge.
     * @return
     */
    private int randomNum(int maxNum) {
        double numDbl = Math.random() * maxNum + 1;
        int numInt = (int)numDbl;
        this.edgeDistances.add(numInt);
        return numInt;
    }

    //============== DIJKSTRA'S ALGORITHM METHODS ==============

    /**
     * Perform Dijkstra's algorithm on this NetworkMap to get the shortest path from the provided
     * startLocation to the provided endLocation. The shortest path is returned as an ArrayList of
     * Location object representing the nodes in the network.
     * @param startLocation
     * @param endLocation
     * @return
     */
    public ArrayList<Location> dijkstra(Location startLocation, Location endLocation) {
        Node startNode = this.getNodeForLocation(startLocation);
        Node endNode = this.getNodeForLocation(endLocation);
        Node curNode = startNode;
        curNode.minimumDistance = 0;
        // While there are unvisited nodes
        while (this.areThereUnvisitedNodes()) {
            ArrayList<Node> neighbors = this.getNeighbors(curNode);
            // Update the tuple of every unvisited neighbor node
            for (Node neighbor : neighbors) {
                if (!neighbor.visited) {
                    Edge edge = this.getEdge(curNode, neighbor);
                    int distance = edge.distance;
                    int newNeighborMinDistance = curNode.minimumDistance + distance;
                    this.updateNodeTuple(neighbor, newNeighborMinDistance, curNode);
                }
            }
            // The curNode has been visited. Get the next node to visit.
            curNode.visited = true;
            curNode = this.nextNode();
        }
        // Now that all minimumDistances have been computed, get the shortest path.
        return this.getShortestPath(startNode, endNode);
    }

    /**
     * Get the shortest path between the two provided nodes as an ArrayList of Location objects.
     * @param startNode
     * @param endNode
     * @return
     */
    private ArrayList<Location> getShortestPath(Node startNode, Node endNode) {
        // Collect the nodes in the path in a list in the order of the path
        ArrayList<Node> nodesPath = new ArrayList<>();
        Node curNode = endNode;
        while (curNode != startNode) {
            nodesPath.add(0, curNode);
            curNode = curNode.lastNode;
        }
        nodesPath.add(0, curNode);
        // Translate the Node objects in the list into an identical list with Location objects
        ArrayList<Location> locationsPath = new ArrayList<>();
        for (Node pathNode : nodesPath) {
            if (pathNode.name == Node.sender) {
                locationsPath.add(NodeLocations.host1);
            } else if (pathNode.name == Node.router1) {
                locationsPath.add(NodeLocations.router1);
            } else if (pathNode.name == Node.router2) {
                locationsPath.add(NodeLocations.router2);
            } else if (pathNode.name == Node.router3) {
                locationsPath.add(NodeLocations.router3);
            } else if (pathNode.name == Node.router4) {
                locationsPath.add(NodeLocations.router4);
            } else if (pathNode.name == Node.router5) {
                locationsPath.add(NodeLocations.router5);
            } else if (pathNode.name == Node.receiver) {
                locationsPath.add(NodeLocations.host2);
            }
        }
        locationsPath.add(NodeLocations.end);
        // Return locationsPath
        return locationsPath;
    }

    /**
     * Pick the next node to visit in Dijkstra's algorithm. The next node is the one with the smallest
     * minimum distance among the nodes that have not been visited.
     * @return
     */
    private Node nextNode() {
        // Collect all the unvisited nodes in a list
        ArrayList<Node> unvisitedNodes = new ArrayList<>();
        for (Node node : this.nodes) {
            if (!node.visited) {
                unvisitedNodes.add(node);
            }
        }
        // Determine which node from the unvisitedNodes list has the smallest minimumDistance
        Node returnNode = null;
        int curMinDistance = 9999;
        for (Node curNode : unvisitedNodes) {
            if (curNode.minimumDistance < curMinDistance) {
                returnNode = curNode;
                curMinDistance = curNode.minimumDistance;
            }
        }
        // Return the unvisited Node with the smallest minimum distance
        return returnNode;
    }

    /**
     * Update the tuple of the given node if necessary.
     * @param node
     * @param newMinDistance
     * @param lastNode
     */
    private void updateNodeTuple(Node node, int newMinDistance, Node lastNode) {
        int originalMinDistance = node.minimumDistance;
        // If the new minimum distance is smaller than the original, then update the node's tuple.
        if (newMinDistance < originalMinDistance) {
            node.minimumDistance = newMinDistance;
            node.lastNode = lastNode;
        }
    }

    /**
     * Get the edge between the two given nodes. Returns null if there is no such edge.
     * @param node1
     * @param node2
     * @return
     */
    private Edge getEdge(Node node1, Node node2) {
        for (Edge edge : this.edges) {
            if (edge.node1.name == node1.name && edge.node2.name == node2.name) {
                return edge;
            } else if (edge.node1.name == node2.name && edge.node2.name == node1.name) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Get the neighbor nodes of the given node.
     * @param node
     * @return
     */
    private ArrayList<Node> getNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();
        for (Edge edge : this.edges) {
            if (node.name == edge.node1.name) {
                neighbors.add(edge.node2);
            } else if (node.name == edge.node2.name) {
                neighbors.add(edge.node1);
            }
        }
        return neighbors;
    }

    /**
     * Returns true if there are any unvisited nodes, returns false otherwise.
     * @return
     */
    private boolean areThereUnvisitedNodes() {
        for (Node node : this.nodes) {
            if (!node.visited) {
                return true;
            }
        }
        return false;
    }

    private Node getNodeForLocation(Location location) {
        if (location == NodeLocations.host1) {
            return this.getNode(Node.sender);
        } else if (location == NodeLocations.router1) {
            return this.getNode(Node.router1);
        } else if (location == NodeLocations.router2) {
            return this.getNode(Node.router2);
        } else if (location == NodeLocations.router3) {
            return this.getNode(Node.router3);
        } else if (location == NodeLocations.router4) {
            return this.getNode(Node.router4);
        } else if (location == NodeLocations.router5) {
            return this.getNode(Node.router5);
        } else if (location == NodeLocations.host2) {
            return this.getNode(Node.receiver);
        }
        return null;
    }

    private Node getNode(char name) {
        for (Node node : this.nodes) {
            if (node.name == name) {
                return node;
            }
        }
        return null;
    }

    public String toString() {
        String str = "NetworkMap:\n"
                + "Nodes: " + this.nodes.toString() + "\n"
                + "Edges: " + this.edges.toString();
        return str;
    }

    //============================ PRIVATE INNER CLASSES ============================

    /**
     * Private inner class Edge implements an edge between two nodes in a NetworkMap object.
     */
    private class Edge {

        private Node node1;
        private Node node2;
        private int distance;

        private Edge(Node node1, Node node2, int distance) {
            this.node1 = node1;
            this.node2 = node2;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "(" + node1.toString() + ", " + node2.toString() + ", " + this.distance + ")";
        }
    }

    /**
     * Private inner class Node implements a network node in a NetworkMap object.
     */
    private class Node {

        //============== NAMES FOR NODES ==============

        private static final char sender = 'S';
        private static final char receiver = 'R';
        private static final char router1 = '1';
        private static final char router2 = '2';
        private static final char router3 = '3';
        private static final char router4 = '4';
        private static final char router5 = '5';

        //============== CLASS VARIABLES ==============

        private final char name;
        private int minimumDistance;
        private Node lastNode;
        private boolean visited;

        //============== CONSTRUCTOR ==============

        private Node(char name) {
            this.name = name;
            this.minimumDistance = 9999;
            this.visited = false;
        }

        //============== METHODS ==============

        private void setTuple(int minimumDistance, Node lastNode) {
            this.minimumDistance = minimumDistance;
            this.lastNode = lastNode;
        }

        @Override
        public String toString() {
            return "'" + this.name + "'";
        }
    }
}
