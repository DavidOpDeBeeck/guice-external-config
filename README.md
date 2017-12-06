# Guice external config

A simple guice module library to import external configuration files as java objects.

## Getting started

1. Register the module with the Guice injector.

```java
// Scan all packages for classes annotated with @Configuration
Injector injector = createInjector(new ExternalConfigModule())
// Scan only 'com.company' for classes annotated with @Configuration
Injector injector = createInjector(new ExternalConfigModule("com.company"))
```

2. Create a json file with your configuration.

```json
{
  "applicationName": "<string>",
  "listOfStrings": ["<string>", "<string>"],
  "complexObject": {
    "stringField" : "<string>"
  },
  "listOfComplexObjects": [{
      "stringField" : "<string>"
  }]
}
```

3. Create a java configuration class annotated with `@Configuration`.
    - **location** - the relative path to the json file
    - **external** - if the configuration file is inside (`false`) or outside (`true`) the jar

**Note:** Configuration classes need an empty constructor (can be private or an implicit public one) and need a getter for each configuration property.

**Note:** Configuration properties that are present in the json file but not in the java class will simply not be mapped (the application will not fail).

```java
@Configuration(location = "application.json", external = true)
public class ApplicationConfig {

    @SuppressWarnings("unused")
    private String applicationName;
    @SuppressWarnings("unused")
    private List<String> listOfStrings;
    @SuppressWarnings("unused")
    private ComplexObject complexObject;
    @SuppressWarnings("unused")
    private List<ComplexObject> listOfComplexObjects;

    public String getApplicationName() {
        return applicationName;
    }

    public List<String> getListOfStrings() {
        return listOfStrings;
    }

    public ComplexObject getComplexObject() {
        return complexObject;
    }

    public List<ComplexObject> getListOfComplexObjects() {
        return listOfComplexObjects;
    }
}
```

```java
public class ComplexObject {

    private String stringField;

    public String getStringField() {
        return stringField;
    }
}
```