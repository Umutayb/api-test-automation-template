## API Test Automation With Retrofit

This project demonstrates my api test automation design, combining **retrofit2, OkHttp3 & BDD**. 

There are **26 api requests** that were implemented, representing various interactions with Gitlab, including creating a project, creating an issue in a
given project, print all issues in a project, update issues in a project, clone issue in a project, delete issue in a
project smf acquiring issues filtered by author, assignee, state, title, description & labels.
The project also has built in slack integration, built in the same design with the rest of the project.

### Running:
Running the tests are done by combining feature and scenario tags.

``` shell
mvn -B clean test -q -Dcucumber.filter.tags="@GitlabIssues and @IssueCreation"
```

Please remember to clone the repository, create a gitlab token and update the gitlab-token property 
within test.properties (src/test/resources/test.properties) file. Add the id of your newly created 
project to the Project.java enum & Finally update the scenario with the name of your new enum.
