# entity-events-and-auditing

`spring-boot` Library for **`auditing`** and **`realtime spring application events`** of every entity **save** or **update**

# Description

After we call 
`org.springframework.data.repository.CrudRepository#save(java.lang.Object)`

or 

`org.springframework.data.jpa.repository.JpaRepository#save(java.lang.Iterable)`

it saves the object to DB as well as it will also create a copy of saved entity snapshot in `ss_audit` table as well


# Version used

We used `spring-boot-starter-parent` version `1.5.21.RELEASE` as parent and will upgrade versions in later releases
