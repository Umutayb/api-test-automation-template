## API Test Automation With Retrofit

In this feature i demonstrate my api test automation design, combining **retrofit2, OkHttp3 & BDD**. I have implemented
**26 api requests**, representing various interactions with Gitlab, including creating a project, creating an issue in a
given project, print all issues in a project, update issues in a project, clone issue in a project, delete issue in a
project smf acquiring issues filtered by author, assignee, state, title, description & labels.
The project also has built in slack integration, built in the same design with the rest of the project.

To counter the amount of content in the feature file (and considering my tight schedule) i have implemented various
BDD steps, but did not compose scenarios with them. I believe the design speaks for itself and composing test multiple
scenarios & steps will not prove much, as all the utility is already implemented.

### Please see:

    src/main/java/gitlab/GitlabServices.java
    src/main/java/gitlab/Gitlab.java 
    src/test/java/steps/IssuesSteps.java

### Running:
Running the tests are done by combining feature and scenario tags.

``` shell
mvn -B clean test -q -Dcucumber.filter.tags="@GitlabIssues and @IssueCreation"
```

Please remember to clone the repository, create a gitlab token and update the gitlab-token property 
within test.properties (src/test/resources/test.properties) file. Add the id of your newly created 
project to the Project.java enum & Finally update the scenario with the name of your new enum.
