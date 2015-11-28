# Environment Variables in Path Variables
An IntelliJ IDEA plugin to automatically add environment variables to [Path Variables].

Environment variable are added to Path Variables with "env." prefix to avoid collisions.

## Getting Started

1. Clone the repository
2. Open Intellij IDEA, "Create New Project" -> choose "IntelliJ Platform Plugin"
3. The next page in "Project Location", Choose the cloned repository location.

T run (will open a new intellij idea window with the plugin running)

    Run Menu -> Run

To deploy

    Build Menu -> Prepare Plugin Module

Then copy the output jar to USERHOME/.IdeaIC14/config/plugins

## Plugin Usage

To reload the environment variables loaded:
Tools -> Reload Environment Variables in Path Variables.

Not all environment variables are added: 
Intellij IDEA automatically checks for any thing it will save from the project data that has a matched
value in Path Variables, and if it found one, it will replace the value with the Path Variable name
automatically. Since some environment variables may be a number or a name as a result Intellij may
wrongly use this Path Variable instead of the actual number.

For example, when adding all the environment variables and inspecting a project's iml file, this was found

&lt;module type=&quot;PLUGIN_MODULE&quot; version=&quot;$env.NUMBER_OF_PROCESSORS$&quot;&gt;

while it should have been

&lt;module type=&quot;PLUGIN_MODULE&quot; version=&quot;4&quot;&gt;

Intellij has found that there's a Path Variable $env.NUMBER_OF_PROCESSORS$ that has the value "4", so it
automatically replaced the "version" value with the variable's name.

To overcome this issue, the value of the environment variable is checked against a regex that is modifiable at
USERHOME/.IdeaIC14/config/options/options.xml at property with name "envVarsInPathVars.regexFilter". There
are currently no gui to change this setting.

The default regex filter is ".*_HOME" because it became a convention for the environment variable name of
a library or a software installation path.
        
[Path Variables]:https://www.jetbrains.com/idea/help/path-variables.html

## License
MIT