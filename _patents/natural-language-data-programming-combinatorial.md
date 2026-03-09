---
title: "Bridging Natural Language with Data Programming for Combinatorial Problems"
excerpt: "For combining the common sense and the generative power of LLM with a symbolic engine able to solve computational intensive problems in a precise and predictable way. Those intrinsically different systems can be integrate by means of a simple, concise and declarative language: Kubrick. The language must be relatively easy for LLMs to generate from a natural language request with minimal instruction (zero-shot learning) without any specific training. At the same time, the declarative language should allow the definition of the goals and the constraints, without having to worry about the implementation details, which are the realm of the symbolic engine"
date: 2026-03-09
last_modified_at: 2026-03-09
permalink: /patents/bridging-natural-language-with-data-programming-combinatorial
header:
  og_image: /assets/patents/kubrick-combinatorics/main.png
tags:
 - optimization 
 - programming
 - NLP
 - logic programming
 - combinatorial problems
---
| Id                                                                | Status    | Submission Date | Grant Date  | Authorship      |
| ----------------------------------------------------------------- |
| 19/365,428 | Pending | {{ "2025-10-22" | date_to_string }} |  | 100% |

The term combinatorial problems refer to a broad range of problem classes that involve finding a solution from a finite set of possibilities. Each solution is distinct and countable, as they exist within a discrete solution space. 

These problems can be categorized as **decision problems** (answering "Is there a solution that satisfies the given constraints?"), **optimization problems** (seeking the best solution according to some criteria), or **counting problems** (determining the number of solutions that meet the constraints). These categories form a hierarchy: an optimization problem can only be formulated if a solution exists, and solutions can be enumerated, which leads to the counting family of problems.  

An example of a decision problem is the graph coloring problem, which asks whether the vertices of a graph can be colored with a given number of colors so that no two adjacent vertices share the same color. The problem can be turned into an optimization problem by minimizing the number of colors used, or into a counting problem by asking how many solutions exist with a given number of colors.  

# What is ASP?

The purpose of [answer set programming (ASP)]({% post_url 2022-04-27-how-programming-system-logic-programming %}) is to generate combinatorial solutions over an initial base of facts and rules. What differs ASP from other logic programming languages like Prolog is that ASP aims to generate stable models that satisfy all generation principles and constraints. An ASP runtime will terminate only when no new solutions will be possible to generate from those already found, or when no solution exists. 

This is the crucial difference from Prolog, which computation's is initiated by a query that can be validated/invalidated or can bind proper variables to atoms in the database, rather than generating multiple instances of facts. 

ASP is then suited for combinatorial problems, and its language reflects this purpose. The specific affordance is given by the choices, a syntactic feature that describes how single atoms may spark the ramification of the solutions.  

Consider the following code: 
```asp
color(red; green; blue). 

node(X) :- edge(X, _); edge(_, X). 

{ hasColor(N, C) : color(C) } = 1 :- node(N). 

:- edge(A,B), hasColor(A,C), hasColor(B,C).
```

The third row establishes the generation of multiple solutions by indicating that each node will ramify into as many solutions as the color cardinality, and in each of those solutions, the node will have different colors. This is the generational part of the program. The last row instead will prune out all solutions on which adjacent nodes have the same color. This is defined by using the same variable `C` for both nodes, here the engine will attempt to unify the nodes with the same color, and if it occurs, the solution is discarded. 

With this short contextualisation, I describe the phases of solving a combinatorial problem.

# Grounding

Grounding is the process of creating a superset of candidate solutions from the original problem space. This involves aggregating items to satisfy certain compositions. These aggregations can be defined as sets (combinations) or sequences (permutations). This pre-processing step can be extremely demanding in terms of computational resources. The term _combinatorial explosion_ describes how a small increase in problem size can cause some problems (especially counting problems) become intractable. 

In [Kubrick]({% post_url 2026-01-31-pull-down-programming-complexity-kubrick %}), the grounding phase is managed by the generators. They generate a stream of facts according to a given criterion. The criteria are a set of declarations (Repetitions and Partitions) over variables. Let's assume we need to model a restaurant, where a dish is a restaurant's offering: 

```
# Italian Dishes 
dish bruschetta  course -> appetizer price -> 8.50  rating -> 4 origin -> italian 
dish caprese     course -> appetizer price -> 9.00  rating -> 5 origin -> italian 
dish lasagna     course -> main      price -> 15.00 rating -> 5 origin -> italian 
dish carbonara   course -> main      price -> 14.00 rating -> 4 origin -> italian 
dish tiramisu    course -> dessert   price -> 7.50  rating -> 5 origin -> italian 
dish panna_cotta course -> dessert   price -> 7.00  rating -> 4 origin -> italian 
 
# German Dishes 
dish kartoffelsalat course -> appetizer price -> 7.00  rating -> 4 origin -> german 
dish bretzel        course -> appetizer price -> 5.00  rating -> 3 origin -> german 
dish schnitzel      course -> main      price -> 18.00 rating -> 5 origin -> german 
dish sauerbraten    course -> main      price -> 20.00 rating -> 4 origin -> german 
dish apfelstrudel   course -> dessert   price -> 8.00  rating -> 5 origin -> german 
dish schwarzwälder  course -> dessert   price -> 9.00  rating -> 5 origin -> german  
```

The restaurant needs to create different combinations of menus all consistent with their origin and with only one dish per course: 
```
menu dish -|1_Origin, 1#Course| dish Dish course -> Course origin -> Origin 
```

The rule above differs from its canonical implication (syntax: `CLAIM -| PREMISE`) by injecting special information regarding the generation of possible combinations. In particular, the variables of `Origin` and `Course` are interested in this phase: the `Origin` has a Partition constraint, while the `Course` is constrained in its Repetition. This generator definition (`1_Origin, 1#Course`) could be translated as: 
> take only 1 origin and 1 repetition of unique courses, at time for each solution. 

| Solution 1 |             | Solution 2   |             |
|------------|-------------|--------------|-------------|
| **dish**   | **course**  | **dish**     | **course**  |
| brushetta  | appetizer   | bretzel      | appetizer   |
| lasagne    | main        | schnitzel    | main        |
| tiramisu   | dessert     | apfelstrudel | dessert     |

*Table 1. Multiple solutions can be flushed on request. The query will run over each of them separately.*

# Partitions

With the syntax `min_variable_max` it is intended that the values of variable are partitioned in way that each solution will hold at least min occurrences of variable unique values (independently by whether they are repeated or not) till a maximum of `max`. If `min` is specified but `max` isn't, then `max` is intended to be set with the `min` value, and vice-versa. In the example above, the `Origin` has minimal and maximal occurrences set to one. Therefore, a solution may contain either italian or german dishes, not both. 

# Repetitions

Repetitions state how many _unique instances_ of values carried by a variable must be used for each Solution. Above, the `1#Course` defines that there must be strictly one occurrence of course values. The number of repetitions is configurable `min#variable#max` where `min` is the minimum number of unique instances of a variable accepted in a Solution. The omitted `max` or `min` follows the pattern applied for Partitions.

# Combining Generators

Generators might be combined in an arbitrary way, like in the following sample, where we have a set of students with different skills and projects with different complexity. We want to form teams for each project with max two students with required skills: 

```
student alice   beginner 
student tom     beginner 
student bob     advanced 
student cynthia advanced 
student alissa  advanced 
project quantum advanced 
project tictac beginner 
 
team Student Project -|Student_2, Project_1| student Student Level, project Project 	Level 
 
?team Student Project
```

There will be 1 solution for the tictac project with alice and bob, and 3 solutions for the quantum project, for all combinations:  `binomial(2,3)=3`. 

Generators can be concatenated one after another. In addition to the multiverse setup for menu, in cascade more versions of data could be generated. If the restaurant has wine in the cellar and what to enrich the menu offering with a bottle of wine: 
```
wine brunello   type -> red 
wine lagrein    type -> red 
wine prosecco   type -> white 
wine vermentino type -> white 
recWine Wine -|1#Wine| wine Wine
```

# Filters

The prune out Solutions based on criteria. If we want to show in the economy section of our offerings only those menus cheaper than 30€: 
```
\ menu Dish, dish Dish price -> price, < (group sum Price) 30
```

A Premise prefixed by `\` is a positive filter that lets pass those Solutions that succeed with the Premise. A negative filter is declared with “`!\`” prefix and let pass those Solutions that _fail_. The following example is equivalent with previous one: 
```
!\ menu Dish, dish Dish price -> price, >= (group sum Price) 30 
```

In the restaurant domain, if we need to properly associate the `recWine` according to the type of main dish, we should _exclude_ all combinations where a red wine is associated with `fish` or a `white` wine is paired with a `meat` course: 
```
!\ menu Dish, dish Dish course -> main content -> meat, recWine Wine, wine Wine type -> white  
!\ menu Dish, dish Dish course -> main content -> fish, recWine Wine, wine Wine type -> red  
```

# Multi-objective Optimization

[Multi-objective optimization]({% post_url 2018-06-28-first-steps-evolutionary %}) addresses the challenge of simultaneously satisfying several, possibly conflicting, goals. There are 3 types of optimizations: 

1. **Fully strict**. All criteria are strictly prescriptive. That means only solutions that maximize every criterion are passed. 
2. **Hybrid strict/Pareto**. Some criteria are strict, others are Pareto driven. The Pareto optimizer allows you to select solutions that are optimal for all Pareto criteria. 
3. **Fully Pareto**. Solutions that turn out to be optimal are collected (frontier) and flushed out when all solutions have been fetched. 

In (1) every optimizer is independent from the others and since the solutions are incrementally generated on demand, the optimizer does not have to hold the entire history of solutions to perform its function. The strict optimizer just holds the best score which compares the new incoming solutions. In (2) and (3) the system will find the Pareto optimal set of solutions. The Pareto optimizer will consider the optimal optimizers all together. Unlike strict optimizers, the Pareto optimizer keeps track of the optimal set. When a new incoming solution will be fetched there are three possible cases: 

- **If it dominates** (i.e.: the new one is better in all optimizations) one of the optimal sets, the dominant solution will be dropped in favor of the new dominating one. 
- **else** if it is better in at least one optimization, it will be added to the frontier. 
- **Otherwise**, it is discarded. 

The Pareto optimization will then fetch _all_ solutions before flushing the frontier, differently from the strict optimizer that just filters in/out solutions if they are generated. Those considerations imply that in (1) setup the streamed solutions will be _monotonic_ in respect of the optimization criteria, i.e.: the solution score will only increase, and the last flushed solution will be the best one. Instead, in (2) and (3) the streamed-out solutions will be already the optimal ones, and they will be flushed out only when the last solution has been generated. 

The syntax for optimizers: `\OPTIMIZATION PREMISE`, where `OPTIMIZATION` is: 

- `>>` strict maximization 
- `<<` strict minimization 
- `>` Pareto maximization 
- `<` Pareto minimization 

`PREMISE` is the function to maximize/minimize. In the restaurant example, if we need to just get the cheapest menu: 
```
\<< menu Dish, dish Dish price -> Price, group sum Price
```

If we need to optimize (Pareto) for lower price and higher rating:
```
\< menu Dish, dish Dish price -> Price, group sum Price 
\> menu Dish, dish Dish rating -> Rating, group sum Rating
```

Pareto optimization will occur only when there are at least two Pareto optimizers, otherwise the optimizer would be treated as a strict optimizer. 

# Lazy Computation

Within this section we have found that each solution is basically a computation performed over a single version of the database. Less noticeable but equally important, is that the entire system is based on the solution streaming paradigm. The system applies to the concept of **call-by-need** typical of functional programming, similarly to what is discussed for [lazy stream solutions](/patents/hypergraph-machine-learning) in the hypergraph machine learning patent. Each solution is computed only when requested by the client. This does not prevent fetching the entire solution space, but it is useful when only one or few solutions are needed because the system does not have to compute all solutions in advance. Graphically, it is evident in the solution section. If a next link appears, it means that the end of the available solution has not been reached, and the next one - if any - will be elaborated. 
 