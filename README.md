## ProjectReport
ProjectReport uses Gradle Enterprise API generating a report of the builds by user, project and task of a Gradle Enterprise
instance.

```
./projectreport --api-key=$GE_KEY --url=$GE_URL --max-builds=5000
```

#### Using Binary

Github release latest version contains the Project Report binary. After downloading the binary you can execute:
```
 curl -L https://github.com/cdsap/ProjectReport/releases/download/v.0.4.3/projectreport --output projectreport
 chmod 0757 projectreport
./projectreport --api-key=$KEY --url=$URL --max-builds=1000
```
Where:
* `api-key` represents the Gradle Enterprise API token
* `url` Gradle Enterprise instance

##### You can build from sources using this repository:
```
./gradlew install
cd build/install/projectreport/bin
./projectreport --api-key=$KEY --url=$URL --max-builds=20000
```

##### Output
###### Report
```
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                            Top 10 - Builds by project in https://instance-ge.com                                 │
├───────────────┬────────┬──────┬───────┬────────────┬─────────┬───────────┬───────────┬─────────────┬─────────────┤
│ Project       │ Builds │  CI  │ Local │    Mean    │   P25   │    P50    │    P75    │     P90     │     P99     │
├───────────────┼────────┼──────┼───────┼────────────┼─────────┼───────────┼───────────┼─────────────┼─────────────┤
│ gissele       │      1 │    0 │     1 │     1.188s │  1.188s │    1.188s │    1.188s │      1.188s │      1.188s │
├───────────────┼────────┼──────┼───────┼────────────┼─────────┼───────────┼───────────┼─────────────┼─────────────┤
│ leo           │      1 │    0 │     0 │     4.554s │  4.554s │    4.554s │    4.554s │      4.554s │      4.554s │
├───────────────┼────────┼──────┼───────┼────────────┼─────────┼───────────┼───────────┼─────────────┼─────────────┤
│ megan         │      2 │    0 │     0 │     5.452s │   638ms │    5.452s │   10.267s │     10.267s │     10.267s │
├───────────────┼────────┼──────┼───────┼────────────┼─────────┼───────────┼───────────┼─────────────┼─────────────┤
│ andrew        │      6 │    0 │     6 │    22.945s │  3.779s │    9.825s │   41.228s │  1m 26.662s │  1m 26.662s │
├───────────────┼────────┼──────┼───────┼────────────┼─────────┼───────────┼───────────┼─────────────┼─────────────┤
│ teamcity      │     12 │   12 │     0 │      5.39s │  4.235s │    4.439s │    6.806s │      8.523s │      8.869s │
├───────────────┼────────┼──────┼───────┼────────────┼─────────┼───────────┼───────────┼─────────────┼─────────────┤
│ github        │     16 │   16 │     0 │ 2m 28.215s │ 10.909s │ 2m 6.793s │ 5m 8.024s │  5m 21.341s │  5m 27.079s │
├───────────────┼────────┼──────┼───────┼────────────┼─────────┼───────────┼───────────┼─────────────┼─────────────┤
│ tom           │    241 │    0 │   220 │       6.9s │   414ms │     669ms │    7.032s │     11.795s │    1m 0.07s │
├───────────────┼────────┼──────┼───────┼────────────┼─────────┼───────────┼───────────┼─────────────┼─────────────┤
│ root          │    671 │   12 │   425 │    12.757s │  1.821s │    3.899s │    9.497s │     32.029s │  2m 45.437s │
├───────────────┼────────┼──────┼───────┼────────────┼─────────┼───────────┼───────────┼─────────────┼─────────────┤
│ runner        │   3232 │ 2191 │     0 │    32.153s │  1.507s │    4.098s │    8.431s │     41.595s │ 20m 33.015s │
└───────────────┴────────┴──────┴───────┴────────────┴─────────┴───────────┴───────────┴─────────────┴─────────────┘

┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                        Top 10 - Builds by project in https://instance-ge.com                                                     │
├────────────────────────────────────────┬────────┬─────┬───────┬─────────────┬────────────┬─────────────┬─────────────┬─────────────┬─────────────┤
│ Project                                │ Builds │ CI  │ Local │    Mean     │    P25     │     P50     │     P75     │     P90     │     P99     │
├────────────────────────────────────────┼────────┼─────┼───────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┼─────────────┤
│ kotlin                                 │      5 │   0 │     0 │       491ms │      322ms │       365ms │       724ms │       914ms │       914ms │
├────────────────────────────────────────┼────────┼─────┼───────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┼─────────────┤
│ mvp-controller                         │      6 │   0 │     6 │     42.721s │     1.958s │     13.498s │  1m 18.571s │   3m 1.549s │   3m 1.549s │
├────────────────────────────────────────┼────────┼─────┼───────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┼─────────────┤
│ example_compose                        │      7 │   1 │     6 │     15.327s │      568ms │      7.966s │     25.839s │     53.603s │     53.603s │
├────────────────────────────────────────┼────────┼─────┼───────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┼─────────────┤
│ android_app_vendor                     │     10 │   0 │    10 │      5.376s │     1.041s │      2.491s │       7.41s │     22.312s │     23.298s │
├────────────────────────────────────────┼────────┼─────┼───────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┼─────────────┤
│ backend_vendor                         │     12 │   0 │    12 │     15.276s │     2.345s │      6.345s │     33.517s │     45.123s │     47.296s │
├────────────────────────────────────────┼────────┼─────┼───────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┼─────────────┤
│ android_project                        │     15 │   7 │     8 │     11.272s │     1.023s │      4.598s │     18.555s │     36.578s │     38.153s │
├────────────────────────────────────────┼────────┼─────┼───────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┼─────────────┤
│ maven_example                          │     15 │   0 │    15 │     42.653s │    19.672s │     57.999s │  1m 10.269s │  1m 15.752s │  1m 18.203s │
├────────────────────────────────────────┼────────┼─────┼───────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┼─────────────┤
│ java_vs_kotlin_benchmarks              │     18 │  13 │     5 │ 12m 45.891s │    28.877s │ 10m 47.016s │ 23m 49.873s │  32m 4.701s │ 32m 47.525s │
├────────────────────────────────────────┼────────┼─────┼───────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┼─────────────┤
│ mercury                                │     51 │   3 │    48 │   1m 2.451s │     6.667s │     22.253s │  1m 47.507s │   2m 4.964s │  8m 28.657s │
├────────────────────────────────────────┼────────┼─────┼───────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┼─────────────┤
│ okhttp_example                         │    303 │ 303 │     0 │  7m 45.605s │ 2m 32.937s │  9m 18.045s │  10m 53.54s │ 12m 22.885s │  17m 8.165s │
└────────────────────────────────────────┴────────┴─────┴───────┴─────────────┴────────────┴─────────────┴─────────────┴─────────────┴─────────────┘
┌─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                                             Top 10 - Builds by task in https://instance-ge.com                                                                          │
├───────────────────────────────────────┬───────────────────────────────────────┬────────┬─────┬───────┬─────────────┬─────────────┬────────────┬─────────────┬─────────────┬─────────────┤
│ Task                                  │                Project                │ Builds │ CI  │ Local │    Mean     │     P25     │    P50     │     P75     │     P90     │     P99     │
├───────────────────────────────────────┼───────────────────────────────────────┼────────┼─────┼───────┼─────────────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┤
│ clean build                           │ backend_vendor                        │      7 │   0 │     7 │     49.605s │      8.339s │    35.244s │     47.296s │   3m 1.549s │   3m 1.549s │
├───────────────────────────────────────┼───────────────────────────────────────┼────────┼─────┼───────┼─────────────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┤
│ build                                 │ example_compose                       │     10 │   5 │     1 │   2m 3.934s │       486ms │     2.974s │      9.908s │  18m 0.628s │ 19m 57.827s │
├───────────────────────────────────────┼───────────────────────────────────────┼────────┼─────┼───────┼─────────────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┤
│ clean package                         │ mercury                               │     12 │   0 │    12 │     52.011s │     24.464s │    59.518s │  1m 11.685s │  1m 16.977s │  1m 18.203s │
├───────────────────────────────────────┼───────────────────────────────────────┼────────┼─────┼───────┼─────────────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┤
│ testDebugUnitTest                     │ android_project                       │     20 │  20 │     0 │  5m 36.565s │  4m 45.064s │ 5m 12.645s │  6m 15.684s │   8m 4.511s │  8m 31.169s │
├───────────────────────────────────────┼───────────────────────────────────────┼────────┼─────┼───────┼─────────────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┤
│ test                                  │ android_project                       │     22 │  21 │     1 │ 13m 18.469s │ 12m 22.036s │ 14m 0.918s │ 15m 18.322s │ 17m 50.667s │ 19m 15.568s │
├───────────────────────────────────────┼───────────────────────────────────────┼────────┼─────┼───────┼─────────────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┤
│                                       │ backend_vendor                        │     28 │   0 │    28 │      29.33s │      2.577s │    13.474s │     33.195s │   2m 6.499s │  2m 57.543s │
├───────────────────────────────────────┼───────────────────────────────────────┼────────┼─────┼───────┼─────────────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┤
│ clean :intellij-plugin:instrumentCode │ example_compose                       │     41 │   3 │    38 │   1m 4.736s │      6.829s │    23.738s │  1m 46.298s │   2m 1.642s │  8m 28.657s │
├───────────────────────────────────────┼───────────────────────────────────────┼────────┼─────┼───────┼─────────────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┤
│ clean                                 │ android_project                       │     41 │  41 │     0 │      2.437s │      1.964s │     2.361s │      2.695s │      3.228s │      4.705s │
├───────────────────────────────────────┼───────────────────────────────────────┼────────┼─────┼───────┼─────────────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┤
│ :app:assembleProdDebug                │ android_project                       │     64 │  64 │     0 │  9m 21.541s │  8m 24.199s │ 9m 15.106s │  9m 49.276s │ 10m 54.675s │ 11m 51.258s │
├───────────────────────────────────────┼───────────────────────────────────────┼────────┼─────┼───────┼─────────────┼─────────────┼────────────┼─────────────┼─────────────┼─────────────┤
│ :app:assembleProdRelease              │ android_project                       │    158 │ 158 │     0 │  8m 32.512s │  2m 59.784s │  10m 5.49s │ 11m 28.141s │ 12m 16.328s │ 14m 23.128s │
└───────────────────────────────────────┴───────────────────────────────────────┴────────┴─────┴───────┴─────────────┴─────────────┴────────────┴─────────────┴─────────────┴─────────────┘

```
###### CSV
Complete list of results by entity:
* project-report-projects-$timestamp.csv
* project-report-tasks-$timestamp.csv
* project-report-users-$timestamp.csv

Example:
```
user,project,builds,ci,local,mean,p25,p50,p75,p90,p99
testAndroid8_1_0,android-cache-fix-gradle-plugin,1,1,0,32m 47.525s,32m 47.525s,32m 47.525s,32m 47.525s,32m 47.525s,32m 47.525s
testAndroid8_2_0_alpha14,android-cache-fix-gradle-plugin,1,1,0,19m 14.588s,19m 14.588s,19m 14.588s,19m 14.588s,19m 14.588s,19m 14.588s
clean :integration-tests:check,spring,1,0,1,24s,24s,24s,24s,24s,24s
```

###### Json
Using the argument `file-json-output` the information will be stored additionally in different files with Json format:
* project-report-projects-$timestamp.json
* project-report-tasks-$timestamp.json
* project-report-users-$timestamp.json

#### Parameters

| Name             | Description                                                                               | Default | Required | Example                         |
|------------------|-------------------------------------------------------------------------------------------|---------|----------|---------------------------------|
| api-key          | String token                                                                              |         | Yes      | --api-key=$GE_KEY               |
| url              | Gradle Enterprise instance                                                                |         | Yes      | --url=https://ge.acme.dev       |
| max-builds       | Max builds to be processed                                                                | 1000    | No       | --max-builds=2000               |
| project          | Root project in Gradle Enterprise                                                         |         | No       | --project=acme                  |
| tags             | One or more tags included in the build.                                                   |         | No       | --tags=main --tags=not:local    |
| user             | Author of the build                                                                       |         | No       | --user=leo                      |
| since-build-id   | Instead getting the last n builds, we can define the starting point to request the builds |         | No       | --since-build-id=3a234rhlfrycbs |
| file-json-output | Generates Json files for each type of metrics                                             | false   | No       | --file-json-output              |


#### Examples
##### Filtering by project
```
./projectreport --api-key=$GE_KEY --url=$GE_URL --max-builds=5000 --project=acme
```
##### Set different starting point
```
./projectreport --api-key=$GE_KEY --url=$GE_URL --max-builds=5000 --since-build-id=3a234rhlfrycbs
```

#### Compatibility
We have tested the tool with Java 8, 11 and 17.
