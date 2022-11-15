---
title: "The Meeting of Minds"
share: true
header:
  og_image: /assets/images/meeting-minds1.png
tag:
  - language
  - conceptual spaces
---
“I’ve broken it off.”

“What do you mean, you’ve broken it off? She was the best thing that ever happened to you. I loved her too, if the truth be known. You’re such an idiot! I have a mind to…”

“I mean I’ve broken the tip off my pen.”

“…Oh.”

Reciprocal understanding is one of the important goals when we communicate and when not properly held back on track, it is a source of confusions. Though we easily recognize when the conversation goes off track, it might be less clear how those communicative processes run, because we’re hardly conscious of the mechanics of the mind working silently under the hood. This post is the first of a short serie, that attempts to shed some light on them with a distillation of ‘Geometry of Mearning’ written by Prof. Gärdenfors, an interesting set of theories that can help us to understand better what semantics is and how we process it. I have found those topics very curious and fascinating to explore, especially in the ultimate goal to build automatic systems that attempt to implement those processes in a similar way we do, for solving problems for us.

## Layers of Knowledge
In the short dialog I used to open this post, Bob and Alice align themselves into a shared knowledge, but sometimes it is far from simple to establish apparently simple truths. Bob is ambiguous in his ‘**I’ve broken off**’; he does not specify what he has actually broken off with, and Alice, the interlocutor, wrongly infers he’s leaving his girlfriend. To create the basis of reciprocal understanding Bob pulls the communication level down and he raises Alice’s knowledge by precising that what is broken is the tip of his pen, and not his relationship. 

I guess everybody agrees on the importance of sharing a common background of knowledge and Bob shows us we shift from a high semantic layer to a lower one to meet our interlocutor and restart the coordination from there. This admits that knowledge lies in [hierarchical structures](http://simonwinter.se/avhandling/intro.html) where the first rung of the ladder is populated with the fundamental and cognitively irreducible terms; they form new and more abstract concepts with increasing abstraction as long as we climb the ladder, in the upper layers. I don’t know whether there is a finite number of layers or it is open-ended, but on a high level of shared knowledge, the communication style would flow according to the ‘_the obvious goes without saying_’, where the implicit understandings are at their maximum.

- Level 3. Non verbal communication. People interact with minimal information exchange.
- Level 2. Instruction. coordination of action is achieved by instruction.
- Level 1. Coordination of the inner world. people inform each other so as to reach a richer or better coordination. It can also be achieved via questions.
- Level 0. Meaning of words. Lowest level people negotiate the meanings of words and other basic communicative elements.


## Communication as Coordination
Language and other forms of communication open the way to various types of engagement. Sophisticated collaborations can enable collective creation of value that can’t be done by single individuals; they’re fruits of communication. If we can summarise in a single word what communication stands for, I would recommend coordination. Coordination among interlocutors is a practical way to point at objects, places or actions, but also as alignment of intents, ideas, persuasion and even entertainment. More generically, coordination is a convergence of mental representations.

If coordination implies a transfer of information, it is expressed in several forms where language is the most rich and expressive one. In the dawn of humanity when the communicative acts became more varied and detached from the immediate and practical purposes, the value of meanings, or semantics, turned out to be more salient in communication. The coordination among participants is an iterative process where the meeting of minds ultimately converge in an alignment of meanings. Sounds almost romantic! As borrowed from maths, the metaphorical meeting point is also named fixpoint. A fixpoint is a value for which a function returns exactly the same value. It is a value with which a specific function works as an identity function. Let’s unfold how the process occurs in our dialog.

![image-center](/assets/images/meeting-minds.png){: .align-center}

Before Bob speeches out with Alice we can assume he has his own imperscrutable beliefs that we can’t have access but he’s willing to express to his friend. Bob pulls out of his linguistic toolset for transforming his ideas into verbal sentences.  Once Bob has expressed his view, Alice can transform such sentences into her inner mental representation.
More mechanically, Bob applies the function `f` into his mental idea and the resulting sentence is passed to Alice, then she applies `g’` and acquires the meaning of that sentence, which is returned back to Bob as she has understood. Alice encodes her understanding into expression, then Bob applies `f’`-  the inverse function (cofunctor) of `f` - to acquire Alice’s expression. If the idea generated in the round back coincides with the original one, the coordination has been successful. If the original and derived ideas don’t overlap, the alignment failed and Bob will attempt to correct Alice.

For it to be effective on how to fix Alice’s misunderstanding, the distance between Bob’s idea and what he derived from Alice’s feedback should be articulated enough to convey a rich sort of information. It should not just give a quantitative distance from the successful fixpoint, and also a qualitative account on how they differ from each other. This is why the discrepancy should be encoded as a vector in order to be also descriptive of the conversation’s status. This introduces the next topic of conceptual spaces, which poses the representation of semantic values in the between of symbolic AI and neural networks.

[Giancarlo Frison](https://gfrison.com)

Credits 
http://fiftywordstories.com/2013/09/11/connell-wayne-regner-oh/ 
