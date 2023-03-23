HelpDesk
---
---
#### _Technologies:_
- Java 11
- Spring Boot (MVC, Security, REST)
- Spring DATA
- Hibernate + h2
- JUnit (Mockito)
- Lombok
- Thymeleaf
- Security: JWT-token
- Front-end: Angular 15.1.6, rxjs 7.8.0, typescript 4.9.5
---
#### _Logins and passwords of users:_
| Role | Login | Password |
| :------: | :------: | :------: |
| Employee | e1@yopmail.com | 123Aa* |
| Employee | e2@yopmail.com | 123Bb*|
| Manager | m1@yopmail.com | Mm123* |
| Manager | m2@yopmail.com | Mm456* |
| Engineer | a1@yopmail.com | Ee123* |
| Engineer | a2@yopmail.com | Ee456* |
---
#### _Project start:_
To start application run the following command from your terminal:
```sh
java -jar helpdesk-0.1.jar
```
Client-Page start:
```sh
http://localhost:4200
```
Server-Page start:
```sh
http://localhost:8080
```
---
---
## _Server requests:_
##### 1. User Authentication:
Login:
```sh
POST http://localhost:8080/login
```
Logout:
```sh
POST http://localhost:8080/exit
```
##### 2. Entity requests:
**Ticket:**

Create ticket:
```sh
POST http://localhost:8080/tickets
```
- **RequestBody** - `TicketDto`

Get ticket by Id:
```sh
GET http://localhost:8080/tickets/{id}
```
Get current user tickets:
```sh
GET http://localhost:8080/tickets?
```
- **RequestParam**:
> `flag` - if present: return all tickets related to the current user,
if not present: return own tickets related to the current user;
`pageNo` - return the list starting from the page number PageNo (optional);
`sortBy` - the attribute by which the list is sorted (optional);
`orderBy` - sort order: asc or desc (optional);
`filter_id` - filter by _id_ (optional);
`filter_name` - filter by _name_ (optional);
`filter_date` - filter by _desired_ (optional);
`filter_urgency` - filter by _urgency_ (optional);
`filter_state` - filter by _state_ (optional);

Update ticket by id:
```sh
PUT http://localhost:8080/tickets/{id}
```
- **RequestBody**
  `TicketDto` - if present: update ticket fields;
- **RequestParam**:
  `action` - if present: ticket status change.

**History:**

Get history by ticket:
```sh
GET http://localhost:8080/histories?ticketId=
```
- **RequestParam**:
  `pageNo` - return the list starting from the page number PageNo (optional).

**Comment:**

Create comment by ticket:
```sh
POST http://localhost:8080/comments?ticketId=
```
- **RequestBody** - `CommentDto`.

Get all comments by ticket:
```sh
GET http://localhost:8080/comments?ticketId=
```
- **RequestParam**:
  `pageNo` - return the list starting from the page number PageNo (optional).

Update comment:
```sh
PUT http://localhost:8080/comments/{id}
```
- **RequestBody** - `CommentDto`.

Delete comment:
```sh
DELETE http://localhost:8080/comments/{id}
```

**Attachment:**

Save file by ticket:
```sh
POST http://localhost:8080/files?ticketId=
```
- **RequestParam**:
  `files` - add MultipartFile[];

Get file by id:
```sh
GET http://localhost:8080/files/{id}
```
- **RequestParam**:
  `file` - add token.

Get File list by ticketId:
```sh
GET http://localhost:8080/files?ticketId
```
Delete file:
```sh
DELETE http://localhost:8080/files/{id}
```

**Feedback:**

Create feedback by ticket:
```sh
POST http://localhost:8080/feedbacks?ticketId=
```
- **RequestBody** - `FeedbackDto`.

Get feedback by ticketId:
```sh
GET http://localhost:8080/feedbacks?ticketId
```

**Urgency:**

Get all urgencies:
```sh
GET http://localhost:8080/urgencies
```

**Category:**

Get all categories:
```sh
GET http://localhost:8080/categories
```

