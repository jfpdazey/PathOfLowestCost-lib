#Path Of Lowest Cost (Library)
This is a standalone Java library created to support the Path Of Lowest Cost kata. See below for more details on the kata.

##Build/test
The library uses Gradle.  After cloning, you can run various tasks:
* `./gradlew test` to test
* `./gradlew build` to build
* `./gradlew jar` to archive the library

Of course, you can always import it into your editor of choice...

##Integration
If this library requires updating or will be copied into another project: after archiving the library per the instructions above, copy it from the `build/libs` directory into the destination.

For the Android project, the destination will be the `libs` directory.

##The kata
###The Challenge
The objective is to find the path of lowest cost when moving across a grid. For this challenge, you are provided a grid of integers where each integer represents the amount of cost encountered at a given point on the grid. A path enters the grid from the left (at any point) and passes through the grid to the right, moving only one column per round. Movement is always to the same row or an adjacent row, meaning the path can proceed horizontally or diagonally. For the sake of this challenge, we assume the first and last row are also adjacent. Effectively, the grid “wraps”.

The total cost of a path is the sum of the integers in each of the visited cells. The solution needs to handle grids of various sizes with a minimum of 1 row and 5 columns up to 10 rows and 100 columns. If in the next move, the total cost will exceed 50, that path is abandoned.

The purpose of this challenge is to find the path of least cost (that is, the path with the lowest total cost of any possible path). The paths of least cost through two slightly different 5 x 6 grids are shown below. The grid values differ only in the bottom row. The path for the grid on the right takes advantage of the adjacency between the first and last rows.

###Input
The input consists of a sequence of row specifications. Each row is represented by a series of delimited integers on a single line. Note: integers are not restricted to being positive.

###Output
Three lines should be output for each matrix specification. The first line is either “Yes” or “No” to indicate the path made it all the way through the grid. The second line is the total cost. The third line shows the path taken as a sequence of n delimited integers, each representing the rows traversed in turn. If there is more than one path of least cost, only one path need be shown in the solution.