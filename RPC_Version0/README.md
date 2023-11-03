## Issue 1- Connection Refused

- Must run Server before the client
- Because client to requesting connection to server, if client found the ip + port doesn't exist, an error occurs

## Issue 2 - Deadlock

- MUST ensure that the ObjectOutputStream is initialized before the ObjectInputStream in both the client and server to avoid deadlocks
---
- **Stream Headers**
  - When an ObjectOutputStream is created, it writes a header (serialization stream magic number and version) to the underlying stream during the construction.
  - Conversely, when an ObjectInputStream is created, it expects to read this header to validate that the incoming data is a valid serialization stream.
- **Deadlock Scenario**
  - If both the client and server create their ObjectInputStream first, they will both be blocked waiting to read the header written by the opposing side's ObjectOutputStream.
  - However, neither side can write this header because they are both waiting for the other to send it, leading to a deadlock situation where both are stuck waiting indefinitely.
