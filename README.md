# concurrentserver
a simple concurrent server that logs inputs and reports to standard output

### Usage
`./gradlew clean build run`

### Function
Connect a client socket to port 4000. Server accepts input as a nine-digit number followed by a line feed (`\n`). Input can also be `terminate`, which cleanly shuts down the server for all clients. Any other input closes the connection for that client.

All valid number inputs are logged in `numbers.log`, except duplicates.

The server reports new inputs every ten seconds to standard output.

