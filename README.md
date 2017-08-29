# JMX-MBean

JMX managed bean example application. Message source produce messages and send them to message broker.
Broker just store messages in queue and don't process anything.

### MBean convention
1. same package
2. MBean postfix
3. TestClassMBean must be extended by TestClass
4. get method prefix for attributes
5. jdk types only

### Run
`sbt run`