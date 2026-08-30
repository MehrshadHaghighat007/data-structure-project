# Data Structure Project

A Java-based data structure project focused on **graph representation, graph algorithms, graph transformation, and structural comparison**.

The project implements a weighted undirected graph model and provides algorithms for constructing graphs, finding connected components, checking graph properties, merging graph structures, and comparing graphs based on their topology and weights.

## Overview

The project represents graphs using custom `Graph`, `Vertex`, and `MyMap` classes.

Each vertex stores:

* A unique identifier
* Cartesian coordinates `(x, y)`
* A weight
* Its adjacent vertices and corresponding edge weights

Edges are represented using adjacency maps, with their weights calculated from the Euclidean distance between the coordinates of their endpoints.

The project also maintains a collection of graphs corresponding to the letters of the alphabet. These graphs can be constructed through a command-based input system and subsequently compared with connected components of an input graph.

## Main Features

### Graph Representation

The project provides custom graph structures consisting of:

* `Graph` — represents a graph and its vertices.
* `Vertex` — represents a vertex with an identifier, coordinates, weight, and adjacent edges.
* `MyMap<K, V>` — a custom map based on Java's `TreeMap`.

The graph representation uses adjacency maps to store edges and their weights.

### Command-Based Graph Construction

Graphs can be constructed using commands such as:

```text
NEW_GRAPH <letter>
ADD_VERTEX <vertexID> <x> <y>
ADD_EDGE <startVertexID> <endVertexID>
```

The implementation uses Java regular expressions to validate and parse these commands.

For example:

```text
NEW_GRAPH a
ADD_VERTEX 1 10 20
ADD_VERTEX 2 30 40
ADD_EDGE 1 2
```

### Euclidean Edge Weights

The weight of an edge is calculated from the coordinates of its two endpoints using the Euclidean distance:

$$
d(u,v) =
\sqrt{(x_u-x_v)^2 + (y_u-y_v)^2}
$$

This allows the graph to represent both its topology and geometric information.

### Connected Components

The project uses **Depth-First Search (DFS)** to identify connected components of the input graph.

Each connected component is converted into a separate `Graph` object for further processing.

### Graph Properties

Several structural properties are evaluated during graph comparison, including:

* Number of vertices
* Number of edges
* Degree sequence
* Bipartiteness
* Edge connectivity
* Vertex correspondence

The project uses **Breadth-First Search (BFS)** for bipartiteness checking.

### Graph Matching

The project attempts to establish a vertex mapping between two graphs.

Before performing the mapping, several necessary structural conditions are checked:

1. The number of vertices must be equal.
2. The number of edges must be equal.
3. The degree sequences must be equal.
4. Both graphs must have the same bipartiteness property.
5. A valid vertex mapping must preserve adjacency.

The mapping procedure is implemented recursively using backtracking.

### Graph Contraction and Merging

The project includes graph transformation operations in which vertices and edges can be merged.

Two major operations are implemented:

* **Vertex merging**
* **Edge merging**

These operations generate different graph configurations that are subsequently considered during graph comparison.

The cost associated with these transformations is accumulated as part of the graph comparison process.

### Weighted Graph Comparison

When two graph structures can be matched, their difference is evaluated using vertex and edge weights.

The comparison includes:

* Differences between corresponding vertex weights
* Differences between corresponding edge weights
* Costs introduced by graph transformations

The resulting value is used to determine the closest matching graph.

## Algorithms

The main algorithms implemented in the project include:

| Algorithm / Technique | Purpose                                  |
| --------------------- | ---------------------------------------- |
| DFS                   | Finding connected components             |
| BFS                   | Checking graph bipartiteness             |
| Backtracking          | Finding vertex mappings between graphs   |
| Degree Sequence       | Preliminary structural comparison        |
| Graph Contraction     | Generating transformed graph states      |
| Vertex Merging        | Transforming graph structure             |
| Edge Merging          | Transforming graph structure             |
| Euclidean Distance    | Computing geometric edge weights         |
| Recursive Search      | Exploring possible graph transformations |

## Project Structure

```text
data-structure-project/
├── .idea/
├── src/
│   └── Main.java
├── .gitignore
└── untitled57.iml
```

The main implementation is currently contained in:

```text
src/Main.java
```

The source file contains the graph data structures, command processing, graph algorithms, graph transformations, and comparison logic.

## Technologies

* **Language:** Java
* **Collections:** `TreeMap`, `HashMap`, `HashSet`, `ArrayList`, `LinkedList`
* **Algorithms:** DFS, BFS, recursive backtracking
* **Parsing:** Java Regular Expressions
* **Development Environment:** IntelliJ IDEA

## Running the Project

Clone the repository:

```bash
git clone https://github.com/MehrshadHaghighat007/data-structure-project.git
```

Navigate to the project:

```bash
cd data-structure-project
```

Open the project in a Java-compatible IDE such as IntelliJ IDEA.

Compile and run:

```text
src/Main.java
```

The program reads commands from standard input and processes the graph definitions and input data accordingly.

## Input Processing

The program supports two stages of input processing.

First, graph definitions are processed using commands such as:

```text
NEW_GRAPH
ADD_VERTEX
ADD_EDGE
```

The program then processes a `READ_TEXT` command followed by the specified number of input lines.

The vertices and edges described in this section are stored in a separate passage graph. The passage graph is then divided into connected components.

## Output

After processing the input, the program compares the connected components of the passage graph with the predefined alphabet graphs.

The selected graph identifiers are converted to alphabetic characters and printed as the resulting sequence.

## Design Considerations

The implementation emphasizes the practical application of fundamental data structures and graph algorithms.

In particular, the project demonstrates how:

* Maps can be used to implement adjacency structures.
* DFS can be used to decompose a graph into connected components.
* BFS can determine whether a graph is bipartite.
* Recursive backtracking can search for graph mappings.
* Graph transformations can be explored recursively.
* Structural and numerical properties can be combined to compare weighted graphs.

## Learning Objectives

This project provides practical experience with:

* Graph data structures
* Adjacency-map representations
* Tree-based maps
* Graph traversal
* Connected components
* Bipartite graphs
* Graph isomorphism concepts
* Backtracking
* Recursive algorithms
* Graph contraction
* Weighted graph comparison
* Regular-expression-based command parsing
* Object-oriented programming in Java

## Author

**Mehrshad Haghighat**

GitHub: [@MehrshadHaghighat007](https://github.com/MehrshadHaghighat007)

## Repository

[Data Structure Project](https://github.com/MehrshadHaghighat007/data-structure-project)
