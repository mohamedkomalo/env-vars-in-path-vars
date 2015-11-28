package com.github.mohamedkomalo.envVarsInPathVars;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.PathMacros;
import com.intellij.openapi.components.ApplicationComponent;

import java.util.regex.Pattern;

/**
 * Created by Mohamed Kamal on 11/26/2015.
 */
public class EnvironmentVariablesOnStartupRegistrar extends ApplicationComponent.Adapter {

    private static final String ENV_PREFIX = "env.";
    private static final String REGEX_FILTER_KEY = "envVarsInPathVars.regexFilter";
    public static final String DEFAULT_REGEX_FILTER = ".*_HOME";

    @Override
    public void initComponent() {
        reloadEnvironmentVariables();
    }

    public static void reloadEnvironmentVariables() {
        PathMacros.getInstance().getUserMacroNames().forEach(name -> {
            if (name.startsWith(ENV_PREFIX)) {
                PathMacros.getInstance().removeMacro(name);
            }
        });

        /*
        Intellij IDEA automatically checks for any thing it will save from the project data that has a matched
        value in Path Variables, and if it found one, it will replace the value with the Path Variable name
        automatically. Since some environment variables may be a number or a name as a result Intellij may
        wrongly use this Path Variable instead of the actual number.

        For example, when inspecting a project's iml file, this was found

        <module type="PLUGIN_MODULE" version="$env.NUMBER_OF_PROCESSORS$">

        while it should have been

        <module type="PLUGIN_MODULE" version="4">

        Intellij has found that there's a Path Variable $env.NUMBER_OF_PROCESSORS$ that has the value "4", so it
        automatically replaced the "version" value with the variable's name

        To overcome this issue, the value of the environment variable is checked against a regex that is modifiable at
        USERHOME/.IdeaIC14/config/options/options.xml at property with name "envVarsInPathVars.regexFilter". There
        are currently no gui to change this setting.

        The default regex filter is ".*_HOME" because it became a convention for the environment variable name of
        a library or a software installation path
         */
        String regexFilterStr = PropertiesComponent.getInstance().getOrInit(REGEX_FILTER_KEY, DEFAULT_REGEX_FILTER);
        Pattern regexFilter = Pattern.compile(regexFilterStr);

        System.getenv().forEach((name, value) -> {
            if (regexFilter.matcher(name).find()) {
                PathMacros.getInstance().setMacro(ENV_PREFIX + name, value);
            }
        });
    }
}