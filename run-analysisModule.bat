IF defined JAVA_HOME_8 (
   set JAVA_HOME=%JAVA_HOME_8%
)


mvn clean verify -Dcucumber.options="--tags @AnalysisModule"