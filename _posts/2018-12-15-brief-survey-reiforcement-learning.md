---
layout: post
description: Can we teach computers to write code? In ICML 2018 an workshop was dedicated on how machine intelligence and reasoning could be capable of creating software applications. Here a short summary of it and what I found interesting in the subject.
title: Reinforcement learning
published: false
image: /assets/dreamcoder.png
comments: true
tags:
mathjax: true
---

reinforcement learning covers a family of algorithms with the purpose of maximize a cumulative reward that an _agent_ can obtain from an _environment_.
It is more like training a dog with a biscuit for complying a desired task, or on paraphrasing an old say, it is like the carrot and stick metaphor transposed on
cold algorithms instead of living creatures. The _agent_ and _environment_ have not been emphasized vainly, but they represent more concretely a vacuum cleaner
sweeping your flat, an A/B testing engine for commerce or a driveless car in a crossroad. If you have heard about latest advances with [AlphaZero](https://deepmind.com/blog/alphazero-shedding-new-light-grand-games-chess-shogi-and-go/) you would know that with an affordable set of hardware (1 TPU and few dozens of CPUs) the [best chess player](https://www.chess.com/news/view/updated-alphazero-crushes-stockfish-in-new-1-000-game-match) in the world could be trained from scratch in just 4 hours.

>“Difficulties strengthen the mind, as labor does the body.”
― _Lucius Annaeus Seneca_

Researches don't lack of challenges in this field. The most important one comes from the intrinsic nature of RL learning process, which rely solely on the evaluations of its actions. All improvements are driven by just one signal, the reward. Rewards are often very sparse. They come after hundred or thousands of steps, dramatically increasing the combination of actions the agent must explore for finding a barely better sequence among of them. If that does not seem arduous, consider also the _non-stationary_ nature of some environments. When the return of an action, in the precisely exact conditions of a past experience, is different from what expected, something has changed in the environment. Thus, the agent must to adapt its behavior dynamically. This is particularly deductive in the case of multi-agent scenario ([MARL](http://www.dcsc.tudelft.nl/~bdeschutter/pub/rep/10_003.pdf)), where the moving parts     does not ease the where conditions change with time and the same action in the exactly same situation can bring different outcomes. Take for example a multi agent scenario, where all parts are learning and consequently changing and adapting their behavior dynamically.

RL helps on creating agents that can autonomously take decisions, this is a shared goal with other families of algorithms, but their working principles are different. Supervised learning could be easily distinguished because it ingests correct actions, but differences with mathematical optimization might appear more subtle.
When considering the [knapsack ](https://en.wikipedia.org/wiki/Knapsack_problem) or the [traveling salesman](https://en.wikipedia.org/wiki/Travelling_salesman_problem) problems all the necessary informations for elaborating the optimal solution are readily there i.e. there is no exploration. Conversely, an RL agent is like a probe on an heavenly body, the assumptions on the environment are nearly absent, and it has t figure out the good and the bad actions only by the feedback from environment, the so called _model free_ learning approach.

## Multi-armed Bandit
That's somewhat a hoary term for slot machines, well-known advised to split forever players from their own money.
The motivating story is about a gambler and a row of levels to pull. Given that the gambler doesn't know which bandit has the best payout, or even whether there is one bandit better than another, he has to _explore_ in first place, then he's faced with the dilemma of exploiting the knowledge he got so far, or continuing exploring alternatives for better outcomes in the future. He has somehow to develop a sequential strategy for pulling arms that balance this trade-off and maximize the cumulative payout. Let's see a more concretely what does it mean. Nominating $$Q_n$$ the return value after $$n$$ pulls, it is the average of earnings $$r$$ got during the iterations:

$$ Q_{n+1} = \frac{r_1 +r_2 + ... +R_{n}}{n} $$

A more scalable formula, equivalent to:

$$ Q_{n+1} = Q_n + \frac{1}{n}(R_n-Q_n)$$

Bring us to a general expression as a fundamental concept in following algorithms:

$$NewEstimate = OldEstimate + StepSize(Target - OldEstimate) $$

## Markov Decision Process
The armed bandit doesn't hold on complex tasks with multiple steps on reaching their goals. Despite in bandit, MDP formalizes the decision making (policy $$π$$)
in a sequence of steps, aggregated in episodes. MDP strives to find the optimal $$π$$ among all possible _trajectories_ the agent can follow.

|:--:|
| ![GridWorld]({{ site.url }}/assets/gridworld.png) |
|:--:|
| *Problem: Find all trajectories towards the flags from any cell* |

Among many examples I may show for explaining what does MDP stands for, this very simple _GridWorld_ conveys the idea of an agent displaced in an 2D environment. The reward drives the agent to move toward the flagged corners regardless the position where the agent is located.  
How the agent could learn the optimal ways? I give you an hint, let's start from the end, the flagged boxes.
In that position you don't have anymore rewards to gather, the task is brilliantly completed, so we assume the terminal's state value is is equal to _zero_.

| ![GridWorld]({{ site.url }}/assets/mdp-t1.png) |
|:--:|
| *Iteration 1. Terminal state value is zero* |

Step back one position, among the actions you can do, the move that catapult you into the terminal state is more rewarding than others (_zero_ instead of _-1_).
Congratulations! You already have solved a part of the puzzle. It is just the last action, you need to solve the whole grid. In that position, as in any other cell of the grid, you need to assign a value to the cell where you are in that particular moment, in order to pave the way to the final goal. That value indicates how good it is to be there. Not all cells carry the same value, some are more valuable than others. The agent, when choosing the next move, will easily move to the cell with highest value, among the surroundings. The value would not be just the reward you get in there (_-1_), but the reward plus the average of the values of the cells proximate to you.
$$ Q_t = r_t + \sum_{n=t+1}{Q_n} $$

| ![GridWorld]({{ site.url }}/assets/mdp-t2.png) |
|:--:|
| *Iteration 1: set the terminal state value to _zero_ * |

Going backward till the initial position, you see all rewards on the way to the terminal state waiting for you to being picked up. This is the value of your state.
You are in the middle of the trajectory towards the end, and the value of your state is equal of the reward in that state plus the sum of the discounted rewards thereafter.
Clearly, the agent moves toward the most promising among the surrounding cells, once it has realized their value.  Let's keep going.
The grid starts to unveil the optimal trajectories by just going backward and evaluating what could be the best move, taking the cell with highest value.
A pattern is identifiable, something that programmers knows well, the _recursion_, and simplified as:
$$Q_t=r_t+argmax(Q_{t+1})  $$
