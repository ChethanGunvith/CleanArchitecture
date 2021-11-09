
This sample respresents Clear Architecture(Three layers - Domain, Data, App layers with MVVM)

its main goal is the separation concerns by dividing software into layers , main idea is to separate code into different layers so that each layers will
mind its own business. 

Clean Architecture maximizes the use of SOLID  principles.
	Separation of Concerns 
	Loose coupling
	Easily Testable


Three layers 
Domain
Data
App(Presentation), 



### Presentation layer : 
It is mainly concerns with what user sees , drawing things, animations , in the most part its not going to handle a lot of thinking and its actually just going to pass that job on to the logic layer 
 
 This layer is consisting of
 - View , View model 
 
### Domain (or Logic) layer : 
logic layer is the first layer that the presentation layer communicate ,Here we put the logic of business: convert, filter, mix and sort raw data that comes from Data layer to be ready and easy to handle in Presentation layer.

 This layer is consisting of
 - Repository class - Single source of truth, most of logic will be hold here
 
### Data Layer
Here we put the logic of bringing data either from local source or server.
persistence layer 
- Entity, data base, retrofit interfaces
