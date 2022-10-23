# The I'm In project

A system used to organize and search events and attraction participants.

RU: Im-in система для организации и поиска мероприятий и привлечения участников. 

Briefly this web application is similar to meetup.com but much better. Some users state that they are going to host events. 
The event has a number of attributes that help interested people to find it: 
- Description or name
- Set of tags that define the nature of the event, what is actually going to happen there. For instance `yoga workout`, 
`group bike ride`, `pipe smokers meet up`, `BDSM party`, `online language lesson` etc.
- Free slots amount
- Date and time
- Place (address/geo point/web address)
- Price
- etc

Others would like to spend time doing actions they enjoy, together with other people. This is what they use the 
application for. They set parameters in filters, and the application searches events matching the parameters. If results 
found the user can claim that they will take part in some of them, which lets the organizers and others know how many 
people are going to come and how many empty slots left.


Dependencies:
- Java 13
- Docker
- Maven
- Spring boot 2.6
- Spring security
- Spring Data JPA
- Rest Templates
- Spring Batch
- Spring Actuator
- JUnit 5
- PostGIS
- H2GIS
- HERE Geocoding & Search API
