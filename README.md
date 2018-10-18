## mason

[![Build Status](https://travis-ci.org/metamug/mason.svg?branch=master)](https://travis-ci.org/metamug/mason) 

Mason is the open-source, ultra-lightweight data access layer for REST resources. It handles incoming API requests and routes the parameters to these resources.

### Mason Resources

Mason resources are Plain Old JSPs with neat tag libraries. Mason doesn't encourage [using scriptlets in Resource JSPs](http://balusc.omnifaces.org/2010/07/how-to-avoid-java-code-in-jsp-files.html). 

### Mason Query

Currently you can write inline queries in the resources. But we are working towards moving the queries outside the JSPs, so you can reference them and queries become reusable. This will be done via query files.

#### How does Mason run APIs ?

- Request Processing
- Routing to REST Resources
- Authentication
- Invoke [Execute](https://metamug.com/docs/code-execution.php) Tag classes.

> In short, APIs generated by Metamug will not work, if there isn't mason jar in the libs folder.

### Mason Jar

![Mason Jar](http://www.hamptonart.com/image/cache/data/2015WEBPHOTOS/PS0927_MasonJar_BL-500x500.jpg)

Clone and package the project with <a href="https://maven.apache.org/download.cgi" target="_blank">mvn</a>

```
mvn clean package
```
After that you can find *mtg-mason-1.0.jar* inside the target folder. You can use this jar as a dependency in your Java webapp.

### Webapp Configuration

Import mtg-mason.tld inside your jsp file. This taglib is present inside the mason jar and enables usage of the *mtg* prefix. 
```  
<% @taglib uri="mtg-mason.tld" prefix="mtg" %>
```

And also add the following filter to your *web.xml*
```
<filter>
    <filter-name>RestRouterFilter</filter-name>
    <filter-class>com.metamug.api.filters.RestRouterFilter</filter-class>
</filter>
```
All the REST API calls to resources are routed through this filter. 

### Dependencies

mtg-mason-x.x.jar is shipped along with MTG SERVER and is present in all the backends generated via console.
Due to this reason most of the dependencies have `<scope>provided</scope>`.
Since all those dependencies are present in server lib.
In case if you were to develop your own backend using this library, you'll have to comment out the <scope> tag of nearly all the dependencies.

### JDBC Drivers

Except for javaee-web-api since that would be present in your application server and any one out of HSQL,MySQL or PostgreSQL dependency.
We also support Oracle database but due to licensing reason we can't ship oracle jdbc driver along with our MTG SERVER.
So in case you are using Oracle database you'll have to manually install its driver as dependency and use that in this project.
Instructions are given regarding how to do the same in below link(s) (You can refer either of them).

https://www.mkyong.com/maven/how-to-add-oracle-jdbc-driver-in-your-maven-local-repository/
					OR
https://stackoverflow.com/a/1074971/4800126

### Data Format Support

Mason supports `application/xml`, `applicaton/json` and `application/json+dataset` 
Read More about how it is used here.
https://metamug.com/docs/api-request
