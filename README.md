# JMX-MBean

JMX managed bean example application. Message source produce messages and send them to message broker.
Broker just store messages in queue and don't process anything.

### MBean convention
1. Same package
2. MBean postfix
3. TestClass must extent TestClassMBean naming
4. get method prefix for attributes

### Run
`sbt run`