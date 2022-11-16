---
title: "Foraging User Preference Information for E-Commerce Product Recommendation"
excerpt: "Automatic system for maximizing user pereferences' gathering for optimizing eCommerce product offering"
date: 2021-04-06 
header:
  # image: /assets/images/foraging.png
---
|Id|Status|Submission Date|Activation Date|Authorship|
|-|
|[US10970241B1](https://patents.google.com/patent/US10970241B1)|Granted|{{ "2019-09-26" | date_to_string }}|{{ "2021-04-06" | date_to_string }}|100%|

Matching complex user needs with multi-attribute products remains a problem in the modern e-commerce. For purchasing complex products, buyers are supposed to have prerequisite knowledge of the domain of the product, in order to express exhaustively the technical terms such product should have to satisfy their needs. However, experience reveals that this is far more than what can be expected from the average customer.
With this invention, customers are driven automatically throughout a soft questionnaire about everything might affect their preference on product’s features. Users are inquired for providing informations not only on the desired product’s features, but also about their profile as customers. Questions can spare in all directions that might provide useful hints on what they ultimately need.
Once the system has acquired sufficing knowledge about the user, the target product will be recommended to the user. This idea contributes to make it easier for e-commerce providers to fill the demand that is usually solved by human operators, the sellers. 

## Domain entities

The central pillar of the system are the domain entities. They define conceptually the target product model. During my research, I defined two entities that were significant for the goal of the project. They are: user profile and the car profile.

Entities might be defined as a set of attributes, each of them is specified along a probability distribution. Attributes might have a continuous or discrete nature. A continuous variable could be defined as a Gaussian distribution (by the median μ and the variance σ), while a discrete variable could be defined as a probability distribution over discrete classes. For example, a car’s price could be a probability dis- tribution among the following classes: cheap, expensive or middlePrice. In this case study all attributes are discrete.

User profile

|Property|Type|Values|
|-|
|hasFamily|Discrete|Bool|
|status|Discrete|poor, collar, rich|
|carForWork|Discrete|Bool|
|longDistance|Discrete|Bool|
|spender|Discrete|ightwad, unconflicted, spendthrift|
|buyWithCash|Discrete|Bool|
|age|Discrete|young, middleAge, old|

Car

|Property|Type|Values|
|price|Discrete|cheap, middlePrice, expensive|
|performance|Discrete|low, middle, high|
|type|Discrete|saloon, estate, coupe, cabrio, hatchback|
|engine|Discrete|otto, diesel, electric|

## Mapping entities

The mapping mechanism should be employed for converting some acquired knowledge into the target entity. In the business problem mentioned above, it is quite convenient to transform the user profile into a car profile, the target product. For this purpose, Bayesian conditional probability is applied for mapping, e.g. the spender field of the user profile within the car’s price.user.spender → car.price. An arbitrarily mapping could look like this:

Conditional probability table (CPT)

|user.spender|car.price|
|-|
|tighwad|cheap:.5, middlePrice:.4, expensive:.1|
|unconflicted|cheap:.2, middlePrice:.6, expensive:.2|
|spendthrift|cheap:.1, middlePrice:.4, expensive:.5|

Every property is composed by a set of classes, each of them has a probability $$ p (0 ≤ p ≤ 1) $$ and the sum of them converge to 1. Any field in the source entity (User) could influence any field in the target entity (Car). Therefore, the maximum number of mappings is the fields’ length of the source entity multiplied the fields’ length of the target entity ($$ User.properties.length · Car.properties.length $$)
Since there could be as many Car.price as the size of User fields, the aggregated target field will be an average of all CPTs for that field.

## Compose question/answer list

The implementer should be able to formalize a list of interactions (questions) by which the user can provide informations to the system (answers). Each user reply affects one, some or all the domain entities described above.

_Question:_ Do you have any trouble limiting your spending?

|Answer|Influence|
|rarely|user.spender ← (tightwad: .7, unconflicted: .2, spendthrift: .1)|
|sometimes|user.spender ← (tightwad: .1, unconflicted: .8, spendthrift: .1)|
|often|user.spender ← (tightwad: .1, unconflicted: 2, spendthrift: .7)|

The answer’s _influence_ will be taken and averaged (section 2.1) with the contextual entity, initialized at the beginning of the session.

## Decision engine

The decision engine system should be able to:
- Quantify the amount of knowledge acquired for profiling user preferences. 
- Vote which question would be prompted at the next iteration.
- Rank products according to the user profile/preferences.

## Knowledge quantification

At the beginning of the session, the domain entities could be initialized:

1. Set all probabilities equally. E.g. user.spender ← (tightwad:.33, unconflicted:.33, spendthrift:.33). 
2. Balance probabilities according to marketing researches, or according to subjective assumptions.

The level of acquired knowledge is quantified by the entropy of the probabilistic distribution of all properties in the domain entity.

$$ Entropy H(X) = − ∑ p(X) log p(X) $$

The entropy reaches the maximum when chances of any possible state are equally distributed, like in the case (1). Conversely, entropy is minimum when all probabilities shift into only one class. Thus it is possible to quantify, in probabilistic manner, how much the system knows about a particular preference. For domain entities, the aggregated entropy is the average of the entropy of every single property.

## Question selection (Minmax algorithm)

The purpose of the Decision Engine is to minimize the overall entropy of the domain entities. Therefore, during the interactive session with the user, the system will choose among the list of question/interaction (QS) the question (Q) which answers (a) minimize such entropy.

$$ NextQuestion = {Q|argmin{H(a)∀a ∈ (∀q ∈ QS)}} $$

## Rank target product

The product profile which could satisfy the user could be deducted:
1. By direct preferences on the type of the product. E.g.: the engine type of a car. 
2. Inferred by the acquired knowledge during user interactions (domain entities).

In the case (2) the target product profile is deducted by applying Bayesian conditional probability de- scribed in the section 2. In (1) the acquired information is still hold as probability distribution but it has higher relevance than in (2) because it is a direct preference and not deducted by CPT. (1) entropy is lower than (2).
The suggested product will be the closest to the acquired entity, among all products. For quantifying that relatedness, a simple euclidean distance function is employed.

## Conclusion

This method brings probabilities in account of recommendation for product purchasing. Any decision over product selection is conveyed with a grade of confidence which quantify how much likely the system is able satisfy customer’s needs. Operators can easily introduce knowledge (common sense know-how, marketing researches) into the system by defining proper CPTs. All knowledge entries are transformed into a single target profile, in a very scalable solution by Bayesian calculations. The decision engine can estimate how the session within the user should be conducted.