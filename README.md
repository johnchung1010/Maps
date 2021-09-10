#Maps 2 - Naveen Sharma, John Chung

NOTE: The maps.sqlite3 database did not push to GitHub when we uploaded our code
(maybe the file was too large). If possible, please add the maps.sqlite3 file to
the ./data/maps directory for our tests to run properly.

Partner Division of Labor

For the map, nearest, and ways commands, we used pair programming and implemented
these commands together. For the route command, we came up with pseudocode for
Dijkstra's algorithm together. Naveen then implemented both route commands,
while John worked on fitting the project's codebases together so that John's REPL
worked with Naveen's KDTree. John then worked on A* and caching while Naveen did
the SQL injections and testing. We worked on the README together.

Known bugs

There are no known bugs in our code. As for known CheckStyle Errors, in Node.java,
we are getting magic number errors. However the numbers are just the indices of the
columns of the results for our SQL query.

Design Details

Our Maps project uses John's REPL and Naveen's KDTree. However, inspired by each
other's code, we modified both the REPL and the KDTree so that they were hybrids
of our code. For example, John's REPL used to be a function in Main, whereas
Naveen's REPL was its own class. Although we used John's REPL, we turned it into
its own class. Similarly, Naveen's KDTree was unbalanced while John's KDTree was
balanced. Although we used Naveen's KDTree, we modified it's implementation so
that it would be balanced.

To make our AStar algorithm generic, we created 2 abstract classes (GenericNode and GenericEdge) which represented nodes and edges in a graph. The GenericNode class featured an abstract method getEdges() which would return all connected edges to the node. In the case of this project, a SQL query would be run to get the connected edges. However, thanks to the abstract class, our algorithm can work with any type of edge retrieval. Because of this, our AStar algorithm is completely independent of SQL querying. The GenericEdge class featured an abstract class called getWeight() which would compute and return the weight of the edge. This way, our algorithm could also be completely independent of how weight was calculated. For this project, Haversine distance was used. For the Maps implementation, two classes (Node and Way) were created which implemented GenericNode and GenericEdge respectively.

To incorporate caching, we created a Cache class that contains a Hashmap that maps nodes’ IDs to sets of ways whose start or end nodes is the node that corresponds to the id. We used the Singleton pattern so that calls to the Cache object refer to the same instance, and therefore preserve each update to the Hashmap. Since caching is used to obtain a node’s ways, the cache is called in the getEdges() in the Node class. If the ways for the node have already been queried, then the cache will return the corresponding ways. Otherwise, the function calls a SQL query and stores the output in the cache. Since a node’s set of ways only changes when a new database is loaded, the Cache is reset whenever the map command is called.

Runtime/space optimizations Beyond the Requirements

N/A

How to Run Our Tests

To run the ta tests provided, the command
“./cs32-test tests/ta/maps/maps1/*test” can be run

To run the system tests we created, the command
“./cs32-test tests/student/maps/maps1/*test -t 60” can be run

How to Build/Run Our Program

To build our program, the command “mvn package” can be run in the project directory. To run the program, the command “./run” can be executed. From here, our REPL will begin and all Stars and Maps commands will be able to run.

Answers to Maps2 Testing Questions

To test the Dijkstra and A* algorithms, PBT could be used since there is often more than one shortest path from one node to another. One key property to test for is the total edge weight of the shortest path. If the total edge weight of two shortest paths is the same, it does not matter which paths were found. Some other properties a PBT could check is whether the start and end nodes are correct, or whether all the nodes in the solution are found within the graph.

Late Days

We used two late days for this assignment. Please remove one late day from each partner.
