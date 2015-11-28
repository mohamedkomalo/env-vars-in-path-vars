package com.github.mohamedkomalo.envVarsInPathVars;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class ReloadEnvironmentVariablesAction extends AnAction {

    @java.lang.Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        EnvironmentVariablesOnStartupRegistrar.reloadEnvironmentVariables();
    }
}