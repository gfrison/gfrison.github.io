---
layout: post
description: I have released an application demonstrating how SOA’s principles can be applied into a small project making use of EIP (Enterprise Integration Pattern), IoC (Inversion of Control), and a building tool and scripting language such as Groovy.
title: SOA example application
published: true
permlink: /2012/01/25/soa-example-application
image: /assets/soa-reward1.png
comments: true
tags: soa, groovy, microservice
---

>SOA describes a set of patterns for creating loosely coupled, standards-based business-aligned services that, because of the separation of concerns between description, implementation, and binding, provide a new level of flexibility.

Service Oriented Architecture terminology has spread in recent years, at least among people who were involved in most of the Information Technology activities. The guidelines suggested by this methodology are granted as major factors to succeed in different distributable systems domains.
Just as the definition is clear and easy to understand, so is its implementation into a real project, being intuitive, concise and elegant.

I have released an application demonstrating how SOA’s principles can be applied into a small project making use of EIP (Enterprise Integration Pattern), IoC (Inversion of Control), and a building tool and scripting language such as Groovy.
I analized a simple business case: an entertainment provider who wanted to dispatch rewards and bonuses to some of its customers, depending on customer service’s subscriptions.
The process sequence is simple:

<center><img title="Rewards" src="{{ site.url }}/assets/soa-reward1.png"/></center>

>It is required to provide an implementation of a RewardsService. The service accepts as input a customer account number and a portfolio containing channels subscriptions. The Customer Status team is currently developing the EligibilityService which accepts the account number as an input.

I set up an infrastructure to write acceptance tests for this first meaningful feature. This is what could be defined as a “walking skeleton,” a prototype with the essential aspect that it could be built, deployed and tested after being easily downloaded from [Github](https://github.com/gfrison/rewards).

<center><img title="Rewards" src="{{ site.url }}/assets/soa-reward2.png"/></center>

[RewardService](https://github.com/gfrison/rewards/blob/master/src/main/groovy/com/gfrison/services/RewardService.groovy) is invoked by the client and it calls, in turn, the eligibility service which however, in this case is not  implemented. As many real scenarios expect external services, this proof-of-concept refers the eligibility service to a black-box, where only request/response interface is known.

The [unit test](https://github.com/gfrison/rewards/blob/master/src/test/groovy/com/gfrison/RewardServiceTest.groovy) simulates the eligibility service behaviors mocking the end-point through the Camel Testing Framework. However, if you want to run the application on your local machine I set up, within a line of code, a faux eligibility service that merely returns a positive response:

{% highlight groovy %}
def alwaysEligible = {exchange ->
  if(exchange){
    exchange.getOut().setBody('CUSTOMER_ELIGIBLE')
  }
} as Processor
{% endhighlight %}

The entry point is an HTTP Restful interface built upon the Apache CXF, and is easily set up within few lines in the [configuration](https://github.com/gfrison/rewards/blob/master/src/main/resources/conf/beans.groovy). CXF is initialized by Spring in this following way:

{% highlight groovy %}
jaxrs.'server'(id:'restService',
  address:'http://${http.host}:${http.port}') {
    jaxrs.'serviceBeans'{ ref(bean:'rewardService')}
}
{% endhighlight %}

Services are connected by Apache Camel. RewardService contains only the reference of the ESB context –  an instance of [ProducerTemplate](http://camel.apache.org/producertemplate.html). Such solution allows a complete separation between the linking system and the business services. The Camel context represents  the SOA’s wiring, and is configured through a DSL as in the example below:

{% highlight groovy %}
from('direct:rewards').to(eligibilityServiceEndpoint)
{% endhighlight %}
