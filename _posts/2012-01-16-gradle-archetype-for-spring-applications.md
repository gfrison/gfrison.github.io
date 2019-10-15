---
layout: posts
description: Gradle archetype useful for creating Java/Groovy applications based on Springframework.
title: Gradle archetype for Spring applications
published: true
permlink: /2012/01/16/gradle-archetype-for-spring-applications
comments: true
tags: archetype, build, continuous integration, development integration, gradle, groovy, integration production, inversion of control, maven, proper plugin, prototype, swiss army knife
---

I am [releasing](https://github.com/gfrison/proto-app) a Gradle archetype useful for creating Java/Groovy applications based on Springframework. Of course, it is not a real archetype because such a creation [is not possible](http://issues.gradle.org/browse/GRADLE-1387). However, with very few steps you can create, edit and deploy an application server. It would be a most accommodating starting point for deployable software projects.

This release is an attempt to mitigate common issues related to development life-cycle phases such as testing, the running of application and deployment in various environments. The archetype leverages upon the flexible building process and on the top-most featured IoC (Inversion of Control) management system.

When creating application modules for linking services through HTTP, JMS or any other connector type, this archetype is refined and can be applied for satisfying these requirements:

- Automatic testing, building and continuous integration.
- A different configuration for each environment (development, integration, production).
- Springframework based system.
- Groovy support.

The project consists of:

- Utility classes for given Spring context
- Grails-like DSL for Spring setup (beans.groovy).
- Logging and application configuration properties for each environment (development/integration/production).
- Gradle config file.

## Why Gradle?

Problems exist using Maven in Groovy projects due to the gmaven plugin, which may indicate that it is not ready for the groovy-user community. Indeed, Gradle works perfectly on Groovy projects. It is so concise and elastic that you don’t have just a building system, you have a programming tool. When a customized behaviours proper plugin cannot be found in the registry, you may add custom tasks by writing groovy code directly to the build.gradle descriptor. Gradle is a swiss army knife for developers.

## Getting started

- Run
{% highlight bash %}
git clone git@github.com:gfrison/proto-app.git myApp
{% endhighlight %}
where myApp is the name of your project.
- Edit property ‘projectName’ in ‘build.gradle’ with project name.
- Add classes, and manage them with spring ‘beans.groovy’.
- You are now ready to test, run and deploy your project through a continuous integration system such as Jenkins.

If you have suggestions, or pull requests from [Github](https://github.com/gfrison/proto-app), myself the author, would be happy to consider them.
