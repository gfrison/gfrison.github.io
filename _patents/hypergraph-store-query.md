---
title: "Hyper-Graph Store Representation and Query Mechanism"
excerpt: "An in-memory hyper-graph encoding for Datalog-based programming systems. It defines data structures for direct access to predicates and their arguments, enabling efficient query resolution with unification support."
date: 2024-12-07
last_modified_at: 2026-03-09
permalink: /patents/hypergraph-store-query
header:
  og_image: /assets/patents/hypergraph-store-query.jpeg
tags:
 - knowledge graph
 - logic programming
 - programming
---
| Id                                                                | Status    | Submission Date | Grant Date  | Authorship      |
| ----------------------------------------------------------------- |
| 18/972,932 | Pending | {{ "2024-12-07" | date_to_string }} |  | 100% |


Accessing data efficiently for analytics is an tacit requirement for any data service and it is critical for data-intensive applications. Graphs are a particular type of data representation on which single nodes - the minimal unit of manageable information - are connected with others through relations. Relations - or edges - in graphs may connect two nodes, or may connect a multitude of nodes. In the latter case, they are called _hyper_-edges.

Hyper-graphs (set of hyper-edges) are not a naive data representation. They are more common than the name may suggest. Relational databases are a form of hyper-graphs, for example. If you consider the table name as the label of the edge type, and each row as an edge of the specified edge type, the cell would be the single node[^1].

Another case that matches with the hyper-graph representation is programs. A simple paradigm in programming that shows the unavoidable similarity is a particular coding representation called [Datalog](/patents/gilp-symbolic-machine-learning-knowledge-graphs), the format used to describe all data processing tasks hereafter. A Datalog program is an unordered set of _predicates_ identifiable by the _predicate type_ (or name) and its arity (it's number of arguments). E.g.: the predicate (or fact) `locatedIn(munich, germany)` is identified as `locatedIn/2`. There could be an arbitrary number of occurrences of this predicate type with any arity > 0. 

Datalog may represent control structures usually present in programming, and also may define data. The account given in this writing is about embedding data and programs structures in memory to favor retrievals in efficient way, while keeping encoding complexity at minimum. 

Data may consist of primitive types but also structured types altogether, the only prerequisite necessary is a software facility to identify objects and encode them into integers (hashing) which is not discussed here. The encoding and retrieval mechanisms deal only with integers[^2]. Access to encoded structures is performed by fast hashing or direct memory accesses to guarantee high throughput.

# Data Structure

### `nodes`
type: `{T => Int}`[^3]
This attribute, maps a value passed as input into a integer. This mapping allows to deal only with integers in the encoding and retrieval processes, making the hashing much faster.

### `posPredNode`
type: `[{Int => {Int => Int}}]`[^4]
This attribute individuates a node by starting from its position (array index) in a specific predicate instance. E.g.: `[{0 => {1 => 2}}]` encodes the node `2` in the instance `1` of the predicate type `0` in the position `0` (the array's index).

### `arities`
type: `{(Int, Int) => Int}`[^5]
Specify the arity of a predicate instance. A predicate type might materialize in instances with different arity. Like: `hasProperty/2` or `hasProperty/3` are instances of the same type (`hasProperty`) but different arity.

### `posNodePreds`
type: `[{Int => <(Int,Int)>}]`[^6]
This attribute leads to identify a set of predicate instances linked to a specific node for a given position.

### `nodePredPos`
type: `{Int => {Int => {Int => <Int>}}}`
given a node, its return the mapping of predicate instances and the position where the node is located.

There are other attributes used for adding new edges once the graph is already encoded. They are not essential for encoding graphs from scratch, but they assist on extending encoded graphs. These are:

### `maxNode`
type: `Int`
the max key used in **nodes**.

### `maxPredIns`
type: `{Int => Int}`
the max instance key for each predicate type.

# Constructor

The graph is encoded with the structure depicted above by providing the a set of edges in with or without an already present graph. For simplicity there in only one single case, the injection of new edges in already present graph[^7]. 

Edge $E$ is a tuple with size greater than zero.  The first (foremost left) element is the predicate type (PT) while the subsequent others are _arguments_ of that PT.  
$$ E = (PT, arg_1...arg_n)$$

The input is then a graph $G$ and a set[^8] of edges,  `Input = (G, <E>)`. For each edge in `<E>` the following algorithm should be performed:
1. retrieve/create the predicate type encoding from `nodes`.
2. increment `maxPredIns` value for the specific predicate type.
3. set the edge arity in `arities`.
4. for each argument `arg` at position `pos` in `E`:
	1. retrieve/create the `arg` encoding in `nodes`
	2. set `posPredNode` at position `pos` the map with `predicate_type => predicate_instance` as keys and `arg` as value.
	3. set `posNodePred` at position `pos` the map with `arg` as key and then add the pair `(predicate_type,predicate_instance)` in the corresponding set.
	4. set `nodePredPos` with key `arg` and value a nested map with `predicate_type => predicate_instance` and `pos` as value.

# Query

There are three ways to retrieve data from $G$:
1. get all encoded edges.
2. get all edges that contains a given node.
3. factoid and confirmation edge query.

The (1) could be meaningful for generic purposes of edge flushing. The (2) and (3) are the basic query functionalities that can be extended by downstream processes for wide variety of retrieval, e.g.: multi-hop query composition (joins), query with negations.

### 1. decode graph

get all edges back means to decode the graph into its originating edges as submitted in the constructor.

### 2. node neighbors

The functionality returns edges that contain a given node `node`, either as predicate type or argument.
1. lookup `node` in `nodePredPos` and get the related sequence `predPoss` with elements of type `(pinstance,pos)` where `pos` is the position of `node` in `pinstance`.
2. for each `pinstance` and `pos` in `predPoss`:
	1. look up the `arity` of `pinstance` in `arities`
	2. for each `pred-pos` from `0` to `arity`: 
		1. lookup the node `n2` in `posPredNode` in correspondence of `pred-pos`/`pinstance`
			1. enqueue `n2` as argument of the returning edge.

### 3. factoid and confirmation query

**Factoid** queries are intended to retrieve some specific argument (or set of arguments) by defining placeholders (or variables) such as: `hasProperty(sky, X)`. In this case `X` is a variable the query engine should materialize in a way that the resulting edges exist in the graph. 
[**Unification**]({% post_url 2022-04-26-multi-directionality-prolog %})[^9] is applied when 2 or more variables are identical. E.g.: two points trace a vertical line when they lay in the horizontal coordinate are the same like in `vertical(point(X,Y1), point(X,Y2))`. In this case, `X` is identical in both points. Unification, guarantees in the example that `X` values applied to both `point` predicates are the same. The query process implements unification for this purpose.

**Confirmation** queries answer with a `yes` or `no` to a precise query that does not hold any variable, such as `hasProperty(sky, blue)` in this case the query engine returns `yes`.

The query input is an edge $E$ which arguments might be values or variables. The query procedure might be described with:
1. A recursive function processes every non-variable element $e_i$ in $E$[^10] in sequence and filter edges that match with $e_i$ at the same position $i$. The edges that are filtered are the ones already filtered by previous argument $e_{i-1}$. 
2. The edges filtered by by $E$ arguments are eventually transformed into a matrix with _width_ equals to the arity of $E$, and _height_ as the number of filtered edges. 
3. The matrix's columns that refers to variables in the input $E$ are then extracted.
4. Unification is applied to correspondent identities among values.
5. In case of factoid request the columns variables are returned as solutions to the query.
6. In case of confirmation query, if the matrix is empty then the result will be `no` otherwise `yes`.

# Considerations

### similarity search

This method is not intended to provide functionalities such as fuzzy/similarity search[^11] but the graph data structure would not prevent to easily implement them, since the interface with actual given data is kept in a single mapping storage `nodes`.

### shared value mapping among multiple graphs

The `nodes` graph attribute is dedicated to map the given values with the internal (numeric) encoding. This attribute might be shared - for optimization purposes - by a multitude of graphs while maintaining the uniqueness of `{T => Int}` mapping. 

[^1]: The main difference one might notice is that in a graph, nodes are uniquely identified via an _URI_, while in relational databases the uniqueness is defined via _normalization_.
[^2]: Integers leverage fast hashing. The mapping between internal entities are defined by the high performing [Fast Mergeable Integer Maps](https://www.semanticscholar.org/paper/Fast-Mergeable-Integer-Maps-Okasaki-Gill/23003be706e5f586f23dd7fa5b2a410cc91b659d) algorithm.
[^3]: The `{}` denotes a function that takes an input and return an `integer`. `T` denotes any type. It equals the `Map` data type present in many programming languages.
[^4]: Squared brackets denotes an indexed sequence, in particular it refers to the `array` data type.
[^5]: The type enclosed by `()` is a tuple, in this case is a `pair` of `integer`.
[^6]: The `< >` delimiters indicate a _set data type_ of elements with type `(Int,Int)`, a `pair` of `integer`. Set type guarantees uniqueness of the collected elements.
[^7]: For brand-new graph without prior edges, the already given graph will be just an empty graph.
[^8]: Every $E$ is unique and therefore exists only 1 occurrence $E$ with equal predicate and arguments' values. For specifying more occurrences, it must be made explicitly with further provided information.
[^9]: [Unification (computer science)](https://en.wikipedia.org/wiki/Unification_(computer_science))
[^10]: Where $i$ is the related position of $e$ in $E$.
[^11]: In SQL there are several functionalities as `LIKE` that correspond to that definition.