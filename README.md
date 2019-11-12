# concurrentserver
a simple concurrent server that logs inputs and reports to standard output

### Usage
`./gradlew clean build run`

### Function
Connect a client socket to port 4000. Server accepts input as a nine-digit number followed by a line feed (`\n`). Input can also be `terminate`, which cleanly shuts down the server for all clients. Any other input closes the connection for that client.

All valid number inputs are logged in `numbers.log`, except duplicates.

The server reports new inputs every ten seconds to standard output.

### Design Decisions
The server reads inputs from a buffer and only logs unique input numbers. To identify duplicates, there are two options:
* Bloom filter: To limit memory usage, unique numbers are inserted into a bloom filter before being logged. The tradeoff with using less memory is that we occasionally have to read the log file to find duplicates, but in a use case where duplicates are rare, this does not impact performance. In the case where duplicates are common, this limits performance.
* Hold all inputed numbers in memory: This uses more memory (only up to a gig since there are 1 billion nine-digit numbers), but is much faster in the case where many duplicates are inserted since it never has to read the log file.

The source contains handlers for both of these cases.
