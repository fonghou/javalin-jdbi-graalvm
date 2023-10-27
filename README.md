# Javalin + GraalVM
### This is a demo project with GraalVM support for building a native image of the project.

First, I will like to say that I achieve this by following the tutorial from the from [GraalVM](https://graalvm.github.io/native-build-tools/latest/maven-plugin-quickstart.html) and also reviewed some sample [projects](https://github.com/graalvm/native-build-tools/tree/master/samples) that they pushed out to help developers like us.

### STEPS:

1. Ensure you have *GraalVM* installed on your local computer. For me, I use a Mac and was able to install it using SDKMAN. [SDKMAN](https://sdkman.io/) is a useful tool for easy management of JDKs. 

`curl -s "https://get.sdkman.io" | bash`

2. Install GraalVM JDK:

`sdk install java 17.0.9-graal && sdk use java 17.0.9-graal`

At the point of writing this, I installed GraalVM support for java17. You can read the SDKMAN [documentation](https://sdkman.io/usage#listversions) to find more information on how you can install a specific version.

3. Now that you have GraalVM installed, you are ready to start tinkering.

- Clone the project

`git clone https://github.com/<project.git>`

4. CD into the project folder and compile the project to create a runnable JAR with all dependencies. Run in your terminal:

`mvn clean package`

5. Run your application with the agent enabled:

`mvn -Pnative -Dagent exec:exec@java-agent`

The agent collects the metadata and generates the configuration files in a subdirectory of `target/native/agent-output`. Those files will be automatically used by the `native-image` tool if you pass the appropriate options.

6. Now build a native executable with the Maven profile:

`mvn -DskipTests=true -Pnative -Dagent package`

When the command completes, a native executable, `javalin-native`, is created in the `/target` directory of the project and ready for use.

7. Run the executable directly or with the Maven profile:

`./target/javalin-native` | `mvn -Pnative exec:exec@native`

### Future:
- Run tests on building native executable file

### NB:
Kindly create a PR for any error fixes, improvements and updates.