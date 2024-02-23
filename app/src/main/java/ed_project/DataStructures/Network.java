package ed_project.DataStructures;

import java.util.Iterator;

import ed_project.Exceptions.EmptyCollectionException;
import ed_project.Interfaces.NetworkADT;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class Network<T> extends Graph<T> implements NetworkADT<T> {

    protected double[][] adjMatrix;

    public Network() {
        this.adjMatrix = new double[super.DEFAULT_CAPACITY][super.DEFAULT_CAPACITY];
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        addTwoEdge(getIndex(vertex1), getIndex(vertex2), weight);

    }

    public void addEdge(T vertex1, T vertex2) {
        addTwoEdge(getIndex(vertex1), getIndex(vertex2), 0);
    }

    public void addOneEdge(T vertex1, T vertex2, double weight){
        super.addOneEdge(vertex1, vertex2);
        addOneEdge(getIndex(vertex1), getIndex(vertex2), weight);
    }

    public void removeOneEdgeWeight(T vertex1, T vertex2, double weight) {
        removeOneEdgeWeight(getIndex(vertex1), getIndex(vertex2), weight);
    }

    public void removeTwoEdgeWeight(T vertex1, T vertex2, double weight) {
        removeTwoEdgeWeight(getIndex(vertex1), getIndex(vertex2), weight);
    }

    public void removeOneEdgeWeight(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
        }
    }

    public void removeTwoEdgeWeight(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            adjMatrix[index2][index1] = weight;
        }
    }

    public void addTwoEdge(T vertex1, T vertex2, double weight){
        super.addEdge(vertex1, vertex2);
        addTwoEdge(getIndex(vertex1), getIndex(vertex2), weight);
    }

    public void addOneEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
        }
    }

    public void addTwoEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            adjMatrix[index2][index1] = weight;
        }
    }


    public boolean connection(int vertex1, int vertex2){
        return adjMatrix[vertex1][vertex2] > 0;
    }

    public Iterator<T>  iteratorShortestPath(int startIndex, int targetIndex) throws EmptyCollectionException {
        ArrayUnorderedList templist = new ArrayUnorderedList();
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return templist.iterator();
        }

        Iterator<Integer> it = iteratorShortestPathIndices(startIndex,
                targetIndex);
        while (it.hasNext()) {
            templist.addToRear(vertices[(it.next()).intValue()]);
        }
        return templist.iterator();
    }
    
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException {
        return iteratorShortestPath(getIndex(startVertex), getIndex(targetVertex));
    }

    public double shortestPathWeight(int startIndex, int targetIndex) throws EmptyCollectionException {
        double result = 0;
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex))
            return Double.POSITIVE_INFINITY;

        int index1, index2;
        Iterator<Integer> it = iteratorShortestPathIndices(startIndex, targetIndex);

        if (it.hasNext())
            index1 = ((Integer) it.next()).intValue();
        else
            return Double.POSITIVE_INFINITY;

        while (it.hasNext()) {
            index2 = (it.next()).intValue();
            result += adjMatrix[index1][index2];
            index1 = index2;
        }

        return result;
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) throws EmptyCollectionException {
        return shortestPathWeight(getIndex(vertex1), getIndex(vertex2));
    }

    protected void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[vertices.length * 2]);
        double[][] largerAdjMatrix = new double[vertices.length * 2][vertices.length * 2];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                largerAdjMatrix[i][j] = adjMatrix[i][j];
            }
            largerVertices[i] = vertices[i];
        }

        vertices = largerVertices;
        adjMatrix = largerAdjMatrix;
    }

    public void setEdgeWeight(T v1, double newWeight, T v2) {
        if (newWeight < 0.0) {
            throw new IllegalArgumentException("Edge weight " + "must be >= 0.0");
        }
        int v1Pos = super.getIndex(v1);
        int v2Pos = super.getIndex(v2);
        adjMatrix[v1Pos][v2Pos] = newWeight;
    }

    protected Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex)
            throws EmptyCollectionException {
        int index;
        double weight;
        int[] predecessor = new int[numVertices];
        LinkedHeap<Double> traversalMinHeap = new LinkedHeap<Double>();
        ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<Integer>();
        LinkedStack<Integer> stack = new LinkedStack<Integer>();

        int[] pathIndex = new int[numVertices];
        double[] pathWeight = new double[numVertices];
        for (int i = 0; i < numVertices; i++) {
            pathWeight[i] = Double.POSITIVE_INFINITY;
        }
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex) || (startIndex == targetIndex) || isEmpty()) {
            return resultList.iterator();
        }

        pathWeight[startIndex] = 0;
        predecessor[startIndex] = -1;
        visited[startIndex] = true;
        weight = 0;
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                pathWeight[i] = pathWeight[startIndex] + adjMatrix[startIndex][i];
                predecessor[i] = startIndex;
                traversalMinHeap.addElement(new Double(pathWeight[i]));
            }
        }
        do {
            weight = (traversalMinHeap.removeMin()).doubleValue();
            traversalMinHeap.removeAllElements();
            if (weight == Double.POSITIVE_INFINITY) {
                return resultList.iterator();
            } else {
                index = getIndexOfAdjVertexWithWeightOf(visited, pathWeight, weight);
                visited[index] = true;
            }
            for (int i = 0; i < numVertices; i++) {
                if (!visited[i]) {
                    if ((adjMatrix[index][i] < Double.POSITIVE_INFINITY)
                            && (pathWeight[index] + adjMatrix[index][i]) < pathWeight[i]) {
                        pathWeight[i] = pathWeight[index] + adjMatrix[index][i];
                        predecessor[i] = index;
                    }
                    traversalMinHeap.addElement(new Double(pathWeight[i]));
                }
            }
        } while (!traversalMinHeap.isEmpty() && !visited[targetIndex]);

        index = targetIndex;
        stack.push(Integer.valueOf(index));
        do {
            index = predecessor[index];
            stack.push(Integer.valueOf(index));
        } while (index != startIndex);

        while (!stack.isEmpty()) {
            resultList.addToRear((stack.pop()));
        }
        return resultList.iterator();
    }

    protected int getIndexOfAdjVertexWithWeightOf(boolean[] visited, double[] pathWeight, double weight) {
        for (int i = 0; i < numVertices; i++) {
            if ((pathWeight[i] == weight) && !visited[i]) {
                for (int j = 0; j < numVertices; j++) {
                    if ((adjMatrix[i][j] < Double.POSITIVE_INFINITY) && visited[j]) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public double[][] getAdjMatrix() {
        return adjMatrix;
    }

    public void setAdjMatrix(double[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
    }

}
