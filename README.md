# Alert Manager Module
The module main functionality is to: 
* Notify users/systems about any alert fired in the RAFM System
* Keep the history of all alert events.
* Expose an API to
    * Associate specific alert type with an action
    * Navigate and discover the alert history
    * Mark an alert as acknowledged

### <ins>Description :</ins>
The main responsibilities of this module is to capture alert events from a kafka topic and react on them,
the reactions to those alerts may be by `Sending Mail` or `SMS`, or broadcasting the alert to `snmp trap`
or calling a rest api by defining a `custom webhook`.

This module also should persist all the alerts to a database `(Postgresql)` and will have the capability to _view_, 
_filter_ and _acknowledge_ them.

Each one of the received alert will have a `type`, `timestamp`, `description` and a `context` to hold the alert data.

The module should have the capability of rendering emails and messages `templates` based on the alert context,
so we can send informative notifications.

The diagram below contains the proposed topology for alert processing :
 
![Alert Processing Pipeline](assets/alert-manager.svg)

### <ins>Action Types: </ins> 

#### Send email
The system user should be able to 
* Specify a list of people (emails) to be notified by mail when ever `specific` alert type fires.
* Specify the email subject.

The system should 
* Provide generic informative email template to generate the email body.
 

#### Send SMS
The system user should be able to 
* Specify a list of people (mobile numbers) to be notified by mail when ever a `specific` alert type fires.

The system should 
* Provide generic informative message template to generate the message body.

#### Call Webhook
The system user should be able to 
* Specify a custom HTTP end point to be called when ever a `specific` alert type fires

### <u>Choosing the right action for the alert</u>
In this module we are going to associate each alert type to a specific action. 
We can represent this association with the following data model :

| Field | Type  | Description
|---|---|---|
| alertType | String | containing alert type e.g MOU Recon Hourly alert|
| action | Enum | e.g SendMail, SendSMS, BroadCastSNMP, Webhook, NOOP |
| config | JSON | contains action configuration | 

The config field will have properties based on the action as following: 

* Send Mail
    - to : list of emails
    - subject: the subject of the mail
* Send SMS 
    - to : list of mobile numbers
* Webhook
    - url 
    - method : POST, GET, PUT, DELETE
    - headers : Dictionary
    - queryParams: Dictionary

### <u>Alert store</u>
The alert store should store all the alert events regardless the action associated with the alert.
the stored alert should
 
| Field | Type | Description |
|---|---|---|
| id | UUID | Alert Identifier|
| alertType | String | |
| context | JSON | |
| on | Timestamp | alert timestamp|
| performedAction | Enum | |
| actionConfig| JSON | | 

### <u>Rest API </u>

#### Set an action for an alert type End Point:
URI : /api/actions <br>
METHOD : POST <br>
Body : JSON with the following fields:

| Field | Data type | Description |
|---|---|---|
| alertType| String | the alert type to associate the action with |
| action | Enum | (SendMail, Webhook , Send SMS)
| config | JSON | see [config](#uchoosing-the-right-action-for-the-alertu)

#### List alerts End Point:
URI : /api/alerts <br>
METHOD : GET <br>

QueryParams:

| Name | Type | Description |
|---|---|---|
| before | DateTime | filter the alert before the specified param(ISO format)| 
| after | DateTime | filter the alert after the specified param(ISO format)|
| type | List | comma separated list of alert types


Response Body: 

JSON Array with items containing the following fields:

| Field | Data type | Description |
|---|---|---|
| id |UUID|---|
| alertType| String | the alert type to associate the action with |
| on | Timestamp | the alert timestamp
| context | JSON ||
| actionPerformed | Enum ||
| actionConfig| JSON | |

#### Acknowledge Alerts Endpoint
URI : /api/alerts <br>
METHOD : POST<br>
Body: json array Of alert ids to acknowledge e.g `["333sd-3323d-3223d-xsdsf","333sd-3323d-3223d-xsdst"]`

### <u>Technologies to use: </u> 
The recommendation to implementation for the module is to use Spring Boot2 with the following advices:
* Use [Kafka Streams](https://kafka.apache.org/documentation/streams/) with [Spring for apache kafka](https://docs.spring.io/spring-kafka/docs/current/reference/html/#streams-kafka-streams) to consume messages from alert topic.
* Use [Command Pattern](https://en.wikipedia.org/wiki/Command_pattern) for action invocation, so we can add more action types in the future.
* Use [Factory Method pattern](https://en.wikipedia.org/wiki/Factory_method_pattern) to build the suitable action.
* Use [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface) as persistence layer to save and query the alerts.
* Use [Spring Web](https://spring.io/guides/gs/rest-service/) to expose the rest API.

