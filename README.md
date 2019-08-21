# entity-events-and-auditing
[![Build Status](https://travis-ci.org/sharmashashank342/entity-events-and-auditing.svg?branch=master)](https://travis-ci.org/sharmashashank342/entity-events-and-auditing)
[![Coverage Status](https://coveralls.io/repos/github/sharmashashank342/entity-events-and-auditing/badge.svg?branch=master)](https://coveralls.io/github/sharmashashank342/entity-events-and-auditing?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/in.techpal/entity-events/badge.svg)](https://maven-badges.herokuapp.com/maven-central/in.techpal/entity-events)

`spring-boot` Library for **`auditing`** and **`realtime spring application events`** of every entity **save** or **update**

## License

Project is licensed under **Apache Software License, Version 2.0**.

# Dependencies
Spring Boot

Spring Data JPA

Hibernate

# Version used

We used `spring-boot-starter-parent` version `1.5.21.RELEASE` as parent and will upgrade versions in later releases


# Description

In Spring Data JPA, many times we require auditing of tables or `entities` and also want to **sync** the same `entities` to any **`NoSQL`** Datasource for **`CQRS`**

In Those cases, this library will be a great help.

It works based on [**`Hibernate Entity Events`**](https://docs.jboss.org/hibernate/core/4.0/hem/en-US/html/listeners.html)


The Events are raised after you call any of the below methods available in JPA

- ```org.springframework.data.repository.CrudRepository#save(java.lang.Object)```

- ```org.springframework.data.jpa.repository.JpaRepository#save(java.lang.Iterable)```


# When to use

For **Auditing** and **Listening Entity Events** (Create or Update currently Supported)


## Support
If you need help using the project feel free to drop an email or create an issue in github.com (preferred)

## Contributions
To help development you are encouraged to  
* Provide suggestion/feedback/Issue
* pull requests for new features
* Star :star2: the project


[![View My profile on LinkedIn](https://static.licdn.com/scds/common/u/img/webpromo/btn_viewmy_160x33.png)](https://www.linkedin.com/in/sharmashashank342)

 