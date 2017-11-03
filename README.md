# CafeX

## Developers Help

Continuous testing 
```
sbt ~test
```

Coverage
```
sbt coverage coverageReport
```
report: target/scala-2.12/scoverage-report/index.html

[OWASP DependencyCheck](https://jeremylong.github.io/DependencyCheck/index.html)
```
sbt dependencyCheck
```
report: target/scala-2.12/dependency-check-report.html

## Requirement

Developer Exercise: Recruitment

Basic exercise
- Use TDD
- Use Git and tag commits (e.g. after step 1)

Complete the steps in order. Don’t read ahead. 

At each step build the simplest possible solution which meets our requirement. 
Tag a git commit after each step so that your approach is clear.

Scenario
Cafe X menu consists of the following items:
- Cola - Cold - 50p
- Coffee - Hot - £1.00
- Cheese Sandwich - Cold - £2.00
- Steak Sandwich - Hot - £4.50
Customers don’t know how much to tip and staff need tips to survive!

Step 1 : Standard Bill
Pass in a list of purchased items that produces a total bill.
e.g. [“Cola”, “Coffee”, “Cheese Sandwich”] returns 3.5

Step 2: Service Charge
Add support for a service charge :
- When all purchased items are drinks no service charge is applied
- When purchased items include any food apply a service charge of 10% to the total bill (rounded to 2 decimal places)
- When purchased items include any hot food apply a service charge of 20% to the total bill with a maximum £20 service charge
