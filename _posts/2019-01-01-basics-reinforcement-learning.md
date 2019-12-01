---
layout: posts
description: Reinforcement learning process rely solely on the evaluation of its actions. It is the carrot and stock metaphor adapted for cold algorithms, instead of living donkeys. This is the first of a short walk-through on building learning agents.
title: First steps in Reinforcement Learning
published: false
header:
  og_image: /assets/rl-survey-giancarlo-frison.png
comments: true
tags:
mathjax: true
---

## Multi-armed Bandit
That's somewhat a hoary term for slot machines, well-known advised to split forever players from their own money.
The motivating story is about a gambler and a row of levels to pull. Given that the gambler doesn't know which bandit has the best payout, or even whether there is one bandit better than another. The gambler has to _explore_ in first place, then he's faced with the dilemma of exploiting the knowledge he got so far, or continuing exploring alternatives for better outcomes in the future. A sequential strategy has to be developed for pulling arms that balance this trade-off and maximize the cumulative payout. Let's see a more concretely what does it mean. Nominating $$Q_n$$ the return value after $$n$$ pulls, it is the average of earnings $$r$$ got during the iterations:

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
