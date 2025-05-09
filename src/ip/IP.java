package src.ip;

import src.exceptions.ParseException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static src.exceptions.ErrorMessages.IP_SYNTAX_ERROR;

/**
 * class to provide functionality regarding IP-Addresses
 *
 * @author usmsk
 * @version 1.0
 */
public class IP implements Comparable<IP> {
    /**
     * regex stating correct IP-Address format
     */
    public static final String IP_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])"
            + "(\\.([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])){3})$";

    private final long ipValue;
    private final String pointNotation;
    //used in BFS not a final variable changes depending on interpretation
    private IP parent = null;
    //used in BFS
    private boolean visited = false;
    private int level = 0;
    private final Set<IP> adjacentNodes = new HashSet<>();

    /**
     * constructor for IPs.
     * checks if specified IP matches IP_PATTERN.
     * then builds ipAsBinary by separately interpreting the 4 sections as 8bit binary numbers and concatenating them.
     *
     * @param pointNotation Ip address in point notation
     * @throws ParseException if ipAddress does not match proper point Notation(IP_PATTERN)
     */
    public IP(final String pointNotation) throws ParseException {
        if (pointNotation == null || (!pointNotation.matches(IP_PATTERN))) {
            throw new ParseException(IP_SYNTAX_ERROR);
        }
        this.pointNotation = pointNotation;
        String[] pointNotationArray = pointNotation.split("\\.");
        StringBuilder ipAsBinary = new StringBuilder();

        for (String s : pointNotationArray) {
            ipAsBinary.append(String.format("%8s", Long.toBinaryString(Long.parseLong(s))).replace(' ', '0'));
        }
        ipValue = Long.parseLong(ipAsBinary.toString(), 2);
    }

    /**
     * getter method for binary interpretation (without '.') of ip as string
     *
     * @return binary interpretation
     */
    public long getIpValue() {
        return ipValue;
    }

    /**
     * setter method for parent attribute used in breadth searching algorithms
     *
     * @param parent IP from which this IP was reached
     */
    public void setParent(IP parent) {
        this.parent = parent;
    }

    /**
     * getter method for parent attribute
     *
     * @return IP from which this IP was reached during searching algorithm
     */
    public IP getParent() {
        return parent;
    }

    /**
     * setter method for visited attribute used in breadth searching algorithms
     *
     * @param b true is visited, false if not
     */
    public void setVisited(boolean b) {
        visited = b;
    }

    /**
     * getter method for visited attribute
     *
     * @return true if visited, false if not
     */
    public boolean getVisited() {
        return visited;
    }

    /**
     * setter method for level attribute used in breadth first search when getting levels
     *
     * @param level states which level of the tree this IP belongs to
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * getter method for level attribute
     *
     * @return level of this IP in tree
     */
    public int getLevel() {
        return level;
    }

    /**
     * adds given IP to Set of adjacent nodes
     *
     * @param ip IP to be added to adjacent nodes
     */
    public void addAdjacentNode(IP ip) {
        adjacentNodes.add(ip);
    }

    /**
     * adds all elements of given collection containing IPs to adjacentNodes Set
     *
     * @param coll collection containing all elements to be added
     */
    public void addAdjacentNodeCollection(Collection<? extends IP> coll) {
        adjacentNodes.addAll(coll);
    }

    /**
     * getter method for adjacent nodes
     *
     * @return reference to this.adjacentNodes (no deep copy)
     */
    public Set<IP> getAdjacentNodes() {
        return adjacentNodes;
    }

    @Override
    public String toString() {
        return pointNotation;
    }

    @Override
    public int compareTo(IP o) {
        return Long.compare(ipValue, o.ipValue);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IP)) {
            return false;
        }

        return compareTo((IP) o) == 0;
    }

    @Override
    public int hashCode() {
        return 42;
    }
}
