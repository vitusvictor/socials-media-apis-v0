
ABOUT PROJECT:
1. Project is on the Main branch.
2. Program is written in Java 17.
3. Program runs on port 8081.
4. Spring security is integrated. Signup and Signin endpoints are the only open apis.
5. With jwt generated on sign in, you can put it in the bearer token in Authorization to access other endpoints.
6. Replace the database parameters in the application.properties file.
7. Database name is socials-apis.


ARCHITECTURE USED:
MODEL-VIEW-CONTROLLER(MVC):
BENEFITS:
1. Separation of Concerns: MVC separates the application into distinct components,
each responsible for a specific concern. This makes the codebase more organized, 
easier to understand, and maintainable.

2. Modularity: MVC promotes modular development by dividing the application into 
independent modules. Changes to one module have minimal impact on others, 
allowing for easier updates and enhancements.

3. Reusability: The separation of concerns and modularity facilitate code reuse. 
For example, a model can be reused across multiple views or controllers.

4.  Testability: MVC's separation of concerns makes it easier to write unit 
tests for each component in isolation. Models, views, and controllers can be tested 
independently.

5. User Interface Updates: In web applications, changes to the user interface 
(views) can be made without altering the underlying business logic (models and 
controllers).

6. Scalability: MVC applications can be designed to scale horizontally (adding 
more servers) or vertically (adding more resources to a server) based on the 
specific needs of each component.


Design pattern used:
SINGLETON DESIGN PATTERN (BENEFITS):
1. Single Instance: It guarantees that a class has only one instance. This can be 
useful when you want to control access to a shared resource, such as a database 
connection, a configuration manager, or a thread pool.

2. Global Point of Access: The Singleton provides a single point of access to 
its instance, making it easy to manage and control the use of the instance across 
the application.

3. Lazy Initialization: Singleton instances can be created lazily, 
meaning that they are only created when they are first needed. This can improve 
performance by avoiding unnecessary instance creation.