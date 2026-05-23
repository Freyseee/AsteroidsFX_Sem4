Clean -> Install -> exec:exec

To do:
Enemy spaceship that shoots and moves randomly
---
Document
- IGamePluginService
- IEntityProcessorService
- IPostEntityProcessorService 

Then implement Player and Enemy using those services

Implement randomly moving asteroids using the IGamePluginService and IEntityProcessorService

---
Implement a simple collision detection system based on Pythagoras and the provided
IPostEntityProcessorService interface. Ships that collide with asteroids should be destroyed. When fired upon Asteroids should split into two smaller Asteroids and when small
enough they should be destroyed. Last, the player ship and enemy-ships should be destroyed
when hit by each others bullets a certain number of times.

Specify at contract level (operation contracts in unified process), the required and provided
interfaces based on identified components. That is, specify pre and post-conditions for each
operation of the component.
---
Automate the Component Assembly in your own Asteroids game using the build-in ServiceLoader in Java (whiteboard component model).
Please use the ServiceLoader helper class is provided in here in project [AsteroidsServiceLoader]for
inspiration only.

Declare imports and exports in module-info.java files for each module in the Asteroids
game. You can see an example in the Player module ect.
---
Declare provides, with and uses in module-info.java files for relevant modules in the
Asteroids game.
---
Try to rename two classes from two different modules to the same unique class name and see
what happens.

Resolve the issue of the introduced split packages by implementing a JPMS Module Layer.
You can see an Hello World example in the GitHub repo [Split Packages and JPMS layers].

Create a plugins folder in your project root folder and move one of the split package modules
to that folder, see GitHub Branch [JPMSModuleLayers] for inspiration on how to utilize the
ModuleLayer API with the JDK ServiceLoader.
---
Implement the Core component from the Asteroids game using the Spring container and the
Dependency Injection Component Model.
You can combine the use of Java Platform Module System (JPMS) with the Spring runtime
containe. Use the JPMS for reliable configuration of dependencies and for strong encapsulation of components.

Instantiate the Game class and use Spring for Dependency injection of the IEntityProcessors
and IGamePluginServices.

I can recommend the [Spring Reference Manual] for more details about the Spring Framework.

---

Write a unit test for one of the components. For example, write a test for moving the player
ship or a test for collision detection.

---

Create a maven module for a scoring system based on this guide [SpringMicroService].
For inspiration see [MicroServices] branch from GitHub.

Integrate the Scoring MicroService in the AsteroidsGame using the Spring RestTemplate, see
[SpringRestTemplate].