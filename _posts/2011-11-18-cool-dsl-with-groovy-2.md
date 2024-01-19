---
permalink: /2011/11/18/cool-dsl-with-groovy-2
layout: single
title: Cool DSL with Groovy
published: true
permlink: /2011/11/18/cool-dsl-with-groovy-2
comments: true
tags: boilerplates, ddd, domain, domain expert, domain problems, domain specific language, dsl, dsl experience, foundation code, groovy, java, java enterprise edition, mda
---
Domain model design has never been confused with ‘ease’. From the dawn of its conception, generating executable Unified Modeling Language (UML) diagrams meant sweat and frustration. Generating stubs & skeletons, alone, led one into quagmires of Java Enterprise Edition. Yet it is inherently possible — and actually has been for ages — to design a programming language dedicated to solve specific domain problems; effortlessly and quickly.

DSL is not a foreign thing. Developers are steeped in DSL, even if unwittingly. Frameworks are DSL. A macro is also DSL.
When you write a function? That too is steeped in DSL. But functions are often a dirty business; un-standarized; quirky, whereby even domain experts face a daunting task when unraveling and then being put to task to update such rapscallion boilerplates (that is if they do not themselves compound the problems).
A programmer worth his reputation would find it necessary to isolate and clean up all non specific domain terms within the DSL. This literally means rooting through and setting aside all secondary-in-importance setup code, allowing the domain expert to establish or reestablish the primary foundation code.
This is most times done for large money for an end user; the helpless customer.

>Given an appropriate DSL that fits their needs, customers could write all of the
code that they need themselves, without having to be programmers.

Now let us imagine, or better, take for granted, that a good DSL experience can exists.
That given an appropriate and standardized DSL environment, even the customer,
with a few simple tools and grasp-of-concept, can self-write code, gracefully, and
specific to their needs without the cost and hassle of having to become, or running to,
programmers. And that is where Groovy comes in.

## What’s Groovy?
It’s Java as it should be. Easy and intuitive, it offers new features unknown to its parent yet (I’m still waiting for release 7), and come up with those benefits that form the basis of the DSLs that we will develop.

A fundamental feature is the MOP, the ability of changing runtime the properties and the behaviour of objects. It allows us to respond to method calls that do not exist in the class, in other word to “pretend” that these methods exist.
Another essence to consider is the Closure. It’s the real power of Groovy. The extreme flexibility of this kind of object/method allows us to change its behaviour replacing its delegate class, on the fly.

## Closure delegate

Groovy has three variables inside each closure for defining different classes in its scope: this, owner, and delegate.
The this variable refers to the enclosing class. The owner variable is the enclosing object of the closure. The delegate variable is the same as the owner, unless that delegate is substituted.
{% highlight groovy %}
class MyClass {
  def closure = {
    println "this class:"+this.class.name
    println "delegate class:"+delegate.class.name
    def nested = {
      println "owner nested class:"+owner.class.name
    }
    nested()
  }
}
def tryme = new MyClass().closure
tryme.delegate = this
tryme()

//output:
//this class:MyClass
//delegate class:Script1
//owner nested class:MyClass_closure1

{% endhighlight %}
When a closure encounters a method call that it cannot handle itself, it automatically relays the invocation to its owner object. If this fails, it relays the invocation to its delegate. one of the reasons builders work the way they do is because they are able to assign the delegate of a closure to themselves.