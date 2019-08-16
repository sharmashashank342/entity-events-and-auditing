# entity-events-and-auditing
[![Coverage Status](https://coveralls.io/repos/github/sharmashashank342/entity-events-and-auditing/badge.svg?branch=master)](https://coveralls.io/github/sharmashashank342/entity-events-and-auditing?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

`spring-boot` Library for **`auditing`** and **`realtime spring application events`** of every entity **save** or **update**

# Description

After we call 
`org.springframework.data.repository.CrudRepository#save(java.lang.Object)`

or 

`org.springframework.data.jpa.repository.JpaRepository#save(java.lang.Iterable)`

it saves the object to DB as well as it will also create a copy of saved entity snapshot in `ss_audit` table as well


# Version used

We used `spring-boot-starter-parent` version `1.5.21.RELEASE` as parent and will upgrade versions in later releases
